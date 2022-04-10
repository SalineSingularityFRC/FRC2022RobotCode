package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import org.frcteam5066.mk3.subsystems.CANdleSystem;
import org.frcteam5066.mk3.subsystems.Climber;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.controllers.*;
import org.frcteam5066.mk3.IntakePneumatics;
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.XboxController;

// Alternative ControlScheme to ArcadeDrive.java (control scheme) to test different mechaninsms without breaking everything
// ControlSchemes can be changed in Robot.java

//Uncomment to enable Smart Dashboard
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Uncomment to enable gyro
//import com.kauailabs.navx.frc.AHRS;

public class Test extends ControlScheme{
    XboxController driveController;
    XboxController armController;

    

    public Test(int driveControllerPort, int armControllerPort){
        driveController = new XboxController(driveControllerPort);
        armController = new XboxController(armControllerPort);
    }


    
/*
    @Override
    public void ledMode(LimeLight limeLight) {
        // TODO Auto-generated method stub
        
    }*/

    


    @Override
    public void intakeConveyer(Intake intake) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void flywheel(Shooter flywheel) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void intakePneumatics(IntakePneumatics intakePneumatics) {
    }

    @Override
    public void limeLightDrive(LimeLight limeLight, DrivetrainSubsystem drive) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void candle(CANdleSystem candle) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void shootSequence(Shooter flywheel, Intake intake) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void intakeSequence(Shooter flywheel, Intake intake) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void colorSensor() {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void climber(Climber climb) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void resetClimber(Climber climber) {
        // TODO Auto-generated method stub
        
    }
/*
    public void setPodEncoders(){
        public static double DRIVETRAIN_FRONT_LEFT_ENCODER_OFFSET = Math.toRadians(73.037      );
        public static double DRIVETRAIN_FRONT_RIGHT_ENCODER_OFFSET = Math.toRadians(17.051   );
        public static double DRIVETRAIN_BACK_LEFT_ENCODER_OFFSET = Math.toRadians(356.396     );
        public static double DRIVETRAIN_BACK_RIGHT_ENCODER_OFFSET = Math.toRadians(222.891    );
    }
*/
}