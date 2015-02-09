package framework.fallingwithstyle;

import java.util.List;

import framework.Game;
import framework.Graphics;
import framework.Image;
import framework.Screen;
import framework.Input.TouchEvent;
import framework.implementation.AndroidGraphics;

public class MainMenuScreen extends Screen {
	
	private Image button;
    public MainMenuScreen(Game game) {
        super(game);
        button = Assets.playButton;
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if (inBounds(event, (g.getWidth() - button.getWidth())/ 2, (g.getHeight() - button.getHeight()) / 2, button.getWidth(), button.getHeight())) {
                    game.setScreen(new GameScreen(game));
                }

            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width,
            int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        Image welcome = Assets.welcomeScreen;
        ((AndroidGraphics)g).drawScaledImage(welcome, 0, 0, g.getWidth(), g.getHeight(), 0, 0, welcome.getWidth(), welcome.getHeight());
        int bx = (g.getWidth() - button.getWidth())/ 2;
        int by = (g.getHeight() - button.getHeight()) / 2;
        g.drawImage(button, bx, by);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
