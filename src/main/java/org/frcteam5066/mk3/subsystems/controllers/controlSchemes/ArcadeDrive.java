package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import org.frcteam5066.common.robot.subsystems.Drivetrain;
import org.frcteam5066.common.robot.subsystems.HolonomicDrivetrain;
import org.frcteam5066.mk3.IntakePneumatics;
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.CANdleSystem;
import org.frcteam5066.mk3.subsystems.Climber;
import org.frcteam5066.mk3.subsystems.ColorSensor;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Servo2;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.controllers.*;

import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.led.CANdle;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;

import org.frcteam5066.mk3.subsystems.controllers.XboxController;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.MotorCycle;



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
    //ColorSensor colorSensor = new ColorSensor(); 
    //MotorCycle motorCycle = new MotorCycle(7);

    Servo2 servo;

    //HolonomicDrivetrain drive;

    ColorSensor colorSensor;

    //DriverStation driverStation = new DriverStation();

    String allianceColor = DriverStation.getAlliance().toString();



    

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
        colorSensor = new ColorSensor();
        servo = new Servo2(0);

        

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
            else {
                intake.intakeCollect();
                
            }
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

        if( armController.getPOVUp() ){
            intake.intakeDeploy();
            SmartDashboard.putNumber("Deploy", 1);
            SmartDashboard.putNumber("Retract", 0);
        }
        else if ( armController.getPOVDown() ){
            intake.intakeRetract();
            SmartDashboard.putNumber("Deploy", 0);
            SmartDashboard.putNumber("Retract", 1);
        }

    }

    @Override
    public void flywheel(Shooter flywheel) {

        SmartDashboard.putNumber("Current Velocity", flywheel.getFlywheelVelocity() + Math.random());

        

        if (armController.getTriggerLeft() > .2) {

            SmartDashboard.putNumber("Running Flywheel", 1);
            
            if (armController.getAButton()) {
                flywheel.flywheelReverse();
            } else if (colorSensor.robotColor()) {
                flywheel.barf();
            } else {
                flywheel.flywheelOn();
            }
        }
        else if (armController.getPOVLeft()) {
            flywheel.barf();
        }
        else flywheel.flywheelOff();

        if (armController.getTriggerRight() > .2) {
            if (armController.getAButton())
                flywheel.feederReverse();
            else flywheel.feederOn(); // turns on feeder wheel
        }
        else flywheel.feederOff();
        SmartDashboard.putNumber("Running Flywheel", 0);
    }

    public void intakePneumatics(IntakePneumatics intakePneumatics) {
        if (armController.getPOVDown()) {
            intakePneumatics.setHigh();
        } else if (armController.getPOVUp()) {
            intakePneumatics.setLow();
        } else {
            intakePneumatics.setOff();
        }
    }






    //Methods above are not used
/*

////////////////////////////////////////////////////////////////////
    _____            _   __  __      _   _               _     
    |  __ \          | | |  \/  |    | | | |             | |    
    | |__) |___  __ _| | | \  / | ___| |_| |__   ___   __| |___ 
    |  _  // _ \/ _` | | | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
    | | \ \  __/ (_| | | | |  | |  __/ |_| | | | (_) | (_| \__ \
    |_|  \_\___|\__,_|_| |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
                                                                
////////////////////////////////////////////////////////////////////                                                        

*/




    public void shootSequence(Shooter flywheel, Intake intake) {
        SmartDashboard.putNumber("current flywheel velocity", flywheel.getFlywheelVelocity());
        if (armController.getTriggerRight() > 0.2 ) {
            if(colorSensor.hasBall()){
                if (/*colorSensor.robotColor()*/ true) {
                    flywheel.flywheelOn();
                    if(armController.getTriggerLeft() > .2){
                        intake.intakeDeploy();
                    }
                    else{
                        intake.intakeShooting();
                    }
                }
                else {
                    flywheel.barf();
                    if(flywheel.readyToShoot()){
                        flywheel.feederOn();
                        intake.conveyorCollect();
                    }
                }
                
                if (flywheel.readyToShoot()) {
                    intake.conveyorCollect();
                    flywheel.feederOn();
                    SmartDashboard.putNumber("Feeding", 1);
                }
            }
            else{
                flywheel.flywheelOn();
                if (flywheel.readyToShoot()) {
                    intake.conveyorCollect();
                    flywheel.feederOn();
                    SmartDashboard.putNumber("Feeding", 1);
                }
            }
            
        }
        else if (armController.getRB()){
            flywheel.barf();
            if (flywheel.readyToShoot()) {
                intake.conveyorCollect();
                flywheel.feederOn();

                
            }

        }
        else{
            if(!(armController.getTriggerLeft() > .2)){
                flywheel.flywheelOff();
            }
            
            SmartDashboard.putNumber("Feeding", 0);
        }

        if(intake.isDeployed()){
            if(armController.getAButton()){
                intake.intakeCompress();
            }
            else{
                intake.intakeDeploy();
            }
        }
            
    }

    public void intakeSequence(Shooter flywheel, Intake intake) {
        //SmartDashboard.putNumber("Deploy Output Percent", intake.getDeployPercent());
        //SmartDashboard.putNumber("Intake Position", intake.getDeploySensorPosition());
        if (armController.getTriggerLeft() > 0.2) {
            intake.intakeDeploy();
            
            
            if (colorSensor.hasBall()) {
                /*if (colorSensor.robotColor()) {
                    flywheel.feederOff();
                }
                else {
                    flywheel.barf();
                    if(flywheel.readyToShoot()){
                        flywheel.feederOn();
                    }
                }*/
                flywheel.feederOff();
            }
            else {
                flywheel.feederOnIntake();
            }

            

            intake.intakeCollect();
            
            intake.conveyorCollect();
        } 
        else if (armController.getLB()) {
            intake.intakeReject();
        }
        else{
            if(!(armController.getTriggerRight() > .2 || armController.getRB())){
                intake.conveyorOff();
                flywheel.feederOff();
            }
            intake.intakeOff();
        }
        
        if(armController.getPOVDown()){
            intake.intakeDeploy();
        }
        else if(armController.getPOVUp()){
            intake.intakeRetract();
        }
        //intake.intakeRetract();

        if(intake.isDeployed()){
            if(armController.getAButton()){
                intake.intakeCompress();
            }
            else{
                intake.intakeDeploy();
            }
        }
    }

    public void colorSensor(){
        colorSensor.robotColor();
        SmartDashboard.putNumber("Has Ball", (colorSensor.hasBall())? 1:0 );
    }



    public void candle(CANdleSystem candle){

        if(armController.getPOVLeft()){
            servo.toIntakeAngle();
        }
        else if(armController.getPOVRight()){
            servo.toTargetingAngle();
        }



        /*if( armController.getYButton() ){
            candle.vBatOn();
            SmartDashboard.putNumber("Motorcycle Light State", 1);
        }
        else{
            SmartDashboard.putNumber("Motorcycle Light State", 0);
            candle.vBatOff();
        }*/
    }

    




    

    /**
     * Only turns on the painfully bright Limelight LEDs when they're being used
     * @param limelight takes in Limelight object
     */

    public void limeLightDrive(LimeLight limelight, DrivetrainSubsystem drive){

        SmartDashboard.putNumber("Servo Position", servo.getServoAngle());

        //motorCycle.on();
        
        boolean runningLimelight;
        boolean hasVision;
        if (driveController.getXButton()) {
            //motorCycle.off();
            SmartDashboard.putNumber("Motorcycle Light State", 0);
            //limelight.ledOn(limelight);
            //limelight.setpipeline(limelight, 0.0);

            
            hasVision = limelight.runLimeLight(drive, 1);
            runningLimelight = true;
            //limelight.ledMode.setBoolean(true);

            
        }
        else if (driveController.getBButton()){
            //motorCycle.on();
            SmartDashboard.putNumber("Motorcycle Light State", 1);
            SmartDashboard.putNumber("Limelight Running", 1);
            //limelight.ledOff(limelight);
            //limelight.setpipeline(limelight, 1.0);
            if(/*allianceColor.equals("Blue")*/true ){ //don't be a sinner and use ==. use .equals();
                hasVision = limelight.runLimeLight(drive, 2);
                SmartDashboard.putNumber("Limelight Running", 1);
            }
            else{
                hasVision = limelight.runLimeLight(drive, 3);
            }
            runningLimelight = true;
        
        }
        else{
            //motorCycle.off();
            SmartDashboard.putNumber("Motorcycle Light State", 0);
            runningLimelight = false;
            hasVision = false;
            limelight.candleOff();
            //limelight.ledOff(limelight);
        }
        SmartDashboard.putNumber("Running Limelight", runningLimelight ? 1:0);
        SmartDashboard.putNumber("Has Vision", hasVision ? 1:0);
    }

    
    public void climber(Climber climb) {
        SmartDashboard.putNumber("Climber Position", climb.getPosition());
        
        if (armController.getXButton()) {
            climb.climb();
        }

        else if (armController.getYButton()) {
            climb.deploy();
        }

        else {
            climb.stop();
        }

    }

    
    public void resetClimber(Climber climb) {
        if (armController.getXButton()) {
            climb.climb();
        }

        else if (armController.getYButton()) {
            climb.reset();
        }

        else {
            climb.stop();
        }
        
    }


    public ColorSensor getColorSensor(){
        return this.colorSensor;
        //this method was made in crunch time
    }
    

    
    

    
    

}
