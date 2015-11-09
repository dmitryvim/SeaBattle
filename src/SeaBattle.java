import Controller.Game;
import WindowView.SeaWindow;

import javax.swing.*;

/**
 * Created by mhty on 03.11.15.
 */
public class SeaBattle {



    static public void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                SeaWindow seaWindow = new SeaWindow("Морской бой", game);
                //
                game.go();
            }
        });
    }
}
