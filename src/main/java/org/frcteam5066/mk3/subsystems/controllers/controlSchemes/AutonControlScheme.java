package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


import org.frcteam5066.common.math.Vector2;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.Intake;

public abstract class AutonControlScheme {

    protected static AHRS gyro;
    protected static LimeLight limeLight;
    protected static DrivetrainSubsystem drive;
    protected static Shooter flywheel;
    protected static Intake intake;
    protected static int position;
    protected static int color;
    protected static int rotationDirection; //1 is clockwise, -1 is counter-clockwise
    
    private boolean driveDone = false;
    private boolean shootDone = false;
    private boolean getBallDone = false;
    private boolean drive2Done = false;

    SendableChooser<Integer> startingPosition = new SendableChooser<>();

    public AutonControlScheme(LimeLight limeLight, Shooter flywheel, DrivetrainSubsystem drive, String color){
        
        drive.resetRotationsZero();

        startingPosition.setDefaultOption("Position 1", 1);
        startingPosition.addOption("Position 2", 2);
        startingPosition.addOption("Position 3", 3);
        startingPosition.addOption("Position 3", 4);

        this.gyro = new AHRS(Port.kMXP);
        this.limeLight = limeLight;
        this.flywheel = flywheel;
       // this.intake = intake;
        this.position = startingPosition.getSelected();
        if(color.equals("Red")) this.color = 2;
        else this.color = 3;
        if(position < 3) rotationDirection = 1;
        else rotationDirection = -1;
    }

    private static boolean hasCargoTarget(){
        if(color == 2) return limeLight.hasRedCargoTarget();
        return limeLight.hasBlueCargoTarget();
    }

    public boolean driveDone(){
         return driveDone;
    }

    public boolean shootDone(){
        return shootDone;
    }

    public boolean getBallDone(){
        return getBallDone;
    }

    public boolean drive2Done(){
        return drive2Done;
    }

    // target == 1 is vision tape, target == 2 is ball
    private void aim(int target){
        if(target == 1){
            if(!limeLight.hasVisionTarget()){
                drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
            }
        
            else {
                limeLight.runLimeLight(drive, 1);
            }
        }

        if(target == 2){
            
        }

    }

    public void drive(){


        if(position == 1){

            drive.drive(new Vector2(1, 0), 0, false);
            
            //AUTON TESTING MODIFY SPOT the "10" below represent the amount of rotations needed to get off the tarmac
            
            if(drive.getRotationsSpun() >= 10){
                drive.drive(new Vector2(0, 0),0, false);
                driveDone = true;
            }
        }   

        else if(position != 1){
            
            //use runLimeLight, pass in color as int (2 or 3)
            limeLight.runLimeLight(drive, color);
            if(!hasCargoTarget()) driveDone = true;

        }
    }
    
    
    public void shoot(){
        //rotates until target is seen
        if(!limeLight.hasVisionTarget()){
            drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
        }
    

        else {
            limeLight.runLimeLight(drive, 1);
            flywheel.shooterOn();
        }
        

        intake.conveyorCollect();
        flywheel.shoot();

        intake.conveyorOff();
        flywheel.shooterOff();

    }

    public void getBall(){
        double initPos = gyro.getAngle();
        while(!hasCargoTarget()){
            drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
            if( Math.abs(initPos - gyro.getAngle()) >  180 ) break;
        }
        initPos = gyro.getAngle();
        while(!hasCargoTarget()){
            drive.drive(new Vector2(0, 0), 1 * -rotationDirection, false);
            if( Math.abs(initPos - gyro.getAngle()) >  180 ) break;
        }

        limeLight.runLimeLight(drive, 1);

    }


}
