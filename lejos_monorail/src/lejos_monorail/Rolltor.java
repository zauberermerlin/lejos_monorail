/**
 * Angelegt am 30.05.2018
 * Compilerung mit Java 7_u_80
 * 
 * 
 */
package lejos_monorail;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.utility.Delay;


/**
 * @author thomas
 *
 */
public class Rolltor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Sound.setVolume(5);
		Sound.beep();
		Button.LEDPattern(4);
		
		EV3MediumRegulatedMotor mittlererMotor = new EV3MediumRegulatedMotor(MotorPort.A);
		mittlererMotor.setSpeed(500);

		// Touch Sensor an Anschluss S1
//		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
//		touchSensor.setCurrentMode("Touch");
//		float[] button_ergebnis;
//		button_ergebnis = new float[1];

		
		
		// Ultraschall an Port s3
		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S3);
	
		
		
		float[] ir_ergebnis;
		ir_ergebnis = new float[1];
		
		
		int button_id;
		int beacon_id;
		
		// solange escape Button nicht gedrückt ist
		
//		LCD.clearDisplay();
//		LCD.drawString("Start", 1, 1);

		
		
		Button.LEDPattern(1);
		
		mittlererMotor.forward();
		
//		Runnable testlauf = new MotorImHintergrund();
//		Thread lauf = new Thread(testlauf);
//		lauf.start();
		
		while (Button.ESCAPE.isUp()) {
//			button_id = Button.waitForAnyPress();
			button_id = Button.getButtons();
			
			
			 // Kanal 1 der Beacon-Fernbedienung
			 beacon_id = irSensor.getRemoteCommand(0);
				LCD.clearDisplay();
				LCD.drawString("Beacon: " + beacon_id, 1, 1);
				Delay.msDelay(100);
			 
			 
			 switch (button_id) {
				case Button.ID_LEFT:
					LCD.clearDisplay();
					LCD.drawString("links", 1, 1);
					Button.LEDPattern(6);
					mittlererMotor.rotate(-3600);
					Button.LEDPattern(4);
					break;
	
				case Button.ID_RIGHT:
					LCD.clearDisplay();
					LCD.drawString("rechts", 1, 1);
					Button.LEDPattern(6);
					mittlererMotor.rotate(3600);
					Button.LEDPattern(4);
					break;
					
				default:
					break;
				} // Ende switch
			
//			 switch (beacon_id) {
//			 // links oben
//			 case 1:	
//				 LCD.clearDisplay();
//					LCD.drawString("Beacon: links oben", 1, 1);
//					mittlererMotor.rotate(-3600);
//				break;
//
//			// links unten
//			 case 2:
//				 LCD.clearDisplay();
//					LCD.drawString("Beacon: links unten", 1, 1);
//					mittlererMotor.rotate(-3600);
//				 break;
//				
//			default:
//				break;
//			} // Ende switch
			 
			
		} // Ende while
		
		
		
//		Wert: 1.0: Touch gedrückt
//		Wert: 0.0: Touch nicht gedrückt
		
		
		LCD.clearDisplay();
		LCD.drawString("Warte auf Touch ...", 1, 1);
		
//		do {
//			touchSensor.fetchSample(button_ergebnis, 0);
//		} while (button_ergebnis[0] == 0.0);
		
//		for (int i = 0; i < 6; i++) {
//			mittlererMotor.rotate(360);

//		}		

		
		
		irSensor.close();
		Sound.beep();		
		System.exit(0);
	} // Ende main-Funktion

}
