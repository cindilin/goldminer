
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Diamond extends Mineral {

    Diamond(double x, double y, double size, int value) {
        super(x, y, size, value, 5);
    }

    void paint(Graphics g) {
        Image icon = new ImageIcon("res/images/mine_diamond_1.png").getImage();
        g.drawImage(icon,
                (int) (x - size),
                (int) (y - size),
                (int) (2 * size),
                (int) (2 * size),
                null);
    }
}
