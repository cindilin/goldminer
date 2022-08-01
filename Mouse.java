import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Mouse extends Mineral {

    private int movingDirection;   // 1 or -1
    private double movingSpeed;    // pixles every step
    private boolean isCaught;      // is isCaught by the claw
    private boolean withDiamond;   // true for a mouse with diamond
    private int paintCount;        // mouse image for animation effect 

    Mouse(double x,
            double y,
            double size,
            int value,
            int movingDirection,
            double movingSpeed,
            boolean withDiamond) {
        super(x, y, size, value, 1);
        this.movingDirection = movingDirection;
        this.movingSpeed = movingSpeed;
        this.isCaught = false;
        this.withDiamond = withDiamond;
        this.paintCount = 0;
    }

    // update the location 
    void runMouse() {
        x += movingDirection * movingSpeed;
        if (x <= 0 || x >= 800) {
            // change the moving direction
            movingDirection = -movingDirection;
        }
    }

    void caught(Level level, int i) {
        level.mineList.remove(i);
        isCaught = true;
    }

    void paint(Graphics g) {
        String suffix;
        if (movingDirection > 0) {
            suffix = new String("_right.png");
        } else {
            suffix = new String("_left.png");
        }
        String prefix;
        if (withDiamond) {
            prefix = new String("res/images/WithDiamond_laoshuzou");
        } else {
            prefix = new String("res/images/laoshuzou");
        }
        Image icon = new ImageIcon(prefix + (paintCount + 1) + suffix).getImage();

        // move the mouse that is not caught
        if (!isCaught) {
            paintCount += movingSpeed / 7 + 1; // move faster when moving speed is faster
            paintCount = paintCount % 4;
        }
        g.drawImage(icon, (int) (x - 2 * size), (int) (y - size), (int) (4 * size), (int) (2 * size), null);
    }
}
