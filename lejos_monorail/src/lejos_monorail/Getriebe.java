/**
 * 
 */
package lejos_monorail;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

/**
 * @author thomas
 *
 */
public class Getriebe {

	// reiner Antrieb
	EV3MediumRegulatedMotor antriebsMotor;
	// stellt die Position ein
	EV3MediumRegulatedMotor positionsMotor;
	
	int geschwindigkeitAntriebsMotor;
	int geschwindigkeitPositionsMotor;
	
	int schrittweiteAntriebsMotor;
	int schrittweitePositionsMotor;
	
	int gesamtUmdrehungenAntriebsMotor = 0;
	int gesamtUmdrehungenPositionsMotor = 0;
	
	int aktuellePosition = 1;
	int maxPosition = 5;
	
	public EV3MediumRegulatedMotor getAntriebsMotor() {
		return antriebsMotor;
	}


	public void setAntriebsMotor(EV3MediumRegulatedMotor antriebsMotor) {
		this.antriebsMotor = antriebsMotor;
	}


	public EV3MediumRegulatedMotor getPositionsMotor() {
		return positionsMotor;
	}


	public void setPositionsMotor(EV3MediumRegulatedMotor positionsMotor) {
		this.positionsMotor = positionsMotor;
	}


	public int getGeschwindigkeitAntriebsMotor() {
		return geschwindigkeitAntriebsMotor;
	}


	public void setGeschwindigkeitAntriebsMotor(int geschwindigkeitAntriebsMotor) {
		this.geschwindigkeitAntriebsMotor = geschwindigkeitAntriebsMotor;
	}


	public int getGeschwindigkeitPositionsMotor() {
		return geschwindigkeitPositionsMotor;
	}


	public void setGeschwindigkeitPositionsMotor(int geschwindigkeitPositionsMotor) {
		this.geschwindigkeitPositionsMotor = geschwindigkeitPositionsMotor;
	}


	public int getSchrittweiteAntriebsMotor() {
		return schrittweiteAntriebsMotor;
	}


	public void setSchrittweiteAntriebsMotor(int schrittweiteAntriebsMotor) {
		this.schrittweiteAntriebsMotor = schrittweiteAntriebsMotor;
	}


	public int getSchrittweitePositionsMotor() {
		return schrittweitePositionsMotor;
	}


	public void setSchrittweitePositionsMotor(int schrittweitePositionsMotor) {
		this.schrittweitePositionsMotor = schrittweitePositionsMotor;
	}


	public int getGesamtUmdrehungenAntriebsMotor() {
		return gesamtUmdrehungenAntriebsMotor;
	}


	public void setGesamtUmdrehungenAntriebsMotor(int gesamtUmdrehungenAntriebsMotor) {
		this.gesamtUmdrehungenAntriebsMotor = gesamtUmdrehungenAntriebsMotor;
	}


	public int getGesamtUmdrehungenPositionsMotor() {
		return gesamtUmdrehungenPositionsMotor;
	}


	public void setGesamtUmdrehungenPositionsMotor(int gesamtUmdrehungenPositionsMotor) {
		this.gesamtUmdrehungenPositionsMotor = gesamtUmdrehungenPositionsMotor;
	}


	public Getriebe() {
		// Standardwerte setzen
		setzeStandardWerte();
		
	}
	
	
	/**
	 * Zeigt den Status der beiden Motoren an:
	 * 
	 * 
	 * 
	 */
	public void statusAnzeige() {
		LCD.clear();
		LCD.drawString("Motor   -umdreh", 0, 0);
		LCD.drawString("Antrieb -" + gesamtUmdrehungenAntriebsMotor, 0, 1);
		LCD.drawString("Position-" + gesamtUmdrehungenPositionsMotor, 0, 2);
	}
	
	/**
	 * Standardwerte werden gesetzt
	 * AntriebsMotor = PortA
	 * PositionsMOtor = PortB
	 * geschwindigkeitAntriebsMotor = 400
	 * geschwindigkeitPositionsMotor = 400
	 * schrittweiteAntriebsMotor = 100
	 * schrittweitePositionsMotor = 100
	 * 
	 */
	public void setzeStandardWerte() {
		EV3MediumRegulatedMotor antriebsMotor = new EV3MediumRegulatedMotor(MotorPort.A);
		EV3MediumRegulatedMotor positionsMotor = new EV3MediumRegulatedMotor(MotorPort.B);
		
		geschwindigkeitAntriebsMotor = 400;
		geschwindigkeitPositionsMotor = 400;
		
		schrittweiteAntriebsMotor = 100;
		schrittweitePositionsMotor = 100;

	}
	
	/*
	 * Position 1: Tor 1
	 * Position 2: Tor 2
	 * Position 3: ZugSteuerung 1
	 * Position 4: Zugsteuerung 2
	 * Position 5: blinkende Lampe
	 * 
	 * 
	 */
	
	
	
	public Boolean gehezuposition(int position) {
		this.antriebsMotor.rotate(position * 360);
		return true;
	}
	
	public void gehezuposition1 () {
		
	}
	
	public void gehezuposition2 () {
		
	}
	
	public void gehezuposition3 () {
		
	}
	
	public void gehezuposition4 () {
		
	}
	
	public void gehezuposition5 () {
		
	}
	
	
	
}
