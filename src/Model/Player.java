package Model;



import java.awt.*;
import java.util.*;

/**
 * Created by mhty on 16.10.15.
 */
public abstract class Player {

    final public static int FIELD_SIZE = 10;

    public String name;
    public boolean turn;

    private Drawable drawable = null;


    Ship[] ships;
    LinkedList<Point> shots;



    public Player(int[] shipCount, String name) {
        this.name = new String(name);

        int size = 0;
        for (int i = 1; i < shipCount.length; ++i) {
            size += shipCount[i];
        }
        ships = new Ship[size];
        int index = 0;
        for (int shipSize = 1; shipSize < shipCount.length; ++shipSize) {
            for (int i = 0; i < shipCount[shipSize]; ++i, ++index) {
                ships[index] = new Ship(shipSize);
            }
        }
        shots = new LinkedList<Point>();
    }


    public boolean isAlive() {
        for (Ship ship: ships) {
            if (ship.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public ShootAnswer shoot(Point point) {
        shots.add(new Point(point));
        for (Ship ship: ships) {
            ShootAnswer answer = ship.shoot(point);
            if (answer != ShootAnswer.BESIDE) {
                repaint();
                return (answer == ShootAnswer.DAMAGED) ? ShootAnswer.DAMAGED : (isAlive() ? ShootAnswer.KILLED : ShootAnswer.FINISHED);
            }
        }
        repaint();
        return ShootAnswer.BESIDE;
    }

    private boolean checkShipPosition(int index) {
        Point front = ships[index].getFrontPoint();
        Point back = ships[index].getBackPoint();
        if (back.x < 0 || front.x >= FIELD_SIZE || back.y < 0 || front.y > FIELD_SIZE) {
            ships[index].unpos();
            return false;
        }

        for (int i = 0; i < ships.length; ++i) {
            if (index != i && ships[index].isInZone(ships[i])) {
                ships[index].unpos();
                return false;
            }
        }

        return true;
    }
    
    public boolean moveShip(int index, Point point, Direction direction) {
        ships[index].move(point, direction);
        Point front = ships[index].getFrontPoint();
        
        return checkShipPosition(index);
        
    }
    
    public boolean moveShipRandom(int index) {
        Random random = new Random();
        boolean horizontal = random.nextBoolean();
        int size = ships[index].size();
        return moveShip(index, new Point(random.nextInt(horizontal ? FIELD_SIZE - size : FIELD_SIZE), 
                random.nextInt(horizontal ? FIELD_SIZE : FIELD_SIZE - size)), horizontal ? Direction.HORIZONTAL : Direction.VERTICAL);
  
    }
    
    public boolean isPosed() {
        for (Ship ship: ships) {
            if (!ship.isPosed()) {
                return false;
            }
        }
        return true;
    }
    
    public void unpos() {
        for (Ship ship: ships) {
            ship.unpos();
        }
    }
    
    public void moveShipsRandom() {
        unpos();
        
        for (int i = ships.length - 1; i >= 0 ; --i) {
            while (!moveShipRandom(i)) {};
        }
    }
    public FieldPointStatus[][] getField(boolean mine, boolean drawUnposed) {
        FieldPointStatus[][] field = new FieldPointStatus[FIELD_SIZE][FIELD_SIZE];
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++) {
                field[x][y] = FieldPointStatus.WATER;
            }
        }

        ListIterator<Point> shotIterator = shots.listIterator();
        while (shotIterator.hasNext()) {
            Point p = shotIterator.next();
            field[p.x][p.y] = FieldPointStatus.BESIDE;
        }

        for (Ship ship: ships) {
            if (drawUnposed || ship.isPosed()) {
                Point p = ship.getPosition();
                for (int i = 0; i < ship.size(); i++) {
                    field[p.x][p.y] = (!ship.getAlive(i)) ? FieldPointStatus.SHIP_DAMAGED : (mine ? FieldPointStatus.SHIP_ALIVE : FieldPointStatus.WATER);
                    if (ship.getDirection() == Direction.HORIZONTAL) {
                        p.x++;
                    } else {
                        p.y++;
                    }
                }
            }
        }

        return field;
    }


    public FieldPointStatus[][] getField() {
        return getField(false, false);
    }


    private static String shootAnswerToString(ShootAnswer shootAnswer) {
        String s = new String();
        switch (shootAnswer) {
            case BESIDE:
                s = "мимо\n";
                break;
            case DAMAGED:
                s = "попал\n";
                break;
            case KILLED:
                s = "убил\n";
                break;
            case FINISHED:
                s = "победил\n";
                break;
        }
        return s;
    }

    public static void printField(FieldPointStatus[][] field) {
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++) {
                System.out.print(field[y][x]);
            }
            System.out.println();
        }
    }

    public void printShipInfo() {
        for (Ship ship: ships) {
            System.out.print(ship);
        }
        System.out.println(isAlive());
    }

    public abstract Point getShoot();
    public abstract void setShoot(Point point);

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void repaint() {
        if (drawable != null) {
            drawable.repaint();
        }
    }

}
