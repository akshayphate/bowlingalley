package com.bowling.alley.storage;

import com.bowling.alley.model.Bowler;
import com.bowling.alley.model.Score;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;


public interface StorageInterface {
     // Database and table creation
     void createDatabase();
     void createTables();
     // Storing and retrieving bowler(s) details
     void putBowlerInfo(String nick_name, String name, String email) throws IOException;
     Bowler getBowlerInfo(String nick_name) throws FileNotFoundException;
     void addBowlersToDB();
     Vector<String> getBowlers() throws FileNotFoundException;
     // Adding new parties
     int numberOfParties();
     void addParty(int party_id, String party_name) throws IOException;
     // Storing score of a player
     void addScore(String nick_name, int score, int party_id, int game_num, int strikes, int gutters);
     // Retrieving score details of a bowler
     Vector<Score> getScores(String nick_name) throws FileNotFoundException;
     int getHighestScoreOfBowler(String nick_name) throws FileNotFoundException;
     int getLowestScoreOfBowler(String nick_name) throws FileNotFoundException;
     int getStrikesOfBowler(String nick_name) throws FileNotFoundException;
     int getGuttersOfBowler(String nick_name) throws FileNotFoundException;
     int getStrikesOfBowlerScore(String nick_name,int score) throws FileNotFoundException;
     int getGuttersOfBowlerScore(String nick_name,int score,int strikes);
     // Retrieving top bowlers
     Vector<String> getTopBowlers() throws IOException;
}
