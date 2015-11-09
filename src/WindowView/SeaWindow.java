package WindowView;

import Controller.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mhty on 03.11.15.
 */
public class SeaWindow extends JFrame{



    public SeaWindow(String text, Game game) {
        super(text);
        setSize(600, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel(game.getPlayerLeft().name));
        panel.add(new JLabel(game.getPlayerRight().name));

        panel.add(new PlayerPanel(game.getPlayerLeft(), game.getPlayerRight()));
        panel.add(new PlayerPanel(game.getPlayerRight(), game.getPlayerLeft()));

        add(panel);

    }

}
