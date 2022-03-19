package org.frcteam5066.mk3;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import org.frcteam5066.common.robot.UpdateManager;
import org.frcteam5066.mk3.commands.DontDriveCommand;
import org.frcteam5066.mk3.subsystems.CANdleSystem;
import org.frcteam5066.mk3.subsystems.Climber;
import org.frcteam5066.mk3.subsystems.ColorSensor;
import org.frcteam5066.mk3.subsystems.DrivetrainSubsystem;
import org.frcteam5066.mk3.subsystems.Intake;
import org.frcteam5066.mk3.subsystems.Shooter;
import org.frcteam5066.mk3.subsystems.CANdleSystem.AnimationTypes;
import org.frcteam5066.mk3.subsystems.controllers.ControlScheme;
import org.frcteam5066.mk3.subsystems.controllers.XboxController;
import org.frcteam5066.mk3.subsystems.controllers.controlSchemes.ArcadeDrive;
import org.frcteam5066.mk3.subsystems.controllers.controlSchemes.RunAuton;

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;
    private UpdateManager updateManager;

    private DontDriveCommand dontDriveCommand;

    //Compressor compressor;
    LimeLight limeLight;

    int flywheelMotor1Port, flywheelMotor2Port, flywheelMotor3Port;
    int conveyorMotorPort;

    ControlScheme currentScheme;

    Climber climber; 
    
    Shooter flywheel;
    Intake intake;
    //IntakePneumatics intakePneumatics;
    RunAuton runAuton;

    final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
    final int SMALL_JOYSTICK_PORT = 2;

    CANdleSystem candle;

    private final String allianceColor = DriverStation.getAlliance().toString();

    

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        updateManager = new UpdateManager(
                robotContainer.getDrivetrainSubsystem()
        );
        updateManager.startLoop(5.0e-3);

        dontDriveCommand = new DontDriveCommand(robotContainer.getDrivetrainSubsystem());

        runAuton = new RunAuton(limeLight, flywheel, intake, robotContainer.getDrivetrainSubsystem(), allianceColor);
        
        

        

        flywheel = new Shooter(61, 11, 3);

        climber = new Climber(6);

        currentScheme = new ArcadeDrive(XBOX_PORT, XBOX_PORT + 1);

        intake = new Intake(15, 1, 31);

        candle = new CANdleSystem();

        //intakePneumatics = new IntakePneumatics(0, 1);

        //compressor = new Compressor(PneumaticsModuleType.CTREPCM);
        limeLight = new LimeLight();

        //candle = new CANdleSystem();
        
        //drive = robotContainer.getDrivetrainSubsystem();

        //runAuton = new RunAuton(limeLight, flywheel, intake, robotContainer.getDrivetrainSubsystem(), allianceColor);
        // candle.changeAnimation(AnimationTypes.TwinkleOff);

 
    }

    @Override
    public void robotPeriodic() {
        //compressor.enableDigital();
        CommandScheduler.getInstance().run();

        SmartDashboard.putNumber("Running Robot", 1);
        currentScheme.shootSequence(flywheel, intake);
        currentScheme.intakeSequence(flywheel, intake);
        // currentScheme.flywheel(flywheel);           
        // currentScheme.intakeConveyer(intake);
        //currentScheme.intakePneumatics(intakePneumatics);
        
        currentScheme.limeLightDrive(limeLight, robotContainer.getDrivetrainSubsystem());

        currentScheme.candle(candle);

        currentScheme.climber(climber);

        currentScheme.colorSensor();

        SmartDashboard.putNumber("Gyro Angle", robotContainer.getGyroAngle());
        SmartDashboard.putNumber("CAN Test", Constants.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR);

        
    }

    @Override
    public void autonomousInit() {
        robotContainer.getDrivetrainSubsystem().resetRotationsZero();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        CommandScheduler.getInstance().setDefaultCommand( (Subsystem) robotContainer.getDrivetrainSubsystem(), (Command) dontDriveCommand);
        
        
    }

    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
        //CommandScheduler.getInstance().setDefaultCommand(subsystem, defaultCommand);

        runAuton.actuallyRunAutonTheMethod();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
        currentScheme.climber(climber);
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().setDefaultCommand( (Subsystem) robotContainer.getDrivetrainSubsystem(), robotContainer.getDefaultCommand());
    }
}
