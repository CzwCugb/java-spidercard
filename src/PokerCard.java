import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class PokerCard extends JLabel{

    Point initPoint;

    int value;
    int type;

    boolean movable;
    boolean front;

    PokerCard previousCard;

    Spider main = null;

    public PokerCard(int type_,int value_,Spider spider_){
        value = value_;
        type = type_;
        main = spider_;
        setSize(70,100);
        setMovable(true);
        turnRear();
        addMouseMotionListener(new PokerCardMouseListener());
        addMouseListener(new PokerCardMouseListener());
    }


    public void moving(int x_, int y_){
        Point p = new Point(this.getX() + x_,this.getY() + y_);
        this.setLocation(p);
    }

    public void moveTo(int x_, int y_){
        Point p = new Point(x_,y_);
        this.setLocation(p);
    }

    public void turnFront(){
        front = true;
        setIcon(new ImageIcon("./images/" + type + "-" + value + ".gif"));
    }

    public void turnRear(){
        front = false;
        setIcon(new ImageIcon("./images/rear.gif"));
    }

    public void setMovable(boolean movable_){
        movable = movable_;
    }

    public boolean isFront(){
        return this.front;
    }

    public boolean isMovable(){
        return this.movable;
    }

    public int getValue(){
        return value;
    }

    public int getType(){
        return type;
    }

    class PokerCardMouseListener extends MouseAdapter{


        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            moving(e.getX() - 20,e.getY() - 20);
        }

    }

}


