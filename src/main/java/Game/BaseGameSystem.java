package Game;

import java.awt.*;
import Game.GameEngine.GameContext;

public abstract class BaseGameSystem implements GameSystem {
    private GameEngine gameEngine;
    private InputManager input;
    private boolean isInitialized = false;

    /**
     * Initializes system context with game dependencies.
     * Called automatically by {@link GameEngine} when system is added.
     *
     * @param context Container for core game references
     * @throws IllegalStateException if called more than once
     */
    final void initializeContext(GameContext context) {
        if (this.gameEngine != null) throw new IllegalStateException("Context already initialized");
        this.gameEngine = context.gameEngine();
        this.input = context.gameWindow().getInputManager();
        this.isInitialized = true;
        onContextInitialized();
    }

    /**
     * Called when graphics rendering is required.
     * Override to implement custom rendering logic.
     *
     * @param graphics Graphics context for the game window
     */
    @Override
    public void render(Graphics graphics) {
        return;
    }

    /**
     * Main game logic update method. Called ~60 times per second.
     *
     * @param delta Time in seconds since last update (typically ~0.016667s for 60Hz)
     */
    @Override
    public void update(double delta) {
        return;
    }


    /**
     * Cleanup resources when game stops. Called automatically by {@link GameEngine#stop}.
     */
    @Override
    public void cleanUp() {
    }

    /**
     * Post-initialization hook. Called after context is set up but before game starts.
     */
    protected void onContextInitialized() {
    } // Optional override
    // Accessors and utility methods

    ///Gets the Current {@link GameEngine} Instance.
    protected final GameEngine getGame() {
        return gameEngine;
    }

    ///Gets the current {@link InputManager} Instance.
    protected final InputManager getInput() {
        return input;
    }

    /// return True When the {@link GameContext} gets passed to the System by the {@link GameEngine} on add.
    protected final boolean isInitialized() {
        return isInitialized;
    }

    /// Gets the Current DeltaTime of the Game Loop.
    protected final float getDeltaTime() {
        return (float) gameEngine.getDeltaTime();
    }
}