package main.java.com.bowling.alley;

import main.java.com.bowling.alley.model.Alley;
import main.java.com.bowling.alley.publisher.ControlDesk;
import main.java.com.bowling.alley.view.ControlDeskView;

public class Drive {

	public static void main(String[] args) {

		int numLanes = 4; 
		int maxPatronsPerParty=5;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
