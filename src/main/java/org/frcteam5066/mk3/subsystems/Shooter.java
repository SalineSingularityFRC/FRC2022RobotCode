package org.frcteam5066.mk3.subsystems;

import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Falcon;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Spark;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Falcon;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;

public class Shooter {
 
    MotorController flywheel1, flywheel2, flywheelFeed;
 //motorcontroller class is the parent class(not all motor controllers are falcons)
 //specify that something is a moter controller, then later specify if it's a falcon
    double kP = 6e-5;
    double kI = 0;
    double kD = 0;
    double kIz = 0;
    double kFF = 0.000015;
    double kMaxOutput = 1;
    double kMinOutput = -1;
    double maxRPMflywheel2 = 11000;
    double maxRPMflywheel1 = 11000;
    double maxRPMFeed = 5700;
    double barfRPM = 1000;
 //change can ID of falcons to 60 on phoenix tuner 
    public Shooter(int flywheel1Port, int flywheel2Port, int flywheelFeedPort){
       // flywheel2 = new Falcon(60, 1, true);
        flywheel1 = new Falcon(61, 1.0, true);
        flywheelFeed = new Spark(flywheelFeedPort, true, 0.00, "FlywheelFeed", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        //flywheel2.follow(flywheel1, true);
    }
 
 
    public void shooterOn(){
       // flywheel2.setVelocity(maxRPMflywheel2);
        flywheel1.setVelocity(maxRPMflywheel1);
    }
 
 
     public void shooterOff(){
        //flywheel2.setVelocity(0.0);
        flywheel1.setVelocity(0.0);
    }
 
 
    public void shooterReverse(){
        //flywheel2.setVelocity(-maxRPMflywheel2);
        flywheel1.setVelocity(-maxRPMflywheel1);
    }
 
 
    public void hold(){
        flywheelFeed.setVelocity(0.0);
    }
 
 
    public void shoot(){
        flywheelFeed.setVelocity(maxRPMFeed);
    }

    public void feederReverse () {
        flywheelFeed.setVelocity(-maxRPMFeed);
    }

    public void barf() {
        flywheel1.setVelocity(barfRPM);
        //flywheel2.setVelocity(barfRPM);
    }
}
