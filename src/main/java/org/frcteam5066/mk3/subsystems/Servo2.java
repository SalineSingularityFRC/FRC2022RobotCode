package org.frcteam5066.mk3.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Servo2 {

    Servo servo;

    double targetingAngle = 37.0;
    double intakeAngle = 112;
    double centerOffset = 60;

    public Servo2(int channel){
        servo = new Servo(channel);
        SmartDashboard.putNumber("Targeting Servo Angle", 37);
        SmartDashboard.putNumber("Intake Servo Angle", 112);
        
    }

    //TODO Test Servo Positions

    public void toTargetingAngle(){
        //double angleTarget = SmartDashboard.getNumber("Targeting Servo Angle", 0);
        double angleTarget = targetingAngle;
        servo.setAngle(angleTarget);
        SmartDashboard.putNumber("Servo Position", servo.getAngle());
    }

    public void toIntakeAngle(){
        double angleIntake = intakeAngle;
        servo.setAngle(angleIntake);
        SmartDashboard.putNumber("Servo Position", servo.getAngle());
    }

    public double getServoAngle(){
        return servo.getAngle();
    }

    

    
    
}
