package org.frcteam5066.mk3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

//there's no way of accessing the code to the motorcycle now. We're now using can to start up 
public class IntakePneumatics{

	/*
	DoubleSolenoid leftDoubleSolenoid;
	DoubleSolenoid s1;
	DoubleSolenoid s2;
	DoubleSolenoid s3;
*/
	
	//left and right looking at the robot from the front

    //DoubleSolenoid doubleSolenoid;

    public IntakePneumatics(int forwardChannel, int reverseChannel){
        /*leftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
		s1= new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
		s2= new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 5, 6);
		s1.set(DoubleSolenoid.Value.kReverse);
		s2.set(DoubleSolenoid.Value.kReverse);
		leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
		s3= new DoubleSolenoid(reverseChannel, PneumaticsModuleType.CTREPCM, 7, reverseChannel);
		s3.set(DoubleSolenoid.Value.kForward);
*/
		//rightDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, forwardChannel, reverseChannel);
    }
	
	public void setHigh() {
		//leftDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
		//rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setLow() {
		//leftDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
		//rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setOff() {
		//leftDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
		//rightDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

}