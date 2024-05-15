import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class PokerCard extends JLabel{

    //5.14:陈志伟 => value 1-13表示A-K ; type 1-4 表示四种花色
    int value;
    int type;
    int initPoint;
    boolean movable;
    boolean front;
    Spider main = null;

    //5.14:陈志伟 => 扑克牌初始化
    public PokerCard(int type_,int value_,Spider spider_){
        value = value_;
        type = type_;
        main = spider_;
        setSize(70,100);
        turnRear();
        addMouseMotionListener(new PokerCardMouseListener());
        addMouseListener(new PokerCardMouseListener());
    }

    //5.14:陈志伟 => 两个移动函数
    public void moving(int x_, int y_){
        Point p = new Point(this.getX() + x_,this.getY() + y_);
        this.setLocation(p);
    }

    public void moveTo(int x_, int y_){
        Point p = new Point(x_,y_);
        this.setLocation(p);
    }

    //5.14:陈志伟 => 切换正反面
    public void turnFront(){
        front = true;
        movable = true;
        setIcon(new ImageIcon("./images/" + type + "-" + value + ".gif"));
    }

    public void turnRear(){
        front = false;
        movable = false;
        setIcon(new ImageIcon("./images/rear.gif"));
    }

    //5.14:陈志伟 => 设置属性和获取属性 函数
    public void setMovable(boolean movable_){
        movable = movable_;
    }

    public boolean isMovable(){
        return this.movable;
    }

    public boolean isFront(){
        return this.front;
    }

    public int getValue(){
        return value;
    }

    public int getType(){
        return type;
    }

    //5.14:陈志伟 => 鼠标事件监听器
    //TODO 鼠标事件监听器实现
    class PokerCardMouseListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
        }

        //将当前牌放到最近的、可放置的列上
        //若可行，则放上，并判断是否完成一套牌
        //否则，返回原位置
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        //若可移动，移动
        @Override
        public void mouseDragged(MouseEvent e) {
            if (movable){
                moving(e.getX() - 20,e.getY() - 20);
            }
        }

    }
}


