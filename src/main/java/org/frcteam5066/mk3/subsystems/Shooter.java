package org.frcteam5066.mk3.subsystems;

import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Falcon;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Spark;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;

public class Shooter {
 
    MotorController flywheel1, flywheel2, flywheelFeed;
 //motorcontroller class is the parent class(not all motor controllers are falcons)
 //specify that something is a moter controller, then later specify if it's a falcon
    double kP = 1e-4;
    double kI = 0;
    double kD = 0;
    double kIz = 0;
    double kFF = 0.0005;
    double kMaxOutput = 1;
    double kMinOutput = -1;
    double maxRPMflywheel2 = 6380;
    double maxRPMflywheel1 = 6380;
    double idealRPM = 3000; //@ 1.22 faster gearing, around the distance we want
    double flywheelkP = .03;
    double flywheelkI = 0.0;
    double flywheelkD = 0;
    double flywheelFF = 0.04766;
    double maxRPMFeed = 5700;
    double barfRPM = 1500;
    double gearRatio = 1.22;
    boolean ceaseFlywheel = false;

    private static double lastSetVelocity = 0;

    /*double velocity = Math.sqrt((-.5 * 9.807 * Math.pow( distance, 2 )) / 
                    ( Math.pow( Math.cos(Math.toRadians(60) ), 2 ) * ( height - distance * Math.tan(Math.toRadians(60)) ) ) );
            //plz never make me type that again*/

 //change can ID of falcons to 60 on phoenix tuner 
    public Shooter(int flywheel1Port, int flywheel2Port, int flywheelFeedPort){
       // flywheel2 = new Falcon(60, 1, true);
        //flywheel1 = new Falcon(61, 1.0, true);
        flywheel1 = new Falcon(61, 0.0, true, "rio", flywheelkP, flywheelkI, flywheelkD, flywheelFF);
        flywheelFeed = new Spark(flywheelFeedPort, true, 0.00, "FlywheelFeed", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        //flywheelFeed = new Spark(portNumber, brushlessMotor, rampRate)
        //flywheel2.follow(flywheel1, true);
        SmartDashboard.putNumber("Controllable Velocity", 3500);
    }
 
    private double computeVelocity(double distance){
        double velocity = Math.sqrt((-.5 * 9.807 * Math.pow( distance, 2 )) / 
                    ( Math.pow( Math.cos(Math.toRadians(60) ), 2 ) * ( 2.2 - distance * Math.tan(Math.toRadians(60)) ) ) );
            //plz never make me type that again
        return velocity;
        
    }
    
    public void setCeaseFlywheel(boolean set){
        ceaseFlywheel = set;
    }

    public void flywheelOn(){
        //flywheel2.setVelocity(maxRPMflywheel2);
        
        //double flywheelVelo = SmartDashboard.getNumber("Controllable Velocity", 3100);
        double flywheelVelo = 3100;
        flywheel1.setVelocity(flywheelVelo);
        lastSetVelocity = flywheelVelo;
    }

    public void flywheelOn(double distance){

        flywheel1.setVelocity( computeVelocity(distance) / gearRatio);
        lastSetVelocity = computeVelocity(distance) / gearRatio;
    }
 
 
     public void flywheelOff(){
        //flywheel2.setVelocity(0.0);
        if(ceaseFlywheel){
            flywheel1.setSpeed(0.0);
            lastSetVelocity = 0;
        }
    }
 
 
    public void flywheelReverse(){
        //flywheel2.setVelocity(-maxRPMflywheel2);
        flywheel1.setSpeed(-.5);
        lastSetVelocity = -maxRPMflywheel1;
    }
 
 
    public void feederOff(){
        if(ceaseFlywheel) flywheelFeed.setVelocity(0.0);
    }
 
 
    public void feederOn(){
        flywheelFeed.setSpeed(-.9);
    }

    public void feederOnIntake(){
        flywheelFeed.setSpeed(-.350);
    }

    public void feederReverse () {
        flywheelFeed.setVelocity(maxRPMFeed);
    }

    public void barf() {
        flywheel1.setVelocity(barfRPM);
        lastSetVelocity = barfRPM;
        SmartDashboard.putNumber("Barfing", 1);
        //flywheel2.setVelocity(barfRPM);
    }
    
    public boolean readyToShoot(){
        SmartDashboard.putNumber("current flywheel velocity", getFlywheelVelocity());
        SmartDashboard.putNumber("Last Set Velocity", lastSetVelocity);
        //SmartDashboard.putNumber("Is Flywheel Ready", (getFlywheelVelocity() >= lastSetVelocity - 200 && getFlywheelVelocity() <= lastSetVelocity + 200)? 1:0 );
        return (getFlywheelVelocity() >= lastSetVelocity - 200 && getFlywheelVelocity() <= lastSetVelocity + 200);
    }

    public double getFlywheelVelocity(){
        return flywheel1.getVelocity();
    }

    

}
