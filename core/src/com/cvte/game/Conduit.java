package com.cvte.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Conduit {

	private TextureRegion mTRConduit;
	
	private Rectangle mCollisionRect = new Rectangle();
	
	private float mX;
	private float mY;
	
	private boolean mIsCounted = false;//标识是否已经算分数了
	
	private int mState;
	private final int STATE_FREE = 0;
	private final int STATE_USING = 1;
	
	public final static int CONDUIT_HEIGHT = 320;
	
	public Conduit() {
		mState = STATE_FREE;
	}
	
	public void dispose() {
		mTRConduit = null;
		
		mCollisionRect = null;
	}
	
	public void draw(SpriteBatch batch) {
		if (mState == STATE_USING) {
			batch.draw(mTRConduit, mX, mY);
		}
	}
	
	public void init(boolean isDown, float x, float y) {
		mTRConduit = Assets.getInstanceValue().getConduit(isDown);
		
		mX = x;
		mY = y;

		mIsCounted = false;
		
		mState = STATE_USING;
	}
	
	public void setToFree() {
		mState = STATE_FREE;
		
		mIsCounted = false;
	}
	
	public boolean isFree() {
		return (mState == STATE_FREE);
	}
	
	public float getX() {
		return mX;
	}
	
	public float getY() {
		return mY;
	}
	
	public float getWidth() {
		return mTRConduit.getRegionWidth();
	}

	public float getHeight() {
		return mTRConduit.getRegionHeight();
	}
	
	public float getCenterX() {
		return mX + (mTRConduit.getRegionWidth()>>1);
	}
	
	public Rectangle getCollisionRect() {
		mCollisionRect.set(mX, mY, mTRConduit.getRegionWidth(), mTRConduit.getRegionHeight());
		return mCollisionRect;
	}
	
	public boolean isCounted() {
		return mIsCounted;
	}
	
	public void setCountedFlag(boolean isCounted) {
		mIsCounted = isCounted;
	}
	
}
