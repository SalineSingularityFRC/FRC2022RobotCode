package org.frcteam5066.mk3.subsystems.controllers;


/**
 * The MotorController interface allows us to create MotorController classes (implememnting this interface)
 * with standard methods. This should allow handling sudden changes in motor controller types on the robot
 * to be much more painless... Simply change the instantiation to a new type.
 */
public interface MotorController {

    /**
     * using the followw() method slaves a motor controller to another motor controller so that we only have
     * to change the speed of the base.
     * @param baseController the motor controller that we will actually control
     * @param invert pass true if the direction of the motors are opposite
     */
    public void follow(MotorController baseController, boolean invert);

    /**
     * Set the motor controller to percent of the available voltage
     * @param percentOutput between -1.0 and 1.0
     */
    public void setSpeed(double percentOutput);

    /**
     * Allows adjustiability in motor ramping to limit wear on motors
     * @param rampRate number of seconds to go from 0 to full power
     */
    public void setRampRate(double rampRate);

    /**
     * Allows control of letting motor controllers coast or forcing them to brake
     * @param coast pass true to coast, false to brake (recommended is true for coast)
     */
    public void setCoastMode(boolean coast);

    public void setVelocity(double velocity);

    public double getVelocity();

    public abstract double getRotationsSpun();
    public abstract void resetRotationsZero();
    public double getMotorRPM();


    
}