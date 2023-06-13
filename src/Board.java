import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT = 540;
    int B_WIDTH = 720;

    int MAX_DOTS = 3888;
    int DOT_SIZE = 10;
    int DOTS;
    int x[] = new int[MAX_DOTS];
    int y[] = new int[MAX_DOTS];

    int apple_x;
    int apple_y;

    //Images
    Image body, head, apple;
    Timer timer;
    int DELAY = 150; // Value of Delay inversely proportional to Speed of Snake
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setBackground(Color.DARK_GRAY);
        initGame();
        loadImages();
    }

    //Initialize game
    public void initGame() {
        DOTS = 3;
        //Initialize Snake Position
        x[0] = 250;
        y[0] = 250;
        //Initialize Snake's position
        for (int i = 0; i < DOTS; i++) {
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

        //Load Images from resources folder to Image object
        public void loadImages(){
            ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
            body = bodyIcon.getImage();
            ImageIcon headIcon = new ImageIcon("src/resources/head.png");
            head = headIcon.getImage();
            ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
            apple = appleIcon.getImage();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            doDrawing(g);
        }
        //draw image
        public void doDrawing(Graphics g){
            if(inGame) {
                g.drawImage(apple, apple_x, apple_y, this);
                for(int i=0; i<DOTS; i++) {
                    if(i==0) { //means it is the head
                        g.drawImage(head, x[0], y[0], this);
                    }
                    else {
                        g.drawImage(body, x[i], y[i], this);
                    }
                }
            }
            else {
                gameOver(g);
                timer.stop();
            }
        }
        //Randomize apple's position
        public void locateApple() {
            apple_x = ((int)(Math.random()*71))*DOT_SIZE;
            apple_y = ((int)(Math.random()*53))*DOT_SIZE;
        }
        //Check Collision with Body & Border
        public void checkCollision(){
            //Collision with Body
            for(int i=1; i<DOTS; i++) {
                if(i>4 && x[0]==x[i] && y[0]==y[i]) {
                    inGame = false;
                }
            }
            //Collision with Border
            if(x[0]<0 || x[0]>=B_WIDTH || y[0]<0 || y[0]>=B_HEIGHT) {
                inGame = false;
            }
        }
        //Displaying Game Over msg
        public void gameOver(Graphics g) {
            String msg = "Game Over";
            int score = (DOTS-3)*100;
            String scoremsg = "Score: "+Integer.toString(score);
            Font small = new Font("Helvetica", Font.BOLD, 36);
            FontMetrics fontMetrics = getFontMetrics(small);
            g.setColor(Color.WHITE);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH-fontMetrics.stringWidth(msg)) /2, B_HEIGHT/4);
            g.drawString(scoremsg, (B_WIDTH-fontMetrics.stringWidth(scoremsg)) /2, (3*B_HEIGHT)/4);
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
        }

        public void move() {
            for(int i=DOTS-1; i>0; i--){
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
            if(leftDirection) {
                x[0] -= DOT_SIZE;
            }
            if(rightDirection) {
                x[0] += DOT_SIZE;
            }
            if(upDirection) {
                y[0] -= DOT_SIZE;
            }
            if(downDirection) {
                y[0] += DOT_SIZE;
            }
        }
        //Make Snake eat Apple
        public void checkApple(){
            if(apple_x==x[0] && apple_y==y[0]) {
                DOTS++; //size of snake will be increased
                locateApple(); //apple position needs to be randomized again
            }
        }
        //Implement Controls
        private class TAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent keyEvent){
                int key = keyEvent.getKeyCode();
                if((key==KeyEvent.VK_LEFT || key==KeyEvent.VK_A) && !rightDirection) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }
                if((key==KeyEvent.VK_RIGHT || key==KeyEvent.VK_D) && !leftDirection) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }
                if((key==KeyEvent.VK_UP || key==KeyEvent.VK_W) && !downDirection) {
                    leftDirection = false;
                    upDirection = true;
                    rightDirection = false;
                }
                if((key==KeyEvent.VK_DOWN || key==KeyEvent.VK_S) && !upDirection) {
                    leftDirection = false;
                    downDirection = true;
                    rightDirection = false;
                }
            }
        }
}
