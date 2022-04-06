package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.sql.Time;
import java.time.*;

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
    protected static int position = 2;
    protected static int color;
    protected static int rotationDirection; //1 is clockwise, -1 is counter-clockwise
    
    private boolean autonBarfDone;
    private boolean driveDone;
    private boolean aimDone;
    private boolean driveReverseDone;
    private boolean shootDone;
    private boolean getBallDone;
    private boolean aimBeenReset;
    private boolean shootBeenReset;
    private boolean getBallBeenReset;
    
    boolean autonBarfProgress;
    boolean driveProgress;
    boolean driveProgress2;
    long barfStartTime;
    boolean aimProgress1;
    boolean driveReverseProgress1;
    boolean shootProgress1;
    long shootStartTime;
    boolean getBallProgress1;
    boolean getBallProgress2;
    boolean testDProgress;
    boolean testDProgress2;
    private double initAnglePos;
    private double d = 7.5; //feet - CONVERT TO METERS LATER

    SendableChooser<Integer> startingPosition = new SendableChooser<>();

    public AutonControlScheme(LimeLight limeLight, Shooter shooter, Intake intake, DrivetrainSubsystem _drive, String color, ColorSensor colorSensor){
        
        drive = _drive;

        drive.resetRotationsZero();

        startingPosition.setDefaultOption("Position 1", 1);
        startingPosition.addOption("Position 2", 2);
        startingPosition.addOption("Position 3", 3);
        startingPosition.addOption("Position 3", 4);

        //this.gyro = new AHRS(Port.kMXP);
        this.limeLight = limeLight;
        this.shooter = shooter;
        this.intake = intake;
        this.colorSensor = colorSensor;
        
        //this.position = startingPosition.getSelected();
        this.position = 1;
        if(color.equals("Blue")) this.color = 2;
        else this.color = 3;
        if(position < 3) rotationDirection = 1;
        else rotationDirection = -1;

    autonBarfDone = false;
    driveDone = false;
    aimDone = false;
    driveReverseDone = false;
    shootDone = false;
    getBallDone = false;
    aimBeenReset = false;
    shootBeenReset = false;
    getBallBeenReset = false;
    
    autonBarfProgress = false;
    driveProgress = false;
    driveProgress2 = false;
    barfStartTime = 0;
    aimProgress1 = false;
    driveProgress = false;
    driveReverseProgress1 = false;
    shootProgress1 = false;
    shootStartTime = 0;
    getBallProgress1 = false;
    getBallProgress2 = false;
    testDProgress = false;
    testDProgress2 = false;
    initAnglePos = 0;

    }

    private static boolean hasCargoTarget(){
        if(color == 2) return limeLight.hasRedCargoTarget();
        return limeLight.hasBlueCargoTarget();
    }

    private void resetAnglePos(){
        initAnglePos = drive.getGyroAngle();
    }

    public boolean autonBarfDone(){
        return autonBarfDone;
    }

    public boolean driveDone(){
         return driveDone;
    }

    public boolean aimDone(){
        return aimDone;
    }

    public boolean driveReverseDone(){
        return driveReverseDone;
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
    
    //TODO test "D" value more

    public void testD(){
        SmartDashboard.putNumber("Testing D", 1);
        
        if(!testDProgress){
            resetAnglePos();
            testDProgress = true;
        }

        if( ( drive.getGyroAngle() - initAnglePos) < 180 ){
            drive.drive(new Vector2(1, 0), 1, true);
            SmartDashboard.putNumber("Driving", 1);
            SmartDashboard.putNumber("Angle Difference", Math.abs(drive.getGyroAngle() - initAnglePos) );
        }
        else{
            SmartDashboard.putNumber("Driving", 0);
            drive.drive(new Vector2(0,0), 0, true);
        }
        SmartDashboard.putNumber("Encoder", drive.getRotationsSpun());

    }

    public void 
    autonBarf(){
        SmartDashboard.putNumber("Auton Barf Progress", (autonBarfProgress)? 1:0);
        SmartDashboard.putNumber("Auton Barf Done", (autonBarfDone)? 1:0);
        SmartDashboard.putNumber("Current System Time", System.currentTimeMillis());
        if(!autonBarfProgress){
            intake.intakeShooting();
            //barfStartTime = System.currentTimeMillis();
            autonBarfProgress = true;
        }
        

        /*
        if(/*System.currentTimeMillis() - barfStartTime > 3 !colorSensor.hasBall() ){
            autonBarfDone = true;
        }
        */
        
        else{
            shooter.barf();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
            
            intake.conveyorCollect();
            shooter.feederOn();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            autonBarfDone = true;
        }


    }

    public void drive(){

        intake.setCeaseIntake(false);

        if(/*position == 1*/ true){
            
            SmartDashboard.putNumber("Drive Done", driveDone ? 1:0);
            SmartDashboard.putNumber("Driving", 1);
            
            
            if(!driveProgress){
                intake.intakeDeploy();
                drive.resetWheelAngles();
                driveProgress = true;

            }
            
            
            intake.conveyorCollect();
            intake.intakeCollect();

            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            drive.drive(new Vector2(.3, 0), 0, false);
            
            //Auton TESTING MODIFY SPOT the "10" below represent the amount of rotations needed to get off the tarmac
            SmartDashboard.putNumber("Wheel Rotations", drive.getRotationsSpun());
            
            
            if(Math.abs(drive.getRotationsSpun()) >= 3.5){
                drive.drive(new Vector2(0, 0),0, false);
                SmartDashboard.putNumber("Driving", 0);

                /*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //intake.setCeaseIntake(true);
                intake.intakeOff();
                */
                driveDone = true;
                
            }
            
        }   

        else if(/*position != 1*/ false){
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
        intake.intakeDeploy();  
        SmartDashboard.putNumber("Auton Deploying Intake", 1);  
        SmartDashboard.putNumber("Color", color);        
        if( limeLight.runLimeLight(drive, color) ){
            
                if (colorSensor.hasBall()) {
                    if (colorSensor.robotColor()) {
                        shooter.feederOff();
                    }
                    else {
                        shooter.barf();
                        if(shooter.readyToShoot()){
                            shooter.feederOn();
                        }
                    }
                }
                else {
                    shooter.feederOnIntake();
                }
    
                intake.intakeCollect();
                intake.conveyorCollect();

            }
            else driveDone = true;

            
        }
        
    }
    
    public void driveReverse(){

        if ( !driveReverseProgress1 ){
            drive.resetWheelAngles();
            intake.intakeDeploy();
            driveReverseProgress1 = true;
            SmartDashboard.putNumber("Wheel rotations have been reset", 1);
        }

        intake.intakeCollect();

        SmartDashboard.putNumber("Driving Reverse", 1);
        drive.drive(new Vector2(-.3, 0), 0, false);

        SmartDashboard.putNumber("Wheel Rotations", drive.getRotationsSpun());
        if(Math.abs( drive.getRotationsSpun() ) >= 5){
            drive.drive(new Vector2(0, 0), 0, false);
            SmartDashboard.putNumber("Driving", 0);
            driveReverseDone = true;
                
        }
    }

    public void aim(){

        shooter.flywheelOn();
        intake.intakeCollect();
        intake.conveyorCollect();
        
        if(!aimProgress1){
            drive.drive(new Vector2(0, 0), .3 * rotationDirection, false);

            if( limeLight.hasVisionTarget() ) aimProgress1 = true;
            aimDone = true;
        }
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
        /*
        else {
            limeLight.runLimeLight(drive, 1);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }

            //aimDone = true;
        }
        */
        

    }
    
    public void shoot(){
        
        if(!aimProgress1){
            intake.intakeShooting();
            aimProgress1 = true;
        }
        
        else{
            shooter.flywheelOn();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
            
            intake.conveyorCollect();
            shooter.feederOn();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            driveDone = true;
        }

        
        /*
        if(!aimProgress1){
            shootStartTime = System.currentTimeMillis();
            aimProgress1 = true;
        }

        if( System.currentTimeMillis() - shootStartTime < 5000 ) shooter.feederOn();
        else{
            shooter.feederOff();
            shooter.flywheelOff();
            intake.conveyorOff();
            shootDone = true;
        }
        */
    }

    public void getBall(){
        
        if(!getBallProgress1){

            if(!getBallProgress2){
                resetAnglePos();
                getBallProgress2 = true;
            }

            drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
            
            if( Math.abs(drive.getGyroAngle() - initAnglePos ) >= 180 ) rotationDirection = -rotationDirection;
            
            if( hasCargoTarget() ) getBallProgress1 = true;
        }
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
        else if (limeLight.runLimeLight(drive, color)){}
        else {
            rotationDirection = -rotationDirection;
            getBallDone = true;
        }

    }




























    

    private boolean fixedDrive1Done = false;
    private boolean fixedDrive2Done = false;
    private boolean fixedDrive3Done = false;
    private boolean fixedDrive4Done = false;
    private boolean fixedShoot1Done = false;
    private boolean fixedShoot2Done = false;
    private boolean fixedShoot3Done = false;
    private boolean fixedAim1Done = false;
    private boolean fixedAim2Done = false;
    private boolean fixedAim3Done = false;
    

    boolean driveAndSpinProgress1 = false;
    boolean driveAndSpinProgress2 = false;
    boolean driveDistanceProgress = false;

    final double spin180Distance = 2.286;
    final double wheelCirc = 0.3191858136047229930278045677412;

    public boolean drive1Done(){
        return fixedDrive1Done;
    }
    
    public boolean drive2Done(){
        return fixedDrive1Done;
    }
    
    public boolean drive3Done(){
        return fixedDrive1Done;
    }
    
    public boolean drive4Done(){
        return fixedDrive1Done;
    }
    
    public boolean shoot1Done(){
        return fixedShoot1Done;
    }
    
    public boolean shoot2Done(){
        return fixedShoot2Done;
    }
    
    public boolean shoot3Done(){
        return fixedAim1Done;
    }
    
    public boolean aim1Done(){
        return fixedAim1Done;
    }
    
    public boolean aim2Done(){
        return fixedAim2Done;
    }
    
    public boolean aim3Done(){
        return fixedAim3Done;
    }
    
    private void progressFixedDrive(){
        if( fixedDrive4Done ){
            return;
        }
        else if( fixedDrive3Done ){
            fixedDrive4Done = true;
        }
        else if( fixedDrive2Done ){
            fixedDrive3Done = true;
        }
        else if( fixedDrive1Done ){
            fixedDrive2Done = true;
        }
        else {
            fixedDrive1Done = true;
        }
    }

    private void progressFixedShoot(){
        if( fixedShoot3Done ){
            return;
        }
        else if( fixedShoot2Done ){
            fixedShoot3Done = true;
        }
        else if( fixedShoot1Done ){
            fixedShoot2Done = true;
        }
        else {
            fixedShoot1Done = true;
        }
    }

    private void progressFixedAim(){
        if( fixedAim3Done ){
            return;
        }
        else if( fixedAim2Done ){
            fixedAim3Done = true;
        }
        else if( fixedAim1Done ){
            fixedAim2Done = true;
        }
        else {
            fixedAim1Done = true;
        }
    }

    private boolean driveDistance(double distance){
        if(!driveDistanceProgress){
            drive.resetRotationsZero();
            driveDistanceProgress = true;
            return false;
        }
        
        if( wheelCirc * drive.getRotationsSpun() < distance){
            //limeLight.runLimeLight(drive, color);
            drive.drive(new Vector2(1,0), 1, false);
            return false;
        }
        else {
            return true;
        }
    }

    public void driveAndSpin(double distance, double angleFromNorth, double deltaAngle, int rotDirection){
        if(!driveAndSpinProgress1){
            initAnglePos = drive.getGyroAngle();
            driveAndSpinProgress1 = true;
        }

        if( Math.abs( drive.getGyroAngle() - initAnglePos) > deltaAngle){
            drive.drive(new Vector2(Math.sin(angleFromNorth), Math.cos(angleFromNorth)), 1 * rotDirection, true);
        }
        else if (  driveDistance(distance - (spin180Distance * (deltaAngle/180) ) )  ){
            progressFixedDrive();
        }
        //mayhaps have intakeCollect running for all of auton
        else {
            intake.intakeCollect();
            
        }
    }

    public void fixedAim(int rotDirection){

        shooter.flywheelOn();
        if( !colorSensor.hasBall() ) intake.conveyorCollect();
        
        if(!aimProgress1){
            drive.drive(new Vector2(0, 0), 1 * rotDirection, false);

            if( limeLight.hasVisionTarget() ) aimProgress1 = true;
        }
        // runLimeLight() both aims/drives towards ball and returns "true" if it is still adjusting/driving ("false" if not making adjustments)
        else if (limeLight.runLimeLight(drive, 1)){}
        else progressFixedAim();

    }

    public void fixedShoot(){
        if( colorSensor.hasBall() ){
             shooter.feederOn();
          if (shooter.readyToShoot()) {
               intake.conveyorCollect();
               shooter.feederOn();
               SmartDashboard.putNumber("Feeding", 1);
           }
        }
        else {
            shooter.feederOff();
            shooter.flywheelOff();
            intake.conveyorOff();
            progressFixedShoot();
        }

        
    }

}
