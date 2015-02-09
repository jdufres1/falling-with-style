package framework.fallingwithstyle;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import framework.Graphics;
import framework.Image;
import framework.Graphics.ImageFormat;
import framework.implementation.AndroidGraphics;
import framework.implementation.AndroidImage;

public class Bird 
{
	private AndroidImage image;
	private AndroidImage rightImage;
	private AndroidImage leftImage;
	private boolean facingLeft;
	private static Random rand = new Random();
	private int x, y;
	private GameScreen owner;
	private boolean hit = false;
	private int speed;
	public Bird(GameScreen owner)
	{
		this.owner = owner;
		image = (AndroidImage) Assets.bird;
		rightImage = image;
		Matrix matrix = new Matrix();

        matrix.preScale(-1.0f, 1.0f);

        Bitmap origBmap = image.bitmap.copy(Bitmap.Config.ARGB_4444, true);
		Bitmap leftImageBmap = Bitmap.createBitmap(origBmap, 0, 0, origBmap.getWidth(), origBmap.getHeight(), matrix, true);
		leftImage = new AndroidImage(leftImageBmap, ImageFormat.ARGB4444);
		init();
	}
	public void init()
	{
		// roll a three-sided 'die' to decided whether the bird will appearbelow, to the right, or to the left of the screen. 
		// Once that is decided roll a die to decide where in that space the bird will appear. 
		int where = rand.nextInt() % 3;
		where = Math.abs(where);
		if (where == 1)
		{
			// The bird will appear below the screen.
			y = owner.getHeight() + 100;
			facingLeft = rand.nextBoolean();
			x = rand.nextInt() % (owner.getWidth() + 400) - 200;
		}
		else if (where == 2)
		{
			// The bird will appear to the right of the screen
			x = -100;
			facingLeft = false;
			y = rand.nextInt() % (owner.getHeight() + 400) - 200;
		}
		else
		{
			// The bird will appear to the left of the screen
			x = owner.getWidth() + 100;
			facingLeft = true;
			y = rand.nextInt() % (owner.getHeight() + 400) - 200;
		}		
		hit = false;
		speed = Math.abs(rand.nextInt() % 5);
	}
	public void update(float delta, float griffinSpeedX, float griffinSpeedY)
	{
		// Birds fly 1 pixel in their direction per frame
		if (facingLeft)
		{
			x-=speed;
		}
		else 
		{
			x+=speed;
		}
		
		// Birds also move off screen as Griffin moves, at the same speed as the background
		x -= griffinSpeedX;
		y += griffinSpeedY;
		
		if (hit && (x < 0 || x > owner.getWidth() || y < 0 || y > owner.getHeight()))
			init();
		
		if (x < -500 || x > owner.getWidth() + 500 || y < -500 || y > owner.getHeight() + 500)
			init();
	}
	public void draw(Graphics g)
	{
		if (facingLeft)
		{
			((AndroidGraphics)g).drawImage(leftImage, x, y);
		}
		else
		{
			((AndroidGraphics)g).drawImage(rightImage, x, y);
		}
		
		if (SampleGame.DEBUG)
			g.drawRect(x + 5, y, image.getWidth() - 10, 5, Color.BLACK);
		
	}
	public boolean intersects(Griffin griffin)
	{
		int gw = griffin.getImage().getWidth();
		int gh = griffin.getImage().getHeight();
		
		Rect a = new Rect(x + 5, y, x + 5 + image.getWidth() - 10, y + 5);
		Rect b = new Rect(griffin.getX(), griffin.getY(), griffin.getX() + gw, griffin.getY() + gh);
		
		if (Rect.intersects(a, b)) 
		{
			hit = true;
			return true;
		}
		else return false;
		
	}
}
