package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {
    Texture texture;
    int x;
    int y = 10;

    public Paddle(){
        texture = new Texture("paddle.bmp");

    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public void move() {
        if(Gdx.input.isTouched()){
            x = Gdx.input.getX() - texture.getWidth() / 2;

            // Paddle does not move past left wall
            if(x < 0){
                x = 0;
            }

            // Paddle does not move past right wall
            if(x > Gdx.graphics.getWidth() - texture.getWidth()){
                x = Gdx.graphics.getWidth() - texture.getWidth();
            }
        }
    }

    public void center(){
        x = (Gdx.graphics.getWidth() - texture.getWidth()) / 2;
    }

    public void dispose(){ texture.dispose(); }
}

// introduce lives, and points for speed of ball.
