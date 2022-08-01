import java.awt.Graphics;

/*
    each mine will be painted as a square at the center (x, y)
    each side is 2r 
*/
public abstract class Mineral {
   
    // x coordinate of the center
    double x;   
    
    // y coordinate of the center
    double y;
    
    // half of the side
    double size;
    
    // the value of this mine for score 
    int value;
    
    // density is used to calulate the speed of pulling
    int density;

    Mineral(double x, double y, double size, int value, int density) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.value = value;
        this.density = density;
    }

    abstract void paint(Graphics g);

    // reset the location during pulling
    void refresh(double newX, double newY) {
        x = newX;
        y = newY;
    }

    void caught(Level level, int i) {
        level.mineList.remove(i);
    }
}




