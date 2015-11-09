package Controller;

import Model.Player;
import Model.PlayerAI;
import Model.PlayerHuman;
import Model.ShootAnswer;

import java.awt.*;

/**
 * Created by mhty on 03.11.15.
 */
public class Game {
    Player playerLeft;
    Player playerRight;

    boolean turnLeft;
    boolean finished;



    public Game() {
        playerLeft = new PlayerHuman(new int[]{0, 4, 3, 2, 1}, "left");
        playerRight = new PlayerAI(new int[]{0, 4, 3, 2, 1}, "right");
        turnLeft = true;
        finished = false;
    }

    public Player getPlayerLeft() {
        return playerLeft;
    }

    public Player getPlayerRight() {
        return playerRight;
    }

    public Player getTurnPlayer() {
        return (turnLeft) ? playerLeft : playerRight;
    }

    public Player getWaitingPlayer() {
        return (turnLeft) ? playerRight : playerLeft;
    }

    public boolean shoot() {

        Point point = getTurnPlayer().getShoot();
        ShootAnswer answer = getWaitingPlayer().shoot(point);
        System.out.println(getTurnPlayer().name + " attaks " + getWaitingPlayer().name + " position: " + point + " answer: " + answer);

        switch (answer) {
            case BESIDE:
                turnLeft = !turnLeft;
                break;
            case DAMAGED:
                break;
            case KILLED:
                break;
            case FINISHED:
                finished = true;
                break;
        }
        return !finished;
    }

    public void go() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (shoot()) {};
                System.out.println("FINISHED\n");
            }
        }).start();

    }
}
