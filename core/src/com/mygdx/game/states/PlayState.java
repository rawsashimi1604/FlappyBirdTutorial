package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DemoGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125; // Spacing between tubes not including tube itself.
    private static final int TUBE_COUNT = 4; // Number of tubes at any time in PlayState.

    private Bird bird;
    private Tube tube;

    private Texture bg;

    private Array<Tube> tubes;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        bg = new Texture("./images/bg.png");

        // Add tubes to PlayState
        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        // Set OrthographicCamera, start from bottom up, zoomed in to half of width and half of height.
        cam.setToOrtho(false, DemoGame.WIDTH / 2, DemoGame.HEIGHT/ 2);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        // Reposition tube when it gets off viewport.
        for(Tube tube : tubes) {
            // If tube is off to the left of the screen.
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())) {
                gsm.set(new PlayState(gsm));
            }
        }

        // Reposition Camera
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        // Instructs sb to used (combined view and projection matrix), render only things that are on the screen.
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
//        sb.draw(bg, 0, 0);
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y); // Draw current position x and y of bird.

        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
