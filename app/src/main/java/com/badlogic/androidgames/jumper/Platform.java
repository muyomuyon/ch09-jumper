package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Platform extends DynamicGameObject {
    /*足場の幅と高さ*/
	public static final float PLATFORM_WIDTH = 2;
	public static final float PLATFORM_HEIGHT = 0.5f;

    /*静止している足場*/
	public static final int PLATFORM_TYPE_STATIC = 0;
    /*動いている足場*/
	public static final int PLATFORM_TYPE_MOVING = 1;

    /*壊れていない足場*/
	public static final int PLATFORM_STATE_NORMAL = 0;
    /*壊れている足場*/
	public static final int PLATFORM_STATE_PULVERIZING = 1;
    /*壊れる時間（アニメーション時間*段階数）*/
	public static final float PLATFORM_PULVERIZE_TIME = 0.2f * 4;

    /*足場が動く速度*/
	public static final float PLATFORM_VELOCITY = 2;
	
	int type;/*足場のタイプ（動くか動かないか）*/
	int state;/*足場の状態（壊れていないか壊れているか）*/
	float stateTime;/*その状態でいる時間*/
	
	public Platform(int type, float x, float y) {
		super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		this.type = type;
		this.state = PLATFORM_STATE_NORMAL;
		this.stateTime = 0;
		if(type == PLATFORM_TYPE_MOVING) {
			velocity.x = PLATFORM_VELOCITY;
		}
	}
	
	public void update(float deltaTime) {
		if(type == PLATFORM_TYPE_MOVING) {
			position.add(velocity.x * deltaTime, 0);
			bounds.lowerLeft.set(position).sub(PLATFORM_WIDTH / 2, PLATFORM_HEIGHT / 2);

			if(position.x < PLATFORM_WIDTH / 2) {
				velocity.x = -velocity.x;
				position.x = PLATFORM_WIDTH / 2;
			}
			if(position.x > World.WORLD_WIDTH - PLATFORM_WIDTH / 2) {
				velocity.x = -velocity.x;
				position.x = World.WORLD_WIDTH - PLATFORM_WIDTH / 2;
			}
		}
		stateTime += deltaTime;
	}	
	
	public void pulverize() {
		state = PLATFORM_STATE_PULVERIZING;
		stateTime = 0;
		velocity.x = 0;
	}
}
