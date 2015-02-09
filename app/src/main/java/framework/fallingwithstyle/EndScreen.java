package framework.fallingwithstyle;

import java.util.List;

import android.graphics.Rect;
import framework.Game;
import framework.Graphics;
import framework.Image;
import framework.Screen;
import framework.Input.TouchEvent;

public class EndScreen extends Screen{
	private Image image;
	private PlayAgainButton playButton;
	private ScoreBox scoreBox;
	public EndScreen(Game game, int score)
	{
		super(game);
		image = Assets.endScreen;
		playButton = new PlayAgainButton(game);
		scoreBox = new ScoreBox(score);
	}

	@Override
	public void update(float deltaTime) {
		List touchEvents = game.getInput().getTouchEvents();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = (TouchEvent) touchEvents.get(i);
			// if it is, figure out where, then turn and move the ship in that direction.
			if (event.type == TouchEvent.TOUCH_DOWN) {
				Rect buttonRect = playButton.getBounds();
				if (buttonRect.contains(event.x, event.y))
				{
					playButton.onClick();
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(image, 0, 0);
		playButton.draw(g);
		scoreBox.draw(g);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}
	
}
