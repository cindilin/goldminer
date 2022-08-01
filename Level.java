import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Level extends JPanel {

    // the states of each level
    public static final int MENU_STATE = 1;
    public static final int PLAYING_STATE = 2;
    public static final int PAUSE_STATE = 3;
    public static final int GAME_OVER_STATE = 4;

    private Image scoreboardImg = Toolkit.getDefaultToolkit().createImage("res/images/scoreboard.png");
    private Image gameoverImg = Toolkit.getDefaultToolkit().createImage("res/images/gameover1.jpg");
    private Image gameBackgroundImg = Toolkit.getDefaultToolkit().createImage("res/images/map_bg_0.png");
    private Image brownTimeLine = Toolkit.getDefaultToolkit().createImage("res/images/timebg.png");
    private Image greenTimeLine = Toolkit.getDefaultToolkit().createImage("res/images/timegood.png");
    private Image redTimeLine = Toolkit.getDefaultToolkit().createImage("res/images/timepoor.png");
    private Image buttonBackgroundImg = Toolkit.getDefaultToolkit().createImage("res/images/text-background.png");
    private Image timeLineCenterImg = Toolkit.getDefaultToolkit().createImage("res/images/timecenter.png");
    private Image replayButtonImg = Toolkit.getDefaultToolkit().createImage("res/images/replay.png");

    // maximum level number
    public static final int TOTAL_LEVEL_NUMBER = 5;

    // panel dimension
    private double width;
    private double height;

    // current game level
    private int levelNumber;

    // current state
    private int state;

    // the crawl for this level
    private Claw claw;

    int leftTime;

    // total time for this level
    int totalTime;

    // current score
    int score;

    // total score required for this level
    int targetScore;

    // golds and diamonds in this level
    List<Mineral> mineList = new ArrayList<Mineral>();

    // bombs in this level
    List<Bomb> bombList = new ArrayList<Bomb>();

    // this timer is used to repaint the panel every 100 ms 
    Timer timer;

    // bombs in explosion
    List<Explosion> explosions = new ArrayList<Explosion>();

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.claw = new Claw(width, 180);
        this.state = Level.MENU_STATE;
        this.requestFocus();
    }

    public Claw getClaw() {
        return this.claw;
    }

    public void setClaw(Claw claw) {
        this.claw = claw;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    // loadLevel the level 
    void loadLevel(int levelNumber) {

        // set the current level number
        this.levelNumber = levelNumber;

        // clear all mines
        this.mineList.clear();

        // clear all bombs
        this.bombList.clear();

        switch (levelNumber) {
            case 0:
                // set life time and total score
                this.leftTime = 200;
                this.totalTime = leftTime;
                this.targetScore = 400;

                // add bombs
                this.bombList.add(new Bomb(200, 300, 20, this));
                this.bombList.add(new Bomb(225, 300, 20, this));
                this.bombList.add(new Bomb(250, 300, 20, this));
                this.bombList.add(new Bomb(275, 300, 20, this));
                this.bombList.add(new Bomb(300, 300, 20, this));
                this.bombList.add(new Bomb(325, 300, 20, this));
                this.bombList.add(new Bomb(350, 300, 20, this));
                this.bombList.add(new Bomb(375, 300, 20, this));
                this.bombList.add(new Bomb(400, 300, 20, this));
                this.bombList.add(new Bomb(425, 300, 20, this));
                this.bombList.add(new Bomb(450, 300, 20, this));
                this.bombList.add(new Bomb(475, 300, 20, this));
                this.bombList.add(new Bomb(500, 300, 20, this));
                this.bombList.add(new Bomb(100, 400, 20, this));

                // add golds
                this.mineList.add(new Gold(500, 500, 50, 500));

                break;
            case 1:
                // set life time and total score
                this.leftTime = 200;
                this.totalTime = leftTime;
                this.targetScore = 1500;

                // add bomb
                this.bombList.add(new Bomb(400, 300, 20, this));

                // add mines
                this.mineList.add(new Rock(300, 400, 40, 20));
                this.mineList.add(new Gold(250, 280, 30, 200));
                this.mineList.add(new Diamond(600, 340, 10, 500));
                this.mineList.add(new Gold(300, 500, 40, 300));
                this.mineList.add(new Mouse(100, 450, 20, 501, 1, 10, true));

                break;
            case 2:
                // set life time and total score
                this.leftTime = 1000;
                this.totalTime = leftTime;
                this.targetScore = 4000;

                // add bomb
                this.bombList.add(new Bomb(300, 350, 20, this));

                // add mines
                this.mineList.add(new Rock(500, 350, 30, 15));
                this.mineList.add(new Gold(600, 420, 40, 400));
                this.mineList.add(new Gold(200, 500, 30, 300));
                this.mineList.add(new Gold(100, 240, 40, 400));
                this.mineList.add(new Gold(400, 550, 40, 400));
                this.mineList.add(new Gold(300, 250, 20, 150));
                this.mineList.add(new Diamond(700, 300, 10, 500));
                this.mineList.add(new Diamond(600, 550, 10, 500));
                this.mineList.add(new Mouse(100, 450, 15, 500, 1, 10, true));

                break;
            case 3:
                // set life time and total score
                this.leftTime = 1000;
                this.totalTime = leftTime;
                this.targetScore = 5000;

                // add bomb
                this.bombList.add(new Bomb(400, 375, 20, this));

                // add mines
                this.mineList.add(new Diamond(400, 300, 10, 500));
                this.mineList.add(new Diamond(350, 350, 10, 500));
                this.mineList.add(new Diamond(450, 350, 10, 500));
                this.mineList.add(new Diamond(350, 400, 10, 500));
                this.mineList.add(new Diamond(450, 400, 10, 500));
                this.mineList.add(new Diamond(400, 450, 10, 500));
                this.mineList.add(new Rock(120, 500, 30, 50));
                this.mineList.add(new Rock(170, 430, 30, 50));
                this.mineList.add(new Rock(530, 300, 10, 10));
                this.mineList.add(new Rock(420, 500, 20, 30));

                break;
            case 4:
                // set life time and total score
                this.leftTime = 1000;
                this.totalTime = leftTime;
                this.targetScore = 9000;

                // add bomb
                this.bombList.add(new Bomb(260, 525, 20, this));
                this.bombList.add(new Bomb(530, 375, 20, this));

                // add mines
                this.mineList.add(new Mouse(120, 200, 15, 1, 1, 5, false));
                this.mineList.add(new Mouse(250, 250, 15, 1, -1, 10, false));
                this.mineList.add(new Mouse(700, 300, 15, 1, -1, 5, false));
                this.mineList.add(new Mouse(260, 350, 15, 501, 1, 10, true));
                this.mineList.add(new Mouse(200, 400, 15, 501, -1, 15, true));
                this.mineList.add(new Mouse(450, 450, 15, 1, -1, 10, false));
                this.mineList.add(new Mouse(230, 500, 15, 501, 1, 10, true));
                this.mineList.add(new Mouse(100, 550, 15, 501, -1, 15, true));

                break;
        }

    }

    // switch the states of pause and playing
    void pauseOrPlay() {
        if (this.state == Level.PLAYING_STATE) {
            this.state = Level.PAUSE_STATE;
        } else if (this.state == Level.PAUSE_STATE) {
            this.state = Level.PLAYING_STATE;
        }
    }


    void gameOver() {
        timer.cancel();
        this.score = 0;
        this.state = Level.GAME_OVER_STATE;
        this.mineList.clear();
        this.claw = new Claw(width, 180);
        loadLevel(0);
    }

    void nextLevel() {
        if (this.score < this.targetScore) {
            // stop the timer
            timer.cancel();

            // clear bombs
            bombList.clear();

            // set the state and score
            gameOver();
        } else {
            this.levelNumber++;
            if (levelNumber < TOTAL_LEVEL_NUMBER) {
                loadLevel(this.levelNumber);
            } else {
                this.levelNumber = 0;
                this.score = 0;
                loadLevel(levelNumber);
            }
        }
    }

    void refreshPanel() {

        // refresh the panel only if the state is playing
        if (state != Level.PLAYING_STATE) {
            return;
        }

        // if all minerals are elimniated or no leftTime, then move to the next level 
        if ((mineList.isEmpty() && !claw.hasMineral()) || leftTime <= 0) {
            nextLevel();
        }

        // decrease the leftTime by 1
        leftTime--;

        // repaint the claw 
        claw.refresh(this);

        // repaint the mice
        for (Mineral mineral : mineList) {
            if (mineral instanceof Mouse) {
                ((Mouse) mineral).runMouse();
            }
        }

        repaint();
    }

    void start() {
        state = Level.PLAYING_STATE;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshPanel();
            }
        }, 0, 100);
    }

    @Override
    public void paint(Graphics g) {

        // clear the panel
        g.clearRect(0, 0, (int) width, (int) height);

        // the percent of unused time
        double leftPercent = 1.0;

        switch (state) {
            case PLAYING_STATE:
            case PAUSE_STATE:

                // paint the background
                g.drawImage(gameBackgroundImg, 0, 0, (int) width, (int) height, this);

                // paint the score board
                g.drawImage(scoreboardImg, 30, 20, 145, 80, this);

                // paint the score
                g.setFont(new Font("Tahoma", Font.BOLD, 28));
                g.setColor(Color.white);
                g.drawString(String.valueOf(targetScore), 75, 50);
                g.drawString(String.valueOf(score), 75, 90);

                // paint the background time line
                g.drawImage(brownTimeLine, 15, 115, 165, 15, this);

                // update the timeline after 2 seconds
                if (totalTime - leftTime > 5) {
                    leftPercent = (1.0 * leftTime) / (1.0 * totalTime);
                }

                // paint 
                g.drawImage(redTimeLine,
                        (int) (20 + 165 * (1.0 - leftPercent)),
                        115,
                        180,
                        130,
                        (int) ((1.0 - leftPercent) * greenTimeLine.getWidth(this)),
                        0,
                        (int) greenTimeLine.getWidth(this),
                        (int) greenTimeLine.getHeight(this),
                        this);

                // paint 
                if ((1.0 * leftTime) / (1.0 * totalTime) >= 0.3) {
                    g.drawImage(greenTimeLine,
                            (int) (20 + 165 * (1.0 - leftPercent)),
                            115,
                            180,
                            130,
                            (int) ((1.0 - leftPercent) * greenTimeLine.getWidth(this)),
                            0,
                            (int) greenTimeLine.getWidth(this),
                            (int) greenTimeLine.getHeight(this),
                            this);
                }
                g.setColor(Color.black);

                // repaint the claw
                try {
                    claw.paint(g);
                } catch (IOException error) {
                }

                // repaint minerals
                for (Mineral mineral : mineList) {
                    mineral.paint(g);
                }

                // repaint bombs
                for (Bomb bomb : bombList) {
                    bomb.paint(g);
                }

                // repaint exploded bomb
                for (int i = 0, len = explosions.size(); i < len; ++i) {
                    if (explosions.get(i) != null) {
                        explosions.get(i).paint(g);
                        if (explosions.get(i).isEnd()) {
                            explosions.remove(i);
                            --i;
                            --len;
                        }
                    }
                }

                g.setColor(Color.red);
                break;

            case MENU_STATE:
                // paint the background
                g.drawImage(gameBackgroundImg, 0, 0, (int) width, (int) height, this);

                // paint the Start button
                g.drawImage(buttonBackgroundImg, 330, 210, 150, 50, this);
                g.setFont(new Font("Tahoma", Font.BOLD, 28));
                g.setColor(Color.white);
                g.drawString("Start", 370, 242);

                // paint the Quit button
                g.drawImage(buttonBackgroundImg, 330, 290, 150, 50, this);
                g.drawString("Quit", 370, 322);

                break;
            case GAME_OVER_STATE:
                // paint the background for game over
                g.drawImage(gameoverImg, 0, 0, (int) width, (int) height, this);

                // paint the retry button
                g.drawImage(replayButtonImg, 380, 400, 64, 64, this);

                break;
        }
    }

}
