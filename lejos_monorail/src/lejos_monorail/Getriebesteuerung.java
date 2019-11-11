package lejos_monorail;

import lejos.ev3.tools.LCDDisplay;
import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class Getriebesteuerung {

	public static void main(String[] args) {

		Sound.setVolume(15);
		Sound.beep();
		LCD.drawInt(Battery.getVoltageMilliVolt(), 0, 2);
		
		final int GRUEN_AN = 1;
		final int ROT_AN = 2;
		final int ORANGE_AN = 3;
		
		final int GRUEN_FLACKERN = 4;
		final int ROT_FLACKERN = 5;
		final int ORANGE_FLACKERN = 6;
		
		final int POSITION_BLINKLICHT = 1;
		final int POSITION_LINKE_ZUGSTEUERUNG = 2;
		final int POSITION_RECHTE_ZUGSTEUERUNG = 3;
		final int POSITION_LINKES_TOR = 4;
		final int POSITION_RECHTES_TOR = 5;
			
		final int BEACON_ESCAPE = 9;
		final int BEACON_LINKS_HOCH = 1;
		final int BEACON_LINKS_RUNTER = 2;
		
		final int BEACON_RECHTS_HOCH = 3;
		final int BEACON_RECHTS_RUNTER = 4;
		
		Boolean links_auf = false;
		Boolean links_zu = false;
		
		Boolean rechts_auf = false;
		Boolean rechts_zu = false;
		
		
		Button.LEDPattern(ORANGE_FLACKERN);

		// Start immer mit Position 1
		// 2. Gang/Position mit Pfeil oben
		int position = 1;
		int gangUmdrehung = 115;
		int antriebUmdrehung = 12 * 360;
		int geschwindigkeitRolltor = 1400;
		int geschwindigkeitGaragenLeuchte = 100;
		int umdrehungGaragenLeuchte = 135;
		
//		IllegalArgumentException
		EV3IRSensor irSensor = null;
		try {
			irSensor = new EV3IRSensor(SensorPort.S4);			
		} catch (IllegalArgumentException e) {
			LCD.clear();
			LCD.drawString("Fehler -IR Sensor", 0, 0);
			// rotes Dauerlicht
			Button.LEDPattern(ROT_AN);
			Delay.msDelay(5000);
			Sound.beepSequenceUp();
			System.exit(0);
		}

		// rechter TouchSensor
		EV3TouchSensor touchSensorRechts = null;
		try {
			touchSensorRechts = new EV3TouchSensor(SensorPort.S1);
			LCD.clear();
			LCD.drawString(touchSensorRechts.getName(), 0, 0);
		} catch (Exception e) {
			LCD.clear();
			LCD.drawString("Fehler Touch-Sensor", 0, 0);
			// rotes Dauerlicht
			Button.LEDPattern(2);
			Delay.msDelay(5000);
			Sound.beepSequenceUp();
			System.exit(0);
		}
		float[] touchSampleRechts;
		touchSampleRechts = new float[touchSensorRechts.sampleSize()];


		// linker TouchSensor
		EV3TouchSensor touchSensorLinks = null;
		try {
			touchSensorLinks = new EV3TouchSensor(SensorPort.S2);
			LCD.clear();
			LCD.drawString(touchSensorLinks.getName(), 0, 0);
		} catch (Exception e) {
			LCD.clear();
			LCD.drawString("Fehler Touch-Sensor", 0, 0);
			// rotes Dauerlicht
			Button.LEDPattern(2);
			Delay.msDelay(5000);
			Sound.beepSequenceUp();
			System.exit(0);
		}
		float[] touchSampleLinks;
		touchSampleLinks = new float[touchSensorRechts.sampleSize()];

		

		EV3MediumRegulatedMotor positionsMotor = new EV3MediumRegulatedMotor(MotorPort.A);
		EV3MediumRegulatedMotor antriebsMotor = new EV3MediumRegulatedMotor(MotorPort.B);
		
		
		// Fernbedienung Beacon auf Kanal 1
		// linker Bereich: Position vor / zurück
		// pro Gang: 115 Grad
		
		// rechter Bereich: Schrittweite 100 plus/minus
		
		// Abbruch mit Escape-Taste auf Beacon
		
		Boolean ende = true;
		Sound.beep();
		// grünes Flackern
		Button.LEDPattern(GRUEN_FLACKERN);
		
		while (ende) {
			
			switch (irSensor.getRemoteCommand(0)) {
			// PositionsMotor
			case 1:
				if (position!=5) {
					// oranges Flackern
					Button.LEDPattern(6);
					positionsMotor.rotate(gangUmdrehung);
					position++;
					LCD.clear();
					LCD.drawString("Position: " + position, 0, 0);
					Delay.msDelay(250);
				} else {
					// rotes Flackern
					Button.LEDPattern(5);
				}
				break;

			// PositionsMotor zurück
			case 2:
				if (position!=1) {
					// oranges Flackern
					Button.LEDPattern(6);
					positionsMotor.rotate(-1 * gangUmdrehung);
					position--;
					LCD.clear();
					LCD.drawString("Position: " + position, 0, 0);
					Delay.msDelay(250);					
				} else {
					// rotes Flackern
					Button.LEDPattern(5);
				}
					
				break;
			
				
			/*
			 * Antriebsmotor
			 * 
			 * je nach Position unterschiedliche Aktionen
			 * 
			 * 
			 */
			case BEACON_RECHTS_HOCH:
				LCD.clear();
				LCD.drawString("Position: " + position, 0, 0);				

					switch (position) {
					case 1:
						// GaragenLeuchte blinken
						antriebsMotor.setSpeed(geschwindigkeitGaragenLeuchte);
						antriebsMotor.rotate(-1 * umdrehungGaragenLeuchte);
						break;
	
					case 2:
						
						break;
	
					case 3:
						
						break;	

					// wenn TouchSensor gedrückt, dann rotes Blinken und keine Aktion
					// rechtes Tor
					case 4:

						// Wert 0.0 = nicht gedrückt
						// Wert 1.0 = gedrückt
						touchSensorRechts.fetchSample(touchSampleRechts, 0);
						if (touchSampleRechts[0] == 0.0) {
							// Tor hoch
							antriebsMotor.setSpeed(geschwindigkeitRolltor);
							antriebsMotor.rotate(-1 * antriebUmdrehung);
							Delay.msDelay(250);
						} else {
							Button.LEDPattern(ROT_FLACKERN);
						}

						break;
					
						// linkes Tor
					case 5:

						// Wert 0.0 = nicht gedrückt
						// Wert 1.0 = gedrückt
						touchSensorLinks.fetchSample(touchSampleRechts, 0);
						if (touchSampleRechts[0] == 0.0) {
							// Tor hoch
							antriebsMotor.setSpeed(geschwindigkeitRolltor);
							antriebsMotor.rotate(-1 * antriebUmdrehung);
							Delay.msDelay(250);
						} else {
							Button.LEDPattern(ROT_FLACKERN);
						}

						
						break;
					} // Ende innere Switch-Anweisung
//				antriebsMotor.rotate(360);
//				Delay.msDelay(250);
				break;
				
			// Antriebsmotor
			case BEACON_RECHTS_RUNTER:
				LCD.clear();
				LCD.drawString("Position: " + position, 0, 0);				

					switch (position) {
					case 1:
						// GaragenLeuchte ausschalten
						antriebsMotor.setSpeed(geschwindigkeitGaragenLeuchte);
						antriebsMotor.rotate(umdrehungGaragenLeuchte);
						break;
	
					case 2:
						
						break;
	
					case 3:
						
						break;	

					// wenn TouchSensor gedrückt, dann rotes Blinken und keine Aktion
					// rechtes Tor
					case 4:

						// Wert 0.0 = nicht gedrückt
						// Wert 1.0 = gedrückt
						touchSensorRechts.fetchSample(touchSampleRechts, 0);
						if (touchSampleRechts[0] == 0.0) {
							// Tor runter
							antriebsMotor.setSpeed(geschwindigkeitRolltor);
							antriebsMotor.rotate(antriebUmdrehung);
							Delay.msDelay(250);
						} else {
							Button.LEDPattern(ROT_FLACKERN);
						}
						break;
					
					// linkes Tor
					case 5:

						// Wert 0.0 = nicht gedrückt
						// Wert 1.0 = gedrückt
						touchSensorLinks.fetchSample(touchSampleLinks, 0);
						if (touchSampleLinks[0] == 0.0) {
							// Tor runter
							antriebsMotor.setSpeed(geschwindigkeitRolltor);
							antriebsMotor.rotate(antriebUmdrehung);
							Delay.msDelay(250);
						} else {
							Button.LEDPattern(ROT_FLACKERN);
						}

						
						
						break;
					} // Ende innere Switch-Anweisung

//				antriebsMotor.rotate(-360);
//				Delay.msDelay(250);
				break;
				
				
				
			// Programm wird beendet
			case BEACON_ESCAPE:
				ende = false;
				
				// PositionsMotor auf Position 1 fahren
				positionsMotor.rotate((1-position) * gangUmdrehung);	
					
				break;
			
			default:
				Delay.msDelay(250);
				break;
			} // Ende switch
			
			// grünes Flackern
			Button.LEDPattern(GRUEN_FLACKERN);
			
		} // Ende while-Schleife
		
		
		irSensor.close();
		touchSensorRechts.close();
		positionsMotor.close();
		antriebsMotor.close();

		Sound.beep();
		
	} // Ende main Funktion

}
