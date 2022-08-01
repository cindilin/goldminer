import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Gold extends Mineral {

    Gold(double x, double y, double size, int value) {
        super(x, y, size, value, 5);
    }

    void paint(Graphics g) {
        Image icon = new ImageIcon("res/images/mine_gold_b_1.png").getImage();
        g.drawImage(icon, 
                (int) (x - size), 
                (int) (y - size), 
                (int) (2 * size), 
                (int) (2 * size), 
                null);
    }
}
