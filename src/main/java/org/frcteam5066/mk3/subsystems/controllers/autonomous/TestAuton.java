package frc.controller.autonomous;

import frc.singularityDrive.SingDrive;
import frc.robot.LimeLight;
import frc.robot.Flywheel;
import frc.robot.Conveyor;
import frc.robot.CellCollector;

public class TestAuton extends AutonControlScheme{

    
    public TestAuton(SingDrive drive, LimeLight limeLight, Flywheel flywheel, Conveyor conveyor, CellCollector cellCollector) {
        super(drive, limeLight, flywheel, conveyor, cellCollector);
    }


    @Override
    public void moveAuton() {
        //super.vertical(10, 0.1);
        //super.vertical(-10);
        //super.rotate(90, false);
        //super.adjustToTarget();
        super.vertical(40, 0.3);
        //super.shoot();
    } 

}