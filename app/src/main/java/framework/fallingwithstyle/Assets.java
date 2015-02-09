package framework.fallingwithstyle;

import framework.Graphics;
import framework.Image;
import framework.Music;
import framework.Sound;
import framework.Graphics.ImageFormat;

public class Assets {
   
    public static Image griffin, griffin_chute, bird, ground, background;
    public static Image altitudeBox;
    public static Image button, yellowbutton;
    public static Image menu, playButton, welcomeScreen, deathScreen, endScreen, playagain, scoreBox;
    public static Image numbers[];
    
    public static void load(Graphics g)
    {
    	background = g.newImage("background.png", ImageFormat.RGB565);
        griffin = g.newImage("griffin.png", ImageFormat.ARGB4444);
        griffin_chute = g.newImage("griffin_chute.png", ImageFormat.ARGB4444);
        bird = g.newImage("bird.png",  ImageFormat.ARGB4444);
        button = g.newImage("button.png", ImageFormat.RGB565);
        yellowbutton = g.newImage("yellowbutton.png", ImageFormat.ARGB4444);
        altitudeBox = g.newImage("altitudeBox.png", ImageFormat.RGB565);
        ground = g.newImage("ground.png", ImageFormat.RGB565);
        deathScreen = g.newImage("deathScreen.png", ImageFormat.RGB565);
        numbers = new Image[10];
        endScreen = g.newImage("endScreen.png", ImageFormat.ARGB4444);
        playagain = g.newImage("playagain.png", ImageFormat.ARGB4444);
        scoreBox = g.newImage("scoreBox.png", ImageFormat.ARGB4444);
        playButton = g.newImage("playButton.png", ImageFormat.ARGB4444);
        
        for (int i = 0; i < numbers.length; i++)
        {
        	numbers[i] = g.newImage("" + i + ".png", ImageFormat.ARGB4444);
        }
    }   
}