package com.cvte.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {
	private static SoundManager instance;
	
	private Music mMusicBG;
	
	private Sound mSoundWing;
	private Sound mSoundDie;
	private Sound mSoundPoint;
	
	public static SoundManager getInstance() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	public static SoundManager getInstanceValue() {
		return instance;
	}
	
	private SoundManager() {
		
	}
	
	public void dispose() {
		if (mMusicBG != null) {
			mMusicBG.dispose();
			mMusicBG = null;
		}
		
		if (mSoundWing != null) {
			mSoundWing.dispose();
			mSoundWing = null;
		}
		if (mSoundDie != null) {
			mSoundDie.dispose();
			mSoundDie = null;
		}
		if (mSoundPoint != null) {
			mSoundPoint.dispose();
			mSoundPoint = null;
		}
		
		instance = null;
	}
	
	public void playBGMusic() {
		mMusicBG = Gdx.audio.newMusic(Gdx.files.internal("bgmusic.mp3"));
		mMusicBG.setLooping(true);
		mMusicBG.play();
	}
	
	public void createSoundWing() {
		if (mSoundWing == null) {
			mSoundWing = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
		}
	}
	
	public void playSoundWing() {
		createSoundWing();
		
		if (mSoundWing != null) {
			mSoundWing.play();
		}
	}
	
	public void createSoundDie() {
		if (mSoundDie == null) {
			mSoundDie = Gdx.audio.newSound(Gdx.files.internal("sfx_die.ogg"));
		}
	}
	
	public void playSoundDie() {
		createSoundDie();
		
		if (mSoundDie != null) {
			mSoundDie.play();
		}
	}

	public void createSoundPoint() {
		if (mSoundPoint == null) {
			mSoundPoint = Gdx.audio.newSound(Gdx.files.internal("sfx_point.ogg"));
		}
	}
	
	public void playSoundPoint() {
		createSoundPoint();
		
		if (mSoundPoint != null) {
			mSoundPoint.stop();
			mSoundPoint.play();
		}
	}
	
}
