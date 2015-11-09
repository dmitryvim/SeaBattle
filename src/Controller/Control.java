package Controller;

import Model.Player;
import Model.ShootAnswer;

import java.awt.*;

/**
 * Created by mhty on 03.11.15.
 */
public class Control {
    public static boolean shoot(Player player, Point point) {
        System.out.println(player.name + " shooted " + point.x + " " + point.y);
        return (player.shoot(point) != ShootAnswer.FINISHED);

    }


}
