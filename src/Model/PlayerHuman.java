package Model;

import java.awt.*;

/**
 * Created by mhty on 03.11.15.
 */
public class PlayerHuman extends Player{
    private volatile boolean waitShoot;
    Point shootPoint;

    public PlayerHuman(int[] shipCount, String name) {
        super(shipCount, name);
        moveShipsRandom();
    }

    @Override
    public FieldPointStatus[][] getField() {
        return super.getField(true, false);
    }

    @Override
    public Point getShoot() {
        waitShoot = true;
        System.out.print("\n[" + Thread.currentThread().getName() + "] waiting for click ...\n");
        while(waitShoot);
        System.out.print("\n ...click got\n");
        return shootPoint;
    };

    @Override
    public void setShoot(Point point) {
        shootPoint = point;
        waitShoot = false;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
