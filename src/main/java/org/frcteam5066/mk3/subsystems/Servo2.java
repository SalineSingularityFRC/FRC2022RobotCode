package org.frcteam5066.mk3.subsystems;

import edu.wpi.first.wpilibj.Servo;

public class Servo2 {

    Servo servo;

    double targetingAngle = 37.0;
    double intakeAngle = 21.2;
    double centerOffset = 50;

    public Servo2(int channel){
        servo = new Servo(channel);
    }

    public void toTargetingAngle(){
        servo.setAngle(centerOffset + targetingAngle);
    }

    public void toIntakeAngle(){
        servo.setAngle(centerOffset - intakeAngle);
    }

    public double getServoAngle(){
        return servo.getAngle();
    }


    
    
}
