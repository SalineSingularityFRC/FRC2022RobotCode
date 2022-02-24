package org.frcteam5066.mk3.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;


public class DontDriveCommand extends CommandBase {

    public DontDriveCommand(DrivetrainSubsystem drivetrainSubsystem){
        addRequirements(drivetrainSubsystem);
    }

    
    
}
