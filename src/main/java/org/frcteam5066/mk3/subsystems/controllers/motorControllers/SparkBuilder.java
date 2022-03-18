package org.frcteam5066.mk3.subsystems.controllers.motorControllers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;

import org.json.simple.*;
import org.json.simple.parser.*;

public class SparkBuilder {
    private CANSparkMax m_motor;

    private CANEncoder m_encoder;
    private SparkMaxPIDController m_pidController;
    private int portNumber;
    private boolean brushlessMotor;
    private double rampRate;

    private double kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut;

    //name of the mechanism
    String name;

    double initialPosition;
    double previousPosition, previousJoystick;
    boolean controlWithJoystick;

    boolean usingLimitSwitch, upperLimit;
    
    // When controlling by voltage, set the max voltage here
    private final double maxVoltage = 9.0;

    public SparkBuilder(JSONObject Motor){
        
        this(
            (int)Motor.get("CanID"), 
            true, 
            1, 
            (String)Motor.get("Name"), 
            false, 
            false, 
            (double)((JSONObject)Motor.get("PID")).get("kP"), 
            (double)((JSONObject)Motor.get("PID")).get("kI"), 
            (double)((JSONObject)Motor.get("PID")).get("kD"), 
            1, 
            1, 
            1, 
            1
        );
    }

    private SparkBuilder(int portNumber, boolean brushlessMotor, double rampRate, String name, boolean useLimitSwitch, boolean useUpperLimitSwitch,
                        double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut){
        this.portNumber = portNumber;

        this.m_pidController = m_motor.getPIDController();

        this.rampRate = rampRate;

        //this.putConstantsOnDashboard(name, kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
        //this.setPIDConstantsSilent(kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kIZ = kIZ;
        this.kFF = kFF;
        this.kMinOut = kMinOut;
        this.kMaxOut = kMaxOut;




        //If intitialPosition = -100, lower limit switch has not been pressed.
        this.initialPosition = -100;
        this.previousPosition = -100;

        this.previousJoystick = 0.0;
        this.controlWithJoystick = false;

        this.name = name;

        this.usingLimitSwitch = useLimitSwitch;
        
        this.upperLimit = useUpperLimitSwitch;
    }

    public SparkBuilder portNumber(int portNumber){
        this.portNumber = portNumber;
        return this;
    }

    public SparkBuilder brushlessMotor(boolean value){
        this.brushlessMotor = value;
        return this;
    }

    public SparkBuilder rampRate(double value){
        this.rampRate = value;
        return this;
    }

    public SparkBuilder name(String value){
        this.name = value;
        return this;
    }

    public SparkBuilder limitSwitch(boolean value){
        this.usingLimitSwitch = value;
        return this;
    }

    public SparkBuilder upperLimitSwitch(Boolean value){
        this.upperLimit = value;
        return this;
    }

    public SparkBuilder kP(double value){
        this.kP = value;
        return this;
    }

    public SparkBuilder kI(double value){
        this.kI = value;
        return this;
    }
    
    public SparkBuilder kD(double value){
        this.kD = value;
        return this;
    }
    
    public SparkBuilder kIZ(double value){
        this.kIZ = value;
        return this;
    }

    public SparkBuilder kFF(double value){
        this.kFF = value;
        return this;
    }

    public SparkBuilder kMinOut(double value){
        this.kMinOut = value;
        return this;
    }

    public SparkBuilder kMaxOut(double value){
        this.kMaxOut = value;
        return this;
    }

    public Spark build(){
        return new Spark(portNumber, brushlessMotor, rampRate, name, usingLimitSwitch, upperLimit,
        kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
    }





}
