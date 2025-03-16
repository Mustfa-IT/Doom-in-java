package Game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private double dt;
    private final List<GameSystem> systems;
    private boolean running;
    private Thread gameThread;
    private final int TARGET_FPS = 60;
    private final long TARGET_FRAME_TIME_NANOS = 1_000_000_000 / TARGET_FPS;

    public Game() {
        systems = new ArrayList<>();
        running = false;
    }

    public void start() {
        running = true;
        gameWindow = new GameWindow();
        gameWindow.initialize();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
        try {
            // Remove thread interruption
            if (gameThread != null && Thread.currentThread() != gameThread) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        long previousTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            dt = (currentTime - previousTime) / 1_000_000_000.0;
            previousTime = currentTime;

            try {
                update(dt);
            } catch (Exception e) {
                handleException("update", e);
            }

            try {
                render();
            } catch (Exception e) {
                handleException("render", e);
            }

            regulateFrameRate(currentTime);
        }
        performCleanup();
    }

    private void regulateFrameRate(long currentTime) {
        long frameTime = System.nanoTime() - currentTime;
        long sleepTime = TARGET_FRAME_TIME_NANOS - frameTime;
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime / 1_000_000, (int) (sleepTime % 1_000_000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void update(double delta) {
        if (delta > 0.1) return;
        if (gameWindow != null) {
            gameWindow.processInput();
        }
        systems.forEach(system -> system.update(delta));
    }

    void render() {
        if (gameWindow == null) return;

        BufferStrategy strategy = gameWindow.getBufferStrategy();
        if (strategy == null) return;

        do {
            do {
                Graphics graphics = strategy.getDrawGraphics();
                try {
                    clearScreen(graphics);
                    systems.forEach(system -> system.render(graphics));
                } finally {
                    graphics.dispose();
                }
            } while (strategy.contentsRestored());
            strategy.show();
        } while (strategy.contentsLost());
    }

    private void clearScreen(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, gameWindow.getCanvasWidth(), gameWindow.getCanvasHeight());
    }

    private void performCleanup() {
        try {
            cleanUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void cleanUp() throws InterruptedException {
        systems.forEach(GameSystem::cleanUp);
        if (gameWindow != null) {
            gameWindow.dispose();
        }
        if (gameThread != null && Thread.currentThread() != gameThread) {
            gameThread.join();
        }
    }

    private void handleException(String phase, Exception e) {
        System.err.println("Error during " + phase + ": " + e.getMessage());
        e.printStackTrace();
    }

    public void addSystem(GameSystem system) {
        if (system != null && !systems.contains(system)) {
            systems.add(system);
        }
    }

    public void removeSystem(GameSystem system) {
        if (system != null) {
            systems.remove(system);
        }
    }

    public boolean isRunning() {
        return running;
    }
}