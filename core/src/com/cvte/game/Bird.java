package com.cvte.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bird {

	private Flappy mFlappy;
	
	private Animation mAnimBird;
	
	private Vector2 mBirdPosition = new Vector2();
	private Vector2 mBirdVelocity = new Vector2();
	private Vector2 mGravity = new Vector2();
	
	private Rectangle mCollisionRect = new Rectangle();
	
	private float mRotation = 0;
	
	private int mState;
	private final int STATE_NORMAL = 0;
	private final int STATE_TO_DIE = 1;
	private final int STATE_DEAD_ON_EARTH = 2;
	
	private final float BIRD_FRAME_DUTATION = 0.08f;

	private final float BIRD_VELOCITY_X = 200;
	private final float BIRD_JUMP_IMPULSE = 350;
	private final float GRAVITY = -18;
	
	private final float ROTATION_FLY = 40;
	private final float ROTATION_END = -90;
	
	public final static int INIT_BIRD_X = 80;
	private final int INIT_BIRD_Y = 260;
	
	
	public Bird(Flappy parent) {
		mFlappy = parent;
		
		mAnimBird = new Animation(BIRD_FRAME_DUTATION,
				Assets.getInstanceValue().getBird(0),
				Assets.getInstanceValue().getBird(1),
				Assets.getInstanceValue().getBird(2));
		mAnimBird.setPlayMode(PlayMode.LOOP);
		
		mState = STATE_NORMAL;
	}
	
	public void dispose() {
		mFlappy = null;
		
		mAnimBird = null;
		
		mBirdPosition = null;
		mBirdVelocity = null;
		mGravity = null;
		
		mCollisionRect = null;
	}

	public void update(float detalTime) {
		mBirdVelocity.add(mGravity);
		mBirdPosition.mulAdd(mBirdVelocity, detalTime);

		mRotation -= 2;
		if (mRotation - 2 < ROTATION_END) {
			mRotation = ROTATION_END;
		}
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion region = mAnimBird.getKeyFrame(mFlappy.getStateTime());
		batch.draw(region, mBirdPosition.x, mBirdPosition.y,
				region.getRegionWidth()>>1, region.getRegionHeight()>>1, region.getRegionWidth(), region.getRegionHeight(), 1, 1, mRotation);
	}
	
	public void touchHandle() {
		if (mState == STATE_NORMAL) {
			mBirdVelocity.set(BIRD_VELOCITY_X, BIRD_JUMP_IMPULSE);
			
			mRotation = ROTATION_FLY;
			
			SoundManager.getInstanceValue().playSoundWing();
		}
	}
	
	public void init() {
		mBirdPosition.set(INIT_BIRD_X, INIT_BIRD_Y);
		mBirdVelocity.set(0, 0);
		mGravity.set(0, GRAVITY);
		
		mRotation = 0;
		
		mState = STATE_NORMAL;
	}
	
	public Vector2 getBirdPosition() {
		return mBirdPosition;
	}
	
	public Rectangle getCollisionRect() {
		TextureRegion region = mAnimBird.getKeyFrame(0);
		mCollisionRect.set(mBirdPosition.x, mBirdPosition.y, region.getRegionWidth(), region.getRegionHeight());
		return mCollisionRect;
	}
	
	public void die() {
		mState = STATE_TO_DIE;
		mBirdVelocity.x = 0;
		
		SoundManager.getInstanceValue().playSoundDie();
	}
	
	public void deadOnEarth() {
		if (mState !=  STATE_DEAD_ON_EARTH) {
			mState = STATE_DEAD_ON_EARTH;

			mBirdVelocity.set(0, 0);
			mBirdPosition.y = Flappy.EARTH_Y;
			mGravity.set(0, 0);
		}
	}
	
	public boolean isDeadOnEarth() {
		return (mState == STATE_DEAD_ON_EARTH);
	}
	
	public float getCenterX() {
		return mBirdPosition.x + (mAnimBird.getKeyFrame(0).getRegionWidth()>>1);
	}
	
}
