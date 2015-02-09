package framework.fallingwithstyle;

import framework.Image;

public class Background {
	   
    private int bgX, bgY, speedX;
    Image image;
   
    public Background(int x, int y){
        bgX = x;
        bgY = y;
        speedX = 0;
        image = Assets.background;
    }
   
    public void update() {
        
    }

    public void updateDrawY(float speed)
    {
    	// FallSpeed is Griffin's current velocity - i.e. the number of meters he moved this frame
    	// Remember, a negative FallSpeed means Griffin is moving downward.
    	float pixelsPerMeter = -1; // We'll move the background up by 1 pixel for every 1 meter Griffin falls.
    	
    	float temp = bgY + pixelsPerMeter * speed; 
    	while(temp < 0)
    		temp += image.getHeight();
    	bgY = ((int)temp) % image.getHeight();
    }
    
    public void updateDrawX(float speed)
    {
    	// speed is Griffin's current velocity in the x dimension - i.e. the number of meters he moved this frame
    	// Remember, a negative speed means Griffin is moving left
    	float pixelsPerMeter = 1; // We'll move the background left 1 pixel for every meter he moves to the right.
    	float temp = bgX + pixelsPerMeter * speed; 
    	while(temp < 0)
    		temp += image.getWidth();
    	bgX = ((int)temp) % image.getWidth();
    }
    
    public int getBgX() {
        return bgX;
    }

    public int getBgY() {
        return bgY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getWidth()
    {
    	return image.getWidth();
    }
    
    public int getHeight()
    {
    	return image.getHeight();
    }
    
    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

   
   
   
}