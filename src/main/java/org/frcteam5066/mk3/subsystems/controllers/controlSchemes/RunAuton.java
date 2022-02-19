package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Shooter;


public class RunAuton extends AutonControlScheme{
    
    SendableChooser<Boolean> doDrive = new SendableChooser<>();
    SendableChooser<Boolean> doShoot = new SendableChooser<>();
    SendableChooser<Boolean> doSearch = new SendableChooser<>();
    SendableChooser<Boolean> doShoot2 = new SendableChooser<>();


    public RunAuton(LimeLight limeLight, Shooter flywheel, DrivetrainSubsystem drive, String color){

        super(limeLight, flywheel, drive, color);

        doDrive.setDefaultOption("Yes", true);
        doDrive.addOption("No", false);

        doShoot.setDefaultOption("Yes", true);
        doShoot.addOption("No", false);

        doSearch.setDefaultOption("Yes", true);
        doSearch.addOption("No", false);

        doShoot2.setDefaultOption("Yes", true);
        doShoot2.addOption("No", false);
    }

    public void actuallyRunAutonTheMethod(){
        
        if( doDrive.getSelected() && !driveDone() ) super.drive();
        if( doShoot.getSelected() && !aimDone() ) super.aim();
        if( doShoot.getSelected() && !shootDone() ) super.shoot();
        if( doShoot.getSelected() && !getBallDone() ) super.getBall();
        if( doShoot2.getSelected() ){       
            super.resetAimDone();
            super.resetShootDone();
        }
    }

}
