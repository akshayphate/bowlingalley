package com.bowlingalley.alley;

import com.bowling.alley.publisher.ControlDesk;

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
