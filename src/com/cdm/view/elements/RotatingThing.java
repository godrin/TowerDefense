package com.cdm.view.elements;

public class RotatingThing {
	private float currentAngle = 0.0f;
	private float targetAngle = 0.0f;
	private float turningSpeed = 90.0f;

	public float getCurrentAngle() {
		return currentAngle;
	}

	public void setCurrentAngle(float currentAngle) {
		this.currentAngle = currentAngle;
	}

	public float getTargetAngle() {
		return targetAngle;
	}

	public void setTargetAngle(float targetAngle) {
		this.targetAngle = targetAngle;
	}

	public float getTurningSpeed() {
		return turningSpeed;
	}

	public void setTurningSpeed(float turningSpeed) {
		this.turningSpeed = turningSpeed;
	}

	public float move(float time) {
		if (targetAngle - 180 > currentAngle)
			targetAngle -= 360;
		if (targetAngle + 180 < currentAngle)
			targetAngle += 360;

		float turnVec = targetAngle - currentAngle;
		float turnDir = Math.signum(turnVec);
		float target = currentAngle + turnDir * turningSpeed * time;
		if (Math.signum(targetAngle - target) != turnDir
				|| Math.abs(targetAngle - target) < 0.5f) {
			// reached
			
			currentAngle = targetAngle;
			float timediff= Math.abs(turnVec)/turningSpeed;
			return timediff;
		} else {
			currentAngle = target;
			// all used up
			return time;
		}

	}

}
