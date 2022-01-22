package org.frcteam5066.mk3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import org.json.simple.*;
import org.json.simple.parser.*;

import org.frcteam5066.mk3.subsystems.controllers.MotorController;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Falcon;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.FalconBuilder;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.Spark;
import org.frcteam5066.mk3.subsystems.controllers.motorControllers.SparkBuilder;
//import jdk.vm.ci.code.CodeUtil.RefMapFormatter;

public class Json {
    JSONParser parser;
    JSONObject jsonObject;

    public Json() {
        parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(new FileReader("../motorControllers/MotorConfig.json"));
            jsonObject = (JSONObject) obj;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public boolean isObject(String name){
        return (jsonObject.get(name) != null);
    }

    private MotorController getMotor(JSONObject Motor, JSONObject Default) {
        
        MotorController returnMotor;
        String motorType = (String) Motor.get("Type");

        if (motorType.equals("Falcon")) {
            JSONObject FalconDefault = (JSONObject) Default.get(motorType);
            FalconBuilder fb = new FalconBuilder(FalconDefault);
            if (Motor.get("CanID") != null)
                fb.canID((int) Motor.get("CanID"));
            if (Motor.get("Ramp") != null)
                fb.ramp((double) Motor.get("Ramp"));
            if (Motor.get("Coast") != null)
                fb.coast((boolean) Motor.get("Coast"));
            if(Motor.get("PID") != null){
                JSONObject PID = (JSONObject) Motor.get("PID");
                if (Motor.get("kP") != null )
                    fb.kP((double) PID.get("kP"));
                if (Motor.get("kI") != null )
                    fb.kI((double) PID.get("kI"));
                if (Motor.get("kD") != null )
                    fb.kD((double) PID.get("kD"));
            }
            if (Motor.get("IsMotorInverted") != null)
                fb.inverted((boolean) Motor.get("IsMotorInverted"));
            if (Motor.get("SensorType") != null)
                fb.sensor((int) Motor.get("SensorType"));
            if (Motor.get("LimitStateNO") != null)
                fb.sensorType((boolean) Motor.get("LimitStateNO"));
            if (Motor.get("CanCoderID") != null)
                fb.CanCoderID((int) Motor.get("CanCoderID"));
            
            returnMotor = fb.build();
        } 
        else {
            JSONObject SparkDefault = (JSONObject) Default.get("Spark");
            // return new Spark((int)Motor.get("CanID"), true, 1, (String)Motor.get("Name"),
            // false, false,
            // (double)PID.get("kP"), (double)PID.get("kI"), (double)PID.get("kD"), 1, 1, 1,
            // 1);
            SparkBuilder sb = new SparkBuilder(SparkDefault);
            if (Motor.get("CanID") != null)
                sb.portNumber((int) Motor.get("CanID"));
            if (Motor.get("BrushlessMotor") != null)
                sb.brushlessMotor((boolean) Motor.get("BrushlessMotor"));
            if (Motor.get("RampRate") != null)
                sb.rampRate((double) Motor.get("RampRate"));
            if (Motor.get("Name") != null)
                sb.name((String) Motor.get("Name"));
            if (Motor.get("LimitSwitch") != null)
                sb.limitSwitch((boolean) Motor.get("LimitSwitch"));
            if (Motor.get("UpperLimitSwitch") != null)
                sb.upperLimitSwitch((boolean) Motor.get("UpperLimit"));
            if(Motor.get("PID") != null){
                JSONObject PID = (JSONObject) Motor.get("PID");
                if (Motor.get("kP") != null )
                    sb.kP((double) PID.get("kP"));
                if (Motor.get("kI") != null )
                    sb.kI((double) PID.get("kI"));
                if (Motor.get("kD") != null )
                    sb.kD((double) PID.get("kD"));
                if (Motor.get("kIZ") != null )
                    sb.kIZ((double) PID.get("kIZ"));
                if (Motor.get("kFF") != null )
                    sb.kFF((double) PID.get("kFF"));
            }
            if (Motor.get("kMinOut") != null)
                sb.kMinOut((double) Motor.get("kMinOut"));
            if (Motor.get("kMaxOut") != null)
                sb.kMaxOut((double) Motor.get("kMaxOut"));
            returnMotor = sb.build();
        }
        return returnMotor;

    }

    public int getChassisLength() {
        JSONObject Chassis = (JSONObject) jsonObject.get("Chassis");
        return (int) Chassis.get("Length");
    }

    public int getChassisWidth() {
        JSONObject Chassis = (JSONObject) jsonObject.get("Chassis");
        return (int) Chassis.get("Width");
    }

    public MotorController getShooterMotor(String name) throws NoSuchObjectException {
        JSONObject Shooter = (JSONObject) jsonObject.get("Shooter");
        JSONArray Motors = (JSONArray) jsonObject.get("Motors");
        JSONObject Default = (JSONObject) jsonObject.get("DefaultConfig");
        Iterator i = Motors.iterator();
        while (i.hasNext()) {
            JSONObject Motor = (JSONObject) i.next();
            if (((String) Motor.get("Name")).equalsIgnoreCase(name)) {
                return getMotor(Motor, Default);
            }
            
        }

        throw new NoSuchObjectException("Motor Not Found");


    }

    public MotorController getSwerveDriveMotors(String name) throws NoSuchObjectException {
        JSONObject Swerve = (JSONObject) jsonObject.get("Swerve");
        JSONArray Motors = (JSONArray) jsonObject.get("Motors");
        JSONObject Default = (JSONObject) jsonObject.get("DefaultConfig");
        Iterator i = Motors.iterator();
        while (i.hasNext()) {
            JSONObject Motor = (JSONObject) i.next();
            if (((String) Motor.get("Name")).equalsIgnoreCase(name)) {
                return getMotor(Motor, Default);
            }
        }

        throw new NoSuchObjectException("Motor Not Found");

    }

    public double getLimelightKP_Heading(){
        JSONObject Limelight = (JSONObject) jsonObject.get("Limelight");
        return (double) Limelight.get("kP_Heading");
    }
    public double getLimelightKP_Distance(){
        JSONObject Limelight = (JSONObject) jsonObject.get("Limelight");
        return (double) Limelight.get("kP_Distance");
    }

}
