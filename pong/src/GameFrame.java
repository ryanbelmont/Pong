import javax.swing.*;
public class GameFrame extends JFrame{
    GameFrame()
    {
        this.add(new GamePanel());
        this.setTitle("Pong");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
