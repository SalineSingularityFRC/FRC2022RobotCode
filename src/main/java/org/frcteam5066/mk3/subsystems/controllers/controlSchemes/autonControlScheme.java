package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;



//import org.frcteam5066.common.math.Vector2;
import com.kauailabs.navx.frc.AHRS;
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.Intake;

public abstract class AutonControlScheme {

    protected static AHRS gyro;
    protected static LimeLight limeLight;
    protected static DrivetrainSubsystem drive;
    protected static Shooter flywheel;
    protected static Intake intake;
    protected static int position;
    protected static int color;
    protected static int rotationDirection; //1 is clockwise, -1 is counter-clockwise
    
    SendableChooser<Integer> startingPosition = new SendableChooser<>();

    public AutonControlScheme(LimeLight limeLight, Shooter flywheel, DrivetrainSubsystem drive, String color){
        
        startingPosition.setDefaultOption("Position 1", 1);
        startingPosition.addOption("Position 2", 2);
        startingPosition.addOption("Position 3", 3);
        startingPosition.addOption("Position 3", 4);

        this.gyro = new AHRS(SPI.Port.kMXP);
        this.limeLight = limeLight;
        this.flywheel = flywheel;
        this.intake = intake;
        this.position = startingPosition.getSelected();
        if(color.equals("Red")) this.color = 2;
        else this.color = 3;
        if(position < 3) rotationDirection = 1;
        else rotationDirection = -1;
    }

    private static boolean hasCargoTarget(){
        if(color == 2) return limeLight.hasRedCargoTarget();
        return limeLight.hasBlueCargoTarget();
    }

    public void drive(){
        if(position == 1){
            //I don't know yet
        }

        else if(position != 1){
            
            //use runLimeLight, pass in color as int (2 or 3)
            limeLight.runLimeLight(drive, color);
  

        }
    }
    
    
    public void shoot(){
        if(position != 1){
            while(!limeLight.hasVisionTarget()){
                drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
            }
        }

        limeLight.runLimeLight(drive, 1);
        flywheel.shooterOn();

        //this next section waits for 1 second to pass. This should be optimized once we get a chance to run this on the robot
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            
        }

        intake.conveyorCollect();
        flywheel.shoot();

        //this will be replaced with color sensor telling us when a ball has been fired
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            
        }

        intake.conveyorOff();
        flywheel.shooterOff();

    }

    public void getBall(){
        double initPos = gyro.getAngle();
        while(!hasCargoTarget()){
            drive.drive(new Vector2(0, 0), 1 * rotationDirection, false);
            if( Math.abs(initPos - gyro.getAngle()) >  180 ) break;
        }
        initPos = gyro.getAngle();
        while(!hasCargoTarget()){
            drive.drive(new Vector2(0, 0), 1 * -rotationDirection, false);
            if( Math.abs(initPos - gyro.getAngle()) >  180 ) break;
        }

        limeLight.runLimeLight(drive, 1);

    }


}
