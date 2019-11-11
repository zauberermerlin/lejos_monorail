package lejos_monorail;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class RolltorLeuchte {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sound.setVolume(5);
		Sound.beep();

		// grünes Flackern
		Button.LEDPattern(4);

		Delay.msDelay(2000);
		
		
	}

}
