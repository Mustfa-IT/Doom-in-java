package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameWindow extends JFrame {
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private InputManager inputManager;
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

        //Add the Input manager
        this.inputManager = new InputManager(canvas);

        // Always create on visible component
        canvas.createBufferStrategy(3);
        // Wait for strategy to be ready
        while (canvas.getBufferStrategy() == null) {
            Thread.yield();
        }
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
        SwingUtilities.invokeLater(super::dispose);
    }

    public void processInput() {
        inputManager.update();
    }
    public InputManager getInputManager() {
        return inputManager;
    }
}