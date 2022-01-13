package org.frcteam5066.mk3;

import edu.wpi.first.wpilibj.DoubleSolenoid;


public class DrivePneumatics{

    DoubleSolenoid doubleSolenoid;

    public DrivePneumatics(int forwardChannel, int reverseChannel){
        doubleSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
    }
	
	public void setHigh() {
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setLow() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setOff() {
		doubleSolenoid.set(DoubleSolenoid.Value.kOff);
	}

}