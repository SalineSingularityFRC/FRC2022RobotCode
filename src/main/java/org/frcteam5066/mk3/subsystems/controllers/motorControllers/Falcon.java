package org.frcteam5066.mk3.subsystems.controllers.motorControllers;

import javax.swing.text.Position;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Falcon implements MotorController {

    private WPI_TalonFX talon; 
    private int canID;

    TalonFXConfiguration config = new TalonFXConfiguration();



    public Falcon(int canID, double rampRate, boolean coast ) {
        talon = new WPI_TalonFX(canID);
        this.canID = canID;
         
        //talon.configFactoryDefault();
        setRampRate(rampRate);
        setCoastMode(coast);
    }

    public Falcon(int canID, double rampRate, boolean coast, String canBusName ) {
        talon = new WPI_TalonFX(canID, canBusName);
        this.canID = canID;
         
        //talon.configFactoryDefault();
        
        setRampRate(rampRate);
        setCoastMode(coast);
    }

    public Falcon(int canID, double rampRate, boolean coast, String canBusName, double kP, double kI, double kD ) {
        talon = new WPI_TalonFX(canID, canBusName);
        this.canID = canID;
         
        //talon.configFactoryDefault();
        talon.config_kP(0, kP);
        talon.config_kI(0, kI);
        talon.config_kD(0, kD);
        setRampRate(rampRate);
        setCoastMode(coast);
    }

    public Falcon(int canID, double rampRate, boolean coast, String canBusName, double kP, double kI, double kD, double kF ) {
        talon = new WPI_TalonFX(canID, canBusName);
        this.canID = canID;
         
        //talon.configFactoryDefault();
        talon.config_kP(0, kP);
        talon.config_kI(0, kI);
        talon.config_kD(0, kD);
        talon.config_kF(0, kF);
        
        setRampRate(rampRate);
        setCoastMode(coast);
    
    }

    

    public Falcon(int canID, double rampRate, boolean inverted, boolean coast, String canBusName, double kP, double kI, double kD, double kF ) {
        talon = new WPI_TalonFX(canID, canBusName);
        this.canID = canID;

        

        

        
         
        //talon.configFactoryDefault();
        talon.setInverted(inverted);
        //talon.configPeakOutputForward(percentOut)
        talon.config_kP(0, kP);
        talon.config_kI(0, kI);
        talon.config_kD(0, kD);
        talon.config_kF(0, kF);
        
        setRampRate(rampRate);
        setCoastMode(coast);
    }

    public Falcon(int canID, double rampRate, boolean inverted, boolean coast, String canBusName, double kP, double kI, double kD, double kF, double max_out, double max_out_Rev ) {
        talon = new WPI_TalonFX(canID, canBusName);
        this.canID = canID;

        
         
        //talon.configFactoryDefault();
        talon.setInverted(inverted);
        talon.configPeakOutputForward(max_out);
        talon.configPeakOutputReverse(max_out_Rev);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setSensorPhase(false);

        talon.config_kP(0, kP);
        talon.config_kI(0, kI);
        talon.config_kD(0, kD);
        talon.config_kF(0, kF);
        setRampRate(rampRate);
        //setCoastMode(coast);
    }

    public int getCanID(){
        
        return canID;
    }

    

    public void setConfiguration(TalonFXConfiguration config){
        talon.configAllSettings(config);
    }

    public TalonFXConfiguration getConfiguration(){
        TalonFXConfiguration tempConfig = new TalonFXConfiguration();
        talon.getAllConfigs(tempConfig);
        return tempConfig;
    }


    public TalonFXConfiguration makeFalconConfig(){
        TalonFXConfiguration newConfig = new TalonFXConfiguration();
        //TalonFXConfiguration
        //newConfig.SensorInitializationStrategy = 1;



        return newConfig;
    }

    public void setInverted(InvertType invert){
        this.talon.setInverted(invert);
        
    }

    public void setSpeed(double speed) { //speed will be from -1.0 to 1.0
        this.talon.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setVelocity(double rpm) { 
        double velocity = rpm * 2048 / 600;
        this.talon.set(TalonFXControlMode.Velocity, velocity);
        //setSpeed(1);
        //SmartDashboard.putNumber("Target Velocity", rpm);
        //SmartDashboard.putNumber("Current Velocity", this.talon.getSelectedSensorVelocity());
    }

    public void setRPMFromStick(double stickValue){ //stickValue will be between -1.0 and 1.0
        double rpm = stickValue * 6380;
        this.setVelocity(rpm);

    }

    public double getMotorRPM(){
        double rpm = ( this.talon.getSelectedSensorVelocity() * 60 * 10 ) / (2048); //go from units/100ms to rpm
        return rpm;
    }

    public void setPositionDegrees(double position){ //position is measured in degrees
        position /= 360;
        position *= 2048; //2048 is the number of ticks of the encoder (to our best estimate, most likely true)
        this.talon.set(TalonFXControlMode.Position, position); //position here is measured in encoder ticks
        SmartDashboard.putNumber("Setting Talon Position to ", position);
        SmartDashboard.putNumber("Current Falcon Position: ", talon.getSelectedSensorPosition());
        SmartDashboard.putNumber("Position Difference", talon.getSelectedSensorPosition() - position);
        
        
    }

    public void setPosition(double position){
        
        this.talon.set(TalonFXControlMode.Position, position); //position here is measured in encoder ticks
        /*SmartDashboard.putNumber("Setting Talon Position to ", position);
        SmartDashboard.putNumber("Current Falcon Position: ", talon.getSelectedSensorPosition());
        SmartDashboard.putNumber("Position Difference", talon.getSelectedSensorPosition() - position);*/
        
        
    }

    public void setRampRate(double rampRate) {
        talon.configClosedloopRamp(rampRate);
    }

    public void setCoastMode(boolean coast) {
        if(coast)
            talon.setNeutralMode(NeutralMode.Coast);
        else
            talon.setNeutralMode(NeutralMode.Brake);
    }

    //Not needed for swerve, maybe needed for other systems
    public void follow(MotorController baseController, boolean invert) { 
        //this.talon.follow(((Falcon)baseController).getMotorController(), invert);
        //this.talon.follow(baseController);
        //this.talon.setInverted(InvertType.FollowMaster);

    }

    public void setCurrentLimit(boolean limitEnabled, int limit, int triggerThreshold, int triggerTime){
        talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 10, 15, 0.5));
    }

    public WPI_TalonFX getMotorController() {
        return this.talon;
    }

    @Override
    public double getRotationsSpun() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void resetRotationsZero() {
        // TODO Auto-generated method stub
        
    }

    public double getVelocity(){
        return this.talon.getSelectedSensorVelocity() * 600 / 2048;
    }

    public double getSelectedSensorPosition(){
        return this.talon.getSelectedSensorPosition();
    }

    public double getPower(){
        return talon.getMotorOutputPercent();
    }
    
}
