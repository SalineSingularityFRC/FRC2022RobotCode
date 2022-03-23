package org.frcteam5066.mk3.subsystems;

import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Falcon;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Spark;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;

//This intake class includes both the collector and conveyor
public class Intake {
 
    MotorController intakeDrive, intakeConveyor;
    Falcon intakeDeploy;
 
 
    double kP = 0.02;
    double kI = 0.0000;
    double kD = 0.0;
    double kIz = 0;
    double kFF = 0.0;
    double kMaxOutput = .5;
    double kMinOutput = -.5;
    //TODO increase deploy speed
    double maxRPMIntake = 11000;
    // maxRPMIntakeconveyor is copied from 2021 conveyor class
    double maxRPMIntakeconveyor = -4000;
    double maxRPMFeed = 5700;
    double deployPosition = 60000;
    double retractPosition = 8350; //~115 degrees to the 
 
 
 
 
    //Intake Constructor
    public Intake(int intakeDrivePort, int intakeConveyorPort, int intakeDeployPort ){
 
        //550 motors used for intake, unsure if they are brushed
        intakeDrive = new Spark(intakeDrivePort, true, 0.00, "IntakeDrive", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        intakeConveyor = new Spark(intakeConveyorPort, true, 0.00, "IntakeConveyor", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        //intakeDeploy = new Falcon(intakeDeployPort, 1.0, true);
        intakeDeploy = new Falcon(intakeDeployPort, 1, false, false, "rio", kP, kI, kD, kFF, kMaxOutput, kMinOutput);
        //intakeDeploy = new Falcon(canID, rampRate, inverted, coast, canBusName, kP, kI, kD, kF)
    }
 
    

    
    public void intakeCollect() {
        intakeDrive.setSpeed(.3);
        //intakeConveyor.setVelocity(maxRPMIntakeconveyor);
        intakeDrive.setSpeed(.30);
        //TODO Test intake collection
    }
 
    //Use if color senser recognizes opposite team's ball
    public void intakeReject(){
        intakeDrive.setSpeed(-.3);
        //intakeConveyor.setVelocity(-maxRPMIntakeconveyor);
    }
 
    public void intakeOff(){
        intakeDrive.setSpeed(0.0);
        //intakeConveyor.setVelocity(0.0);
    }

    public void conveyorCollect(){
        intakeConveyor.setSpeed(.3);
    }

    public void conveyorReject(){
        intakeConveyor.setSpeed(-.3);
    }

    public void conveyorOff(){
        intakeConveyor.setSpeed(0.0);
    }

    //this method must be run iteratively (NOT COMPLETE)
    public void conveyorPrimeCargo(){
        intakeConveyor.setVelocity(maxRPMIntakeconveyor);
    }

    public void intakeDeploy(){//bring one of the falcons to make the intake deploy, which way is it supposed to turn
        intakeDeploy.setPosition(deployPosition);
        SmartDashboard.putNumber("Target Deploy Position", deployPosition);
        SmartDashboard.putNumber("Deploying Intake from intake.java", 1);
        SmartDashboard.putNumber("Retracting Intake from intake.java", 0);
        
    }

    //TODO add deploy intermediate position
        //This is done so that the shooter can shoot but is high enough that it isn't so clonky 

    public void intakeRetract(){//bring one of the falcons to make the intake deploy
        intakeDeploy.setPosition(retractPosition);
        SmartDashboard.putNumber("Target Deploy Position", retractPosition);
        SmartDashboard.putNumber("Deploying Intake from intake.java", 0);
        SmartDashboard.putNumber("Retracting Intake from intake.java", 1);
    }

    public double getDeploySensorPosition(){
        SmartDashboard.putNumber("Deploy Sensor Position", intakeDeploy.getSelectedSensorPosition());
        return intakeDeploy.getMotorController().getSelectedSensorPosition();
    }

    public double getDeployPercent(){
        return intakeDeploy.getPower();
    }

    
}
//how are we going to deploy the intake out?
//