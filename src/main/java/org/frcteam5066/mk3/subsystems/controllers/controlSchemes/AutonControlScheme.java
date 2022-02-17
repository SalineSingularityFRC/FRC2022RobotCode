package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

import org.frcteam5066.common.robot.subsystems.Drivetrain;
import org.frcteam5066.common.robot.subsystems.HolonomicDrivetrain;
import org.frcteam5066.mk3.LimeLight;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.controllers.*;

public abstract class AutonControlScheme {

    LimeLight lili = new LimeLight();

    public abstract void moveAuton(DrivetrainSubsystem drive);

    public void driveToPoint(DrivetrainSubsystem drive){
        
        //run motor until out of tarmak
    }

    public void searchAlign(DrivetrainSubsystem drive){
        //spin until ball is in center of vision
    }
    
    public void searchLeft(DrivetrainSubsystem drive){
        //searchAlign starting left
    }

    public void searchRight(DrivetrainSubsystem drive){
        //searchAlign starting right
    }

    public void deployIntake(DrivetrainSubsystem drive){
        //deploy intake
    }
    
    public void getBall(DrivetrainSubsystem drive){
        //run AFTER searchAlign(), drive until ball is in robot
    }
    public void aim(DrivetrainSubsystem drive){
        //aim robot at goal. Use this in teleop as well
    }


}
