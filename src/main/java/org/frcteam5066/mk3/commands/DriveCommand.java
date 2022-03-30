package org.frcteam5066.mk3.commands;


import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

import java.lang.ModuleLayer.Controller;

import org.frcteam5066.common.math.Vector2;
import org.frcteam5066.common.robot.input.Axis;
import org.frcteam5066.common.robot.input.XboxController;
import org.frcteam5066.common.robot.input.DPadButton.Direction;

public class DriveCommand extends CommandBase {
    private DrivetrainSubsystem drivetrainSubsystem;
    private Axis forward;
    private Axis strafe;
    private Axis rotation;
    private XboxController controller;
    private double reducedSpeed;

    public DriveCommand(DrivetrainSubsystem drivetrain, Axis forward, Axis strafe, Axis rotation, XboxController controller, double reducedSpeed) {
        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;
        this.controller = controller;
        this.reducedSpeed = reducedSpeed;

        drivetrainSubsystem = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if(controller.getDPadButton( Direction.UP).get() ){
            drivetrainSubsystem.drive(new Vector2(reducedSpeed, 0), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.RIGHT).get() ){
            drivetrainSubsystem.drive(new Vector2(0, -reducedSpeed), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.DOWN).get() ){
            drivetrainSubsystem.drive(new Vector2(-reducedSpeed, 0), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.LEFT).get() ){
            drivetrainSubsystem.drive(new Vector2(0, reducedSpeed), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.UPRIGHT).get() ){
            drivetrainSubsystem.drive(new Vector2(reducedSpeed, -reducedSpeed), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.DOWNRIGHT).get() ){
            drivetrainSubsystem.drive(new Vector2(-reducedSpeed, -reducedSpeed), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.DOWNLEFT).get() ){
            drivetrainSubsystem.drive(new Vector2(-reducedSpeed, reducedSpeed), rotation.get(true), true);
        }
        else if(controller.getDPadButton( Direction.UPLEFT).get() ){
            drivetrainSubsystem.drive(new Vector2(reducedSpeed, reducedSpeed), rotation.get(true), true);
        }
        else{
            drivetrainSubsystem.drive(new Vector2(forward.get(true), strafe.get(true)), rotation.get(true), true);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.drive(Vector2.ZERO, 0, false);
    }
}
