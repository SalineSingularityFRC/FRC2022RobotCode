package frc.robot;

import frc.controller.MotorController;
import frc.controller.motorControllers.Spark;

public class Shooter {

    Spark flywheelBottom, flywheelTop, flywheelFeed;

    double kP = 6e-5; 
    double kI = 0;
    double kD = 0; 
    double kIz = 0; 
    double kFF = 0.000015; 
    double kMaxOutput = 1; 
    double kMinOutput = -1;
    double maxRPMFlywheelTop = 11000;
    double maxRPMFlywheelBottom = 11000;
    double maxRPMFeed = 5700;

    public Shooter(int flywheelBottomPort, int flywheelTopPort, int flywheelFeedPort){
        flywheelTop = new Spark(flywheelTopPort, true, 0.00, "FlywheelTop", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        flywheelBottom = new Spark(flywheelBottomPort, true, 0.00, "FlywheelBottom", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        flyweelFeed = new Spark(flywheelFeedPort, false, 0.00, "FlywheelFeed", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
    }


    public void shooterOn(){
        flywheelTop.setVelocity(maxRPMFlywheelTop);
        flywheelBottom.setVelocity(maxRPMFlywheelBottom);
    }


     public void shooterOff(){
        flywheelTop.setVelocity(0.0);
        flywheelBottom.setVelocity(0.0);
    }


    public void shooterReverse(){
        flywheelTop.setVelocity(-maxRPMFlywheelTop);
        flywheelBottom.setVelocity(-maxRPMFlywheelBottom);
    }


    public void hold(){
        flywheelFeed.setVelocity(0.0);
    }


    public void shoot(){
        flywheelFeed.setVelocity(maxRPMFeed);
    }
}