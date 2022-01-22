package org.frcteam5066.mk3.subsystems;

import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Spark;
import org.frcteam5066.mk3.subsystems.controllers.MotorController;

//This intake class includes both the collector and conveyor
public class Intake {
 
    MotorController intakeDrive, intakeConveyor;
 
 
    double kP = 6e-5;
    double kI = 0;
    double kD = 0;
    double kIz = 0;
    double kFF = 0.000015;
    double kMaxOutput = 1;
    double kMinOutput = -1;
    double maxRPMIntake = 11000;
    // maxRPMIntakeconveyor is copied from 2021 conveyor class
    double maxRPMIntakeconveyor = -4000;
    double maxRPMFeed = 5700;
 
 
 
 
    //Intake Constructor
    public Intake(int intakeDrivePort, int intakeConveyorPort){
 
        //550 motors used for intake, unsure if they are brushed
        intakeDrive = new Spark(intakeDrivePort, true, 0.00, "IntakeDrive", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        intakeConveyor = new Spark(intakeConveyorPort, true, 0.00, "IntakeConveyor", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
 
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
}
