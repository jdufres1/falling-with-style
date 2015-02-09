package framework.fallingwithstyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import framework.fallingwithstyle.R;

import android.util.Log;
import framework.Screen;
import framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame {

    boolean firstTimeCreate = true;
    public final static boolean DEBUG = false;

    @Override
    public Screen getInitScreen() {

        if (firstTimeCreate) {
            firstTimeCreate = false;
        }

        return new LoadingScreen(this);

    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }
    
    @Override
    public void onResume() {
        super.onResume();

    //    Assets.theme.play();

    }

    @Override
    public void onPause() {
        super.onPause();
    //    Assets.theme.pause();

    }
}