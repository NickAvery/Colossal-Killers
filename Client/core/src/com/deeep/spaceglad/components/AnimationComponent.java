package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;

// Created by Kevin Dorscher 04-04-2018

public class AnimationComponent extends Component
{
	//Nick A for HW#6
	private AnimationController animationController;
	private AnimationDesc currentAnimation;
	
	public AnimationComponent (ModelInstance instance)
	{
		animationController = new AnimationController(instance);
		animationController.allowSameAnimation = true;
		currentAnimation = null;
	}
	
	public AnimationController getController()
	{
		return animationController;
	}
	
	public AnimationDesc getDesc()
	{
		return currentAnimation;
	}
	
	public void animate(final String id, final int loops, final int speed)
	{
		currentAnimation = animationController.animate(id, loops, speed, null, 0);
	}
	
	public void animate(String id, float offset, float duration, int loopCount, int speed)
	{
		currentAnimation = animationController.animate(id, offset, duration, loopCount, speed, null, 0);
	}
	
	public void action(String id, int loopCount, int speed)
	{
		currentAnimation = animationController.action(id, loopCount, speed, null, 0);
	}
	//end
	public void update(float delta)
	{
		animationController.update(delta);
	}
}
