package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {

    private static final int FLUCTUATION = 130; // Determines y position of tube.
    private static final int TUBE_GAP = 100; // Difference between top and bottom tube.
    private static final int LOWEST_OPENING = 120; // Determines lowest y position for tube.

    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;

    private Random rand; // Randomize y location of tube

    public Tube(float x) {
        topTube = new Texture("./images/toptube.png");
        bottomTube = new Texture("./images/bottomtube.png");

        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }
}
