package org.frcteam5066.mk3.subsystems.controllers;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is a subclass of Joystick where the methods
 * are conveniently named. For example, instead of remembering
 * the port number of the B button on the Xbox controller,
 * one can simply use the method getBButton() through this class.
 * 
 * @author camtr
 *
 */

public class XboxController extends Joystick{
	
	/**
	 * Constructor for XboxController
	 * 
	 * @param port is the port number needed to 
	 * communicate with the xbox controller. In the past,
	 * this number has been 0 by default.
	 * 
	 */
	
	public XboxController(int port) {
		super(port);
	}
	
	public boolean getAButton() {
		return this.getRawButton(1);
	}
	
	public boolean getBButton() {
		return this.getRawButton(2);
	}
	
	public boolean getXButton() {
		return this.getRawButton(3);
	}
	
	public boolean getYButton() {
		return this.getRawButton(4);
	}
	
	public boolean getLB() {
		return this.getRawButton(5);
	}
	
	public boolean getRB() {
		return this.getRawButton(6);
	}
	
	public boolean getBackButton() {
		return this.getRawButton(7);
	}
	
	public boolean getStartButton() {
		return this.getRawButton(8);
	}
	
	public boolean getL3() {
		return this.getRawButton(9);
	}
	
	public boolean getR3() {
		return this.getRawButton(10);
	}
	
	public double getLS_X() {
		return this.getRawAxis(0);
	}
	
	public double getLS_Y() {
		return -1 * this.getRawAxis(1);
	}
	
	public double getRS_X() {
		return this.getRawAxis(4);
	}
	
	public double getRS_Y() {
		return -1 * this.getRawAxis(5);
	}
	
	public double getTriggerRight() {
		return this.getRawAxis(3);
	}
	public double getTriggerLeft() {
		return this.getRawAxis(2);
	}
	
	public boolean getPOVUp() {
		return (super.getPOV() == 0);
	}
	public boolean getPOVUpRight() {
		return (super.getPOV() == 45);
	}
	public boolean getPOVRight() {
		return (super.getPOV() == 90);
	}
	public boolean getPOVDownRight() {
		return (super.getPOV() == 135);
	}
	public boolean getPOVDown() {
		return (super.getPOV() == 180);
	}
	public boolean getPOVDownLeft() {
		return (super.getPOV() == 225);
	}
	public boolean getPOVLeft() {
		return (super.getPOV() == 270);
	}
	public boolean getPOVUpLeft() {
		return (super.getPOV() == 315);
	}
	public boolean getPOVOff() {
		return (super.getPOV() == -1);
	}
}