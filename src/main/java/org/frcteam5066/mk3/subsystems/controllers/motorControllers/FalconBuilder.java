package org.frcteam5066.mk3.subsystems.controllers.motorControllers;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;

import org.json.simple.*;
import org.json.simple.parser.*;



public class FalconBuilder {
    private TalonFXConfiguration config;
    private int canID;
    private double ramp;
    private boolean coast;
    private double kP;
    private double kI;
    private double kD;
    private boolean limitState; ///NC vs NO
    private boolean inverted;
    private int sensor;
    private int canCoderID;
    
    




    public FalconBuilder(JSONObject jsonObject){
        this( (int)jsonObject.get("CanID"), (double) jsonObject.get("RampRate"), (boolean) jsonObject.get("Coast"), 
            (double) ((JSONObject)jsonObject.get("PID")).get("kP"), (double) ((JSONObject)jsonObject.get("PID")).get("kI"), (double) ((JSONObject)jsonObject.get("PID")).get("kD"), 
            (boolean) jsonObject.get("Limit"), (boolean) jsonObject.get("IsMotorInverted"), (int)jsonObject.get("SensorType"), (int)jsonObject.get("CanCoderID"));
    }

    private FalconBuilder(int canID, double rampRate, boolean coast, 
        double kp, double ki, double kd, boolean limitState, boolean inverted, 
        int sensor, int canCoderID){

        this.canID = canID;
        this.ramp = rampRate;
        this.coast = coast;
        this.kP = kp;
        this.kI = ki;
        this.kD = kd;
        this.limitState = true;
        this.inverted = inverted;
        this.sensor = sensor;
        this.canCoderID = canCoderID;



        
    }

    public FalconBuilder canID(int value){  
        this.canID = value;
        return this;
    }

    public FalconBuilder ramp(double value){
        this.ramp = value;
        return this;
    }

    public FalconBuilder coast(Boolean value){
        this.coast = value;
        return this;
    }

    public FalconBuilder kP(double value){
        this.kP = value;
        return this;
    }
    public FalconBuilder kI(double value){
        this.kI = value;
        return this;
    }

    public FalconBuilder kD(double value){
        this.kD = value;
        return this;
    }

    public FalconBuilder inverted(boolean value){
        this.inverted = value;
        return this;
    }

    public FalconBuilder sensor(int value){
        this.sensor = value;
        return this;
    }


    public FalconBuilder sensorType(boolean nc){
        this.limitState = nc;
        return this;
    }

    public FalconBuilder CanCoderID(int value){
        this.canCoderID = value;
        return this;
    }

    public int cancoderToCanID(){
        CANCoder cancoder = new CANCoder(this.canID);
        return cancoder.getDeviceID();

    }

    

    public Falcon build(){
        Falcon talon = new Falcon(this.canID, this.ramp, this.coast); 
        config.slot0.kP = this.kP;
        config.slot0.kI = this.kI;
        config.slot0.kD = this.kD;
        if( this.inverted ){
            talon.setInverted(InvertType.InvertMotorOutput);
        }
        
        //Decode the sensor number and turn them into FeedbackDevice objects
        if(sensor == 1){
            config.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
        }
        else if (sensor == 11){
            config.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.RemoteSensor0.toFeedbackDevice();
            config.remoteFilter0.remoteSensorDeviceID = cancoderToCanID();
        }
        else if (sensor == 12){
            config.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.RemoteSensor1.toFeedbackDevice();
            config.remoteFilter1.remoteSensorDeviceID = cancoderToCanID();
        }
        else if(sensor == 14){
            config.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.None.toFeedbackDevice();
        }
        else if(sensor == 15){
            config.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.SoftwareEmulatedSensor.toFeedbackDevice();
        }
        


        talon.setConfiguration(config);
        return talon;
    }


}
