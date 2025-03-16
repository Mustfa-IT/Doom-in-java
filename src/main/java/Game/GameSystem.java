package Game;

import java.awt.Graphics;

public interface GameSystem {
    void update(double delta);
    void render(Graphics graphics);
    void cleanUp();
}

