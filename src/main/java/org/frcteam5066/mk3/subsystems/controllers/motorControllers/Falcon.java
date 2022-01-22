package org.frcteam5066.mk3.subsystems.controllers.motorControllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;

public class Falcon implements MotorController {

    private WPI_TalonFX talon; 
    private int canID;


    public Falcon(int canID, double rampRate, boolean coast ) {
        talon = new WPI_TalonFX(canID);
        this.canID = canID;
         
        talon.configFactoryDefault();
        setRampRate(rampRate);
        setCoastMode(coast);
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

    public void setVelocity(double rpm) { //speed will be from -1.0 to 1.0
        this.talon.set(TalonFXControlMode.Velocity, rpm);
    }

    public void setRPMFromStick(double stickValue){ //stickValue will be between -1.0 and 1.0
        double rpm = stickValue * 6380;
        this.setVelocity(rpm);
    }

    public void setPosition(double position){ //position is measured in degrees
        position /= 360;
        position *= 2048; //2048 is the number of ticks of the encoder (to our best estimate, most likely true)
        this.talon.set(TalonFXControlMode.Position, position); //position here is measured in encoder ticks
        
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
}
