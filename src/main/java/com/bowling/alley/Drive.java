package main.java.com.bowling.alley;

import com.bowling.alley.db.DBUtil;

public class Drive {

	public static void main(String[] args) throws Exception {

//		int numLanes = 4;
//		int maxPatronsPerParty=5;
//
//		Alley a = new Alley( numLanes );
//		ControlDesk controlDesk = a.getControlDesk();
//
//		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
//		controlDesk.subscribe( cdv );

//		To create database and tables
//		DBInit dbInit = new DBInit();
//		dbInit.createDatabase();
//		dbInit.createTables();

//      To add players in db
		DBUtil dbUtil = new DBUtil();
		dbUtil.addBowlersToDB();
	}
}
