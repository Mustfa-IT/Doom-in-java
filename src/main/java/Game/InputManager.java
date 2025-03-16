package Game;

import java.awt.Component;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Input handling system. Tracks:
 * - Keyboard state (pressed/consumed keys)
 * - Mouse position and button states
 * - Mouse drag deltas
 */
public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Set<Integer> pressedMouseButtons = new HashSet<>();
    private final Set<Integer> consumedKeys = new HashSet<>();
    private int mouseX, mouseY;
    private int mouseDragX, mouseDragY;

    public InputManager(Component component) {
        component.addKeyListener(this);
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        component.setFocusable(true);
        component.requestFocusInWindow();
    }

    // Keyboard state methods
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public boolean wasKeyJustPressed(int keyCode) {
        return pressedKeys.contains(keyCode) && !consumedKeys.contains(keyCode);
    }

    public void consumeKey(int keyCode) {
        consumedKeys.add(keyCode);
    }

    // Mouse state methods
    public boolean isMouseButtonPressed(int button) {
        return pressedMouseButtons.contains(button);
    }

    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }
    public int getMouseDragX() { return mouseDragX; }
    public int getMouseDragY() { return mouseDragY; }

    // Update should be called at the end of each frame
    public void update() {
        consumedKeys.clear();
        mouseDragX = 0;
        mouseDragY = 0;
    }

    // Event listeners
    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        consumedKeys.remove(e.getKeyCode());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressedMouseButtons.add(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedMouseButtons.remove(e.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseDragX = e.getX() - mouseX;
        mouseDragY = e.getY() - mouseY;
    }

    // Unused event methods
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}