package com.cvte.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {

	private Flappy mFlappy;
	
	private BitmapFont mBFScore;

	private float mCurScore;
	
	private final int SCORE_Y = 450;
	
	public Score(Flappy parent) {
		mFlappy = parent;
		
		mBFScore = new BitmapFont(Gdx.files.internal("bigNum.fnt"));
		
		mCurScore = 0;
	}
	
	public void dispose() {
		mFlappy = null;
		
		if (mBFScore != null) {
			mBFScore.dispose();
			mBFScore = null;
		}
	}
	
	public void draw(SpriteBatch batch) {
		int score = (int)mCurScore;
		int width = (int)mBFScore.getBounds(score + "").width;
		mBFScore.draw(batch, (int)score + "", mFlappy.getCameraX() - (width>>1), SCORE_Y);
	}
	
	public void addScore() {
		mCurScore += 0.5;
	}
	
	public void reset() {
		mCurScore = 0;
	}
	
	public float getScore() {
		return mCurScore;
	}
	
}
