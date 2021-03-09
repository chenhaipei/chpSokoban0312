package sokoban;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * Main page. Includes layout of the entire interface,
 * loading the game, counting steps on the left, selecting levels on the right, and signing below
 *
 * @author Chen Haipei
 */
public class SokobanMain extends JFrame implements ItemListener, ActionListener {

    private static final long serialVersionUID = -7116962815957581285L;
    static JLabel lblTitle, lblGateTitle, lblGate, lblStepTitle, lblStep, lblStatus, lblChp;

    public static void main(String[] args) {
        new SokobanMain();
    }

    JToolBar tb1;
    static JButton btnReset, btnBack, btnFirst, btnNext, btnPrev, btnLast, btnMusic, btnSelect, btnHelp, btnExit;
//    JComboBox<BGM> cbMusic;
    String[] s = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    JComboBox selectGate;
    JPanel eastPanel, westPanel, bottomPanel, topPanel;
    Game game;
    private String[] musicFile = {"music1"};
    //private String[] smusic = { "Make a wish", "Moongate"};
    BGM sound;

    public SokobanMain() {
        Image icon = new ImageIcon("pic/candy1.jpg").getImage();
        setIconImage(icon);
        setTitle("Sokoban by Haipei");

        tb1 = new JToolBar();
        tb1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
//        btnHelp = new JButton("Help");
////		tb1.add(btnHelp);
//        btnHelp.setPreferredSize(new Dimension(80, 25));
//        btnMusic = new JButton("Music off");
////		tb1.add(btnMusic);
//        btnMusic.setPreferredSize(new Dimension(80, 25));
//        btnExit = new JButton("Exit");
//		tb1.add(btnExit);
//        btnExit.setPreferredSize(new Dimension(80, 25));
        //Add title text, at the top
        //lblTitle = new JLabel("chpSOKOBAN", JLabel.CENTER);
        //lblTitle.setFont(new Font("Arial", Font.BOLD, 36));
        //lblTitle.setForeground(new Color(241, 112, 30));
        //topPanel.add(lblTitle);
        topPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        topPanel.add(tb1);

        bottomPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        lblStatus = new JLabel(" ");
        lblChp = new JLabel("Copyright © Haipei Chen ");
        bottomPanel.add(lblStatus);
        bottomPanel.add(lblChp);

        //west: level and step information
        lblGateTitle = new JLabel("Level", JLabel.CENTER);
        lblGateTitle.setFont(new Font("Arial", Font.BOLD, 15));
        lblGateTitle.setForeground(Color.black);

        lblGate = new JLabel("1", JLabel.CENTER);
        lblGate.setFont(new Font("Arial", Font.BOLD, 15));
        lblGate.setForeground(Color.black);
        //
        String step = "";
        lblStepTitle = new JLabel("Steps", JLabel.CENTER);
        lblStepTitle.setFont(new Font("Arial", Font.BOLD, 15));
        lblStepTitle.setForeground(Color.black);

        lblStep = new JLabel(step, JLabel.CENTER);
        lblStep.setFont(new Font("Arial", Font.BOLD, 15));
        lblStep.setForeground(Color.black);

        westPanel = new JPanel(new GridLayout(15, 1, 10, 10));
        westPanel.add(lblGateTitle);
        westPanel.add(lblGate);
        westPanel.add(lblStepTitle);
        westPanel.add(lblStep);
        btnReset = new JButton("Restart");
        btnBack = new JButton("Start");
//		westPanel.add(btnBack);
        westPanel.add(btnReset);
        btnHelp = new JButton("Help");
        westPanel.add(btnHelp);
        btnMusic = new JButton("Music off");
        westPanel.add(btnMusic);

        //east:gate selecting
        eastPanel = new JPanel(new GridLayout(15, 1, 10, 10));
        btnFirst = new JButton("First level");
        btnLast = new JButton("Last level");
        btnPrev = new JButton("Prev level");
        btnNext = new JButton("Next level");
        btnSelect = new JButton("Select level");
        selectGate = new JComboBox(s);
        selectGate.addItemListener(this);

        //
        eastPanel.add(btnFirst);
        eastPanel.add(btnPrev);
        eastPanel.add(btnNext);
        eastPanel.add(btnLast);
        eastPanel.add(btnSelect);
        eastPanel.add(selectGate);

        //Registration events
        btnReset.addActionListener(this);
        btnBack.addActionListener(this);
        btnFirst.addActionListener(this);
        btnPrev.addActionListener(this);
        btnNext.addActionListener(this);
        btnLast.addActionListener(this);
        btnSelect.addActionListener(this);
        btnMusic.addActionListener(this);
        btnHelp.addActionListener(this);
        btnMusic.addActionListener(this);
//        btnExit.addActionListener(this);
        //Used to capture focus listeners all buttons on the side

        //main game in center
        game = new Game();

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);
        add(game, BorderLayout.CENTER);
        game.requestFocus();


        setSize(720, 600);      // Width-height
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sound = new BGM(musicFile[0]);

        addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent e) {
                                  sound.mystop();
                                  System.exit(0);
                              }
                          }
        );
        sound.run();
        //Get the focus let people can move
        game.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        String opt = e.getActionCommand().toString();
        switch (opt) {
            case "Restart":
                game.reStart();
                break;
            case "Start":
                game.back();
                btnBack.setText("Step Back");
                break;
            case "Back":
                game.back();
                break;
            case "Last level":
                game.Sel("Last");
                lblGate.setText(game.level + "");
                break;
            case "First level":
                game.Sel("First");
                lblGate.setText("1");
                break;
            case "Prev level":
                game.Sel("Prev");
                lblGate.setText(game.level + "");
                break;
            case "Next level":
                game.Sel("Next");
                lblGate.setText(game.level + "");
                break;
            case "Select level":
                String str = "Please input a level between (1-" + Game.MAXLEVEL + ")"
                        + "\nYou are in level " + game.level + "";
                String slevel = JOptionPane.showInputDialog(null, str, " Select level", JOptionPane.OK_CANCEL_OPTION);
                int currentLevel = game.level;
                try {
                    if (Integer.parseInt(slevel) < 0 || Integer.parseInt(slevel) > Game.MAXLEVEL)
                        JOptionPane.showMessageDialog(null,
                                "Please input a level between (1-" + Game.MAXLEVEL + ")", "Error", 1);
                    else {
                        game.level = Integer.parseInt(slevel);
                        lblGate.setText(game.level + "");
                    }
                } catch (Exception ex) {
                    game.level = currentLevel;
                }
                game.init(game.level);
                break;
            case "Music off":
                if (sound.ac.isPlaying()) {
                    //check whether music is playing or not
                    sound.mystop();    //stop playing
                }
                btnMusic.setText("Music on");
                break;
            case "Music on":
//                int index = cbMusic.getSelectedIndex();
                sound.music = sound.setMusic(musicFile[0]);
                //set the music
                sound.run();
                btnMusic.setText("Music off");
                break;
            case "Help":
                JOptionPane.showMessageDialog(null, "SOKOBAN "
                                + "\n©Haipei Chen "
                                + "\n "
                                + "\n 1.How to play "
                                + "\n Move Miku by pressing the direction key."
                                + "\n Find a way to push the candy into pudding's mouth."
                                + "\n *You can move up, down, left and right. "
                                + "\n *You can't pass through walls or candies. "
                                + "\n *You can push only one candy at a time (never pull). "
                                + "\n At any time a square can only be occupied by one of a wall, candy or Miku. "
                                + "\n"
                                + "\n 2.How to select a level "
                                + "\n The button on the right side can help you select level."
                                + "\n"
                                + "\n 3.What to do if there's no way to go "
                                + "\n Click the button on the left side to restart this game."
                                + "\n ",
                        "Tips", 3);
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
        //Used to capture focus listeners all buttons on the side
        game.requestFocus();
    }

    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == selectGate) {
            int index = selectGate.getSelectedIndex();
            String ss = (String) selectGate.getItemAt(index);
            System.out.print(ss);
            try {
                game.level = Integer.parseInt(ss);
                lblGate.setText(game.level + "");
            } catch (Exception ex) {
                //the program appropriately handles errors with exceptions
            }
            game.init(game.level);
        }
        game.requestFocus();
    }
}