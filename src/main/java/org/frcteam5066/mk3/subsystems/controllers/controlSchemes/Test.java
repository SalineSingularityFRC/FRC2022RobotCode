package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import frc.controller.*;
import frc.robot.*;
import frc.singularityDrive.*;
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

    public void colorSensor(ColorSensor colorSensor) {
        
    }

    public Test(int driveControllerPort, int armControllerPort){
        driveController = new XboxController(driveControllerPort);
        armController = new XboxController(armControllerPort);
    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {
        
    }

    public void smartDrive(SmartSingDrive drive, DrivePneumatics pneumatics) {
        
    }

    public void conveyorFlywheel(Conveyor conveyor, Flywheel flywheel) {
        
    }

    public void climber(Climber climber) {
        
    }

    public void climberReset(Climber climber) {
        
    }


    public void ledMode(LimeLight limeLight){
        
    }

    public void limeLightDrive(LimeLight limeLight, SmartSingDrive drive, boolean isAuto){
        
    }

}