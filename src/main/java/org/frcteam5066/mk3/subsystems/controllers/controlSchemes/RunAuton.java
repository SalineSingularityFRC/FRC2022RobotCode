package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;


public class RunAuton extends AutonControlScheme{
    
    SendableChooser<Boolean> doDrive = new SendableChooser<>();
    SendableChooser<Boolean> doShoot = new SendableChooser<>();
    SendableChooser<Boolean> doSearch = new SendableChooser<>();
    SendableChooser<Boolean> doShoot2 = new SendableChooser<>();
    SendableChooser<Boolean> testD = new SendableChooser<>();
    SendableChooser<Boolean> doFixedAuton = new SendableChooser<>();

    

    



    public RunAuton(LimeLight limeLight, Shooter flywheel, Intake intake, DrivetrainSubsystem drive, String color){

        super(limeLight, flywheel, intake, drive, color);

        doDrive.setDefaultOption("Do Shoot", true);
        doDrive.addOption("dont Drive", false);

        doShoot.setDefaultOption("DO Shoot", true);
        doShoot.addOption("DOn't Shoot", false);

        doSearch.setDefaultOption("DO Search ", true);
        doSearch.addOption("Don't Search", false);

        doShoot2.setDefaultOption("DoShoot 2", true);
        doShoot2.addOption("Don't Shoot 2", false);

        testD.setDefaultOption("TestD", true);
        testD.addOption("Don't TestD", false);

        doFixedAuton.setDefaultOption("Do Fixed Auton", true);
        doFixedAuton.addOption("Don't do Fixed Auton", false);

        SmartDashboard.putData(doDrive);
        SmartDashboard.putData(doShoot);
        SmartDashboard.putData(doSearch);
        SmartDashboard.putData(doShoot2);
        SmartDashboard.putData(testD);
        SmartDashboard.putData(doFixedAuton);

    }

    public void actuallyRunAutonTheMethod(){
        

        if( testD.getSelected() ) super.testD();

        else if (doFixedAuton.getSelected()){
            /*
                drive straght and pick up ball
                aim and shoot
                spin and drive to next ball
                aim and shoot
                spin and drive to player station
                drive to previous position
                aim and shoot
            */

            super.driveDistance(1.204);
            

        }

        else{
            if( doDrive.getSelected() && !driveDone() ) super.drive();
            if( doShoot.getSelected() && !aimDone() && driveDone() ) super.aim();
            if( doShoot.getSelected() && !shootDone() && aimDone() ) super.shoot();
            if( doShoot.getSelected() && !getBallDone() && shootDone() ) super.getBall();
            if( doShoot2.getSelected() ){       
                super.resetAimDone();
                super.resetShootDone();
            }
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
