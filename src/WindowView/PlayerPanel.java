package WindowView;

import Controller.Control;
import Model.Drawable;
import Model.FieldPointStatus;
import Model.Player;
import Model.ShootAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by mhty on 03.11.15.
 */
public class PlayerPanel extends JPanel implements Drawable{
    int shiftX = 5;
    int shiftY = 5;
    int step = 20;
    boolean alive;

    Player player;
    Player attacker;

    public PlayerPanel (Player player, Player attacker) {
        super();
        this.player = player;
        player.setDrawable(this);
        this.attacker = attacker;
        alive = true;
        setShift(5, 5, 20);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (alive) {
                    shoot(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //            }
            }
        });

        System.out.println("Created player:" + player.name + ". Attacker: " + attacker.name + ".");
    }

    public void setShift(int shiftX, int shiftY, int step) {
        this.step = step;
        this.shiftX = shiftX;
        this.shiftY = shiftY;

        setSize(step * Player.FIELD_SIZE + shiftX, step * Player.FIELD_SIZE + shiftY);
    }



    private void shoot(Point point) {
        if (point.x > shiftX && point.x < shiftX + step * Player.FIELD_SIZE &&
                point.y > shiftY && point.y < shiftY + step * Player.FIELD_SIZE)
        attacker.setShoot(new Point((point.x - shiftX) / step, (point.y - shiftY) / step));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        FieldPointStatus[][] field = player.getField();
        for (int x = 0; x < Player.FIELD_SIZE; ++x) {
            for (int y = 0; y < Player.FIELD_SIZE; ++y) {

                switch (field[x][y]) {
                    case WATER:
                        break;
                    case BESIDE:
                        g.drawOval(shiftX + step * x + step / 2, shiftY + step * y + step / 2, 1, 1);
                        break;
                    case SHIP_ALIVE:
                        g.drawRect(shiftX + step * x, shiftY + step * y, step, step);
                        break;
                    case SHIP_DAMAGED:
                        g.drawRect(shiftX + step * x, shiftY + step * y, step, step);
                        g.drawLine(shiftX + step * x, shiftY + step * y, shiftX + step * x + step, shiftY + step * y + step);
                        g.drawLine(shiftX + step * x + step, shiftY + step * y, shiftX + step * x, shiftY + step * y + step);
                        break;
                }

            }
        }

    }


}
