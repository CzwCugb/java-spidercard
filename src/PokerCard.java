import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class PokerCard extends JLabel {

    private int value;
    private int type;
    private Point initPoint = null;
    private boolean movable;
    private boolean front;
    private SpiderGame main = null;
    private Container pane = null;
    private PokerCard me = this;

    Border cardPickedBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.YELLOW,Color.YELLOW);

    public static int CARD_HEIGHT = 100;
    public static int CARD_WIDTH = 70;

    public PokerCard(int value_,int type_,SpiderGame main_){
        value = value_;
        type = type_;
        main = main_;
        pane = main.getContentPane();
        setSize(CARD_WIDTH, CARD_HEIGHT);
        turnRear();
        setMovable(false);
        setBackground(Color.YELLOW);
        addMouseMotionListener(new PokerCardMouseListener());
        addMouseListener(new PokerCardMouseListener());
    }

    public void turnFront(){
        front = true;
        ImageIcon icon = new ImageIcon("./images/pixel/" + type + "-" + value + ".gif");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(70, 100,  java.awt.Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(newImg));
        //setIcon(new ImageIcon("./images/pixel/" + type + "-" + value + ".gif"));
    }

    public void turnRear(){
        front = false;
        ImageIcon icon = new ImageIcon("./images/pixel/rear.gif");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(70, 100,  java.awt.Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(newImg));
        //setIcon(new ImageIcon("./images/pixel/rear.gif"));
    }

    public int getValue(){
        return value;
    }

    public boolean getFront(){
        return front;
    }

    public void setMovable(boolean movable_){
        movable = movable_;
    }

    public boolean getMovable(){return movable;}

    public void setInitPoint(Point initPoint_){
        initPoint = initPoint_;
    }

    private class PokerCardMouseListener extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!movable) return;
            new MusicThread(MusicThread.moveCardsMusicPath).start();
            //找到最近的一列
            int toCol = -1;
            for(int i = 0 ; i < 10 ; i ++){
                Point colP = main.getLastPoint(i);
                Point relP = me.getLocation();
                if(Point2D.distance(colP.x, colP.y, relP.x,relP.y) < CARD_WIDTH){
                    toCol = i;
                    break;
                }
            }
            //如果找不到
            if(toCol == -1){
                Point p = me.initPoint;
                while(p != null) {
                    PokerCard tempCard = main.getMapValue(p);
                    tempCard.setBorder(null);
                    tempCard.setLocation(tempCard.initPoint);
                    p = main.getNextPoint(p);
                }
                return;
            }
            //如果找到，是否值可以对应
            PokerCard lastCard = main.getMapValue(main.getLastPoint(toCol));
            if(lastCard == null || lastCard.getValue() == me.value + 1){
                //可以对应，则
                Point lastP = main.getLastPoint(toCol);
                if(lastCard == null) lastP.y -= SpiderGame.FRONT_LINE_HEIGHT;
                Point p = me.initPoint;
                main.reduceScore(1);
                while(p != null){
                    PokerCard tempCard = main.getMapValue(p);
                    lastP.y += SpiderGame.FRONT_LINE_HEIGHT;
                    tempCard.setLocation(lastP);
                    tempCard.setBorder(null);
                    main.removePointFromMap(tempCard.initPoint);
                    tempCard.initPoint = tempCard.getLocation();
                    main.addItemToMap(tempCard.getLocation(),tempCard);
                    p = main.getNextPoint(p);
                }

            }else{
                //否则
                Point p = me.initPoint;
                while(p != null) {
                    PokerCard tempCard = main.getMapValue(p);
                    tempCard.setLocation(tempCard.initPoint);
                    tempCard.setBorder(null);
                    p = main.getNextPoint(p);
                }
                return;
            }
            //最后检查移动后所有列是否需要修正
            main.turnFrontLastCards();
            main.resetCardsMovable();
            main.checkColumnsFinished();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(movable){
                Point p = me.initPoint;
                while(p != null){
                    PokerCard tempCard = main.getMapValue(p);
                    tempCard.setBorder(cardPickedBorder);
                    tempCard.setLocation(tempCard.getX() + e.getX() -  CARD_WIDTH/5, tempCard.getY() + e.getY() - CARD_HEIGHT /5);
                    pane.setComponentZOrder(tempCard,0);
                    p = main.getNextPoint(p);
                }
            }
        }
    }

    //带动画的移动
    public void moveTo(Point toPoint){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                int duration = 180; //总用时
                int delay = 30; // 多少毫秒更新一次位置
                int steps = duration / delay;

                double dx = (double) (toPoint.x - me.getX()) / steps;
                double dy = (double) (toPoint.y - me.getY()) / steps;

                Timer timer = new Timer(delay,null);
                ActionListener listener = new ActionListener() {
                    int step = 0; // 当前步数
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (step >= steps) {
                            // 如果已经移动了指定的步数：
                            timer.stop();
                            timer.removeActionListener(this);
                            me.setLocation(toPoint.x, toPoint.y);
                        } else {
                            // 否则
                            me.setLocation((int) (me.getX() + dx), (int) (me.getY() + dy));
                            step++;
                        }
                    }
                };
                timer.addActionListener(listener);
                timer.start();
            }
        });
    }

}
