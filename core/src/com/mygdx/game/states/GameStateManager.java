package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    // By using a stack to store states, we can easily choose which state is currently active in our game. (Menu State, Game State, Pause State)

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop(State state) {
        states.pop();
    }

    public void set(State state) {
        // Remove current state, then immediately set new state.
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        // Update current state's delta time.
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}
