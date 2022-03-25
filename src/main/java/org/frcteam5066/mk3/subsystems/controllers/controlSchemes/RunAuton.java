package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.ColorSensor;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;


public class RunAuton extends AutonControlScheme{
    
    SendableChooser<Boolean> doDrive = new SendableChooser<>();
    SendableChooser<Boolean> doShoot = new SendableChooser<>();
    SendableChooser<Boolean> doDriveReverse = new SendableChooser<>();
    SendableChooser<Boolean> doSearch = new SendableChooser<>();
    SendableChooser<Boolean> doShoot2 = new SendableChooser<>();
    SendableChooser<Boolean> testD = new SendableChooser<>();
    SendableChooser<Boolean> doFixedAuton = new SendableChooser<>();
    SendableChooser<Boolean> doMainModularAuton = new SendableChooser<>();

    

    



    public RunAuton(LimeLight limeLight, Shooter flywheel, Intake intake, DrivetrainSubsystem drive, String color, ColorSensor colorSensor){

        super(limeLight, flywheel, intake, drive, color, colorSensor);

        doDrive.setDefaultOption("Do Drive", true);
        doDrive.addOption("Dont Drive", false);

        doShoot.setDefaultOption("Do Shoot", true);
        doShoot.addOption("Don't Shoot", false);

        doDriveReverse.setDefaultOption("Do Reverse Drive", true);
        doDriveReverse.addOption("Don't Reverse Drive", true);

        doSearch.setDefaultOption("DO Search ", true);
        doSearch.addOption("Don't Search", false);

        doShoot2.setDefaultOption("DoShoot 2", true);
        doShoot2.addOption("Don't Shoot 2", false);

        testD.setDefaultOption("TestD", true);
        testD.addOption("Don't TestD", false);

        doFixedAuton.setDefaultOption("Do Fixed Auton", true);
        doFixedAuton.addOption("Don't do Fixed Auton", false);

        doMainModularAuton.setDefaultOption("Do Main Modular Auton", true);
        doMainModularAuton.addOption("Don't do Main Modular Auton", false);

        SmartDashboard.putData(doDrive);
        SmartDashboard.putData(doShoot);
        SmartDashboard.putData(doDriveReverse);
        SmartDashboard.putData(doSearch);
        SmartDashboard.putData(doShoot2);
        SmartDashboard.putData(testD);
        SmartDashboard.putData(doFixedAuton);
        SmartDashboard.putData(doMainModularAuton);

    }

    public void actuallyRunAutonTheMethod(){
        

        if( /*testD.getSelected()*/false  ) super.testD();

        else if ( /*doFixedAuton.getSelected()*/false ){
            /*
                drive straght and pick up ball
                aim and shoot
                spin and drive to next ball
                aim and shoot
                spin and drive to player station
                drive to previous position
                aim and shoot
                meow!
            */
            
            if( !drive1Done() ) super.driveAndSpin(1.204, 0.0, 0.0, 1);
            if( !aim1Done() ) super.fixedAim(-1);
            if( !shoot1Done() ) super.fixedShoot();
            if( !drive2Done() ) super.driveAndSpin(3.004, 122.275644, 67.0187843, 1);//needs deltaAngle
            if( !aim2Done() ) super.fixedAim(-1);
            if( !shoot2Done() ) super.fixedShoot();
            if( !drive3Done() ) super.driveAndSpin(4.068, 79.568889, 155.2916093, 1);//needs deltaAngle
            if( !drive4Done() ) super.driveAndSpin(4.068, 259.568889, 180, -1);
            if( !aim3Done() ) super.fixedAim(1);
            if( !shoot3Done() ) super.fixedShoot();

        }

        else if ( /*doMainModularAuton.getSelected()*/false ){
            if( doDrive.getSelected() && !driveDone() ) super.drive();
            if( doShoot.getSelected() && !aimDone() && driveDone() ) super.aim();
            if( doShoot.getSelected() && !shootDone() && aimDone() ) super.shoot();
            if( doShoot.getSelected() && !getBallDone() && shootDone() ) super.getBall();
            if( doShoot2.getSelected() ){       
                super.resetAimDone();
                super.resetShootDone();
            }
        }

        else{
            if( doDrive.getSelected() && !driveDone() ) super.drive();
            if( doShoot.getSelected() && !aimDone() && driveDone() ) super.aim();
            if( doDriveReverse.getSelected() && !driveReverseDone() && aimDone() ) super.driveReverse();

        }
        
        /*
        if( true && !driveDone() ) super.drive();
        if( true && !aimDone() && driveDone() ) super.aim();
        if( false && !shootDone() && aimDone() ) super.shoot();
        if( false && !getBallDone() && shootDone() ) super.getBall();
        if( false ){       
            super.resetAimDone();
            super.resetShootDone();
        }
        */

    }

}
