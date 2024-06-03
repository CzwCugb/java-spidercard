import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        this.main = father;
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'n'){
                    main.newGame();
                }else if(e.getKeyChar() == 's'){
                    main.sendCards();
                }else if(e.getKeyChar() == 'a'){
                    main.resetAnimated();
                }
            }
        });

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
                main.setSuitSum(1);
            }
        });

        MediumItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setSuitSum(2);
            }
        });

        HardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setSuitSum(4);
            }
        });

        //TODO 关于我们
        oursItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(main,"中国地质大学（北京）面向对象课程设计实践作业:Java版蜘蛛纸牌\n"
                 +"团队成员: 陈志伟 周子豪 张宝仁 朱利康");
            }
        });
        //TODO 游戏规则
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(main,"蜘蛛纸牌总计需要 104 张牌（两套扑克牌）。\n" +
                        "游戏的目标是将所有牌按降序排列（从 K 到 A）形成完整的一行，并收集到牌堆中。\n" +
                        "开端————游戏开始时，共有10列牌，每列有一定数量的牌，牌面朝下；开始时，发一次牌\n" +
                        "移动卡牌————列尾的卡牌序列可以移动到另一列更高一级的牌上。例如，牌6、7、8 可以移动到 牌9。\n" +
                        "行的完成和移除————一旦形成完整的一组，（从 K 到 A ），这些牌将被移动到左下角牌堆。\n" +
                        "发牌————在游戏中，除了移动卡牌和收集卡牌，还可以抽剩余的牌，均分到每一列；有空列时不能发牌。\n" +
                        "获胜条件————如果所有牌都正确排序并被成功收集，您就获胜。\n" +
                        "快捷键————a -- 设置动画效果；n -- 新游戏；s -- 发牌");
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
    }

    public void setScoreLabel(int score_){
        scoreLabel.setText("分数:" + score_);
    }
}
