package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PingPongGame extends ApplicationAdapter {
	Texture gameOverTexture;
	SpriteBatch batch;
	SoundManager soundManager;
	Ball ball;
	Paddle paddle;
	int score;
	BitmapFont font;
	final int CATCH_BALL_BONUS = 100;
	final int INITIAL_LIVES_COUNT = 1;
	int livesCount = INITIAL_LIVES_COUNT;
	boolean isGameOver = false;
	Button closeBtn;
	Button replayBtn;


	@Override
	public void create () {
		font = new BitmapFont();
		font.getData().setScale(5);
		batch = new SpriteBatch();
		ball = new Ball();
		paddle = new Paddle();
		paddle.center();
		ball.restart(paddle);
		gameOverTexture = new Texture("game_over_logo.jpg");
		closeBtn = new Button("close.png");
		closeBtn.x = Gdx.graphics.getWidth() - closeBtn.texture.getWidth();
		closeBtn.y = 10;
		replayBtn = new Button("replay.png");
		replayBtn.y = 10;
		soundManager = new SoundManager();
	}

	@Override
	public void render () {
		if(isGameOver) {
			if (closeBtn.isClicked()) {
				System.exit(0);
			}

			if (replayBtn.isReleased()) {
				score = 0;
				livesCount = INITIAL_LIVES_COUNT;
				isGameOver = false;
				paddle.center();
				ball.restart(paddle);
				ball.velocityX = ball.velocityY = ball.INITIAL_VELOCITY;
			}
		}

		if(! isGameOver){
			// Input
			paddle.move();

			// logic
			ball.move(paddle);
		}

		collideBall();

		// lose ball
		if (ball.y + ball.texture.getHeight() < 0) {
			ball.restart(paddle);
			soundManager.loseBallSound.play();
			livesCount--;

			if(livesCount == 0){
				isGameOver = true;
			}
		}

		// drawing
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.draw(batch);
		paddle.draw(batch);

		if(isGameOver){
			batch.draw(gameOverTexture,(Gdx.graphics.getWidth() - gameOverTexture.getWidth())/ 2,
					(Gdx.graphics.getHeight() - gameOverTexture.getHeight()) / 2);
			closeBtn.draw(batch);
			replayBtn.draw(batch);
		}
		font.draw(batch, "Score: " + score + "  Lives: " + livesCount, 0, Gdx.graphics.getHeight());
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		ball.dispose();
		paddle.dispose();
		soundManager.disposeSound();
		font.dispose();
		gameOverTexture.dispose();  // dispose of the game_over_logo.
		closeBtn.dispose();
		replayBtn.dispose();
	}

	private void collideBall() {
		// The ball bounces off the right edge.
		if(ball.x >= Gdx.graphics.getWidth() - ball.texture.getWidth()){
			soundManager.playRandomBounceSound();
			ball.velocityX = -ball.velocityX;
		}

		// The ball bounces off the top edge.
		if(ball.y >= Gdx.graphics.getHeight() - ball.texture.getHeight()){
			soundManager.playRandomBounceSound();
			ball.velocityY = -ball.velocityY;
		}
		
		// The ball bounces off the left wall soon
		if(ball.x <= 0){
			soundManager.playRandomBounceSound();
			ball.velocityX = -ball.velocityX;
		}

		// the ball will bounce off the top of the paddle
		if(ball.x >= paddle.x - ball.texture.getWidth() / 2 && ball.x < paddle.texture.getWidth() + paddle.x - ball.texture.getWidth() / 2){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				soundManager.playRandomBounceSound();
				ball.velocityY = -ball.velocityY;

				score += Math.abs(ball.velocityX) * CATCH_BALL_BONUS;
			}
		}

		// The ball will bounce off left side of paddle
		if(ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}

		//The ball will bounce off right side of paddle
		if(ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
	}

}
