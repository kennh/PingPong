package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {

    Texture texture;
    int x;
    int y;
    boolean hasBeenClicked;


    public Button(String textureName){

        texture = new Texture(textureName);
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public boolean isClicked(){
//        if(Gdx.input.justTouched()){
//            return true;
//        } else {
//            return false;
//        }
        if(Gdx.input.justTouched() ){
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if(touchX >= x && touchX < x + texture.getWidth() && touchY >= y && touchY < y + texture.getHeight()){
                return true;
            }
        }
        return false;
    }

    public boolean isReleased(){


        if(isClicked()){
            hasBeenClicked = true;
        }

        if(! Gdx.input.isTouched() && hasBeenClicked ){
            hasBeenClicked = false;
            return true;
        }
        return false;
    }

    public void dispose(){
        texture.dispose();
    }
}
