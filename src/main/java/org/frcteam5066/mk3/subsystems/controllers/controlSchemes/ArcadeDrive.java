package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.controllers.*;

import edu.wpi.first.wpilibj.smartdashboard.*;
import com.kauailabs.navx.frc.AHRS;

import org.frcteam5066.mk3.subsystems.controllers.XboxController;

//Uncomment to enable gyro stuff
//import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * Main class to control the robot
 * 
 */
public class ArcadeDrive extends ControlScheme {

    //Create all objects & a speedMode object
    XboxController driveController, armController;

    

    boolean lowGear;
    boolean climberExtended;
    boolean climberDown;

    double tx, tv;
    
    final double driveSpeedConstant = 0.3;
    final double txkP = 0.022;
    final double angleDifferencekP = 0.011;
    final double endDistance = 2.0;

    /**
     * 
     * @param driveControllerPort Controller port the drive controller is connected to, probably 0
     * @param armControllerPort Controller port the arm controller is connect to, problably 1
     */
    public ArcadeDrive(int driveControllerPort, int armControllerPort) {
        driveController = new XboxController(driveControllerPort);
        armController = new XboxController(armControllerPort);

        lowGear = true;
        climberExtended = false;
        

    }

    /*@Override
    public void ledMode(LimeLight limeLight) {
        // TODO Auto-generated method stub
        
    }*/



    

    /**
     * Drives arcade drive
     * 
     */
    

    

    /**
     * method that controls the conveyor, collector, and flywheel as the three need to move together
     * 
     */

     public void intakeConveyer(Intake intake){
        
        if(armController.getLB()){
            if(armController.getAButton())
                intake.intakeReject();
            else intake.intakeCollect();
        }
        else{
            intake.intakeOff();
        }

        if(armController.getRB()){
            if (armController.getAButton()) 
                intake.conveyorReject();
            else intake.conveyorCollect();
        }
        else {
            intake.conveyorOff();
        }
     }

    @Override
    public void flywheel(Shooter flywheel) {
        if (armController.getTriggerLeft() > .2) {
            if (armController.getAButton())
                flywheel.shooterReverse();
            else flywheel.shooterOn();
        }
        else if (armController.getPOVUp()) {
            flywheel.barf();
        }
        else flywheel.shooterOff();

        if (armController.getTriggerRight() > .2) {
            if (armController.getAButton())
                flywheel.feederReverse();
            else flywheel.shoot();
        }
        else flywheel.hold();
    }
    

    




    

    /**
     * Only turns on the painfully bright Limelight LEDs when they're being used
     * @param limelight takes in Limelight object
     */
    

}

/**
 * Pseudocode for Limelight targeting 
 * Use a p controller
 */