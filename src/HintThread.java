import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HintThread extends Thread{

    //自身参数
    private SpiderGame main = null;
    private final int delayMillis = 300;
    private final int highlightCount = 3;

    //构造函数
    public HintThread(SpiderGame father){
        main = father;
    }

    //重现run，实现提示
    @Override
    public void run(){
        boolean isCheckOver = false;
        //尝试找到提示点
        for(int i=0;i<10;i++){
            Point p = main.getFirstPoint(i);
            PokerCard card ;
            while(p != null && main.mapContains(p) ) {
                card = main.getMapValue(p);
                if(card.getMovable()) {
                    for (int j = 0; j < 10; j++) {
                        if(j==i) continue;
                        Point p1 = main.getLastPoint(j);
                        if (p1 != null && main.mapContains(p1)) {
                            PokerCard card1 = main.getMapValue(p1);
                            if (card1.getValue() == card.getValue() + 1) {
                                for(int t = 0 ; t < highlightCount ; t ++){
                                    highlight(p, p1);
                                    try {
                                        Thread.sleep(delayMillis*2);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                isCheckOver = true;
                                break;
                            }
                        }
                    }
                    if (isCheckOver) break;
                }
                p = main.getNextPoint(p);
            }
            if(isCheckOver){
                break;
            }
        }
        //如果找不到，且依然有牌可发，提示发牌
        if(!isCheckOver && SpiderGame.sendCardsCol < 6){
            int bottom_left = SpiderGame.SENDCARDS_AREA_LEFT + SpiderGame.sendCardsCol*SpiderGame.SENDCARDS_AREA_GAP;
            int bottom_top = SpiderGame.SENDCARDS_AREA_TOP;
            for(int t = 0 ; t < highlightCount ; t ++){
                highlightSendCards(new Point(bottom_left,bottom_top));
                try {
                    Thread.sleep(delayMillis*2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }else if(!isCheckOver){ //无牌可发，游戏结束
            int opt = JOptionPane.showConfirmDialog(main,"游戏结束,是否重新开始？","提示",JOptionPane.YES_NO_OPTION);
            if(opt == JOptionPane.YES_OPTION){
                main.newGame();
            }
        }
    }

    //高亮发牌区卡牌
    public void highlightSendCards(final Point p){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PokerCard card= main.getMapValue(p);
                if(card == null) return;
                card.turnWhite();
                Timer timer = new Timer(delayMillis,null);
                ActionListener listener = new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        card.turnRear();
                        timer.stop();
                    }
                };
                timer.addActionListener(listener);
                timer.start();
            }
        });
    }

    //高亮操作区卡牌
    public void highlight(final Point p1, final Point p2) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PokerCard card1= main.getMapValue(p1);
                PokerCard card2= main.getMapValue(p2);
                if(card1 == null || card2 == null) return;
                card1.turnWhite();
                card2.turnWhite();
                Timer timer = new Timer(delayMillis,null);
                ActionListener listener = new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        card1.turnFront();
                        card2.turnFront();
                        timer.stop();
                    }
                };
                timer.addActionListener(listener);
                timer.start();
            }
        });
    }

}
