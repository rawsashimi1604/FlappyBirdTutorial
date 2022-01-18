package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.DemoGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125; // Spacing between tubes not including tube itself.
    private static final int TUBE_COUNT = 4; // Number of tubes at any time in PlayState.
    private static final int GROUND_Y_OFFSET = -50; // Determine how many pixels to move ground downwards
    private static final int GAMEOVER_X_OFFSET = 26; // Determine how many pixels to move game over to the right

    private Bird bird;
    private Tube tube;

    private Texture bg;
    private Texture ground;
    private Texture gameover;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    private boolean isGameover;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        bg = new Texture("./images/bg.png");
        ground = new Texture("./images/ground.png");
        gameover = new Texture("./images/gameover.png");

        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        // Add tubes to PlayState
        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        // Set OrthographicCamera, start from bottom up, zoomed in to half of width and half of height.
        cam.setToOrtho(false, DemoGame.WIDTH / 2, DemoGame.HEIGHT/ 2);

        // Reset game over to false
        isGameover = false;
    }

    @Override
    protected void handleInput() {

        // Check for game over, if clicked, reset
        if (isGameover) {
            if(Gdx.input.justTouched()) {
                gsm.set(new PlayState(gsm));
            }
        }

        if(Gdx.input.justTouched()) {
            bird.jump();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        // Reposition tube when it gets off viewport.
        for(Tube tube : tubes) {
            // If tube is off to the left of the screen.
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            // If bird hits the tube, game over.
            if(tube.collides(bird.getBounds())) {
                isGameover = true;
                break; // stop checking for more tubes after collision
            }
        }

        // If bird hits the ground, game over.
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            isGameover = true;
        }

        // Reposition Camera
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        // Instructs sb to used (combined view and projection matrix), render only things that are on the screen.
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y); // Draw current position x and y of bird.

        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        if (isGameover) {
            sb.draw(gameover, (cam.position.x - cam.viewportWidth / 2) + GAMEOVER_X_OFFSET, (cam.viewportHeight / 2));
        }
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for(Tube tube : tubes) {
            tube.dispose();
        }
        ground.dispose();
        System.out.println("Play State Disposed.");
    }

    private void updateGround() {
        // If ground moves out of bounds, update x position
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }

        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

}
