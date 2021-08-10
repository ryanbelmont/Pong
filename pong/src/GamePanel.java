import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 5;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    int DELAY = 60;
    int paddleY = (int)(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE/2;
    int ballX;
    int ballY;
    int score = 0;
    int incSpeed = 0;
    int BallDirectionX = 0;
    int BallDirectionY = 0;
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
            for (int i=0; i<3*UNIT_SIZE; i++)
            {
                for (int z=0; z<3*UNIT_SIZE; z++)
                {
                    g.setColor(Color.white);
                    g.fillOval(ballX + i,ballY+z,UNIT_SIZE,UNIT_SIZE);
                }
            }

            g.setColor(Color.white);
            for (int i =0; i < 12; i++)
            {
                g.fillRect((int)((SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE)-(5*UNIT_SIZE),paddleY + i*UNIT_SIZE,3*UNIT_SIZE,3*UNIT_SIZE);
            }

                // do the wall
            g.fillRect(UNIT_SIZE/2 + 3*UNIT_SIZE, 0, UNIT_SIZE*3, SCREEN_HEIGHT);

            // do top and bottom
            g.fillRect(0, 3*UNIT_SIZE, SCREEN_WIDTH, UNIT_SIZE*3);
            g.fillRect(0, SCREEN_HEIGHT- 6*UNIT_SIZE, SCREEN_WIDTH, UNIT_SIZE*3);


            // show score
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free",Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + score, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + score))/2,UNIT_SIZE*10);

        }
        else
        {
            GameOver(g);
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

    public void GameOver(Graphics g)
    {
        for (int i=0; i<3*UNIT_SIZE; i++)
        {
            for (int z=0; z<3*UNIT_SIZE; z++)
            {
                g.setColor(Color.white);
                g.fillOval(ballX + i,ballY+z,UNIT_SIZE,UNIT_SIZE);
            }
        }

        g.setColor(Color.white);
        for (int i =0; i < 12; i++)
        {
            g.fillRect((int)((SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE)-(5*UNIT_SIZE),paddleY + i*UNIT_SIZE,3*UNIT_SIZE,3*UNIT_SIZE);
        }

        // do the wall
        g.fillRect(UNIT_SIZE/2 + 3*UNIT_SIZE, 0, UNIT_SIZE*3, SCREEN_HEIGHT);

        // do top and bottom
        g.fillRect(0, 3*UNIT_SIZE, SCREEN_WIDTH, UNIT_SIZE*3);
        g.fillRect(0, SCREEN_HEIGHT- 6*UNIT_SIZE, SCREEN_WIDTH, UNIT_SIZE*3);


        // show score
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("SCORE: " + score, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + score))/2,UNIT_SIZE*10);
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
    }

    public void startBall()
    {
        ballX = SCREEN_WIDTH/2;
        ballY = SCREEN_HEIGHT/2;
        BallDirectionX = 1;
        do {
            BallDirectionY = (random.nextInt(3) - 1);
        }
        while (BallDirectionY == 0);

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

    public void ballMove()
    {
        ballX = ballX + (BallDirectionX)*UNIT_SIZE;
        ballY = ballY + (BallDirectionY)*UNIT_SIZE;
    }

    public void checkWall()
    {
        // check top wall
        if(ballY <= 3*UNIT_SIZE + 3*UNIT_SIZE)
        {
            BallDirectionY = - BallDirectionY;
        }

        // check bottom wall
        if(ballY >= SCREEN_HEIGHT - 6*UNIT_SIZE - 3*UNIT_SIZE)
        {
            BallDirectionY = - BallDirectionY;
        }
        // check side wall

        if(ballX < 3*UNIT_SIZE + 3*UNIT_SIZE)
        {
            BallDirectionX = - BallDirectionX;
        }

        // check paddle
        if((ballX == (SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE-(5*UNIT_SIZE) - 3*UNIT_SIZE)
            && (ballY >= paddleY - 3*UNIT_SIZE)
            && (ballY <= paddleY + 13*UNIT_SIZE))
        {
            BallDirectionX = - BallDirectionX;
            score++;
            incSpeed++;
        }
        if(ballX > SCREEN_WIDTH-2*UNIT_SIZE)
        {
            running = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running)
        {
            if(incSpeed % 4 == 3)
            {
                DELAY -= 5;
                timer.setDelay(DELAY);
                incSpeed = 0;
            }
            move();
            ballMove();
            checkWall();
        }
        repaint();
    }
}
