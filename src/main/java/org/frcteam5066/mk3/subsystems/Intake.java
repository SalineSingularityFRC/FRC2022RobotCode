package frc.robot;

import frc.controller.MotorController;
import frc.controller.motorControllers.Spark;

//This intake class includes both the collector and conveyer
public class Intake {

    Spark intakeDrive, intakeConveyer;


    double kP = 6e-5; 
    double kI = 0;
    double kD = 0; 
    double kIz = 0; 
    double kFF = 0.000015; 
    double kMaxOutput = 1; 
    double kMinOutput = -1;
    double maxRPMIntake = 11000;
    // maxRPMIntakeConveyer is copied from 2021 Conveyer class
    double maxRPMIntakeConveyer = -4000;
    double maxRPMFeed = 5700;




    //Intake Constructor
    public Intake(int intakeDrivePort, int intakeBottomPort){

        //550 motors used for intake, unsure if they are brushed
        intakeDrive = new Spark(intakeDrivePort, false, 0.00, "IntakeDrive", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);
        intakeConveyer = new Spark(intakeConveyerPort, false, 0.00, "IntakeConveyer", false, false, kP, kI, kD, kIz, kFF, kMinOutput, kMaxOutput);

    }

    public void intakeCollect() {
        intakeDrive.setVelocity(maxRPMIntake);
        intakeConveyer.setVelocity(maxRPMIntakeConveyer);
    }

    //Use if color senser recognizes opposite team's ball
    public void intakeReject(){
        intakeDrive.setVelocity(-maxRPMIntake);
        intakeConveyer.setVelocity(-maxRPMIntakeConveyer);
    }

    public void intakeOff(){
        intakeDrive.setVelocity(0.0);
        intakeConveyer.setVelocity(0.0);
    }
}
