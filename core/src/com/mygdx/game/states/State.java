package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput(); // Handle inputs made by user.
    public abstract void update(float dt); // Update game logic, used by DemoGame's render().
    public abstract void render(SpriteBatch sb); // Handle SpriteBatch for that particular State.
    public abstract void dispose(); // Free memory used by SpriteBatch.
}
