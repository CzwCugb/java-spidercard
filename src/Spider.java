import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class Spider extends JFrame{

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    private int difficulty;

    private Container pane;
    private PokerCard []cards = new PokerCard[104];
    private JLabel []groundLabel;
    private JLabel SendCardsLabel;

    private int idx;
    private int suitsNum;
    private int finishCount;
    Hashtable map = null;

    public static void main(String[] args){
        Spider spider = new Spider();
        spider.setVisible(true);
    }

    public Spider(){
        setTitle("蜘蛛纸牌");
        setBounds(0,0,1000,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.map = new Hashtable<Point,PokerCard>();
        this.suitsNum = 1;
        this.pane = this.getContentPane();
        pane.setBackground(new Color(36, 145, 24, 255));

        newGame();
    }

    public void newGame(){
        initCards();
        initCardsLocation();
        randomCards();
        sendCards();
    }

    
    public void initCards(){
        for(int i = 0 ; i < 8 ; i ++){
            for(int j = 0 ; j < 13; j ++){
                cards[i*13 + j] = new PokerCard((i+1)%suitsNum + 1,(j+1),this);
            }
        }
        //较低索引的JLabel将覆盖较高索引的
        for(int i = 0; i < 103 ; i ++){
            pane.add(cards[103-i],i);
        }
    }

    public void initCardsLocation(){
        //底部发牌区
        int left = 830;
        int top = 550;
        int index = 0;
        for(int i = 0 ; i < 6 ; i ++){
            for(int j = 0 ; j < 10 ; j ++){
                index = i*10 + j;
                cards[index].moveTo(left,top);
                Point p = cards[index].getLocation();
                map.put(p,cards[index]);
            }
            left += 10;
        }
        //顶部操作区
        left = 30;
        top = 50;
        index = 60;
        int sum = 5;
        while(index < 104){
            cards[index].moveTo(left,top);
            map.put(cards[index].getLocation(),cards[index]);
            index++;
            if(index >= 80) sum = 4;
            if(index % sum == 0){
                top = 50;
                left += 95;
                continue;
            }
            top += 10;
        }
    }

    public void randomCards(){
    }

    public void sendCards(){

    }

}
