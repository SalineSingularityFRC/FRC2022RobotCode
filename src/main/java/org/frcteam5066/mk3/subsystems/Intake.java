package org.frcteam5066.mk3.subsystems;

import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Falcon;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Spark;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;

//This intake class includes both the collector and conveyor
public class Intake {
 
    MotorController intakeDrive, intakeConveyor;
    Falcon intakeDeploy;
 
 
    double kP = 0.01;
    double kI = 1e-6;
    double kD = 2e-5;
    double kIz = 0;
    double kFF = 0.000015;
    double kMaxOutput = 1;
    double kMinOutput = -1;
    double maxRPMIntake = 11000;
    // maxRPMIntakeconveyor is copied from 2021 conveyor class
    double maxRPMIntakeconveyor = -4000;
    double maxRPMFeed = 5700;
    double deployPosition = 0;
    double retractPosition = (115) * 201.69; //~115 degrees to the 
 
 
 
 
    //Intake Constructor
    public Intake(int intakeDrivePort, int intakeConveyorPort, int intakeDeployPort ){
 
        //550 motors used for intake, unsure if they are brushed
        intakeDrive = new Spark(intakeDrivePort, true, 0.00, "IntakeDrive", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        intakeConveyor = new Spark(intakeConveyorPort, true, 0.00, "IntakeConveyor", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        //intakeDeploy = new Falcon(intakeDeployPort, 1.0, true);
        intakeDeploy = new Falcon(intakeDeployPort, 1, false, "rio", kP, kI, kD);
    }
 
    

    public void intakeCollect() {
        intakeDrive.setVelocity(maxRPMIntake);
        //intakeConveyor.setVelocity(maxRPMIntakeconveyor);
    }
 
    //Use if color senser recognizes opposite team's ball
    public void intakeReject(){
        intakeDrive.setVelocity(-maxRPMIntake);
        //intakeConveyor.setVelocity(-maxRPMIntakeconveyor);
    }
 
    public void intakeOff(){
        intakeDrive.setVelocity(0.0);
        //intakeConveyor.setVelocity(0.0);
    }

    public void conveyorCollect(){
        intakeConveyor.setVelocity(maxRPMIntakeconveyor);
    }

    public void conveyorReject(){
        intakeConveyor.setVelocity(-maxRPMIntakeconveyor);
    }

    public void conveyorOff(){
        intakeConveyor.setVelocity(0.0);
    }

    //this method must be run iteratively (NOT COMPLETE)
    public void conveyorPrimeCargo(){
        intakeConveyor.setVelocity(maxRPMIntakeconveyor);
    }

    public void intakeDeploy(){//bring one of the falcons to make the intake deploy, which way is it supposed to turn
        intakeDeploy.setPosition(deployPosition);
        SmartDashboard.putNumber("Deploying Intake from intake.java", 1);
        SmartDashboard.putNumber("Retracting Intake from intake.java", 0);
        
    }

    public void intakeRetract(){//bring one of the falcons to make the intake deploy
        intakeDeploy.setPosition(retractPosition);
        SmartDashboard.putNumber("Deploying Intake from intake.java", 0);
        SmartDashboard.putNumber("Retracting Intake from intake.java", 1);
    }
}
//how are we going to deploy the intake out?
//