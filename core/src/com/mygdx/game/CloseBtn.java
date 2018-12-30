package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CloseBtn {

    Texture texture;

    public CloseBtn(){
        texture = new Texture("close.png");
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, 0, 0);
    }

    public void dispose(){
        texture.dispose();
    }
}
