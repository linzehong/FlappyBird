package com.cvte.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {
	private static Assets instance;
	
	private TextureAtlas mAtlas;
	
	public static Assets getInstance() {
		if (instance == null) {
			instance = new Assets();
		}
		return instance;
	}
	
	public static Assets getInstanceValue() {
		return instance;
	}
	
	private Assets() {
		mAtlas = new TextureAtlas(Gdx.files.internal("atlas.txt"));
	}
	
	public void dispose() {
		if (mAtlas != null) {
			mAtlas.dispose();
			mAtlas = null;
		}
		
		instance = null;
	}
	
	public TextureRegion getBackGround() {
		return mAtlas.findRegion("background1");
	}
	
	public TextureRegion getEarch() {
		return mAtlas.findRegion("bottom");
	}
	
	public TextureRegion getReady() {
		return mAtlas.findRegion("hint1");
	}
	
	public TextureRegion getTipTap() {
		return mAtlas.findRegion("confirm");
	}
	
	public TextureRegion getConduit(boolean isDown) {
		String regionName = (isDown) ? ("conduit/conduitA1") : ("conduit/conduitA2");
		return mAtlas.findRegion(regionName);
	}
	
	private final String[] BIRD_REGION_NAMES = {
			"bird/BBird1", "bird/BBird2", "bird/BBird3",
	};
	
	public TextureRegion getBird(int index) {
		return mAtlas.findRegion(BIRD_REGION_NAMES[index]);
	}
	
	public TextureRegion getOver() {
		return mAtlas.findRegion("hint2");
	}
	
	public TextureRegion getScoreBoard() {
		return mAtlas.findRegion("bangdan");
	}
	
	public TextureRegion getButtonStart() {
		return mAtlas.findRegion("button");
	}
	
	public TextureRegion getButtonRank() {
		return mAtlas.findRegion("button2");
	}
}
