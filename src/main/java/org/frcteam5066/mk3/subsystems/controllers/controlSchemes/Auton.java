package org.frcteam5066.mk3.subsystems.controllers.controlSchemes;

//import javax.swing.text.Position;

//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;




public class Auton {

    SendableChooser<Integer> startingPositions = new SendableChooser<>();
    
    public Auton(){
        startingPositions.setDefaultOption("Position 1", 1);
        startingPositions.addOption("Position 2", 2);
        startingPositions.addOption("Position 3", 3);
        startingPositions.addOption("Position 4", 4);
    }
        


    
}
