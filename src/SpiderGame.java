import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;


public class SpiderGame extends JFrame {

    public static void main(String[] args){
        new SpiderGame();
    }

    //操作区参数
    public static final int REAR_LINE_HEIGHT = 10;
    public static final int FRONT_LINE_HEIGHT = 20;
    public static final int COLUMN_GAP = 95;
    public static final int LEFT = 30;
    public static final int TOP = 30;

    //发牌区参数
    public static final int SENDCARDS_AREA_GAP = 20;
    public static final int SENDCARDS_AREA_TOP = 530;
    public static final int SENDCARDS_AREA_LEFT = 790;

    //回收区参数
    public static final int FINISH_AREA_GAP = 20;
    public static final int FINISH_AREA_TOP = 530;
    public static final int FINISH_AREA_LEFT = 40;

    //游戏参数
    private int finishCount = 0;
    private int sendCardsCol = 0;
    private boolean animated = true;


    private PokerCard[] cards = new PokerCard[104];
    private Hashtable<Point,PokerCard> map = new Hashtable<Point,PokerCard>();
    private Container pane = this.getContentPane();
    private JLabel[] groundLabels = new JLabel[10];
    private JLabel sendCardsClickArea = null;


    public SpiderGame(){
        setTitle("蜘蛛纸牌");
        setBounds(0,0,1000,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        pane.setLayout(null);
        pane.setBackground(new Color(36, 145, 24, 255));
        setJMenuBar(new SpiderMenuBar(this));
        setVisible(true);
        newGame();
    }

    //功能函数
    public void newGame(){
        initCards();
        randomCards();
        initGroundArea();
        initCardsLocation();
        initClickArea();
        sendCards();
    }

    public void initCards(){
        sendCardsCol = 0;
        finishCount = 0;
        map.clear();
        for(int i = 0 ; i < 104 ; i ++){
            int value_ = i%13 + 1;
            int type_ = 1;
            if(cards[i] != null) pane.remove(cards[i]);
            cards[i] = new PokerCard(value_,type_,this);
        }
    }

    public void randomCards(){
        PokerCard temp = null;
        for (int i = 0; i < 52; i++){
            int a = (int) (Math.random() * 104);
            int b = (int) (Math.random() * 104);
            temp = cards[a];
            cards[a] = cards[b];
            cards[b] = temp;
        }
    }

    public void initCardsLocation(){
        for(int i = 0 ; i < 104 ; i ++) {
            pane.add(cards[i],0);
        }
        int idx = 0;
        //顶部
        int left = LEFT;
        for(int i = 0 ; i < 10 ; i ++){
            int top = TOP;
            int rows = 4;
            if(i <= 3) rows = 5;
            for(int j = 0 ; j < rows ; j ++){
                cards[idx].setLocation(left,top);
                cards[idx].setInitPoint(new Point(left,top));
                map.put(new Point(left,top),cards[idx]);
                idx++;
                top += REAR_LINE_HEIGHT;
            }
            left = left + COLUMN_GAP;
        }
        //发牌区域
        for(int i = 5 ; i >= 0 ; i --){
            int bottom_left = SENDCARDS_AREA_LEFT + i* SENDCARDS_AREA_GAP;
            int bottom_top = SENDCARDS_AREA_TOP;
            for(int j = 0 ; j < 10 ; j ++){
                cards[idx].setLocation(bottom_left,bottom_top);
                cards[idx].setInitPoint(new Point(bottom_left,bottom_top));
                idx++;
            }
        }
    }

    public void initGroundArea(){
        Border dashedBorder = BorderFactory.createDashedBorder(Color.black, 1,3,1,false);
        for(int i = 0 ; i < 10 ; i ++){
            if(groundLabels[i] != null) pane.remove(groundLabels[i]);
            groundLabels[i] = new JLabel();
            groundLabels[i].setOpaque(false);
            groundLabels[i].setBorder(dashedBorder);
            groundLabels[i].setBounds(LEFT + i*COLUMN_GAP,TOP+2,PokerCard.CARD_WIDTH,PokerCard.CARD_HEIGHT - 5);
            pane.add(groundLabels[i],0);
        }
    }

    public void initClickArea(){
        if(sendCardsClickArea != null) pane.remove(sendCardsClickArea);
        sendCardsClickArea = new JLabel();
        pane.add(sendCardsClickArea,0);
        sendCardsClickArea.setBounds(SENDCARDS_AREA_LEFT, SENDCARDS_AREA_TOP,400,110);
        sendCardsClickArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendCards();
                turnFrontLastCards();
                resetCardsMovable();
                checkColumnsFinished();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

    }

    public void sendCards(){
        if(sendCardsCol >= 6) {
            JOptionPane.showMessageDialog(pane,"现在无牌可发","提示",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for(int i = 0 ; i < 10 ; i ++){
            if(!map.containsKey(getFirstPoint(i))){
                JOptionPane.showMessageDialog(pane,"存在空列，无法发牌","提示",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        new MusicThread(MusicThread.sendCardsMusicPath).start();
        int sendFrom = 103 - sendCardsCol*10;
        int sendUntil = sendFrom - 10;
        int col = 0;
        for(int i = sendFrom  ; i > sendUntil ; i --){
            Point toPoint = getLastPoint(col++);
            if(map.get(toPoint).getFront()){
                toPoint.y += FRONT_LINE_HEIGHT;
            }else{
                toPoint.y += REAR_LINE_HEIGHT;
            }
            if(animated) cards[i].moveTo(toPoint);
            else cards[i].setLocation(toPoint);
            cards[i].setInitPoint(toPoint);
            cards[i].turnFront();
            cards[i].setMovable(true);
            pane.setComponentZOrder(cards[i],0);
            map.put(toPoint,cards[i]);
        }
        sendCardsCol++;
    }

    public Point getLastPoint(int col_){
        Point lastP = new Point(LEFT + col_*COLUMN_GAP,TOP);
        while(getNextPoint(lastP) != null) {
            lastP = getNextPoint(lastP);
        }
        return lastP;
    }

    public Point getFirstPoint(int col_){
        return new Point(LEFT + col_*COLUMN_GAP,TOP);
    }

    public Point getNextPoint(Point p){
        Point p1 = new Point(p.x,p.y + FRONT_LINE_HEIGHT);
        Point p2 = new Point(p.x,p.y + REAR_LINE_HEIGHT);
        if(map.containsKey(p1)) return p1;
        else if(map.containsKey(p2)) return p2;
        else return null;
    }

    public Point getPreviousPoint(Point p){
        Point p1 = new Point(p.x,p.y - FRONT_LINE_HEIGHT);
        Point p2 = new Point(p.x,p.y - REAR_LINE_HEIGHT);
        if(map.containsKey(p1)) return p1;
        else if(map.containsKey(p2)) return p2;
        else return null;
    }

    public void removePointFromMap(Point p){
        map.remove(p);
    }

    public void addItemToMap(Point p,PokerCard card){
        map.put(p,card);
    }

    public PokerCard getMapValue(Point p){
        return map.get(p);
    }

    public boolean mapContains(Point p ){
        return map.containsKey(p);
    }

    public void turnFrontLastCards(){
        //检查最后一列是不是负面
        for(int i = 0 ; i < 10 ; i ++){
            PokerCard lastCard = map.get(getLastPoint(i));
            if(lastCard != null){
                if(!lastCard.getFront()){
                    lastCard.turnFront();
                }
                lastCard.setMovable(true);
            }
        }
    }

    public void resetCardsMovable(){
        for(int i = 0 ; i < 10 ; i ++){
            Point p = getLastPoint(i);
            p = getPreviousPoint(p);
            boolean sinceNotMovable = false;
            while(p != null && map.containsKey(p)){
                PokerCard card = map.get(p);
                PokerCard cardNext = map.get(getNextPoint(p));
                if(card.getValue() != cardNext.getValue() + 1 || !cardNext.getFront() || sinceNotMovable){
                    sinceNotMovable = true;
                    card.setMovable(false);
                }else{
                    card.setMovable(true);
                }
                p = getPreviousPoint(p);
            }
        }
    }

    public void checkColumnsFinished(){
        for(int i = 0 ; i < 10 ; i ++){
            int valueCount = 1;
            Point p = getLastPoint(i);
            while(p != null && map.containsKey(p) && map.get(p).getValue() == valueCount){
                p = getPreviousPoint(p);
                valueCount++;
            }
            if(valueCount == 14){
                Point pi = getLastPoint(i);
                for(int j = 0 ; j < 13 ; j ++){
                    PokerCard card = map.get(pi);
                    map.remove(pi);
                    card.turnRear();
                    card.setMovable(false);
                    if(animated) card.moveTo(new Point(FINISH_AREA_LEFT + finishCount*FINISH_AREA_GAP,FINISH_AREA_TOP));
                    else card.setLocation(FINISH_AREA_LEFT + finishCount*FINISH_AREA_GAP,FINISH_AREA_TOP);
                    pi = getPreviousPoint(pi);
                }
                finishCount++;
            }
        }
        if(finishCount == 8){
            gameSuccess();
            return;
        }
        turnFrontLastCards();
    }

    public void resetAnimated(){
        if(!animated) animated = true;
        else animated = false;
    }

    //游戏结算
    public void gameSuccess(){
        JOptionPane.showMessageDialog(this,"恭喜你!成功收集了所有卡牌","提示",JOptionPane.INFORMATION_MESSAGE);
    }


}
