package org.frcteam5066.mk3;

//import java.sql.Time;
//import java.util.Timer;


import org.frcteam5066.common.math.Vector2;
import org.frcteam5066.common.robot.drivers.Limelight;
import org.frcteam5066.common.robot.drivers.Limelight.CamMode;
import org.frcteam5066.mk3.subsystems.CANdleSystem;
//technically we shouldn't use this but were going to anyway
//import org.frcteam5066.common.robot.subsystems.HolonomicDrivetrain;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Servo2;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLight{

    public NetworkTable table;
    public NetworkTableEntry tx, ty, ta, tv, ts, tl, pipeLine, tshort, tlong, thor, tvert, getpipe, camtran, ledMode, camMode;
    //t means target(example-target x, y, )

    public double target_distance = 0.0;
    //TODO Find limelight distance value

    Servo2 servo;



    PIDController headingPID;
    PIDController distancePID;

    CANdleSystem candle;


    //constructor to create the limelight and its values
    //class by: Branden Amstutz
    public LimeLight(CANdleSystem _candle){
        table = NetworkTableInstance.getDefault().getTable("limelight");
        // horizontal offset of cross hair to target
        tx = table.getEntry("tx");
        // vertical offset of cross hair to target
        ty = table.getEntry("ty");
        //target area: 0% of image to 100%   
        ta = table.getEntry("ta");
        // valid target: 0 - invalid, 1 - valid
        tv = table.getEntry("tv");
        // Skew or rotation
        ts = table.getEntry("ts");
        //pipeline Latency
        tl = table.getEntry("tl");
        //sidelength of the shortest side of fitted bounding box
        tshort = table.getEntry("tshort");
        //sidelength of the longest side of fitted bounding box
        tlong = table.getEntry("tlong");
        //horizontal sidelength of rough bounding box
        thor = table.getEntry("thor");
        //vertical sidelength of rough bounding box
        tvert = table.getEntry("tvert");
        // true active pipeline index of the camera
        getpipe = table.getEntry("getpipe");
        // Results of a 3D position solution, 6 numbers: Translation (x,y,y) Rotation(pitch,yaw,roll)
        camtran = table.getEntry("camtran");
        //state of LEDs: 1.0 - on, 2.0 - blink, 3.0 - off
        ledMode = table.getEntry("ledMode");
        //set pipeLine
        pipeLine = table.getEntry("pipeline");
        // swap the limelight between vision processing and drive camera
        camMode = table.getEntry("camMode");

        //initialize PID objects from WPILIB
        headingPID = new PIDController(.02, 0.00025, 0.0004);
        distancePID = new PIDController(0.1, 0.00, 0.00);
        distancePID.setSetpoint(0);
        distancePID.setTolerance(.35);
        headingPID.setTolerance(.35);

        servo = new Servo2(0);
        
        //tune these

        //table.getEntry("pipeline").setNumber(0);
        //SmartDashboard.putNumber("Pipeline", pipeLine.getDouble(0.0));

        this.candle = _candle;


        
        
    }


    // turn on the LEDs, takes a liemlight object
    public void ledOn( LimeLight limeLight ){
        
        limeLight.ledMode.setDouble(3.0);

    }

    // turn off the LEDs, takes a LimeLight object
    public void ledOff(LimeLight limeLight){
        limeLight.ledMode.setDouble(0.0);
    }

    // method to change between pipeLines, takes an int and a LimeLight object
    public void setpipeline(LimeLight limeLight, int pipe){
        limeLight.pipeLine.setNumber( pipe );
        
    }
    
    //method to toggle camera between drive mode and vision mode
    public void setCamMode( LimeLight limeLight, double mode){
        limeLight.camMode.setDouble(mode); 
    }

    public boolean hasVisionTarget(){
        pipeLine.setNumber( 1 );
        return tv.getDouble(0.0) == 1.0;
    }

    public boolean hasBlueCargoTarget(){
        pipeLine.setNumber( 0 );
        return tv.getDouble(0.0) == 1.0;
    }
    public boolean hasRedCargoTarget(){
        pipeLine.setNumber( 2 );
        return tv.getDouble(0.0) == 1.0;
    }

    
    //@TODO actually get limelight distance tuning correct
    public double getDistance(){
        return 4.0;
    }

    public void candleOff(){
        candle.vBatOff();
    }

    
    // runLimeLight returning "true" means it is still in the process of aiming
    public boolean runLimeLight( DrivetrainSubsystem drive, int driveType ){ //drivetype will be 1 for vision tracking, 2 for blue cargo, and 3 for red cargo

        double kP = .2;
        double kD = .2;
        //Timer time = new Timer("PID1");

        /*if(driveType == 0){
            pipeLine.setNumber( 0 );
        }
        else if(driveType == 1){
            pipeLine.setNumber( 1 );
        }*/

        pipeLine.setNumber( driveType );
        SmartDashboard.putNumber("Pipe", driveType);

        if(driveType == 2 || driveType == 3){
            //candle.vBatOn();
            servo.toIntakeAngle();
        }
        else{
            candle.vBatOff();
            servo.toTargetingAngle();
        }


        //TODO limelight contouring in gym

        



        

        //SmartDashboard.putBoolean("isPipeline", pipeLine.setNumber( 2 ));

        //SmartDashboard.putNumber("Pipeline number", getpipe.getDouble(190.0));
        

        

        //pipeLine.set;

        //pipeLine.setNumber( driveType );

        //SmartDashboard.putNumber("X Value", tx.getDouble(0.0));

        


        double hasVision = tv.getDouble(0.0);

    
        
        if(hasVision == 1.0 && !(tx.getDouble(0.0) <= 0.07 && tx.getDouble(0.0) >= -0.07)){
            
            double left_comand = 0.0;
            double right_comand = 0.0;

            double distance = 8.5; //just for lols rn, this is the max in meters
            double height = 2.042;
            
            
            double velocity = Math.sqrt((-.5 * 9.807 * Math.pow( distance, 2 )) / 
                    ( Math.pow( Math.cos(Math.toRadians(60) ), 2 ) * ( height - distance * Math.tan(Math.toRadians(60)) ) ) );
            //plz never make me type that again

            
            //double heading_error = -tx.getDouble(0.0) * .2;
            double heading_error = headingPID.calculate(tx.getDouble(0.0));

            //double distance_error = target_distance - ty.getDouble(0.0);

            double distance_error = distancePID.calculate(ty.getDouble(0.0));
            
            SmartDashboard.putNumber("Target Y", ty.getDouble(0.0));
            //TODO limelight distance PID tuning
            double vertDistance = 0;



            if(driveType == 2 || driveType == 3){
                vertDistance = .8;
            }
            else{
                vertDistance = distance_error;
            }

            

            if(heading_error > .3){
                heading_error = .3;
            }
            else if (heading_error < -.3){
                heading_error = -.3;
            }

            if(distance_error > .3){
                distance_error = .3;
            }
            else if (distance_error < -.3){
                distance_error = -.3;
            }
            



            SmartDashboard.putNumber("Heading Error", heading_error);

            
            SmartDashboard.putNumber("Distance_Error", distance_error);
            SmartDashboard.putNumber("Heading_Error", heading_error);
            drive.drive(new Vector2(vertDistance, 0), heading_error, false); 
            //note that vectors in this notation use (y,x) and should be used with robot oriented control
            //Not necessarily y and x but rather forward/backward and left/right

            return true;
        
        }

        return false;

    }//the pipelines track configurations on the limelight(like the rgb )
}
//PID-adjusts a control output based on difference between a set point 
//derivative is rate of change/predicting future, so as to not over shoot
//integral-sum of instantaneous error over time and gives accumulated
    //offset that should've been corrected
//proportion- the ratio of output response to the error signal.
//This IS JUST AN OPTION FOR THE INTAKE OF THE CARGO
//For the interior, place a color sensor. When the sensor sees a ball 
//that doesn't match with the alliance, it spits the ball out at a slow speed so
//it doesn't shoot in the goal. But for the exterior, use lime light

//put the color sensor right by the shooter. 