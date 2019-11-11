package lejos_monorail;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Ventilator extends Thread{

	public void run() {
		EV3MediumRegulatedMotor VentilatorMotor = new EV3MediumRegulatedMotor(MotorPort.D);
		VentilatorMotor.setSpeed(100);
		VentilatorMotor.forward();
	}
	
}
