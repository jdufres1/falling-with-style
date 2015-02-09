package framework.fallingwithstyle;

import framework.Graphics;

public class AltitudeBox 
{
	private int altitude;
	private int x;
	private int y;
	
	public AltitudeBox(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
		
	void update(int altitude)
	{
		this.altitude = altitude;
	}
	
	void draw(Graphics g)
	{
		g.drawImage(Assets.altitudeBox, x, y);
		int numberWidth = Assets.numbers[0].getWidth();
		int n = altitude;
		int nx = 4*numberWidth;
		int ny = 0;
		while (n != 0)
		{
			int dig = n % 10;
			g.drawImage(Assets.numbers[dig], nx, ny);
			nx -= numberWidth;
			n /= 10;
		}
		if (altitude <= 0)
		{
			g.drawImage(Assets.numbers[0], nx, ny);
		}
	}
	
}
