import javax.swing.*;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

public class Spider extends JFrame{

    //5.14:陈志伟 => 难度设置:先实现单花色的，这个先不管
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    private int difficulty = EASY;
    private int sendCardsNum = 0;
    private Container pane = this.getContentPane();
    private PokerCard []cards = new PokerCard[104];
    private JLabel []groundLabel = new JLabel[10];
    private JLabel sendCardsLabel = new JLabel(); //用于右下角点击的区域Label,此Label不可见
    private int suitsNum = 1;; //花色
    private int finishCount = 0; //当前完成几副牌
    Hashtable map = new Hashtable<Point,PokerCard>();

    //5.14:陈志伟 => 启动Spider窗体
    public static void main(String[] args){
        Spider spider = new Spider();
        spider.setVisible(true);
    }

    //5.14:陈志伟 => 窗体属性
    public Spider(){
        setTitle("蜘蛛纸牌");
        setBounds(0,0,1000,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(new SpiderMenuBar(this));
        setLayout(null);
        pane.setBackground(new Color(36, 145, 24, 255));
        initClickArea();
        initGroundArea();
        newGame();
    }

    //5.14:陈志伟 => 开启新的游戏
    public void newGame(){
        initCards();
        randomCards();
        initCardsLocation();
        sendCards();
    }

    //5.14:陈志伟 => 初始化牌自身属性
    public void initCards(){
        map.clear();
        this.sendCardsNum = 0;
        if(cards[0] != null){
            for(int i = 0 ; i < 104 ; i ++){
                pane.remove(cards[i]);
            }
        }
        for(int i = 0 ; i < 8 ; i ++){
            for(int j = 0 ; j < 13; j ++){
                cards[i*13 + j] = new PokerCard((i+1)%suitsNum + 1,(j+1),this);
            }
        }
    }

    //5.14:陈志伟 => 初始化牌的位置
    public void initCardsLocation(){
        //底部发牌区
        int left = 900;
        int top = 530;
        int index = 0;
      //较低索引的JLabel将覆盖较高索引的
        for(int i = 0; i < 104 ; i ++){
            pane.add(cards[103-i],i+1);
        }
        for(int i = 0 ; i < 6 ; i ++){
            for(int j = 0 ; j < 10 ; j ++){
                index = i*10 + j;
                cards[index].moveTo(left,top);
                Point p = cards[index].getLocation();
                map.put(p,cards[index]);
            }
            left -= 10;
        }
        //顶部操作区
        left = 30;
        top = 40;
        index = 60;
        int sum = 5;
        while(index < 104){
            cards[index].moveTo(left,top);
            map.put(cards[index].getLocation(),cards[index]);
            index++;
            if(index >= 80) sum = 4;
            if(index % sum == 0){
                top = 40;
                left += 95;
                continue;
            }
            top += 15;
        }
    }

    //5:14:陈志伟 => 右下角发牌区
    public void initClickArea(){
        this.sendCardsLabel.setBounds(820,520,150,115);
        this.sendCardsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendCards();
            }
        });
        pane.add(sendCardsLabel,0);
    }

    //TODO 上方底部空区域
    public void initGroundArea(){
    }

    //5.14:张宝仁 => 打乱牌序
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

    //5:15:张宝仁 => 发牌
    public void sendCards(){
    	for (int i = 0; i < 10; i++){
            if (this.getLastCardLocation(i) == null){
                JOptionPane.showMessageDialog(this, "存在空列，不能发牌", "警告ʾ",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
		for (int i = 0; i < 10; i++){
            Point lastPoint = this.getLastCardLocation(i);
            
			if (sendCardsNum == 0){lastPoint.y += 10;}
            else {lastPoint.y += 15;}
            
			map.remove(cards[sendCardsNum + i].getLocation());
            cards[sendCardsNum + i].moveTo(lastPoint.x,lastPoint.y);
            map.put(new Point(lastPoint), cards[sendCardsNum + i]);
            cards[sendCardsNum + i].turnFront();
            cards[sendCardsNum + i].setMovable(true);
			
			this.pane.setComponentZOrder(cards[sendCardsNum + i], 0);
        }
        sendCardsNum += 10;
    }

    //TODO 上一张牌
    public PokerCard getPreviousCard(PokerCard card){
        return null;
    }

    //5:14:张宝仁 => 下一张牌
    public PokerCard getNextCard(PokerCard card) {
    	Point point=new Point(card.getLocation());
    	point.y+=10;
    	card=(PokerCard)map.get(point);
    	if(card!=null)return card;
    	point.y+=5;
    	card=(PokerCard)map.get(point);
    	return card;
    }

    //5.15:张宝仁 => 获得当前列最顶上的牌
    public Point getLastCardLocation(int col) {
    	Point point=new Point(30 + col*95 ,40);
    	PokerCard card=(PokerCard) this.map.get(point);
    	if(card==null)return null;
    	while(card!=null) {
    		point=card.getLocation();
    		card=this.getNextCard(card);
    	}
    	return point;
    }

    //TODO 检查点所在列是否有完成的卡组
    public boolean checkFinish(int col){
        return true;
    }
    //5.15:陈志伟 => 设置难度
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
