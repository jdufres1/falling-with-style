package framework.fallingwithstyle;

import framework.Graphics;
import framework.Image;

public class ScoreBox {
	private int score;
	private Image image;
	public ScoreBox(int score)
	{
		this.score = score;
		image = Assets.scoreBox;
	}
	
	public void draw(Graphics g)
	{
		int x = (g.getWidth() - image.getWidth())/2;
		int y = (g.getHeight() - image.getHeight())/2;
		g.drawImage(image, x, y);
		
		int numberWidth = Assets.numbers[0].getWidth();
		int n = score;
		int nx = x + 4*numberWidth;
		int ny = y + 55;
		while (n != 0)
		{
			int dig = n % 10;
			g.drawImage(Assets.numbers[dig], nx, ny);
			nx -= numberWidth;
			n /= 10;
		}
		if (score <= 0)
		{
			g.drawImage(Assets.numbers[0], nx, ny);
		}
		
	}
	
	
}
