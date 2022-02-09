package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;
//this is for each position you're in
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
//this is for the auton movement
public class Path1 extends AutonControlScheme {

    public Path1(LimeLight limeLight, Shooter flywheel, Intake conveyor, DrivetrainSubsystem drive ) {
        super(limeLight, flywheel, conveyor, drive);
        //TODO Auto-generated constructor stub
    }
    public void moveAuton(){
        super.vertical()//parent's classes variable = super
        super.rotate(90, true);
        super.vertical();
        super.rotate();
        super.vertical();
        super.adjustTOTarget();
        super.vertical();
        super.shoot();
    }
    }
    
}
