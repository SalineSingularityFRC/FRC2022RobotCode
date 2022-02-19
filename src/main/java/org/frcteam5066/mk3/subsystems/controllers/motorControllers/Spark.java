package org.frcteam5066.mk3.subsystems.controllers.motorControllers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frcteam5066.mk3.subsystems.controllers.MotorController;
import org.frcteam5066.mk3.subsystems.controllers.ControlScheme;


/**
 * Class written by 5066 to control Spark motor controllers.
 */
public class Spark implements MotorController {

    // Declare a single CANSparkMax motor controller.
    private CANSparkMax m_motor;

    private CANEncoder m_encoder;
    private CANPIDController m_pidController;

    //name of the mechanism
    String name;

    double initialPosition;
    double previousPosition, previousJoystick;
    boolean controlWithJoystick;

    boolean usingLimitSwitch, upperLimit;
    
    // When controlling by voltage, set the max voltage here
    private final double maxVoltage = 9.0;


    /**
     * Constructor for Spark class.
     * 
     * @param portNumber pass the CAN network ID for this motor controller
     * @param brushlessMotor pass true if using a neo brushless motor
     */
    public Spark(int portNumber, boolean brushlessMotor, double rampRate) {

        MotorType type;


        //Setting motor to either brushed or brushless
        if (brushlessMotor) {
            type = MotorType.kBrushless;
        }
        else {
            type = MotorType.kBrushed;
        }
        
        this.m_motor = new CANSparkMax(portNumber, type);
        this.setCoastMode(true);
        this.setRampRate(rampRate);
        this.m_encoder = m_motor.getEncoder();
        this.m_pidController = m_motor.getPIDController();
    }

    /**
     * Constructor for Spark class.
     * 
     * @param portNumber pass the CAN network ID for this motor controller
     * @param brushlessMotor pass true if using a neo brushless motor
     */
    public Spark(int portNumber, boolean brushlessMotor, double rampRate, String name, boolean useLimitSwitch, boolean useUpperLimitSwitch,
    double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut) {
        this(portNumber, brushlessMotor, rampRate);

        this.m_pidController = m_motor.getPIDController();

        //this.putConstantsOnDashboard(name, kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
        this.setPIDConstantsSilent(kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);

        //If intitialPosition = -100, lower limit switch has not been pressed.
        this.initialPosition = -100;
        this.previousPosition = -100;

        this.previousJoystick = 0.0;
        this.controlWithJoystick = false;

        this.name = name;

        this.usingLimitSwitch = useLimitSwitch;
        if (!useLimitSwitch) {
            this.setInitialPosition();
        }
        this.upperLimit = useUpperLimitSwitch;
    }

    /**
     * 
     * @return the object's instance of the motorController, m_motor
     */
    public CANSparkMax getMotorController() {
        return this.m_motor;
    }

    
    public void follow(MotorController baseController, boolean invert) {

        // Because a Spark object can only follow another Spark object, we must cast the parameter
        // baseController to be a Spark.
        this.m_motor.follow(((Spark)baseController).getMotorController(), invert);
    }

    public void setSpeed(double percentOutput) {
        this.m_motor.set(percentOutput);
    }

    
    public void setRampRate(double rampRate) {

        if (!this.m_motor.isFollower()) {
            this.m_motor.setOpenLoopRampRate(rampRate);
        }
    }

    public void setCurrentLimit(int currentLimit) {
        this.m_motor.setSmartCurrentLimit(currentLimit);
    }

    /**
     * Set the motor based on voltage compared to speed to get a more reliable result
     * @param power from -1 thru 1
     * multiplys the given power value by maximum voltage, in this case 9 volts
     */
    public void setPower(double power) {
        double voltageTarget = power * maxVoltage;

        //If what were setting to is greater than max votlage, set to max voltage 
        if(voltageTarget > maxVoltage) {
            voltageTarget = maxVoltage;
        }

        else if(voltageTarget < -maxVoltage) {
            voltageTarget = -maxVoltage;
        }

        this.m_motor.setVoltage(voltageTarget);
    }

    
    //setCoastMode sets the motor to either coast mode (true) or brake mode (false)
    public void setCoastMode(boolean coast) {

        if (coast) {
            this.m_motor.setIdleMode(IdleMode.kCoast);
        }
        else {
            this.m_motor.setIdleMode(IdleMode.kBrake);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //Encoder methods:


    //Put the current encoder values on the smart dashboard, adjusted for our inital position
    public void printEncoderPosition() {
        SmartDashboard.putNumber(name + " Encoder Value", this.getCurrentPosition());
        
    }

    public void watchEncoderWithJoystick(double percentOutput) {

        SmartDashboard.putBoolean(name + " lower limit switch", isUpperLimitPressed(true));
        if (isLowerLimitPressed(true)) {
            this.setInitialPosition();
        }

        this.printEncoderPosition();

        percentOutput = 0.05;

        SmartDashboard.putNumber(name + " percent output", percentOutput);
        this.setSpeed(percentOutput);
    }


    /**
     * Puts constants on the dashboard, possibly so they can be edited later with getConstantsFromDashboard()
     * 
     * @param name name of the mechanism we are controlling
     * @param kP constant of proportional control
     * @param kI constant of integral control
     * @param kD constant of derivative control
     * @param kIZ constant of IZone (whatever that is)
     * @param kFF constant of FeedvForward
     * @param kMinOut constant of minimum output for the motor in percent voltage (never lower than -1)
     * @param kMaxOut constant of maximum output for the motor in percent voltage (never more that +1)
     */
    public void putConstantsOnDashboard(String name, double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut) {

        this.m_pidController.setP(kP, 0);
        this.m_pidController.setI(kI, 0);
        this.m_pidController.setD(kD, 0);
        this.m_pidController.setIZone(kIZ, 0);
        this.m_pidController.setFF(kFF, 0);
        this.m_pidController.setOutputRange(kMinOut, kMaxOut, 0);
        

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber(name + " P Gain", kP);
        SmartDashboard.putNumber(name + " I Gain", kI);
        SmartDashboard.putNumber(name + " D Gain", kD);
        SmartDashboard.putNumber(name + " I Zone", kIZ);
        SmartDashboard.putNumber(name + " Feed Forward", kFF);
        SmartDashboard.putNumber(name + " Max Output", kMaxOut);
        SmartDashboard.putNumber(name + " Min Output", kMinOut);

    }

    public void setPIDConstantsSilent( double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut) {
        this.m_pidController.setP(kP, 0);
        this.m_pidController.setI(kI, 0);
        this.m_pidController.setD(kD, 0);
        this.m_pidController.setIZone(kIZ, 0);
        this.m_pidController.setFF(kFF, 0);
        this.m_pidController.setOutputRange(kMinOut, kMaxOut, 0);
    }

    
    /**
     * Update PID constants from the dashboard (primarily for testing purposes)
     */
    public void getConstantsFromDashboard() {

        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber(this.name + " P Gain", 0);
        double i = SmartDashboard.getNumber(this.name + " I Gain", 0);
        double d = SmartDashboard.getNumber(this.name + " D Gain", 0);
        double iz = SmartDashboard.getNumber(this.name + " I Zone", 0);
        double ff = SmartDashboard.getNumber(this.name + " Feed Forward", 0);
        double max = SmartDashboard.getNumber(this.name + " Max Output", 0);
        double min = SmartDashboard.getNumber(this.name + " Min Output", 0);
        //double rotations = SmartDashboard.getNumber("Elevator Set Rotations", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if ((p != this.m_pidController.getP())) {
            this.m_pidController.setP(p, 0);
        }
        if((i != this.m_pidController.getI())) {
            this.m_pidController.setI(i, 0);
        }
        if((d != this.m_pidController.getD())) {
            this.m_pidController.setD(d, 0);
        }
        if((iz != this.m_pidController.getIZone())) {
            this.m_pidController.setIZone(iz, 0);
        }
        if((ff != this.m_pidController.getFF())) {
            this.m_pidController.setFF(ff, 0);
        }
        if((max != this.m_pidController.getOutputMax()) || (min != this.m_pidController.getOutputMin())) { 
            this.m_pidController.setOutputRange(min, max, 0);
        }
    }

    

    /**
     * returns state of limit switch (may need to be made more reusable)
     */
    public boolean isLowerLimitPressed(boolean normallyOpen) {
        if (normallyOpen) {
            return m_motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
        }
        return m_motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed).get();
    }

    /**
     * returns state of second limit switch (may need to be made more reusable)
     * @param normallyOpen true if limit switch often is not pressed (I think)
     * @return state of limit switch (true if in unexpected state (maybe))
     */
    public boolean isUpperLimitPressed(boolean normallyOpen) {
        if (normallyOpen) {
            return m_motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
        }
        return m_motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed).get();
    }

    /**
     * sets initial position to m_encoder.getPosition()
     * Call this method when a limit switch is pressed, because then we know where the mechanism is
     */
    public void setInitialPosition() {
        this.initialPosition = this.m_encoder.getPosition();
        SmartDashboard.putNumber("init Po", this.initialPosition);
    }

    /**
     * gets the valueas from the encoders
     * @return Encoder Position in inches
     */
    public double getCurrentPosition() {
        
        if(initialPosition != -100) {
            return this.m_encoder.getPosition() - this.initialPosition;
        }

        return m_encoder.getPosition();
        
    }

    public double getVelocity(){
        return 0.0;
    }



    /**
     * Sets the motor to the designated encoder postition
     * @param joystickControl if the encoder has not been set yet it will move via joystick control
     * @param position the place where to set the target postiton to
     * @param feedForward The amount of feed forward to give the motor, measured in volts (hopefully)
     */
    public void setToPosition(double joystickControl, double position, double feedForward) {

        //getting the updated pid values from the smartdashboard, then prints the postion to the smartdashboard
        this.getConstantsFromDashboard();// eventually comment this out
        this.printEncoderPosition();
        SmartDashboard.putNumber(name + " intended numerical position", position);

        
        
        
        // if the lower limit switch is pressed it means we are at the bottom which is setting the encoder postiton to zero
        if (this.usingLimitSwitch) {

            boolean limitSwitchPressed = this.isLowerLimitPressed(true);
        
            if (upperLimit) {
                limitSwitchPressed = this.isUpperLimitPressed(true);
            }

            SmartDashboard.putBoolean(name + " lower limit switch", limitSwitchPressed);
            if (limitSwitchPressed) {
                this.setInitialPosition();
            }
        }

        //Takes in the joystick value and makes sure it is above the threshold value set in singdrive
        //SET DISCRETELY NOW BECAUSE SINGDRIVE NO LONGER EXISTS
        joystickControl = 0.05;
        
        //Making sure the joystick value is only changed once with if/else statement
        if (joystickControl != previousJoystick) {
            controlWithJoystick = true;
        }

        else if (position != previousPosition) {
            controlWithJoystick = false;
        }

        /*
        Inital Position defaults to -100, so if it still is -100, we havn't hit the bottom limit switch yet
        We don't want the encoder to move if we don't know where the motor actually is, so it will use joystick control until we
        hit the bottom limit switch.
        */
        if (this.initialPosition == -100) {
            DriverStation.reportWarning("We do not know the position of the " + name +
            "; Please move this mechanism to a limit switch manually", true);
            SmartDashboard.putNumber(name + " initial position", initialPosition);
            controlWithJoystick = true;
        }

        //If we are moving with the encoder, set encoder target position to our position we want it to be + the inital position the encoder thinks we are at
        if (!controlWithJoystick) {
            //this.m_pidController.setReference(position + this.initialPosition, ControlType.kPosition, 0);
            this.m_pidController.setReference(position + this.initialPosition, ControlType.kPosition, 0, feedForward);
        }
        //Else control the motor with the joystick
        else {
            this.m_motor.set(joystickControl);
        }

        previousJoystick = joystickControl;
        previousPosition = position;
    }

    /**
     * sets velocity of the motor 
     * @param velocity Velocity in RPM
     */
    public void setVelocity(double velocity) {

        this.m_pidController.setReference(velocity, ControlType.kVelocity);
    }

    public void setSmartMotion(double velocity) {
        this.m_pidController.setReference(velocity, ControlType.kSmartMotion);
    }

    public void setSmartMotionMaxVel(double maxVel) {
        this.m_pidController.setSmartMotionMaxVelocity(maxVel, 0);
    }

    public void setSmartMotionConstants(double maxVel, double minVel, double maxAcc, double allowedErr, int slot) {
    m_pidController.setSmartMotionMaxVelocity(maxVel, slot);
    m_pidController.setSmartMotionMinOutputVelocity(minVel, slot);
    m_pidController.setSmartMotionMaxAccel(maxAcc, slot);
    m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, slot);
    }
    
    public double getRotationsSpun(){
        return m_motor.getEncoder().getPosition();
    }

    public void resetRotationsZero(){
        m_motor.getEncoder().setPosition(0.0);
    }
    
    public double getMotorRPM() {
        // TODO Auto-generated method stub
        return 0;
    }
}
