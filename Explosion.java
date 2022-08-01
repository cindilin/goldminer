
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

class Explosion {

    double x;
    double y;
    double size;
    int effectCount;

    Explosion(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = 3 * size;
        this.effectCount = 1;
    }

    void paint(Graphics g) {
        Image icon = new ImageIcon("res/images/effect_blast_" + effectCount + ".png").getImage();
        g.drawImage(icon, (int) (x - size), (int) (y - size), (int) (3 * size), (int) (3 * size), null);
        ++this.effectCount;
    }

    boolean isEnd() {
        return effectCount == 6; 
    }
}

