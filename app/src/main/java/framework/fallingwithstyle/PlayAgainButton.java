package framework.fallingwithstyle;

import android.graphics.Rect;
import framework.Game;
import framework.Graphics;
import framework.Image;

public class PlayAgainButton {
	private Game game;
	private int x; 
	private int y;
	private Image image;
	public PlayAgainButton (Game game)
	{
		this.game = game;
		image = Assets.playagain;
		x = 0;
		y = game.getGraphics().getHeight() - image.getHeight();
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, x, y);
	}
	
	public Rect getBounds()
	{
		return new Rect(x, y, x + image.getWidth(), y + image.getHeight());
	}
	
	public void onClick()
	{
		game.setScreen(new GameScreen(game));
		
	}
}
