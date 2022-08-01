
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Rock extends Mineral {

    Rock(double x, double y, double size, int value) {
        super(x, y, size, value, 5);
    }

    void paint(Graphics g) {
        Image icon = new ImageIcon("res/images/mine_rock_b.png").getImage();
        g.drawImage(icon, 
                (int) (x - size), 
                (int) (y - size), 
                (int) (2 * size), 
                (int) (2 * size), 
                null);
    }
}



