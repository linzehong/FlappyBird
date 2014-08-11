package com.cvte.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class ConduitManager {

	private Flappy mFlappy;
	
	private Conduit[] mConduitArray;
	
	private int mDelayCount;//延时计数
	
	public final static int MAX_CONDUIT_NUM = 6;
	
	private final int DELAY_TO_CREATE = 80;
	
	private final int CONDUIT_GAP = 140;//水管中间空白距离
	
	public ConduitManager(Flappy parent) {
		mFlappy = parent;
		
		mConduitArray = new Conduit[MAX_CONDUIT_NUM];
	}
	
	public void dispose() {
		mFlappy = null;
		
		if (mConduitArray != null) {
			for (int i = mConduitArray.length - 1; i >= 0; --i) {
				mConduitArray[i] = null;
			}
			mConduitArray = null;
		}
	}
	
	public void draw(SpriteBatch batch) {
		for (int i = mConduitArray.length - 1; i >= 0; --i) {
			if ((mConduitArray[i] != null) && (!mConduitArray[i].isFree())) {
				mConduitArray[i].draw(batch);
			}
		}
	}
	
	public void reset() {
		mDelayCount = 0;
		
		if (mConduitArray != null) {
			for (int i = mConduitArray.length - 1; i >= 0; --i) {
				if (mConduitArray[i] != null) {
					mConduitArray[i].setToFree();
				}
			}
		}
	}
	
	private void init(boolean isDown, float x, float y) {
		for (int i = mConduitArray.length - 1; i >= 0; --i) {
			if (mConduitArray[i] == null) {
				mConduitArray[i] = new Conduit();
			}
			if (mConduitArray[i].isFree()) {
				mConduitArray[i].init(isDown, x, y);
				break;
			}
		}
	}
	
	public void createCtrl() {
		++mDelayCount;
		if (mDelayCount > DELAY_TO_CREATE) {
			mDelayCount = 0;
			
			float x = mFlappy.getCameraX() + (Flappy.SCREEN_WIDTH>>1);
			float yDown = MathUtils.random(-110, 40);
			float yUp = yDown + Conduit.CONDUIT_HEIGHT + CONDUIT_GAP;
			init(true, x, yDown);
			init(false, x, yUp);
		}
	}
	
	public void checkToSetFree() {
		for (int i = mConduitArray.length - 1; i >= 0; --i) {
			if ((mConduitArray[i] != null) && (!mConduitArray[i].isFree())) {
				if (mConduitArray[i].getX() + mConduitArray[i].getWidth() < mFlappy.getCameraX() - (Flappy.SCREEN_WIDTH>>1)) {
					mConduitArray[i].setToFree();
				}
			}
		}
	}
	
	public Conduit getConduit(int index) {
		return mConduitArray[index];
	}
	
}
