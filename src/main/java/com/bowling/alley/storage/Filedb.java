package com.bowling.alley.storage;

import java.util.Properties;


import java.util.Vector;
import com.bowling.alley.model.Bowler;
import com.bowling.alley.model.Score;
import java.io.*;

public class Filedb implements StorageInterface
{
	static String BOWLER_FILES,SCOREHISTORY_FILES,PARTY_FILES;
	public Filedb() throws IOException{
		ReadPropertyFile rpf = new ReadPropertyFile();
        Properties prop = rpf.getProperty();
        BOWLER_FILES = prop.getProperty("BOWLER_FILES");
        SCOREHISTORY_FILES = prop.getProperty("SCOREHISTORY_FILES");
        PARTY_FILES = prop.getProperty("PARTY_FILES");   
		//System.out.println(""+BOWLER_FILES);
	}

	@Override
	public void createDatabase() {

	}

	@Override
	public void createTables() {

	}

	public void putBowlerInfo(String nick_name, String name, String email) throws IOException {
        String data = nick_name + "\t" + name + "\t" + email + "\n";
		RandomAccessFile out = new RandomAccessFile(BOWLER_FILES, "rw");
        out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
    }
    public Bowler getBowlerInfo(String nickName) throws FileNotFoundException {
		try (BufferedReader in = new BufferedReader(new FileReader(BOWLER_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] bowler = data.split("\t");

				if (nickName.equals(bowler[0]))
				{
					System.out.println(
							"Nick: "
									+ bowler[0]
									+ " Full: "
									+ bowler[1]
									+ " email: "
									+ bowler[2]);

					return (new Bowler(bowler[0], bowler[1], bowler[2]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void addBowlersToDB() {

	}

	public Vector<String> getBowlers() throws FileNotFoundException {

		Vector<String> allBowlers = new Vector<>();

		try (BufferedReader in = new BufferedReader(new FileReader(BOWLER_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] bowler = data.split("\t");
				//"Nick: bowler[0] Full: bowler[1] email: bowler[2]
				allBowlers.add(bowler[0]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allBowlers;
	}

	@Override
	public int numberOfParties() {
		return 0;
	}

	public void addParty(int partyid, String partyname) throws IOException {
        String data = partyid + "\t" + partyname + "\n";
		RandomAccessFile out = new RandomAccessFile(PARTY_FILES, "rw");
        out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
    }

	@Override
	public void addScore(String nick_name, int score, int party_id, int game_num, int strikes, int gutters) {

	}

	public void addScore(String nick_name, String date, String score,String partyid, String strikes,String gutters) throws IOException, FileNotFoundException
    {
        String data = nick_name +  "\t" + date + "\t" + score + "\t" + partyid  + "\t" + strikes + "\t" + gutters +  "\n";
		RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_FILES, "rw");
		out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
    }  
    public Vector<Score> getScores(String nick) throws FileNotFoundException {
		Vector<Score> scores = new Vector<>();

		try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick.equals(scoredata[0]))
				{
					scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scores;
	}
    
    public int getHighestScoreOfBowler(String nick_name) throws FileNotFoundException {
		int highest_score = Integer.MIN_VALUE;
        Vector<Score> scores = getScores(nick_name);
		for(int i=0;i<scores.size();i++)
		{
			highest_score  = Math.max(highest_score, Integer.parseInt( scores.get(i).getScore()));
		}
		return highest_score;
    }
    public int getLowestScoreOfBowler(String nick_name) throws FileNotFoundException {
        int lowest_score = Integer.MAX_VALUE;
        Vector<Score> scores = getScores(nick_name);
		for(int i=0;i<scores.size();i++)
		{
			lowest_score  = Math.min(lowest_score, Integer.parseInt( scores.get(i).getScore()));
		}
		return lowest_score;
    }
    public int getStrikesOfBowler(String nick_name) throws FileNotFoundException {
		int strikes=0;
        try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick_name.equals(scoredata[0]))
				{
					strikes += Integer.parseInt( scoredata[4]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strikes;
    }
    public int getGuttersOfBowler(String nick_name) throws NumberFormatException, FileNotFoundException {
        int gutters=0;
        try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick_name.equals(scoredata[0]))
				{
					gutters += Integer.parseInt( scoredata[5]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gutters;
    }

    public int getStrikesOfBowlerScore(String nick_name,int score) throws FileNotFoundException {
        int strikes=0;
        try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick_name.equals(scoredata[0]) && score==Integer.parseInt(scoredata[2]))
				{
					strikes = Math.max(strikes,Integer.parseInt( scoredata[4]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strikes;
    }
    public int getGuttersOfBowlerScore(String nick_name, int score, int strikes) {
        int gutters=100000;
        try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_FILES)))
		{
			String data;

			while ((data = in.readLine()) != null)
			{
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick_name.equals(scoredata[0]) && score==Integer.parseInt(scoredata[2]) && strikes == Integer.parseInt(scoredata[4]))
				{
					gutters = Math.min(gutters,Integer.parseInt( scoredata[5]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gutters;
    }

	public Vector<String> getTopBowlers() throws IOException {
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

        Vector<String> nicks_strikes = new Vector<String>();
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
