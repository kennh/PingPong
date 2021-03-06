package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    Texture texture;
    int x, y;
    final int INITIAL_VELOCITY = 10;
    int velocityX = INITIAL_VELOCITY, velocityY = INITIAL_VELOCITY;
    final int FRAMES_TO_WAIT_BEFORE_BALL_START = 80;
    int ballStartFrameCounter;
    int ballFlyFrameCounter;
    final int FRAMES_TO_WAIT_BEFORE_SPEEDINGUP_BALL = 100;

    public Ball(){
        texture = new Texture("ball_small.png");

    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public void move(Paddle paddle) {
        ballStartFrameCounter++;
        if(ballStartFrameCounter >= FRAMES_TO_WAIT_BEFORE_BALL_START){
            x += velocityX;
            y += velocityY;
            ballFlyFrameCounter++;

            speedUpIfNeeded();

        } else {
            x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        }
    }

    private void speedUpIfNeeded() {
        if(ballFlyFrameCounter % FRAMES_TO_WAIT_BEFORE_SPEEDINGUP_BALL == 0) {
            if(velocityX >= 0){
                velocityX++;
            } else {
                velocityX--;
            }

            if(velocityY >= 0){
                velocityY++;
            } else {
                velocityY--;
            }
        }
    }


    public void restart(Paddle paddle) {
        x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        y = paddle.y + paddle.texture.getHeight();
        ballStartFrameCounter = 0;
        velocityY = Math.abs(velocityY);
    }

    public void dispose(){
        texture.dispose();
    }
}
