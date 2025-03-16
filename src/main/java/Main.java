import Game.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();

        new Timer(5000, e -> {
            game.stop();
            ((Timer) e.getSource()).stop();
        }).start();

    }
}