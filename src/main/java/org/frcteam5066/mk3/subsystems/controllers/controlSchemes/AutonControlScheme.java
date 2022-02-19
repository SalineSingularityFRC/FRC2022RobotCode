package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


import org.frcteam5066.common.math.Vector2;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.ColorSensor;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.Intake;

public abstract class AutonControlScheme {

    protected static AHRS gyro;
    protected static LimeLight limeLight;
    protected static DrivetrainSubsystem drive;
    protected static Shooter shooter;
    protected static Intake intake;
    protected static ColorSensor colorSensor;
    protected static int position;
    protected static int color;
    protected static int rotationDirection; //1 is clockwise, -1 is counter-clockwise
    
    private boolean driveDone = false;
    private boolean aimDone = false;
    private boolean shootDone = false;
    private boolean getBallDone = false;
    private boolean aimBeenReset = false;
    private boolean shootBeenReset = false;
    private boolean getBallBeenReset = false;

    boolean aimProgress1 = false;
    boolean getBallProgress1 = false;
    boolean getBallProgress2 = false;
    private double initAnglePos;

    SendableChooser<Integer> startingPosition = new SendableChooser<>();

    public AutonControlScheme(LimeLight limeLight, Shooter shooter, DrivetrainSubsystem drive, String color){
        
        drive.resetRotationsZero();

        startingPosition.setDefaultOption("Position 1", 1);
        startingPosition.addOption("Position 2", 2);
        startingPosition.addOption("Position 3", 3);
        startingPosition.addOption("Position 3", 4);

        this.gyro = new AHRS(Port.kMXP);
        this.limeLight = limeLight;
        this.shooter = shooter;
       // this.intake = intake;
        this.position = startingPosition.getSelected();
        if(color.equals("Blue")) this.color = 2;
        else this.color = 3;
        if(position < 3) rotationDirection = 1;
        else rotationDirection = -1;
    }

    private static boolean hasCargoTarget(){
        if(color == 2) return limeLight.hasRedCargoTarget();
        return limeLight.hasBlueCargoTarget();
    }

    private void resetAnglePos(){
        initAnglePos = gyro.getAngle();
    }

    public boolean driveDone(){
         return driveDone;
    }

    public boolean aimDone(){
        return aimDone;
    }

    public boolean shootDone(){
        return shootDone;
    }

    public boolean getBallDone(){
        return getBallDone;
    }

    public void resetAimDone(){
        if( !aimBeenReset ){
            aimDone = false;
            aimBeenReset = true;
        }
    }

    public void resetShootDone(){
        if( !shootBeenReset ){
            shootDone = false;
            shootBeenReset = true;
        }
    }

    public void resetGetBallDone(){
        if( !getBallBeenReset ){
            getBallDone = false;
            getBallBeenReset = true;
        }
    }

    // target == 1 is vision tape, target == 2 is ball
    

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
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
            if( limeLight.runLimeLight(drive, color) ){}
            else driveDone = true;

        }
    }
    
    public void aim(){

        shooter.flywheelOn();
        intake.conveyorCollect();
        
        if(aimProgress1){
            drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);

            if( limeLight.hasVisionTarget() ) aimProgress1 = true;
        }
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
        else if (limeLight.runLimeLight(drive, 1)){}
        else aimDone = true;

    }
    
    public void shoot(){

        if( colorSensor.hasBall() ) shooter.feederOn();
        else{
            shooter.feederOff();
            shooter.flywheelOff();
            intake.conveyorOff();
            shootDone = true;
        }

    }

    public void getBall(){
        
        if(!getBallProgress1){

            if(!getBallProgress2){
                resetAnglePos();
                getBallProgress2 = true;
            }

            drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
            
            if( Math.abs( gyro.getAngle() - initAnglePos ) >= 180 ) rotationDirection = -rotationDirection;
            
            if( hasCargoTarget() ) getBallProgress1 = true;
        }
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
        else if (limeLight.runLimeLight(drive, color)){}
        else {
            rotationDirection = -rotationDirection;
            getBallDone = true;
        }

    }


}
