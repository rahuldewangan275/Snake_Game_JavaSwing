import javax.swing.*;
//import java.awt.*;
public class SnakeGame extends JFrame{
    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        pack(); // to set size of frame equal(fit) to panel
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        // Initialize Snake Game
        SnakeGame snakeGame = new SnakeGame();

    }
}