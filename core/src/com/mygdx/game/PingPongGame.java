package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PingPongGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture ballTexture;
	Texture paddleTexture;
	int ballX, ballY;
	int velocityX = 10, velocityY = 10;
	int paddleX;
	int paddleY = 10;
	int ballStartFrameCounter;
	final int FRAMES_TO_WAIT_BEFORE_BALL_START = 80;
	Sound bounceSound;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ballTexture = new Texture("ball_small.png");
		paddleTexture = new Texture("paddle.bmp");
		paddleX = (Gdx.graphics.getWidth() - paddleTexture.getWidth()) / 2;
		restartBall();
		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounce1.ogg"));
	}

	@Override
	public void render () {
		// Input
		movePaddle();

		// logic
		moveBall();

		collideBall();

		if(ballY + ballTexture.getHeight() < 0){
			restartBall();
		}

		// drawing
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(ballTexture, ballX, ballY);

		batch.draw(paddleTexture, paddleX, paddleY);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		ballTexture.dispose();
		paddleTexture.dispose();
		bounceSound.dispose();
	}

	private void collideBall() {
		// The ball bounces off the right edge.
		if(ballX >= Gdx.graphics.getWidth() - ballTexture.getWidth()){
			bounceSound.play();
			velocityX = -velocityX;
		}

		// The ball bounces off the top edge.
		if(ballY >= Gdx.graphics.getHeight() - ballTexture.getHeight()){
			bounceSound.play();
			velocityY = -velocityY;
		}
		// The ball bounces off the left wall soon
		if(ballX <= 0){
			bounceSound.play();
			velocityX = -velocityX;
		}

		// the ball will bounce off the paddle
		if(ballX >= paddleX - ballTexture.getWidth() / 2 && ballX < paddleTexture.getWidth() + paddleX - ballTexture.getWidth() / 2){
			if(ballY < paddleY + paddleTexture.getHeight()){
				bounceSound.play();
				velocityY = -velocityY;
			}
		}
	}

	private void movePaddle() {
		if(Gdx.input.isTouched()){
			paddleX = Gdx.input.getX() - paddleTexture.getWidth() / 2;

			// Paddle does not move past left wall
			if(paddleX < 0){
				paddleX = 0;
			}

			// Paddle does not move past right wall
			if(paddleX > Gdx.graphics.getWidth() - paddleTexture.getWidth()){
				paddleX = Gdx.graphics.getWidth() - paddleTexture.getWidth();
			}
		}
	}

	private void restartBall() {
		ballX = paddleX + paddleTexture.getWidth() / 2 - ballTexture.getWidth() / 2;
		ballY = paddleY + paddleTexture.getHeight();
		ballStartFrameCounter = 0;
		velocityY = Math.abs(velocityY);
	}

	private void moveBall() {
		ballStartFrameCounter++;
		if(ballStartFrameCounter >= FRAMES_TO_WAIT_BEFORE_BALL_START){
			ballX += velocityX;
			ballY += velocityY;
		} else {
			ballX = paddleX + paddleTexture.getWidth() / 2 - ballTexture.getWidth() / 2;
		}
	}
}
