package lejos_monorail;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class RolltorManuell {
	
	public static void main(String[] args) {
		Sound.setVolume(5);
		Sound.beep();

		Boolean hoch_stop = false;
		Boolean runter_stop = false;
		
		// grünes Flackern
		Button.LEDPattern(4);
		
		EV3MediumRegulatedMotor mittlererMotor = new EV3MediumRegulatedMotor(MotorPort.A);
		mittlererMotor.setSpeed(500);
		
		// irSensor an Port 3
		// TouchSensor an Port1
		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S4);
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		
		Boolean bLauf = true;
		
		Rolltorsteuerung rolltorLinks = new Rolltorsteuerung(mittlererMotor, touchSensor);
		rolltorLinks.start();
		
		LCD.clear();
		LCD.drawString("Start",	0 ,0);
		LCD.drawString("Speed: " + mittlererMotor.getSpeed(), 0, 1);
		LCD.drawString("max. Speed: " + mittlererMotor.getMaxSpeed(), 0, 2);
		
		// grünes Dauerlicht
		Button.LEDPattern(1);

//		Ventilator ventilator = new Ventilator();
//		ventilator.start();
		
		while (bLauf) {

			LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt(), 0, 4);
			LCD.drawString("hoch_stop: " + hoch_stop + "  ", 0, 5);
			LCD.drawString("runter_stop: " + runter_stop + "  ", 0, 6);
			
			// Abfrage, welche Taste am Beacon gedrückt wurde
			// auf Kanal 1
			switch (irSensor.getRemoteCommand(0)) {
				// Taste: links oben
				// Rolltor hoch
				case 1:
	
					LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt(), 0, 4);
					LCD.drawString("hoch_stop: " + hoch_stop + "  ", 0, 5);
					LCD.drawString("runter_stop: " + runter_stop + "  ", 0, 6);
	
					// Wenn keine Berührung vorliegt
					if (!rolltorLinks.getGedrueckt()) {
						// oranges Flackern
						Button.LEDPattern(6);
	
						hoch_stop = false;
						runter_stop = false;
	
						mittlererMotor.backward();
						// wird benötigt, damit Motor länger als 1 ms Läuft und es im Dauerlauf keine
						// Unterbrechnung gibt
						while (irSensor.getRemoteCommand(0) == 1) {
							if (rolltorLinks.getGedrueckt()) {
								mittlererMotor.stop();
								LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt() + "  ", 0, 4);
								hoch_stop = true;
								LCD.drawString("hoch_stop: " + hoch_stop, 0, 5);
								LCD.drawString("runter_stop: " + runter_stop, 0, 6);
							}
							Delay.msDelay(500);
						} // Ende while
						mittlererMotor.stop();
					} // Ende if
	
					// Ist gedrückt
					if (rolltorLinks.getGedrueckt()) {
						LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt(), 0, 4);
						LCD.drawString("hoch_stop: " + hoch_stop + "  ", 0, 5);
						LCD.drawString("runter_stop: " + runter_stop + "  ", 0, 6);
	
						// Abfrage welcher Stop liegt vor: hoch oder runter ???
						// Abfrage muss in zwei separaten if-Abfragen gelöst werden
						if (hoch_stop == true) {
							// rotes Flackern
							Button.LEDPattern(5);
							Delay.msDelay(250);
						}
	
						if (runter_stop == true) {
							// oranges Flackern
							Button.LEDPattern(6);
							mittlererMotor.backward();
							while (irSensor.getRemoteCommand(0) == 1) {
								if (!rolltorLinks.getGedrueckt()) {
									LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt() + "  ", 0, 4);
									hoch_stop = false;
									runter_stop = false;
									LCD.drawString("hoch_stop: " + hoch_stop, 0, 5);
									LCD.drawString("runter_stop: " + runter_stop, 0, 6);
								}
								Delay.msDelay(250);
							}
							mittlererMotor.stop();
						} else {
							LCD.drawString("Fehler", 0, 6);
						}
					} // Ende if-else
	
					break;
		
				// Taste: links unten
				// Rolltor runter
				case 2:
					// Wenn keine Berührung vorliegt
					
					LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt(), 0, 4);
					LCD.drawString("hoch_stop: " + hoch_stop + "  ", 0, 5);
					LCD.drawString("runter_stop: " + runter_stop + "  ", 0, 6);

					
					if (!rolltorLinks.getGedrueckt()) {
							// oranges Flackern
							Button.LEDPattern(6);
	
							runter_stop = false;
							hoch_stop = false;
							
							mittlererMotor.forward();
								// wird benötigt, damit Motor länger als 1 ms Läuft und es im Dauerlauf keine
								// Unterbrechnung gibt
								while (irSensor.getRemoteCommand(0) == 2) {
									if (rolltorLinks.getGedrueckt()) {
										mittlererMotor.stop();
										LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt(), 0, 4);
										runter_stop = true;
										LCD.drawString("hoch_stop: " + hoch_stop + "  ", 0, 5);
										LCD.drawString("runter_stop: " + runter_stop + "  ", 0, 6);
									}
									Delay.msDelay(500);
								} // Ende while
							mittlererMotor.stop();
					} 
					
					if (rolltorLinks.getGedrueckt()) {
						
						LCD.drawString("gedrueckt: " + rolltorLinks.getGedrueckt(), 0, 4);
						LCD.drawString("hoch_stop: " + hoch_stop + "  ", 0, 5);
						LCD.drawString("runter_stop: " + runter_stop + "  ", 0, 6);
						
						// Abfrage welcher Stop liegt vor: hoch oder runter ???
						// Alles NICHT in einer Abfrage, sondern in zweien
						if (runter_stop == true) {
							// rotes Flackern
							Button.LEDPattern(5);
							Delay.msDelay(250);
						}
						
						if (hoch_stop == true) {
							// oranges Flackern
							Button.LEDPattern(6);
							mittlererMotor.forward();
								while (irSensor.getRemoteCommand(0) == 2) {
									Delay.msDelay(250);
								}
							mittlererMotor.stop();
						} else {
							LCD.drawString("Fehler         ", 0, 6);
						}
					} // Ende if-else

					
					
					break;
				
					// Escape-Taste bzw. mittlere, obere Taste
					// Ende des Programms
				case 9:	
					// oranges Flackern
					Button.LEDPattern(6);
					// Abfrage Beacon wird beendet
					bLauf = false;
					Delay.msDelay(100);	
					break;
					
					
				default:
					Delay.msDelay(100);
					break;
		
				} // Ende switch
		
			// grünes Dauerlicht
			Button.LEDPattern(1);
			
			
			switch(irSensor.getRemoteCommand(1)) {

			// Taste: links oben
			// Geschwindigkeit erhöhren in 100
			case 1:
				
				if (mittlererMotor.getSpeed() < mittlererMotor.getMaxSpeed()) {
					mittlererMotor.setSpeed(mittlererMotor.getSpeed() + 100);
					LCD.drawString("Speed: " + mittlererMotor.getSpeed() + "  ", 0, 1);
					Delay.msDelay(200);
					}
				break;
			
			// Taste: links unten
			// Geschwindigkeit reduzieren in 100
			case 2:
				if (mittlererMotor.getSpeed() > 100) {
					mittlererMotor.setSpeed(mittlererMotor.getSpeed() - 100);
					LCD.drawString("Speed: " + mittlererMotor.getSpeed() + "  ", 0, 1);
					Delay.msDelay(200);
				}
				break;
			
			} // Ende switch 2 (das mit der Geschwindigkeit
			
			
			
		} // Ende while Schleife		
		
		// grünes Flackern
		Button.LEDPattern(0);

		touchSensor.close();
		irSensor.close();
		mittlererMotor.close();
		Sound.beep();
		
		// Programm beendet sich nicht, wenn noch einzelne Threads laufen
		
//		System.exit(0);
		// Thread wird beendet
		rolltorLinks.setb_amLeben(false);
		
	} // Ende Main Funktion

	/**
	 * @param strRichtung
	 *            hoch/runter
	 */
	public Boolean rolltor(String strRichtung) {

		// nur wenn hoch oder runter übergeben wurden
		if (strRichtung.equals("hoch") || strRichtung.equals("runter")) {
		/*
		 * Ablaufplan:
		 * Garagentor fährt so lange in die angegebene Rochtung bis a) Stop erreicht oder b) Taste nicht mehr gedrückt wird
		 * 
		 * 
		 * 
		 * Was vorgegeben bzw. errechnet werden kann.
		 * Fahrtrichtung und
		 * 	
		 */
			
			
			return true;
		} else {
			return false;
		}
	} // Ende Funktion rolltor
	
} // Ende Klasse
