package framework.fallingwithstyle;

import framework.Game;
import framework.Graphics;
import framework.Graphics.ImageFormat;
import framework.Screen;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
       
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.welcomeScreen = g.newImage("welcomeScreen.png", ImageFormat.RGB565);
        Assets.load(g);
       
        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
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

    }
}