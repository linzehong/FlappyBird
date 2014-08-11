package com.cvte.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ready {

	private Flappy mFlappy;
	
	private TextureRegion mTRReady;
	private TextureRegion mTRTipTap;
	
	private final int READY_Y = 370;
	private final int TIPTAP_Y = 240;
	
	public Ready(Flappy parent) {
		mFlappy = parent;
		
		mTRReady = Assets.getInstanceValue().getReady();
		mTRTipTap = Assets.getInstanceValue().getTipTap();
	}
	
	public void dispose() {
		mFlappy = null;
		
		mTRReady = null;
		mTRTipTap = null;
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(mTRReady, mFlappy.getCameraX() - (mTRReady.getRegionWidth()>>1), READY_Y - (mTRReady.getRegionHeight()>>1));
		batch.draw(mTRTipTap, mFlappy.getCameraX() - (mTRTipTap.getRegionWidth()>>1), TIPTAP_Y - (mTRTipTap.getRegionHeight()>>1));
	}
	
}
