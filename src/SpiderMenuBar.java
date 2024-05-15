import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpiderMenuBar extends JMenuBar {
    Spider main;

    //5.15 陈志伟 => 菜单栏创建
    JMenu gameMenu = new JMenu("游戏");
    JMenuItem newGameItem = new JMenuItem("新游戏");
    JMenuItem messageItem = new JMenuItem("提示");
    JMenuItem sendCardsItem = new JMenuItem("发牌");
    JMenu difficultyMenu = new JMenu("游戏难度");
    JMenuItem EasyItem = new JMenuItem("单花色");
    JMenuItem MediumItem = new JMenuItem("双花色");
    JMenuItem HardItem = new JMenuItem("四花色");
    JMenu aboutMenu = new JMenu("帮助");
    JMenuItem oursItem = new JMenuItem("关于我们");
    JMenuItem helpItem = new JMenuItem("游戏规则");

    public SpiderMenuBar(Spider spider){

        //5.15 陈志伟 => 菜单栏事件

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
                main.setDifficulty(main.EASY);
            }
        });

        MediumItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setDifficulty(main.MEDIUM);
            }
        });

        HardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setDifficulty(main.HARD);
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

        //5.15 陈志伟 => 菜单栏初始化
        gameMenu.add(messageItem);gameMenu.add(newGameItem);gameMenu.add(sendCardsItem);
        difficultyMenu.add(EasyItem);difficultyMenu.add(MediumItem);difficultyMenu.add(HardItem);
        aboutMenu.add(oursItem);aboutMenu.add(helpItem);
        this.add(gameMenu);
        this.add(difficultyMenu);
        this.add(aboutMenu);
        this.main = spider;
    }

}
