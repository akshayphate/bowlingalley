package com.bowling.alley;

import com.bowling.alley.model.Alley;
import com.bowling.alley.publisher.ControlDesk;
import com.bowling.alley.storage.SQLdb;
import com.bowling.alley.storage.StorageInterface;
import com.bowling.alley.view.ControlDeskView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Drive {

	public static void main(String[] args) throws Exception {

		String resourceName = "application.properties"; // could also be a constant
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int numLanes = Integer.parseInt(props.getProperty("numLanes"));
		int numFrames = Integer.parseInt(props.getProperty("numFrames"));
		int maxPatronsPerParty= Integer.parseInt(props.getProperty("maxPatronsPerParty"));

//		Initializing DB
//		StorageInterface storage = new SQLdb();
//		storage.createDatabase();
//		storage.createTables();

//		Adding players in db
//		storage.addBowlersToDB();

		Alley a = new Alley( numLanes, numFrames );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );
	}
}
