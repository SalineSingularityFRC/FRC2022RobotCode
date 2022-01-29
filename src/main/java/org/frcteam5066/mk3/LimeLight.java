package org.frcteam5066.mk3;

import org.frcteam5066.common.math.Vector2;

//technically we shouldn't use this but were going to anyway
import org.frcteam5066.common.robot.subsystems.HolonomicDrivetrain;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight{

    public NetworkTable table;
    public NetworkTableEntry tx, ty, ta, tv, ts, tl, pipeLine, tshort, tlong, thor, tvert, getpipe, camtran, ledMode, camMode;

    public double target_distance = 0.0;

    //constructor to create the limelight and its values
    //class by: Branden Amstutz
    public LimeLight() {

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
        pipeLine = table.getEntry("pipeLine");
        // swap the limelight between vision processing and drive camera
        camMode = table.getEntry("camMode");
        
    }

    // turn on the LEDs, takes a liemlight object
    public void ledOn( LimeLight limeLight ){
        
        limeLight.ledMode.setDouble(1.0);

    }

    // turn off the LEDs, takes a LimeLight object
    public void ledOff(LimeLight limeLight){
        limeLight.ledMode.setDouble(3.0);
    }

    // method to change between pipeLines, takes an int and a LimeLight object
    public void setpipeline(LimeLight limeLight, double pipe){
        limeLight.pipeLine.setDouble(pipe);
    }
    
    //method to toggle camera between drive mode and vision mode
    public void setCamMode( LimeLight limeLight, double mode){
        limeLight.camMode.setDouble(mode);
    }

    @Deprecated //technically this means we shouldn't use it but were going to anyway
    @SuppressWarnings("removal")
    public boolean runLimeLight( HolonomicDrivetrain drive){

        double hasVision = tv.getDouble(0.0);
        
        if(hasVision == 1.0 && !(tx.getDouble(0.0) <= 0.1 && tx.getDouble(0.0) >= -0.1)){
            
            double left_comand = 0.0;
            double right_comand = 0.0;
            
            double heading_error = -tx.getDouble(0.0);
            double distance_error = target_distance - ty.getDouble(0.0);

            

            drive.holonomicDrive(new Vector2(0, distance_error), heading_error);

            return true;
        
        }

        return false;

    }
}
//PID-adjusts a control output based on difference between a set point 
//derivative is rate of change
//integral-sum of instantaneous error over time and gives accumulated
//offset that should've been corrected

//THIS IS JUST AN OPTION FOR THE INTAKE OF THE CARGO
//FOR the interior, place a color sensor. When the sensor sees a ball 
//that doesn't match with the alliance, it spits the ball out at a slow speed so
//it doesn't shoot in the goal. But for the exterior, use lime light

//put the color sensor right by the shooter. 