package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PingPongGame extends ApplicationAdapter {
	SpriteBatch batch;

	Texture paddleTexture;
	int ballX, ballY;
	int velocityX = 10, velocityY = 10;
	int paddleX;
	int paddleY = 10;
	int ballStartFrameCounter;
	final int FRAMES_TO_WAIT_BEFORE_BALL_START = 80;
	SoundManager soundManager;
	Ball ball;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ball = new Ball();
		paddleTexture = new Texture("paddle.bmp");
		paddleX = (Gdx.graphics.getWidth() - paddleTexture.getWidth()) / 2;
		restartBall();
		soundManager = new SoundManager();
	}

	@Override
	public void render () {
		// Input
		movePaddle();

		// logic
		moveBall();

		collideBall();

		if(ballY + ball.texture.getHeight() < 0){
			restartBall();
		}

		// drawing
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(ball.texture, ballX, ballY);

		batch.draw(paddleTexture, paddleX, paddleY);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		ball.disposeBall();
		paddleTexture.dispose();
		soundManager.disposeSound();
	}

	private void collideBall() {
		// The ball bounces off the right edge.
		if(ballX >= Gdx.graphics.getWidth() - ball.texture.getWidth()){
			soundManager.playRandomBounceSound();
			velocityX = -velocityX;
		}

		// The ball bounces off the top edge.
		if(ballY >= Gdx.graphics.getHeight() - ball.texture.getHeight()){
			soundManager.playRandomBounceSound();
			velocityY = -velocityY;
		}
		// The ball bounces off the left wall soon
		if(ballX <= 0){
			soundManager.playRandomBounceSound();
			velocityX = -velocityX;
		}

		// the ball will bounce off the top of the paddle
		if(ballX >= paddleX - ball.texture.getWidth() / 2 && ballX < paddleTexture.getWidth() + paddleX - ball.texture.getWidth() / 2){
			if(ballY < paddleY + paddleTexture.getHeight()){
				soundManager.playRandomBounceSound();
				velocityY = -velocityY;
			}
		}

		// The ball will bounce off left side of paddle
		if(ballX > paddleX - ball.texture.getWidth() && ballX < paddleX - ball.texture.getWidth() / 2 + 1){
			if(ballY < paddleY + paddleTexture.getHeight()){
				if(velocityX > 0) {
					velocityX = -velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
		//The ball will bounce off right side of paddle
		if(ballX > paddleX + paddleTexture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ballX < paddleX + paddleTexture.getWidth()){
			if(ballY < paddleY + paddleTexture.getHeight()){
				if(velocityX < 0) {
					velocityX = -velocityX;
					soundManager.playRandomBounceSound();
				}
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
		ballX = paddleX + paddleTexture.getWidth() / 2 - ball.texture.getWidth() / 2;
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
			ballX = paddleX + paddleTexture.getWidth() / 2 - ball.texture.getWidth() / 2;
		}
	}


}
