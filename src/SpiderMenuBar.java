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
                JOptionPane.showMessageDialog(main,"中国地质大学（北京）面向对象课程设计实践作业 ---- Java版蜘蛛纸牌\n"
                 +"团队成员: 陈志伟、周子豪、张宝仁、朱利康");
            }
        });
        //TODO 游戏规则
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(main,"基本蜘蛛纸牌需要 104 张牌（两套扑克牌）。\n" +
                        "游戏的目标是将牌按降序排列（从 K 到 A）形成完整的一行，并将牌从桌子上移走。\n" +
                        "发牌：游戏开始时，发牌为十行，每行有一定数量的牌。有些牌面朝上，其余的牌面朝下。\n" +
                        "移动卡牌：打开的卡牌可以移动到另一列中排名更高一级的卡牌。例如，卡 8 可以移动到卡 9。\n" +
                        "行的完成和移除：一旦形成完整的行，按照从 K 到 A 的顺序，该行将从游戏中移除。\n" +
                        "抽新牌：如果通过移动一张牌或删除一行来停止移动，则可以抽剩余的牌。这创造了新的运动可能性。\n" +
                        "获胜条件：如果所有牌都正确排序并从桌子上移走，您就获胜。");
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
