package com.cvte.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameOver {

	private Flappy mFlappy;
	
	private TextureRegion mTROver;
	private TextureRegion mTRScoreBoard;
	private TextureRegion mTRBtnStart;
	private TextureRegion mTRBtnRank;
	
	private BitmapFont mBGCurScore;
	
	private final int OVER_Y = 380;
	private final int SCOREBOARD_Y = 270;
	private final int BTN_OFFSET_X = 65;
	private final int BTN_Y = 140;
	private final int CURSCORE_OFFSET_X = 70;
	private final int CURSCORE_OFFSET_Y = 300;
	
	public GameOver(Flappy parent) {
		mFlappy = parent;
	}
	
	public void dispose() {
		mFlappy = null;
		
		mTROver = null;
		mTRScoreBoard = null;
		mTRBtnStart = null;
		mTRBtnRank = null;
		
		if (mBGCurScore != null) {
			mBGCurScore.dispose();
			mBGCurScore = null;
		}
	}
	
	public void update() {
	
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(mTROver, mFlappy.getCameraX() - (mTROver.getRegionWidth()>>1), OVER_Y - (mTROver.getRegionHeight()>>1));
		batch.draw(mTRScoreBoard, mFlappy.getCameraX() - (mTRScoreBoard.getRegionWidth()>>1), SCOREBOARD_Y - (mTRScoreBoard.getRegionHeight()>>1));
		batch.draw(mTRBtnStart, mFlappy.getCameraX() - BTN_OFFSET_X - (mTRBtnStart.getRegionWidth()>>1), BTN_Y - (mTRBtnStart.getRegionHeight()>>1));
		batch.draw(mTRBtnRank, mFlappy.getCameraX() + BTN_OFFSET_X - (mTRBtnRank.getRegionWidth()>>1), BTN_Y - (mTRBtnRank.getRegionHeight()>>1));
		
		int score = mFlappy.getScore();
		int width = (int)mBGCurScore.getBounds(score + "").width;
		mBGCurScore.draw(batch, (int)score + "", mFlappy.getCameraX() + CURSCORE_OFFSET_X - (width>>1), CURSCORE_OFFSET_Y);
	}
	
	public void init() {
		mTROver = Assets.getInstanceValue().getOver();
		mTRScoreBoard = Assets.getInstanceValue().getScoreBoard();
		mTRBtnStart = Assets.getInstanceValue().getButtonStart();
		mTRBtnRank = Assets.getInstanceValue().getButtonRank();
		
		mBGCurScore = new BitmapFont(Gdx.files.internal("smallNum.fnt"));
	}
	
}
