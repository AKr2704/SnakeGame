import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame() {
        board = new Board();
        add(board);
        //To adjust the frame to the board size
        pack();  //packs the frame, adjusting parent component to children comp.
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
    }
}