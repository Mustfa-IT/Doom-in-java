import Game.GameEngine;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        System.setProperty("sun.java2d.opengl", "true");  // For OpenGL pipeline

        GameEngine gameEngine = new GameEngine();
        gameEngine.start();
    }
}