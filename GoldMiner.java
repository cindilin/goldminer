import java.io.IOException;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GoldMiner extends JFrame implements ActionListener{

    static final double TIME_STEP = 1.0;
    static final double PERIOD = 20.0;

    static final int WIDTH = 800;
    static final int HEIGHT = 600;
	
	static Level level;

    public GoldMiner() throws IOException {

        // setup the window title
        setTitle("Gold Miner");

        // setup the window size
        setSize(WIDTH, HEIGHT);

        // exit when close the window 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup the content panel 
        level = new Level(WIDTH, HEIGHT);
        level.setFocusable(true);
        level.requestFocusInWindow();
        this.add(level);
        level.setState(Level.MENU_STATE);

        // add key event handler
        level.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        level.getClaw().launch();
                        break;
                    case KeyEvent.VK_P:
                        level.pauseOrPlay();
                        break;     
                }
            }
			
        });

        JMenu iconsMenu = new JMenu("Menu");
        JMenuItem m;

        m = new JMenuItem("Instructions");
        m.addActionListener(this);
        iconsMenu.add(m);

        m = new JMenuItem("Pause or Play");
        m.addActionListener(this);
        iconsMenu.add(m);

        m = new JMenuItem("Exit");
        m.addActionListener(this);
        iconsMenu.add(m);

        JMenuBar mBar = new JMenuBar();
        mBar.add(iconsMenu);
        setJMenuBar(mBar);
		
		
        // add mouse event handler
        level.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX(), y = e.getY();    // where mouse clicked
                if (level.getState() == Level.MENU_STATE) {
                    if (x > 340 && x < 490 && y > 200 && y < 250) {      
                        // start the level 1
                        level.loadLevel(0);
                        level.start();
                    } else if (x > 340 && x < 490 && y > 280 && y < 330) {
                        dispose();
                    }
                } else if (level.getState() == Level.PLAYING_STATE) {
                    level.getClaw().launch();
                } else if (level.getState() == Level.GAME_OVER_STATE) {
                    int distance = (x - 412) * (x - 412) + (y - 432) * (y - 432);
                    if (distance < 1024) {
                        // start the level 1
                        level.loadLevel(0);
                        level.start();
                    }
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Instructions")) {
            JOptionPane.showMessageDialog(null, "Click or press the spacebar to launch the hook. Collect the gold, diamonds, or mice to reach the level objective. \nEach is worth a different value. The bigger the item, the longer it will take to drag to the surface. \nCareful around the TNT, it will blow up everything around it.\nIf time runs out before you reach the target score, the game ends.\nThe game can be paused at any point by either pressing P on the keyboard or by clicking in the menu.\nGood Luck!");
        } else if (actionCommand.equals("Pause or Play")) {
            level.pauseOrPlay();
        } else if (actionCommand.equals("Exit")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) throws IOException {
        GoldMiner goldMiner = new GoldMiner();
        goldMiner.setLocationRelativeTo(null);
        goldMiner.setVisible(true);
    }

}
