import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

class Bomb {

    // x cordinate
    private double x;

    // y cordinate
    private double y;

    // size of the bomb
    private double size;

    // flag for explosion
    private boolean inExplosion;

    // the level containing this bomb 
    // need to remove minerals that are close to this bomb
    private Level level;

    // the bombs that are affected by this bomb 
    private List<Bomb> chainReaction = new ArrayList<Bomb>();

    // constructor
    Bomb(double x, double y, double size, Level level) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.level = level;
        this.inExplosion = false;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getSize() {
        return this.size;
    }

    void paint(Graphics g) {
        Image icon = new ImageIcon("res/images/mine_tnt.png").getImage();
        g.drawImage(icon, (int) (x - size), (int) (y - size), (int) (2 * size), (int) (2 * size), null);
    }

    void explode() {

        // remove any minerals that are close to this bomb
        for (int i = 0, len = level.mineList.size(); i < len; ++i) {
            if (distance(level.mineList.get(i).x, level.mineList.get(i).y, x, y) < 5 * size) {
                level.mineList.remove(i);
                --len;
                --i;
            }
        }

        // put all bombs that are close to this bomb to the chainReaction
        for (int i = 0, len = level.bombList.size(); i < len; ++i) {
            Bomb aBomb = level.bombList.get(i);
            if (distance(x, y, aBomb.x, aBomb.y) < (size + aBomb.size)
                    && aBomb.inExplosion == false) {
                level.bombList.remove(i);
                len = level.bombList.size();
                --i;
                aBomb.inExplosion = true;
                chainReaction.add(aBomb);
            }
        }

        // explode all bombs in the chain reaction
        for (int i = 0; i < chainReaction.size(); ++i) {
            chainReaction.get(i).explode();
        }

        // clear chain reaction
        chainReaction.clear();

        // notify the level to paint
        level.explosions.add(new Explosion(x, y, size));
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
