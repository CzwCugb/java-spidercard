import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpiderStartFrame extends JFrame {

    private JLabel backgroundLabel = new JLabel();
    private Font butFont = new Font("微软雅黑",Font.PLAIN,20);
    private String backgroundImgPath = "./images/deck.jpg";
    private SpiderStartFrame me = this;
    private JLabel titleLabel = new JLabel("欢迎来到蜘蛛纸牌！");
    private JButton startButton = new JButton("开始游戏");
    private JButton ruleButton = new JButton("游戏规则");
    private JButton exitButton = new JButton("退出游戏");

    public static void main(String[] args){
        new SpiderStartFrame();
    }

    public SpiderStartFrame(){
        //界面基本参数设置
        setTitle("欢迎来到蜘蛛纸牌！");
        setBounds(0, 0, 1000, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        //背景图片设置
        backgroundLabel.setBounds(0, 0, 1000, 700);
        backgroundLabel.setLayout(null);
        ImageIcon icon = new ImageIcon(backgroundImgPath);
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(1000, 700,  java.awt.Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(newImg));
        setContentPane(backgroundLabel);

        //欢迎标签及标题

        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        titleLabel.setForeground(new Color(1, 142, 36));
        titleLabel.setBounds(300, 180, 700, 60);

        //游戏按钮
        startButton.setFont(butFont);
        startButton.setBounds(400, 300, 200, 50);

        ruleButton.setFont(butFont);
        ruleButton.setBounds(400, 380, 200, 50);

        exitButton.setFont(butFont);
        exitButton.setBounds(400, 460, 200, 50);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                me.dispose();
                new SpiderGame().setLocation(getX(),getY());
            }
        });

        ruleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                new RulesFrame();
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                me.dispose();
            }
        });

        //添加组件
        add(titleLabel);
        add(startButton);
        add(ruleButton);
        add(exitButton);
        setVisible(true);
    }

    //游戏规则页
    public static class RulesFrame extends JFrame {
        public RulesFrame() {
            setTitle("蜘蛛纸牌规则");
            setBounds(300, 200, 600, 400);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            JTextArea rulesText = new JTextArea();
            rulesText.setText("蜘蛛纸牌规则简要说明\n\n"
                    + "游戏目标：\n将所有纸牌按从K到A的顺序组成完整的牌组并移除。成功移除所有牌组即获胜。\n\n"
                    + "纸牌：\n使用两副扑克牌，共104张。根据难度，可以选择使用1种花色、2种花色或4种花色。\n\n"
                    + "游戏开始: \n"
                    + "1. 将54张牌随机发到10列牌堆中，前4列各6张，其余6列各5张。每列最上面的那张牌面朝上，其余牌面朝下。\n"
                    + "2. 剩余的50张牌作为备用牌，放在游戏区下方。\n\n"
                    + "移动规则：\n"
                    + "1. 可以移动单张牌或已经按顺序排好的牌组（如8、7、6）。\n"
                    + "2. 移动的牌（组）必须放到目标牌堆顶部的牌面值比其大1点的牌上。\n"
                    + "3. 任何牌或牌组可以移动到一个空列。\n\n"
                    + "完成牌组：\n当某列从国王到A的牌组按顺序完成后，该牌组自动移除。\n\n"
                    + "发牌：\n当无法继续移动时，可以点击备用牌，给每列顶部发一张新牌。\n\n"
                    + "胜利条件：\n成功移除所有纸牌。\n\n"
                    + "祝您游戏愉快！"
            );
            rulesText.setFont(new Font("微软雅黑",Font.PLAIN,15));
            rulesText.setEditable(false);
            rulesText.setLineWrap(true);
            rulesText.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(rulesText);
            add(scrollPane, BorderLayout.CENTER);

            setVisible(true);
        }
    }

}
