package framework.fallingwithstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import framework.Game;
import framework.Graphics;
import framework.Image;
import framework.Input.TouchEvent;
import framework.Screen;
import framework.implementation.AndroidGraphics;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	private ArrayList<Bird> birds;
	private AltitudeBox altitudeBox;
	
	GameState state = GameState.Ready;
	public static int width;
	public static int height;

	// Variable Setup

	private static Background bg1, bg2;

	private Griffin griffin;
	private Image ground;
	private int groundHeight;
	private boolean falling = true;
	private ChuteButton button;
	Paint paint, paint2;

	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here

		bg1 = new Background(0, 0);
		bg2 = new Background(0, 1440);

		button = new ChuteButton(game.getGraphics().getWidth() - Assets.button.getWidth(), 0);
		
		griffin = new Griffin();
		birds = new ArrayList<Bird>();
		for (int i = 0; i < 7; i++)
		{
			birds.add(new Bird(this));
		}
		altitudeBox = new AltitudeBox(0, 0);
		ground = Assets.ground;
		groundHeight = ground.getHeight();

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);

	}

	@Override
	public void update(float deltaTime) {
		List touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List touchEvents, float deltaTime) {

		// 1. All touch input is handled here:
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = (TouchEvent) touchEvents.get(i);
			// if it is, figure out where, then turn and move the ship in that direction.
			if (falling && event.type == TouchEvent.TOUCH_DOWN) {
				Rect buttonRect = button.getBounds();
				
				if (buttonRect.contains(event.x, event.y))
				{
					griffin.deployChute();
				}
				if (!griffin.chuteDeployed())
				{
					int x = event.x;
					if (x > griffin.getX() + griffin.getImage().getWidth()/2)
					{
						griffin.accelerateLeftRight(6);
					}
					else
					{
						griffin.accelerateLeftRight(-6);
					}
				}
			}
			
			if (falling && event.type == TouchEvent.TOUCH_UP)
			{
				griffin.accelerateLeftRight(0);
			}

		}

		// 2. Call individual update() methods here.
		// This is where all the game updates happen.
		if (falling)
		{
			griffin.update(deltaTime);
			for(Bird bird : birds)
			{
				bird.update(deltaTime, griffin.getVelocityX(), griffin.getVelocityY());
			}
			
			altitudeBox.update((int)griffin.getAltitude());
			
			
			if (griffin.getAltitude() <= griffin.getChuteSuggestion())
			{
				button.flash();
			}
			
			if (griffin.getAltitude() <= 0) {
				falling = false;
				if (griffin.getChuteHeight() < griffin.getChuteLimit())
				{
					game.setScreen(new DeathScreen(game));
				}
				else
				{
					game.setScreen(new EndScreen(game, griffin.getScore()));
				}
			}
			
			button.update(deltaTime);
			
			for(Bird bird : birds)
			{
				if (bird.intersects(griffin))
				{
					griffin.bounce();
					break;
				}
			}
			
			bg1.updateDrawY(griffin.getVelocityY());
			bg1.updateDrawX(griffin.getVelocityX());
		}
		else
		{
			for(Bird bird : birds)
			{
				bird.update(deltaTime, 0, 0);
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

	private void updatePaused(List touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = (TouchEvent) touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, 0, 800, 240)) {

					if (!inBounds(event, 0, 0, 35, 35)) {
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) {
					nullify();
					goToMenu();
				}
			}
		}
	}

	private void updateGameOver(List touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = (TouchEvent) touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, 0, 0, 800, 480)) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}
	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		Image bg = Assets.background;
		
		int newY = (bg.getHeight() - bg1.getBgY());
		int newX = (bg.getWidth() - bg1.getBgX());
		
		((AndroidGraphics) g).drawSubImage(bg, 0, 0, bg.getHeight(), bg.getWidth(), bg1.getBgX(), bg1.getBgY());
		((AndroidGraphics) g).drawSubImage(bg, 0, newY, bg.getHeight(), bg.getWidth(), bg1.getBgX(), 0);
		((AndroidGraphics) g).drawSubImage(bg, newX, 0, bg.getHeight(), bg.getWidth(), 0, bg1.getBgY());
		((AndroidGraphics) g).drawSubImage(bg, newX, newY, bg.getHeight(), bg.getWidth(), 0, 0);
		
		for(Bird bird : birds)
		{
			bird.draw(g);
		}
		
		if (griffin.getAltitude() <= groundHeight + 100)
		{
			g.drawImage(ground, 0, griffin.getY() + (int)griffin.getAltitude()*20 + griffin.getHeight());
		}
		
		griffin.draw(g);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	public void animate() {
	
	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		bg1 = null;
		bg2 = null;
		birds = null;
		griffin = null;

		// Call garbage collector to clean up memory.
		System.gc();

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 400, 240, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		altitudeBox.draw(g);
		button.draw(g);

	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paint2);
		g.drawString("Menu", 400, 360, paint2);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 400, 240, paint2);
		g.drawString("Tap to return.", 400, 290, paint);

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		game.setScreen(new MainMenuScreen(game));

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}
	
	public int getWidth()
	{
		return game.getGraphics().getWidth();
	}

	public int getHeight()
	{
		return game.getGraphics().getHeight();
	}
	
}