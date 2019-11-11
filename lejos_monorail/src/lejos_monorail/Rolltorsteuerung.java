package lejos_monorail;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class Rolltorsteuerung extends Thread{

	// Klassenvariablen
	Boolean hoch_stop = false;
	Boolean runter_stop = false;
	
	Boolean stop = false;
	
	Boolean laufrichtung_hoch = false;
	Boolean laufrichtung_runter = true;
	
	EV3MediumRegulatedMotor ev3Motor;
	EV3TouchSensor ev3Touch;
	
	Boolean b_amLeben = true;
	
	/**
	 * Konstruktor
	 * @param EV3Mediummotor
	 * @param EV3TouchSensor
	 * 
	 */
	public Rolltorsteuerung(EV3MediumRegulatedMotor ev3Motor, EV3TouchSensor ev3Touch) {
		this.ev3Motor=ev3Motor;
		this.ev3Touch=ev3Touch;
	}

	public void run() {

		float[] touchSample;
		touchSample = new float[ev3Touch.sampleSize()];
		
		
		while (b_amLeben) {
			Delay.msDelay(250);
			ev3Touch.fetchSample(touchSample, 0);

			// Wert 0.0 = nicht gedrückt
			// Wert 1.0 = gedrückt
			
			if (touchSample[0] == 1.0) {
				stop = true;
			} // Ende if

			if (touchSample[0] == 0.0) {
				stop = false;
			} // Ende if
			
		} // Ende while
		ev3Touch.close();
	} // Ende run-Funktion


	public Boolean getb_amLeben() {
		return b_amLeben;
	}

	public void setb_amLeben(Boolean b_amLeben) {
		this.b_amLeben = b_amLeben;
	}

	/**
	 * @return Wurde der Berührungssensor ausgelöst
	 * @return Boolean
	 * 
	 */
	public Boolean getGedrueckt() {
		return stop;
	}
	
} // Ende Klasse
