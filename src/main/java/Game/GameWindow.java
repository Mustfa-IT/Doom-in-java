package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameWindow extends JFrame {
    private Canvas canvas;
    private BufferStrategy bufferStrategy;

    public void initialize() {
        setTitle("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setPreferredSize(new Dimension(800, 600));

        add(canvas);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }

    public BufferStrategy getBufferStrategy() {
        return bufferStrategy;
    }

    public int getCanvasWidth() {
        return canvas.getWidth();
    }

    public int getCanvasHeight() {
        return canvas.getHeight();
    }

    public void dispose() {
        SwingUtilities.invokeLater(() -> super.dispose());
    }

    public void processInput() {
        // Implement input handling here
    }
}