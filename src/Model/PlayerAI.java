package Model;

import java.awt.*;
import java.util.Random;

/**
 * Created by mhty on 03.11.15.
 */
public class PlayerAI extends Player{
    public PlayerAI(int[] shipCount, String name) {
        super(shipCount, name);
        moveShipsRandom();
    }

    @Override
    public FieldPointStatus[][] getField() {

        return super.getField(false, false);
    }

    @Override
    public Point getShoot(){
        Random random = new Random();
        return new Point(random.nextInt(FIELD_SIZE), random.nextInt(FIELD_SIZE));
    };

    @Override
    public void setShoot(Point point) {
        //
    }
}
