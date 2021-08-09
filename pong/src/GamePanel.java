import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 50;
    int paddleY = (int)(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE/2;
    int ballX;
    int ballY;
    int score = 0;
    int BallDirection = 5;
    char direction = 'N';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        startGame();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void startGame()
    {
       startBall();
       running = true;
       timer = new Timer(DELAY,this);
       timer.start();
    }

    public void draw(Graphics g)
    {
        if(running)
        {
            g.setColor(Color.white);
            g.fillOval(ballX,ballY,UNIT_SIZE,UNIT_SIZE);

            g.setColor(Color.white);
            g.fillRect((int)((SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE)-(2*UNIT_SIZE),paddleY,UNIT_SIZE,UNIT_SIZE);
            g.fillRect((int)((SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE)-(2*UNIT_SIZE),paddleY+UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);
            g.fillRect((int)((SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE)-(2*UNIT_SIZE),paddleY-UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);

                // do the wall
            g.fillRect((int)(SCREEN_WIDTH/UNIT_SIZE), 0, UNIT_SIZE, SCREEN_HEIGHT);

        }
        else
        {
            GameOver();
        }
    }

    public void move()
    {
        switch (direction)
        {
            case 'U':
                if(paddleY-2*UNIT_SIZE > 0) {
                    paddleY = paddleY - UNIT_SIZE;
                }
                break;
            case 'D':
                if (paddleY + 2*UNIT_SIZE < SCREEN_HEIGHT) {
                    paddleY = paddleY + UNIT_SIZE;
                }
                break;
            case 'N':
                break;
        }
    }

    public void GameOver()
    {

    }

    public void startBall()
    {
        ballX = SCREEN_WIDTH/2;
        ballY = SCREEN_HEIGHT/2;
    }

    public class MyKeyAdaptor extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 'D';
                    break;
            }
        }
        public void keyReleased(KeyEvent e)
        {
            direction = 'n';
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running)
        {
            move();
        }
        repaint();
    }
}
