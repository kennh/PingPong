package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Ball {
    Texture texture;

    public Ball(){
        texture = new Texture("ball_small.png");

    }

    public void disposeBall(){
        texture.dispose();
    }
}
