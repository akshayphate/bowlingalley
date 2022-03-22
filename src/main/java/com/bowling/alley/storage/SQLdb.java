package com.bowling.alley.storage;

import com.bowling.alley.model.Bowler;
import com.bowling.alley.model.Score;
import com.bowling.alley.util.BowlerFile;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;


public class SQLdb implements StorageInterface{

    String conn_url, db_url, username, password;

    public SQLdb() throws Exception {
        ReadPropertyFile rpf = new ReadPropertyFile();
        Properties prop = rpf.getProperty();
        conn_url = prop.getProperty("conn_url");
        db_url = prop.getProperty("db_url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
    }

    public void createDatabase() {
        try
        {
            String connectURL = conn_url + "/?user=" + username +"&password=" + password;
            Connection conn = DriverManager.getConnection(connectURL);
            conn.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS BOWLING_ALLEY");
            System.out.println("Created Database...");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void createTables() {
        try{
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS BOWLERS " +
                    " (nick_name VARCHAR(255), " +
                    " full_name VARCHAR(255), " +
                    " email VARCHAR(255), " +
                    " PRIMARY KEY ( nick_name ))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS PARTY " +
                    " (party_id INTEGER NOT NULL, " +
                    " party_name VARCHAR(255), " +
                    " PRIMARY KEY ( party_id ))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS SCORES " +
                    " (nick_name VARCHAR(255), " +
                    " party_id INTEGER not NULL, " +
                    " game_num INTEGER not NULL," +
                    " score INTEGER not NULL, " +
                    " strikes INTEGER not NULL, " +
                    " gutters INTEGER not NULL, " +
                    " PRIMARY KEY ( nick_name,party_id,game_num ), " +
                    " FOREIGN KEY (nick_name) REFERENCES BOWLERS(nick_name), "+
                    " FOREIGN KEY (party_id) REFERENCES PARTY(party_id))";

            stmt.executeUpdate(sql);
            System.out.println("Created tables in the database...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void putBowlerInfo(String nick_name, String name, String email) {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO BOWLERS VALUES ('"+nick_name+"','"+name+"','"+email+"')";
            stmt.executeUpdate(sql);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public Bowler getBowlerInfo(String nick_name)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM BOWLERS WHERE nick_name='" + nick_name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return (new Bowler(rs.getString("nick_name"),rs.getString("full_name"),rs.getString("email")));
            }
            else
                return null;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public void addBowlersToDB() {
        Vector<Bowler> bowlers = null;
        try {
            bowlers = BowlerFile.getBowlerObjs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Bowler b: bowlers){
            putBowlerInfo(b.getNickName(), b.getFullName(), b.getEmail());
        }
    }

    public Vector<String> getBowlers()  {
        Vector<String> allBowlers = new Vector<>();

        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);
            Statement stmt = conn.createStatement();
            String sql = "SELECT nick_name FROM BOWLERS";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                allBowlers.add(rs.getString("nick_name"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return allBowlers;
    }

    public int numberOfParties(){
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM PARTY";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public void addParty(int party_id, String party_name)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO PARTY VALUES ('"+party_id+"','"+party_name+"')";
            stmt.executeUpdate(sql);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void addScore(String nick_name, int score, int party_id, int game_num, int strikes, int gutters)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO SCORES VALUES ('"+nick_name+"','"+party_id+"','"+game_num+"','"+score+"','"+strikes+"','"+gutters+"')";
            stmt.executeUpdate(sql);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public Vector<Score> getScores(String nick_name)
    {
        Vector<Score> scores = new Vector<>();

        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql =  "SELECT * FROM SCORES WHERE nick_name='" + nick_name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                scores.add(new Score(rs.getString("score"),rs.getString("strikes"),rs.getString("gutters")));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return scores;
    }

    public int getHighestScoreOfBowler(String nick_name)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT MAX(score) FROM SCORES WHERE nick_name='" + nick_name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public int getLowestScoreOfBowler(String nick_name)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT MIN(score) FROM SCORES WHERE nick_name='" + nick_name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public int getStrikesOfBowler(String nick_name)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT SUM(strikes) FROM SCORES WHERE nick_name='" + nick_name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public int getGuttersOfBowler(String nick_name)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT SUM(gutters) FROM SCORES WHERE nick_name='" + nick_name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public int getStrikesOfBowlerScore(String nick_name,int score)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT MAX(strikes) FROM SCORES WHERE nick_name='" + nick_name + "' AND score='" + score + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public int getGuttersOfBowlerScore(String nick_name,int score,int strikes)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT MIN(gutters) FROM SCORES WHERE nick_name='" + nick_name + "' AND score='" + score +  "' AND strikes='" + strikes +"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(!rs.wasNull())
            {
                return Integer.parseInt(rs.getString(1));
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    public Vector<String> getTopBowlers()
    {
        Vector<String> bowlers = getBowlers();
        Vector<String> nicks_score = new Vector<String>();
        int score=0;

        for(int i=0;i<bowlers.size();i++)
        {
            int bow_score = getHighestScoreOfBowler(bowlers.get(i));
            if( bow_score > score)
            {
                nicks_score = new Vector<String>();
                score=bow_score;
                nicks_score.add(bowlers.get(i));
            }
            else if(bow_score==score)
                nicks_score.add(bowlers.get(i));
        }

        //System.out.println(""+nicks_score);

        Vector<String> nicks_strikes = null;
        int strikes=0;

        for(int i=0;i<nicks_score.size();i++)
        {
            int bow_strikes = getStrikesOfBowlerScore(nicks_score.get(i),score);
            nicks_strikes = new Vector<String>();
            if( bow_strikes > strikes)
            {
                nicks_strikes = new Vector<String>();
                strikes=bow_strikes;
                nicks_strikes.add(nicks_score.get(i));
            }
            else if(bow_strikes==strikes) {
                nicks_strikes.add(nicks_score.get(i));
            }
            System.out.println(""+bow_strikes);
        }

        //System.out.println(""+nicks_strikes);

        Vector<String> nicks_gutters = new Vector<String>();
        int gutters=100000;

        for(int i=0;i<nicks_strikes.size();i++)
        {
            int bow_gutters = getGuttersOfBowlerScore(nicks_strikes.get(i),score,strikes);
            if( bow_gutters < gutters)
            {
                nicks_gutters = new Vector<String>();
                gutters=bow_gutters;
                nicks_gutters.add(nicks_strikes.get(i));
            }
            else if(bow_gutters==gutters)
                nicks_gutters.add(nicks_strikes.get(i));
        }
        //System.out.println(""+nicks_gutters);
        return nicks_gutters;
    }
}
