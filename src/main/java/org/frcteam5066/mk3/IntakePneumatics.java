package org.frcteam5066.mk3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;


public class IntakePneumatics{

	DoubleSolenoid leftDoubleSolenoid;
	DoubleSolenoid s1;
	DoubleSolenoid s2;

	
	//left and right looking at the robot from the front

    DoubleSolenoid doubleSolenoid;

    public IntakePneumatics(int forwardChannel, int reverseChannel){
        leftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
		s1= new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
		s2= new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 7);
		s1.set(DoubleSolenoid.Value.kReverse);
		s2.set(DoubleSolenoid.Value.kReverse);
		leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);

		//rightDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
    }
	
	public void setHigh() {
		leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
		//rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setLow() {
		leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
		//rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setOff() {
		leftDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
		//rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void intakeDeploy(){
		//I don't know how to do this yet
	}
}