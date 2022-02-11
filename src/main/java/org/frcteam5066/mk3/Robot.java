package org.frcteam5066.mk3;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam5066.common.robot.UpdateManager;
import org.frcteam5066.mk3.subsystems.CANdleSystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.CANdleSystem.AnimationTypes;
import org.frcteam5066.mk3.subsystems.controllers.ControlScheme;
import org.frcteam5066.mk3.subsystems.controllers.XboxController;
import org.frcteam5066.mk3.subsystems.controllers.controlSchemes.ArcadeDrive;

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;
    private UpdateManager updateManager;

    LimeLight limeLight;

    int flywheelMotor1, flywheelMotor2, flywheelMotor3;
    int conveyorMotor;

    ControlScheme currentScheme;

    Shooter flywheel;
    Intake intake;
    
    final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
    final int SMALL_JOYSTICK_PORT = 2;

    CANdleSystem candle;

    

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        updateManager = new UpdateManager(
                robotContainer.getDrivetrainSubsystem()
        );
        updateManager.startLoop(5.0e-3);

        flywheel = new Shooter(12, 11, 8);

        currentScheme = new ArcadeDrive(XBOX_PORT, XBOX_PORT + 1);

        intake = new Intake(10, 7);

        limeLight = new LimeLight();

        candle = new CANdleSystem();
        
        // candle.changeAnimation(AnimationTypes.TwinkleOff);

 
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        currentScheme.flywheel(flywheel);
        currentScheme.intakeConveyer(intake);
        currentScheme.limeLightDrive(limeLight, robotContainer.getDrivetrainSubsystem());
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void teleopInit() {
    }
}
