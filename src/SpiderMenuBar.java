import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpiderMenuBar extends JMenuBar {

    private JMenu gameMenu = new JMenu("游戏");
    private JMenuItem newGameItem = new JMenuItem("新游戏");
    private JMenuItem messageItem = new JMenuItem("提示");
    private JMenuItem sendCardsItem = new JMenuItem("发牌");
    private JMenuItem animatedItem = new JMenuItem("关闭/开启动画");
    private JMenu difficultyMenu = new JMenu("游戏难度");
    private JMenuItem EasyItem = new JMenuItem("单花色");
    private JMenuItem MediumItem = new JMenuItem("双花色");
    private JMenuItem HardItem = new JMenuItem("四花色");
    private JMenu aboutMenu = new JMenu("帮助");
    private JMenuItem oursItem = new JMenuItem("关于我们");
    private JMenuItem helpItem = new JMenuItem("游戏规则");
    private Font menuFont = new Font("微软雅黑",Font.PLAIN,13);
    private JLabel scoreLabel = new JLabel("分数: 500");
    private SpiderGame main;

    public SpiderMenuBar(SpiderGame father){

        animatedItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.resetAnimated();
            }
        });

        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.newGame();
            }
        });
        //TODO 提示
        messageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        sendCardsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.sendCards();
            }
        });

        EasyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.suitSum=1;
            }
        });

        MediumItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.suitSum=2;

            }
        });

        HardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.suitSum=4;
            }
        });

        //TODO 关于我们
        oursItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //TODO 游戏规则
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });




        gameMenu.add(messageItem);gameMenu.add(newGameItem);gameMenu.add(sendCardsItem);gameMenu.add(animatedItem);
        difficultyMenu.add(EasyItem);difficultyMenu.add(MediumItem);difficultyMenu.add(HardItem);
        aboutMenu.add(oursItem);aboutMenu.add(helpItem);

        scoreLabel.setFont(menuFont);
        gameMenu.setFont(menuFont);
        difficultyMenu.setFont(menuFont);
        aboutMenu.setFont(menuFont);

        this.add(gameMenu);
        this.add(difficultyMenu);
        this.add(aboutMenu);
        this.add(scoreLabel);
        this.main = father;
    }

    public void setScoreLabel(int score_){
        scoreLabel.setText("分数:" + score_);
    }
}
