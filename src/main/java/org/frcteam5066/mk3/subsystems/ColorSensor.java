package org.frcteam5066.mk3.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;



public class ColorSensor {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    private String allianceColor = DriverStation.getAlliance().toString();
    private int proximity = m_colorSensor.getProximity();

    //private final Color kBlueTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    //private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);//change values later(accuracy)
    private final Color kRedTarget = new Color(0.561, 0.232, 0.114);//change values later(accuracy)
    //look up robotPeriodic and Init
    
    public ColorSensor() {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
    }

    public boolean robotColor(){
        String colorString;
        Boolean sameColor = true; 
        Color detectedColor = m_colorSensor.getColor();
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
        proximity = m_colorSensor.getProximity();

        if (match.color == kBlueTarget) {
            colorString = "Blue";
        } else if (match.color == kRedTarget) {
            colorString = "Red";
        } else {
            colorString = "Unknown";
        }

        if (!colorString.equalsIgnoreCase(allianceColor) && proximity > 96) {
            sameColor = false;
        }

        SmartDashboard.putNumber("Proximity", proximity);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putString("Detected Color", colorString);
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putString("Alliance Color", allianceColor);
        SmartDashboard.putBoolean("Ball color is the same as alliance color", sameColor);

        return sameColor;
    }

    public boolean hasBall(){
        //AUTON TESTING MODIFY SPOT proximity is how far a thing is away from the color sensor. 
        //The exact number to tell if a ball is in the robot may need to be adjusted
        if(proximity > 110) return true;
        return false;
    }

    public void initTeamColor(){
        allianceColor = DriverStation.getAlliance().toString();
    }
}