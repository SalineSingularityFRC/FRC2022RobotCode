package org.frcteam5066.mk3.subsystems.controllers.autonomous;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

import frc.robot.Flywheel;
import frc.robot.LimeLight;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.*;
import frc.robot.CellCollector;


public abstract class AutonControlScheme {

    protected static AHRS gyro;
    protected static SingDrive drive;
    protected static LimeLight limeLight;
    protected static Flywheel flywheel;
    protected static Conveyor conveyor;
    

    public static final double radius = 3.125;
    
    public static final double encoderTicks = 16.28;

    public AutonControlScheme(SingDrive drive, LimeLight limeLight, Flywheel flywheel, Conveyor conveyor){
        //define Limelight and all the sensors
        this.drive = drive;
        //this.gyro = new AHRS(SerialPort.Port.kUSB);//Via USB
        this.gyro = new AHRS(SPI.Port.kMXP);//Plugged In
        this.limeLight = limeLight;
        this.flywheel = flywheel;
        this.conveyor = conveyor;
        this.cellCollector = cellCollector;
    }

    //the main method of each auton programs
    public abstract void moveAuton();
    
    /**
     * How to make the robot move forward or backwards autonomously.
     * DISCLAIMER If you want the robot to go backwards set verticalSpeed number to negative
     * @param distance the absolute value of the distance in inches the robot will travel 
     * @param verticalSpeed The speed between 0 and 1 the robot will go. 
     */
    public void vertical(double distance, double verticalSpeed){
        
        //if(distance < 0) verticalSpeed *= -1;

        drive.setInitialPosition();

        while ( drive.getCurrentPosition() / encoderTicks > -1 * Math.abs(distance) /( 2* Math.PI *radius)
                && drive.getCurrentPosition() / encoderTicks < Math.abs(distance) / ( 2* Math.PI *radius)) {
        
            SmartDashboard.putNumber("encoderRotations", drive.getCurrentPosition() / encoderTicks);
            SmartDashboard.putNumber("goal", distance / (2 * Math.PI * radius));

            drive.arcadeDrive(verticalSpeed, 0, 0.0, false, SpeedMode.NORMAL);
        
        }
        
        drive.arcadeDrive(0, 0, 0.0, false, SpeedMode.NORMAL);

    }

    public void vertical(double distance){
        vertical(distance, distance / Math.abs(distance) *0.3);
    }

    public void verticalWithCollector(double distance){
        cellCollector.collectorForward();
        conveyor.conveyorForward();
        vertical(distance);
        cellCollector.collectorOff();
    }


    public void rotate(double angle, boolean isCounterClockwise){
        rotate(0.2, angle, isCounterClockwise);
    }

    public void rotate(double rotationSpeed, double angle, boolean isCounterClockwise){
        gyro.reset();
        if(isCounterClockwise) rotationSpeed*= -1;
        SmartDashboard.putNumber("GyroAngle", gyro.getAngle());

		while(gyro.getAngle() < angle) {

            SmartDashboard.putNumber("GyroAngle", gyro.getAngle());
			
			//TODO accelerate motors slowly
            //drive.rampVoltage();
			
			drive.arcadeDrive(0.0, rotationSpeed, 0.0, false, SpeedMode.NORMAL);
		}
		
		drive.arcadeDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);

    }

    public void adjustToTarget(){
        while(drive.limeLightDrive(limeLight));
    }

    public void shoot(){
        /*flywheel.flywheelForward();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            
        }

        flywheel.flywheelFeedOn();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            
        }

        conveyor.conveyorForward();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            
        }
        
        flywheel.flywheelOff();
        flywheel.flywheelFeedOff();
        conveyor.conveyorOff();
        */
    }

}