package org.frcteam5066.mk3;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight{

    public NetworkTable table;
    public NetworkTableEntry tx, ty, ta, tv, ts, tl, pipeLine, tshort, tlong, thor, tvert, getpipe, camtran, ledMode, camMode;

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
}