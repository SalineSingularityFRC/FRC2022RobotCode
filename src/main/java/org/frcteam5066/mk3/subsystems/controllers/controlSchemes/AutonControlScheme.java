//auton is where you talk about what is happening 15 seconds before you start
package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.kauailabs.navx.frc.AHRS;

import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.SerialPort;
//we have to create 4 paths

public abstract class AutonControlScheme {

    //protected static AHRS gyro;
    protected static LimeLight limeLight;
    protected static Shooter flywheel;
    protected static Intake conveyor;
    protected static Intake intake;

    public static final double radius = 3.125;
    
    public static final double encoderTicks = 16.28;

    public AutonControlScheme(LimeLight limeLight, Shooter flywheel, Intake conveyor, DrivetrainSubsystem drive){
        //define Limelight and all the sensors
        //this.gyro = new AHRS(SerialPort.Port.kUSB);//Via USB
       // this.gyro = new AHRS(SPI.Port.kMXP);//Plugged In
        this.limeLight = limeLight;
        this.flywheel = flywheel;
        this.conveyor = conveyor;
        this.intake = intake;
    }

    //the main method of each auton programs
    /**
     * How to make the robot move forward or backwards autonomously.
     * DISCLAIMER If you want the robot to go backwards set verticalSpeed number to negative
     * The speed between 0 and 1 the robot will go. 
     * @return 
     * @return 
     */
    public void (){
        
    }

    public void vertical(double distance){
        vertical(distance, distance / Math.abs(distance) *0.3);
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
    //.\public AutonControlScheme(Limt, )

  
    }

    //public void shoot(){
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