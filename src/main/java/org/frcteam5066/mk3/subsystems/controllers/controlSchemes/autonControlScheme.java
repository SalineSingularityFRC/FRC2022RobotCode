package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Shooter;

public abstract class autonControlScheme {

    //protected static AHRS gyro;
    protected static LimeLight limeLight;
    protected static DrivetrainSubsystem drive;
    protected static Shooter flywheel;
    protected static Intake intake;
    protected static int position;
    protected static int color;
    protected static int rotationDirection; //1 is clockwise, -1 is counter-clockwise

    public autonControlScheme(LimeLight limeLight, Shooter flywheel, DrivetrainSubsystem drive, int position, String color){
        //this.gyro = new AHRS(SPI.Port.kMXP);
        this.limeLight = limeLight;
        this.flywheel = flywheel;
        this.intake = intake;
        this.position = position;
        if(color.equals("Red")) this.color = 2;
        else this.color = 3;
        if(position < 3) rotationDirection = 1;
        else rotationDirection = -1;
    }

    private static boolean hasCargoTarget(){
        if(color == 2) return limeLight.hasRedCargoTarget();
        return limeLight.hasBlueCargoTarget();
    }

    public void drive(){
        if(position == 1){
            //I don't know yet
        }

        else if(position != 1){
            if(color == 2){
                if(limeLight.hasRedCargoTarget()){
                    drive.drive(new Vector2((driveType == 0)? 1:0, 0), 0, false);
                }
            }
            else{
                if(limeLight.hasBlueCargoTarget()){
                    drive.drive(new Vector2((driveType == 0)? 1:0, 0), 0, false);
                }
            }
        }
    }
    
    public void shoot(){
        if(position != 1){
            if(!limeLight.hasVisionTarget()){
                drive.drive(new Vector2((driveType == 0)? 0:0, 0), 1 * rotationDirection, false);
            }
        }

        limeLight.runLimeLight(drive, 1);
        flywheel.shooterOn();

        //this next section waits for 1 second to pass. This should be optimized once we get a chance to run this on the robot
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            
        }

        intake.conveyorCollect();
        flywheel.shoot();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            
        }

        intake.conveyorOff();
        flywheel.shooterOff();

    }

    public void getBall(){
        if(!hasCargoTarget()){
            drive.drive(new Vector2((driveType == 0)? 0:0, 0), 1 * rotationDirection, false);
        }
        limeLight.runLimeLight(drive, 1);

    }


}
