package framework.fallingwithstyle;

import android.graphics.Rect;
import framework.Graphics;
import framework.Image;

public class ChuteButton {
	private static final float NANOS_PER_SECOND = 1000000000;
	private Image redbutton;
	private Image yellowbutton;
	private boolean flashing = false;
	private float timeSinceFlash = 0;
	private float flashTime = 0.5f;
	private boolean red = true;
	private int x, y;
	public ChuteButton(int x, int y)
	{
		redbutton = Assets.button;
		yellowbutton = Assets.yellowbutton;
		this.x = x;
		this.y = y;
	}
	public void update(double delta)
	{
		if (flashing)
		{
			timeSinceFlash += delta;
			if (timeSinceFlash >= flashTime)
			{
				timeSinceFlash = 0;
				red = !red;
			}
		}
	}
	public void draw(Graphics g)
	{
		if (red)
		{
			g.drawImage(redbutton, x, y);;
		}
		else
		{
			g.drawImage(yellowbutton, x, y);
		}
	}
	public void flash()
	{
		flashing = true;
	}
	public Rect getBounds()
	{
		return new Rect(x, y, x + redbutton.getWidth(), y + yellowbutton.getWidth());
	}
}
