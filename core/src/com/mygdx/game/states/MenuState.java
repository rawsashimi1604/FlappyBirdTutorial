package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DemoGame;

public class MenuState extends State {

    private Texture background;
    private Texture playBtn;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("./images/bg.png");
        playBtn = new Texture("./images/playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin(); // Open Box
        sb.draw(background, 0, 0, DemoGame.WIDTH, DemoGame.HEIGHT);
        sb.draw(playBtn, (DemoGame.WIDTH / 2) - (playBtn.getWidth() / 2), DemoGame.HEIGHT / 2);
        sb.end(); // Close Box

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
