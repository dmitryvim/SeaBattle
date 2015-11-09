package Model;

import java.awt.*;
import java.util.Random;

/**
 * Created by mhty on 03.11.15.
 */
class Ship {



    private Point position;
    private Direction direction;
    private boolean[] alive;
    private boolean posed;

    Ship(int size) {
        position = new Point(0, 0);
        posed = false;
        alive = new boolean[size];
        for (int i = 0; i < alive.length; ++i) {
            alive[i] = true;
        }

    }

    void move(Point point, Direction _direction) {
        posed = true;
        position = point;
        direction = _direction;
    }

    void moveRandom() {
        Random random = new Random();
        boolean horizontal = random.nextBoolean();
        direction = horizontal ? Direction.HORIZONTAL : Direction.VERTICAL;
        position.x = random.nextInt(horizontal ? Player.FIELD_SIZE - size() : Player.FIELD_SIZE);
        position.y = random.nextInt(horizontal ? Player.FIELD_SIZE : Player.FIELD_SIZE - size());
    }

    public int size() {
        return alive.length;
    }

    public boolean isAlive() {
        for (boolean e: alive) {
            if (e) {
                return true;
            }
        }
        return false;
    }

    private int getPointIndex(Point point) {
        if ( (direction == Direction.HORIZONTAL && point.y != position.y) ||
                (direction == Direction.VERTICAL && point.x != position.x)) {
            return -1;
        }
        int n = (direction == Direction.HORIZONTAL) ? point.x - position.x : point.y - position.y;
        return (n >= 0 || n < alive.length) ? n : -1;
    }

    public Point getPoint(int index) {
        return new Point((direction == Direction.HORIZONTAL) ? position.x + index : position.x,
                (direction == Direction.VERTICAL) ? position.y + index : position.y);
    }

    public Point getBackPoint() {
        return getPoint(0);
    }

    public Point getPosition() {
        return new Point(position);
    }

    public Point getFrontPoint() {
        return getPoint(size() - 1);
    }

    public boolean getAlive(int index) {
        return (index >= 0 && index < alive.length) ? alive[index] : false;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean containPoint(Point point) {
        return (getPointIndex(point) > -1);
    }

    private boolean shootPoint(Point point) {
        int index = getPointIndex(point);
        if (index < 0 || index >= size()) {
            return false;
        }
        alive[index] = false;
        return true;
    }

    public ShootAnswer shoot(Point point) {
        if (shootPoint(point)) {
            return isAlive() ? ShootAnswer.DAMAGED : ShootAnswer.KILLED;
        } else {
            return ShootAnswer.BESIDE;
        }
    }

    public boolean isInZone(Point point) {
        if (!posed) {
            return false;
        }

        if (direction == Direction.HORIZONTAL) {
            return (point.y >= position.y - 1) && (point.y <= position.y + 1) &&
                    (point.x >= position.x - 1) && (point.x <= position.x + size() + 1);
        } else {
            return (point.x >= position.x - 1) && (point.x <= position.x + 1) &&
                    (point.y >= position.y - 1) && (point.y <= position.y + size() + 1);
        }
    }

    public boolean isInZone(Ship ship) {
        if (!posed) {
            return false;
        }

        for (int i = 0; i < ship.size(); ++i) {
            if (isInZone(ship.getPoint(i))) {
                return true;
            }
        }
        return false;
    }

    public void unpos() {
        posed = false;
    }

    public boolean isPosed() {
        return posed;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("position(");
        stringBuilder.append(position.x).append(", ").append(position.y).append(") ").
                append(direction == Direction.HORIZONTAL ? "horizontal" : "vertical").append(" [");
        for (int i = 0; i < alive.length; ++i) {
            stringBuilder.append(alive[i] ? "H" : "X");
        }
        stringBuilder.append("]\n");

        return stringBuilder.toString();
    }
};
