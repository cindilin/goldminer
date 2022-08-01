import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Claw {
    
    // status of the claw
    private static final int WAIT = 1;      // swing at the orginal location
    private static final int FORWARD = 2;   // throw out
    private static final int BACKWARD = 3;  // pull back
    
    // x coordinate of the claw
    private double sourceX; 
    
    // y coordinate of the claw
    private double sourceY; 
    
    // the rotated angle 
    private double theta = 0.0; 
    
    // the length of the rope 
    private double ropeLength = 0.0; 
    
    // the size of the claw
    private final double size = 30.0; 
    
    // the weight of the claw
    private double weight = 800.0;

    // caught mineral
    private Mineral mineral;

    // moving state
    private int state; 

    // moving direction when wait; 1 or -1
    int waitDirection = 1;

    // width is the width of the panel
    // height is the y coordinate where the claw locates
    public Claw(double width, double height) {
        sourceX = width / 2;
        sourceY = height; 
        state = WAIT;
    }

    double getRopeX() {
        return sourceX + ropeLength * Math.cos(theta);
    }

    double getRopeY() {
        return sourceY + ropeLength * Math.sin(theta);
    }

    double getWeight() {
        return mineral == null ? 
                weight : 
                weight + mineral.density * mineral.size * mineral.size;
    }

    double getPullSpeed() {
        return 40000.0 / getWeight();
    }

    double getPushSpeed() {
        return 20.0;
    }

    boolean hasMineral() {
        return mineral != null;
    }

    // check whether this claw catches the specified mineral
    boolean caughtMineral(Mineral mineral) {
        if (distance(getRopeX(), getRopeY(), mineral.x, mineral.y) < (size / 2 + mineral.size)) {
            this.mineral = mineral;
            state = BACKWARD;
            return true;
        } else {
            return false;
        }
    }

    // check whether this claw touch the specified bomb
    boolean touchBomb(Bomb bomb) {
        if (distance(getRopeX(), getRopeY(), bomb.getX(), bomb.getY()) < (size / 2 + bomb.getSize())) {
            state = BACKWARD;
            return true;
        } else {
            return false;
        }
    }

    // update the location and angle of this claw
    // the speed depends on the weight of the claw and caught mineral
    void refresh(Level level) {
        switch (state) {
            case WAIT:
                theta += waitDirection * Math.PI / GoldMiner.PERIOD;

                // control the direction of claw
                // change the direction when it reaches the end
                if (theta >= Math.PI * 19 / 20) {
                    waitDirection = -1;
                } else if (theta <= Math.PI / 20) {
                    waitDirection = 1;
                }
                break;

            case FORWARD:
                ropeLength += getPushSpeed();

                // check if the rope reaches to the border of the panel
                if (getRopeX() < 50 || getRopeX() > 750 || getRopeY() > 550) {
                    state = BACKWARD;
                    break;
                }

                // check if it catches a mineral 
                for (int i = 0; i < level.mineList.size(); i++) {
                    Mineral aMineral = level.mineList.get(i);
                    if (caughtMineral(aMineral)) {
                        aMineral.caught(level, i);
                        break;
                    }
                }

                // check if it touches a bomb
                for (int i = 0, len = level.bombList.size(); i < len; ++i) {
                    Bomb aBomb = level.bombList.get(i);
                    if (touchBomb(aBomb)) {
                        level.bombList.remove(i);
                        aBomb.explode();
                        len = level.bombList.size();
                        --i;
                    }
                }
                break;

            case BACKWARD:
                ropeLength -= getPullSpeed();

                // check it comes back if it reaches to the end or catches a mineral
                if (mineral != null) {
                    // move the mineral
                    mineral.refresh(
                            getRopeX() + size * Math.cos(theta),
                            getRopeY() + size * Math.sin(theta));
                }

                // add the score when the caught the mineral is back
                // change the state to WAIT
                if (ropeLength <= 0) {
                    if (mineral != null) {
                        level.score += mineral.value;
                        mineral = null;
                    }
                    ropeLength = 0;
                    state = WAIT;
                }
                break;
        }
    }

    // paint the rope, claw and caught mineral
    void paint(Graphics g) throws IOException {
    
        switch (state) {
            case BACKWARD:
                // paint the caught mineral
                if (mineral != null) {
                    mineral.paint(g);	
                }
            default:
                // paint the rope
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2.0f));  
                g2.drawLine((int) sourceX, (int) (sourceY), (int) getRopeX(), (int) getRopeY());
                
                // paint the claw
                BufferedImage clawImage = ImageIO.read(new File("res/images/hook2.png"));
                BufferedImage rotatedImage = rotateImage(clawImage, theta);
                g.drawImage(rotatedImage,
                        (int) (getRopeX() - size), (int) (getRopeY() - size), 2 * (int) size, 2 * (int) size, null);
        }
    }

    void launch() {
        if (state == WAIT) {
            state = FORWARD;
        }
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    // rotate the image by the angle theta
    private static BufferedImage rotateImage(final BufferedImage bufferedimage, final double theta) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(theta, w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}


