package Systems;

import Game.BaseGameSystem;

import java.awt.event.KeyEvent;

public class TestInputManagerSystem extends BaseGameSystem {
    public TestInputManagerSystem() {}

    @Override
    public void update(double delta) {
        if(getInput().isKeyPressed(KeyEvent.VK_A)){
            System.out.println("Key Pressed");
        }
        super.update(delta);
    }
}
