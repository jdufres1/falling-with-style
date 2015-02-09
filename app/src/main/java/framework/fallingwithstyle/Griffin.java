package framework.fallingwithstyle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import framework.Graphics;
import framework.Graphics.ImageFormat;
import framework.Image;
import framework.implementation.AndroidGame;
import framework.implementation.AndroidGraphics;
import framework.implementation.AndroidImage;

// The main character's name is Griffin Dropping

public class Griffin {
	private int moveSpeed = 20;
	private int x = 100;
	private int y = 100;
	private int angle = 0;
	private AndroidImage image;
	private AndroidImage rightImage;
	private AndroidImage leftImage;
	private float altitude = 1500;
	private float accelerationY = -5;
	private float baseAccelerationY = -5;
	private float velocityY = 0;
	private float terminalVelocityY = -25;
	private float accelerationX = 0;
	private float baseAccelerationX = 0;
	private float velocityX = 0;
	private float terminalVelocityX = 30;
	private boolean xyinit = false;
	private boolean facingRight = true; 
	private boolean chuteDeployed = false;
	private int chuteSuggestion = 500;
	private int chuteLimit = 150;
	private int chuteHeight = 10000;
	private float lastPoint = 0;
	int score = 0;
	public Griffin()
	{
		image = (AndroidImage) Assets.griffin;
		rightImage = image;
		Matrix matrix = new Matrix();

        matrix.preScale(-1.0f, 1.0f);

        Bitmap origBmap = image.bitmap.copy(Bitmap.Config.ARGB_4444, true);
		Bitmap leftImageBmap = Bitmap.createBitmap(origBmap, 0, 0, origBmap.getWidth(), origBmap.getHeight(), matrix, true);
		leftImage = new AndroidImage(leftImageBmap, ImageFormat.ARGB4444);
	}
	public boolean isFacingRight() {
		return facingRight;
	}
	public void update(float delta)
	{
		velocityY = velocityY + (delta * accelerationY);
		if (velocityY < terminalVelocityY)
		{
			velocityY = terminalVelocityY;	
		}
		float distY = velocityY * delta;
		if (altitude + distY > 0)
		{
			altitude = altitude + distY;
		}
		else
		{
			altitude = 0;
		}
		
		velocityX = velocityX + (delta * accelerationX);
		if (Math.abs(velocityX) > terminalVelocityX)
		{
			if (velocityX > 0)
				velocityX = terminalVelocityX;	
			if (velocityX < 0)
				velocityX = -terminalVelocityX;
		}
		if (lastPoint >= 1)
		{
			score++;
			lastPoint = 0;
		}
		else
		{
			lastPoint += delta;
		}
	}
	public void bounce()
	{
		if (!chuteDeployed){
			velocityY = 20;
			score += 1;
		}
	}
	public void deployChute()
	{
		chuteDeployed = true;
		chuteHeight = (int)altitude;
		image = (AndroidImage) Assets.griffin_chute;
		rightImage = image;
		Matrix matrix = new Matrix();

        matrix.preScale(-1.0f, 1.0f);

        Bitmap origBmap = image.bitmap.copy(Bitmap.Config.ARGB_4444, true);
		Bitmap leftImageBmap = Bitmap.createBitmap(origBmap, 0, 0, origBmap.getWidth(), origBmap.getHeight(), matrix, true);
		leftImage = new AndroidImage(leftImageBmap, ImageFormat.ARGB4444);
		
		accelerationY = -5;
		velocityY = 1;
		velocityX /= 2;
		terminalVelocityY = -15;
	}
	public int getMoveSpeed() {
		return moveSpeed;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getAngle() {
		return angle;
	}
	public Image getImage() {
		return image;
	}
	public float getAltitude() {
		return altitude;
	}
	public float getAccelerationY() {
		return accelerationY;
	}
	public float getBaseAccelerationY() {
		return baseAccelerationY;
	}
	public float getVelocityY() {
		return velocityY;
	}
	public float getTerminalVelocityY() {
		return terminalVelocityY;
	}
	public float getAccelerationX() {
		return accelerationX;
	}
	public float getBaseAccelerationX() {
		return baseAccelerationX;
	}
	public float getVelocityX() {
		return velocityX;
	}
	public float getTerminalVelocityX() {
		return terminalVelocityX;
	}
	public boolean chuteDeployed()
	{
		return chuteDeployed;
	}
	public void accelerateLeftRight(float acceleration)
	{
		if (acceleration > 0)
		{
			angle = 10;
			facingRight = true;
		}
		else if (acceleration < 0)
		{
			angle = -10;
			facingRight = false;
		}
		else
		{
			angle = 0;
		}
			
		accelerationX = acceleration;
	}
	public void draw(Graphics g)
	{
		if (SampleGame.DEBUG)
			g.drawRect(x, y, image.getWidth(), image.getHeight(), Color.RED);
		if (!xyinit)
		{
			x = (g.getWidth() - image.getWidth()) / 2;
			y = (g.getHeight() - image.getWidth()) / 2;
			xyinit = true;
		}
		if (facingRight)
		{
			((AndroidGraphics)g).drawRotatedImage(rightImage, x, y, angle);
		}
		else
		{
			((AndroidGraphics)g).drawRotatedImage(leftImage, x, y, angle);
		}
	}
	public int getHeight() {
		return image.getHeight();
	}
	public int getChuteSuggestion()
	{
		return chuteSuggestion;
	}
	public int getChuteLimit() {
		return chuteLimit;
	}
	public int getChuteHeight() 
	{
		return chuteHeight;
	}
	public int getScore()
	{
		return score;
	}
}
