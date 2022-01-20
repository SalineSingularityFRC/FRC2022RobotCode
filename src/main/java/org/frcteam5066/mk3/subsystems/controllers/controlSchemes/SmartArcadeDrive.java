package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import frc.controller.*;
import frc.robot.CellCollector;
import frc.robot.Climber;
import frc.robot.ColorSensor;
import frc.robot.Conveyor;
import frc.robot.DrivePneumatics;
import frc.robot.Flywheel;
import frc.robot.LimeLight;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SmartSingDrive;
import frc.singularityDrive.SmartSingDrive.SpeedMode;
import edu.wpi.first.wpilibj.smartdashboard.*;

//Uncomment to enable gyro stuff
//import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * Main class to control the robot
 * 
 */
public class SmartArcadeDrive extends ControlScheme {

    //Create all objects & a speedMode object
    XboxController driveController, armController;

    SpeedMode speedMode;

    boolean lowGear;
    boolean climberExtended;
    boolean climberDown;

    /**
     * 
     * @param driveControllerPort Controller port the drive controller is connected to, probably 0
     * @param armControllerPort Controller port the arm controller is connect to, problably 1
     */
    public SmartArcadeDrive(int driveControllerPort, int armControllerPort) {
        driveController = new XboxController(driveControllerPort);
        armController = new XboxController(armControllerPort);

        lowGear = true;
        climberExtended = false;
        speedMode = SpeedMode.FAST;

    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

    }

    /**
     * Drives arcade drive
     * 
     */
    public void smartDrive(SmartSingDrive drive, DrivePneumatics pneumatics) {
        //Switch speed mode object, set to low with left bumber and high with right bumper
        if(driveController.getLB()) {
            speedMode = SpeedMode.SLOW;
        }

        else if(driveController.getRB()) {
            speedMode = SpeedMode.FAST;
        }

        //Put current speedMode on SmartDashboard
        SmartDashboard.putString("Speed Mode", "" + speedMode);


        //Change physical pneumatic gearing with the start button (high gear) and back button (low gear).
        //This sets a boolean lowGear
        if(driveController.getStartButton()) {
            lowGear = false;
        }

        else if(driveController.getBackButton()) {
            lowGear = true;
        }

        // lowGear is used to actually set the drive pneumatics to intended value.
        if(lowGear) {
            pneumatics.setLow();
        }

        else {
            pneumatics.setHigh();
        }

        //
        //IMPORTANT
        //
        //The only line actually needed to drive - takes in control sticks, speed mode, and drives based on BasicDrive.
        drive.arcadeDrive(driveController.getLS_Y(), driveController.getRS_X(), 0.0, true, speedMode);

        // Use the d-pad/POV hat on the gamepad to drive the robot slowly in any direction for precise adjustments.
        if(driveController.getPOVLeft()) {
            drive.arcadeDrive(0, -0.1, 0.0, false, SpeedMode.FAST);
        }
        else if (driveController.getPOVRight()) {
            drive.arcadeDrive(0.0, 0.1, 0.0, false, SpeedMode.FAST);
        }
        else if (driveController.getPOVDown()) {
            drive.arcadeDrive(-0.1, 0.0, 0.0, false, SpeedMode.FAST);
        }
        else if (driveController.getPOVUp()) {
            drive.arcadeDrive(0.1, 0, 0.0, false, SpeedMode.FAST);
        }

    }

    /**
     * method that controls the conveyor, collector, and flywheel as the three need to move together
     * 
     */
    public void conveyorFlywheel(Conveyor conveyor, Flywheel flywheel) {
        //Flywheel shooter controlled independantly which allows it to ramp up to speed before shooting
        //Turns on when the left trigger is pressed, then turns off when released
        if(armController.getTriggerLeft() > .5) {
            flywheel.flywheelForward();
        }

        else {
            flywheel.flywheelOff();
        }

        //When LB is pressed, the intake turns on and the conveyor is moved simultaneously to feed up to the flywheel feed
        //turns of when released

        if(armController.getRB()) {
            conveyor.conveyorForward();
        }

        else {
            conveyor.conveyorOff();
        }



        //When the right trigger is pressed, the green wheel begins feeding power cells into the ramped up intake
        //Only allow power cells to be fed when the flywheel is running
        if(armController.getTriggerRight() > .5 && armController.getTriggerRight() > .5) {
            flywheel.flywheelFeedOn();
        }

        else if(armController.getBButton()) {
            flywheel.flywheelFeedReverse();
        }

        else {
            flywheel.flywheelFeedOff();
        }
    }

    public void colorSensor(ColorSensor colorSensor){
        if(armController.getPOVUp()) {
            colorSensor.spinColorWheelColor();
            colorSensor.resetCount(false);
        } 
        
        else if (armController.getPOVDown()) {
            colorSensor.spinColorWheelRotations(26);
            colorSensor.resetCount(false);
        } 
        
        else if (armController.getPOVLeft()) {
            colorSensor.resetCount(true);
        } 
        
        else if (armController.getPOVRight()) {
            colorSensor.spinSpeed(ColorSensor.lowspeed);
            colorSensor.resetCount(false);
        } 
        
        else {
            colorSensor.stopSpinning();
            colorSensor.resetCount(false);
        }

        if(armController.getStartButton()) {
            colorSensor.extend();
        }

        else if(armController.getBackButton()) {
            colorSensor.retract();
        }
    }

    public void climber(Climber climber) {

        if(driveController.getBButton()) {
            climber.rachetDown();
        }

        else {
            climber.rachetOffVel();
        }

        if(driveController.getXButton()) {
            climber.setLow();
        }

        else if(driveController.getYButton()) {
            climber.setHigh();
        }
 
    }

    public void climberReset(Climber climber) {
        if(driveController.getXButton()) {
            climber.rachetReset();
        }

        else if(driveController.getYButton()) {
            climber.rachetWind();
        }

        else {
            climber.rachetOffSpeed();
        }

    }

    public void limeLightDrive(LimeLight limeLight, SmartSingDrive drive, boolean runLimeLight){
        SmartDashboard.putNumber("got here", 69.0);
        if(driveController.getAButton()){
            limeLight.runLimeLight(drive);
        }
        if(runLimeLight){
            limeLight.runLimeLight(drive);
        }
    }


    /**
     * Only turns on the painfully bright Limelight LEDs when they're being used
     * @param limelight takes in Limelight object
     */
    public void ledMode(LimeLight limeLight ){

        if(driveController.getAButton()){
            limeLight.ledOn(limeLight);
        }
        else{
            limeLight.ledOff(limeLight);
        }
    }

}
    