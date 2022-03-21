package com.bowling.alley.db;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;
import main.java.com.bowling.alley.model.Bowler;
import main.java.com.bowling.alley.model.Score;
import main.java.com.bowling.alley.util.BowlerFile;


public class DBUtil {
    String db_url, username, password;

    public DBUtil() throws Exception {
        ReadPropertyFile rpf = new ReadPropertyFile();
        Properties prop = rpf.getProperty();
        db_url = prop.getProperty("db_url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
    }

    public void addBowlersToDB() throws IOException, SQLException {
        Vector<Bowler> bowlers = BowlerFile.getBowlerObjs();
        for (Bowler b: bowlers){
            putBowlerInfo(b.getNickName(), b.getFullName(), b.getEmail());
//            System.out.println("Inserted bowler:" + b.getNickName());
        }
    }

    public void putBowlerInfo(String nick_name, String name, String email) throws SQLException
    {
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

    public void addScore(String nick_name, String date, String score,String party_id, String strikes,String gutters)
    {
        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO SCORES VALUES ('"+nick_name+"','"+Integer.parseInt(party_id)+"','"+Integer.parseInt(score)+"','"+Integer.parseInt(strikes)+"','"+Integer.parseInt(gutters)+"')";
            stmt.executeUpdate(sql);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public Vector<Score> getScores(String nick)
    {
        Vector<Score> scores = new Vector<>();

        try
        {
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql =  "SELECT * FROM SCORES WHERE nick_name='" + nick + "'";
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
            if( bow_strikes > strikes)
            {

                nicks_strikes = new Vector<String>();
                strikes=bow_strikes;
                nicks_strikes.add(nicks_score.get(i));
            }
            else if(bow_strikes==strikes)
                nicks_strikes.add(nicks_score.get(i));
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

