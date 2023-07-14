import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT =400;
    int B_WIDTH =400;
int MAX_DOTS = 1600;
int DOT_SIZE =10;
int DOTS;
//WE NEED 2 ARRAYS FOR X AND Y CORDINATED
    int x[] = new int [MAX_DOTS];
    int y[] = new int [MAX_DOTS];

    int apple_x;
    int apple_y;

    //Image
    Image body , head , apple;
    Timer timer;
    int DELAY=100;
    boolean leftDirection =true;
    boolean upDirection =false;
    boolean rightDirection =false;
    boolean downDirection =false;

    boolean inGame =true;
Board(){
    TAdapter tAdapter = new TAdapter();
    addKeyListener(tAdapter);
    setFocusable(true);
    setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
    setBackground(Color.black);
    initGame();
    loadImage();

}

        //initialize Game
    public void initGame(){
    DOTS =3;
    // initialize Snake position
        x[0]=200;
        y[0]=50;
        for(int i=0;i<DOTS;i++){
        x[i] = x[0] + DOT_SIZE*i;
        y[i]=y[0];
}

      //initialize the apple's position
//        apple_x =150;
//        apple_y =150;
        locateApple();
        timer = new Timer( DELAY, this);
        timer.start();
    }

    // Load Images from resources Folder to Image Object
    public void loadImage(){
    ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
    body = bodyIcon.getImage();

    ImageIcon headIcon = new ImageIcon("src/resources/head.png");
    head = headIcon.getImage();

    ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
    apple = appleIcon.getImage();
    }

    @Override
    public void paintComponent(Graphics g){
    super.paintComponent(g);
    doDrawing(g);

    }

    // draw image
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }else{
            gameOver(g);
        }
    }
    //Randomize apples's Position
    public void locateApple(){
    apple_x = ((int)(Math.random()*39))*DOT_SIZE;
    apple_y = ((int)(Math.random()*39))*DOT_SIZE;
    }

    // Make Snake Eat Food
    public void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){ // x[0] and y[0] is position of head
        DOTS++; // size of snake increase
        locateApple();
        }
    }
    //check collision with border and body
    public void checkCollision(){
    // body collision
        for(int i=1;i<DOTS;i++){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
               inGame = false;
            }
        }
    //boundery collision
        if(x[0]<0 || x[0]>=B_WIDTH || y[0]<0 || y[0]>=B_HEIGHT){
              inGame = false;
        }
    }

    // Display Game Over msg
    public void gameOver(Graphics g){
    String msg = "Game Over";
    int score = (DOTS-3)*100; // initial size of snake is 3 so score at this time =0
        String scoremsg = "Score:" + Integer.toString(score);
        Font small = new Font("Helvetika",Font.BOLD, 18);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2 , B_HEIGHT/4);
        g.drawString(scoremsg,(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2 , 3*(B_HEIGHT/4));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
    if(inGame){
    checkApple();
    checkCollision();
    move();
    }
    repaint();
    }
    // snake move
    public void move(){
    for(int i=DOTS-1;i>0;i--){
    x[i]=x[i-1];
    y[i]=y[i-1];
    }
    if(leftDirection){
        x[0] -= DOT_SIZE;
    }
    if (rightDirection){
        x[0] += DOT_SIZE;
    }
    if(upDirection){
        y[0] -= DOT_SIZE;
    }
    if(downDirection){
        y[0] += DOT_SIZE;
    }
    }


    // Implement Key Control
private class TAdapter extends KeyAdapter{ // detecting the keyboard press and change direction of Snake
@Override
    public void keyPressed(KeyEvent keyEvent){
        int key = keyEvent.getKeyCode();
        //conditions for changing direction
        if(key==KeyEvent.VK_LEFT && rightDirection==false){
            leftDirection=true;
            upDirection = false;
            downDirection = false;
        }
        if(key==KeyEvent.VK_RIGHT && leftDirection==false){
            rightDirection=true;
            upDirection = false;
            downDirection = false;
        }
        if(key==KeyEvent.VK_UP && downDirection==false){
            upDirection=true;
            leftDirection = false;
            rightDirection = false;
        }
        if(key==KeyEvent.VK_DOWN && upDirection==false){
            downDirection=true;
            leftDirection = false;
            rightDirection = false;
        }
    }
}



}
