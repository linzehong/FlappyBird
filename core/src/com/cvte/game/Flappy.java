package com.cvte.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Flappy extends ApplicationAdapter {
	
	private OrthographicCamera mCamera;
	private OrthographicCamera mCameraUI;
	
	private Assets mAssetsManager;
	
	private SpriteBatch mBatch;
	
	private TextureRegion mTRBG;
	private TextureRegion mTREarth;
	
	private Bird mBird;
	private ConduitManager mConduitManager;
	private Ready mReady;
	private GameOver mGameOver;
	private Score mScore;
	
	private SoundManager mSoundManager;
	
	private float mStateTime = 0;
	
	private float mEarthOffsetX = 0;
	
	private int mState;
	private final int STATE_READY = 0;
	private final int STATE_RUNNING = 1;
	private final int STATE_GAME_OVER = 2;
	
	public final static int SCREEN_WIDTH = 288;
	public final static int SCREEN_HEIGHT = 512;
	
	public final static float EARTH_Y = 112;
	
	private final int CAMERA_OFFSET_X = (SCREEN_WIDTH>>1) - Bird.INIT_BIRD_X;//相机中心点与bird的偏移量
	
	@Override
	public void create () {
		
		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		mCameraUI = new OrthographicCamera();
		mCameraUI.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
	
		mAssetsManager = Assets.getInstance();
		
		mBatch = new SpriteBatch();
		
		mTRBG = mAssetsManager.getBackGround();
		mTREarth = new TextureRegion(mAssetsManager.getEarch(), 10, 0, 288, 112);
		
		mBird = new Bird(this);
		mConduitManager = new ConduitManager(this);
		
		mScore = new Score(this);
		
		resetWorld();
		
		mState = STATE_READY;
		mReady = new Ready(this);
		
		mSoundManager = SoundManager.getInstance();
		mSoundManager.playBGMusic();
		mSoundManager.createSoundWing();
	}
	
	public void dispose() {
		super.dispose();
		
		mCamera = null;
		mCameraUI = null;
		
		if (mAssetsManager != null) {
			mAssetsManager.dispose();
			mAssetsManager = null;
		}
		
		mBatch = null;
		
		mTRBG = null;
		mTREarth = null;
		
		if (mBird != null) {
			mBird.dispose();
			mBird = null;
		}
		if (mConduitManager != null) {
			mConduitManager.dispose();
			mConduitManager = null;
		}
		if (mReady != null) {
			mReady.dispose();
			mReady = null;
		}
		if (mGameOver != null) {
			mGameOver.dispose();
			mGameOver = null;
		}
		if (mScore != null) {
			mScore.dispose();
			mScore = null;
		}
		
		if (mSoundManager != null) {
			mSoundManager.dispose();
			mSoundManager = null;
		}
	}
	
	private void resetWorld() {
		mCamera.position.x = SCREEN_WIDTH>>1;
		mCameraUI.position.x = SCREEN_WIDTH>>1;
		
		mBird.init();
		
		mEarthOffsetX = 0;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		keyHandle();
		updateWorld();
		drawWorld();
	}
	
	private void updateWorld() {
		float detalTime = Gdx.graphics.getDeltaTime();
		mStateTime += detalTime;
		
		switch (mState) {
		case STATE_READY:
			break;
		case STATE_RUNNING:
			mBird.update(detalTime);
			
			mCamera.position.x = mBird.getBirdPosition().x + CAMERA_OFFSET_X;
			if (mCamera.position.x - (SCREEN_WIDTH>>1) - mEarthOffsetX > SCREEN_WIDTH) {
				mEarthOffsetX += SCREEN_WIDTH;
			}
			
			mConduitManager.createCtrl();
			mConduitManager.checkToSetFree();
			
			checkCollisionWithConduit();
			checkCollisionWithEarth();
			break;
		case STATE_GAME_OVER:
			mBird.update(detalTime);
			
			checkCollisionWithEarth();
			break;
		}
	}
	
	private void drawWorld() {
		switch (mState) {
		case STATE_READY:
			mCameraUI.update();
			mBatch.setProjectionMatrix(mCameraUI.combined);
			mBatch.begin();
			mBatch.draw(mTRBG, 0, 0);
			mBatch.end();
			
			mCamera.update();
			mBatch.setProjectionMatrix(mCamera.combined);
			mBatch.begin();
			mBatch.draw(mTREarth, 0, 0);
			mReady.draw(mBatch);
			mBird.draw(mBatch);
			mBatch.end();
			break;
		case STATE_RUNNING:
			mCameraUI.update();
			mBatch.setProjectionMatrix(mCameraUI.combined);
			mBatch.begin();
			mBatch.draw(mTRBG, 0, 0);
			mBatch.end();
			
			mCamera.update();
			mBatch.setProjectionMatrix(mCamera.combined);
			mBatch.begin();
			mConduitManager.draw(mBatch);
			mBatch.draw(mTREarth, mEarthOffsetX, 0);
			mBatch.draw(mTREarth, mEarthOffsetX + SCREEN_WIDTH, 0);
			mBird.draw(mBatch);
			mScore.draw(mBatch);
			mBatch.end();
			break;
		case STATE_GAME_OVER:
			mCameraUI.update();
			mBatch.setProjectionMatrix(mCameraUI.combined);
			mBatch.begin();
			mBatch.draw(mTRBG, 0, 0);
			mBatch.end();
			
			mCamera.update();
			mBatch.setProjectionMatrix(mCamera.combined);
			mBatch.begin();
			mConduitManager.draw(mBatch);
			mBatch.draw(mTREarth, mEarthOffsetX, 0);
			mBatch.draw(mTREarth, mEarthOffsetX + SCREEN_WIDTH, 0);
			mBird.draw(mBatch);
			if (mBird.isDeadOnEarth()) {
				mGameOver.draw(mBatch);
			}
			mBatch.end();
			break;
		}
	}
	
	private void keyHandle() {
		if (Gdx.input.justTouched()) {
			switch (mState) {
			case STATE_READY:
				switchToRunningFmReady();
				break;
			case STATE_RUNNING:
				mBird.touchHandle();
				break;
			case STATE_GAME_OVER:
				restart();
				break;
			}
		}
	}

	public float getStateTime() {
		return mStateTime;
	}
	
	public float getCameraX() {
		return mCamera.position.x;
	}
	
	private void checkCollisionWithConduit() {
		Rectangle rectBird = mBird.getCollisionRect();
		
		Conduit conduit = null;
		for (int i = 0; i < ConduitManager.MAX_CONDUIT_NUM; ++i) {
			conduit = mConduitManager.getConduit(i);
			if ((conduit != null) && (!conduit.isFree())) {
				if (conduit.getCollisionRect().overlaps(rectBird)) {
					switchToGameOver();
					break;
				}
				
				if (!conduit.isCounted()) {
					if (mBird.getCenterX() >= conduit.getCenterX()) {
						mScore.addScore();
						conduit.setCountedFlag(true);
						mSoundManager.playSoundPoint();
					}
				}
			}
		}
	}
	
	private void checkCollisionWithEarth() {
		Rectangle rectBird = mBird.getCollisionRect();
		if (rectBird.getY() <= EARTH_Y) {
			if (mState != STATE_GAME_OVER) {
				switchToGameOver();
			}
			else {
				mBird.deadOnEarth();
			}
		}
	}
	
	private void switchToRunningFmReady() {
		mState = STATE_RUNNING;
		
		if (mReady != null) {
			mReady.dispose();
			mReady = null;
		}
	}
	
	private void switchToGameOver() {
		mState = STATE_GAME_OVER;
		mBird.die();
		
		mGameOver = new GameOver(this);
		mGameOver.init();
	}
	
	private void restart() {
		if (mGameOver != null) {
			mGameOver.dispose();
			mGameOver = null;
		}
		
		resetWorld();
		mConduitManager.reset();
		mScore.reset();
		
		mState = STATE_READY;
		mReady = new Ready(this);
	}
	
	public int getScore() {
		return (int)mScore.getScore();
	}
	
}
