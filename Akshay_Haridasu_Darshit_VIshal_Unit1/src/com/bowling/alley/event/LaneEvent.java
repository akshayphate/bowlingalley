package com.bowling.alley.event;
/*  $Id$
 *
 *  Revisions:
 *    $Log: LaneEvent.java,v $
 *    Revision 1.6  2003/02/16 22:59:34  ???
 *    added mechnanical problem flag
 *
 *    Revision 1.5  2003/02/02 23:55:31  ???
 *    Many many changes.
 *
 *    Revision 1.4  2003/02/02 22:44:26  ???
 *    More data.
 *
 *    Revision 1.3  2003/02/02 17:49:31  ???
 *    Modified.
 *
 *    Revision 1.2  2003/01/30 21:21:07  ???
 *    *** empty log message ***
 *
 *    Revision 1.1  2003/01/19 22:12:40  ???
 *    created laneevent and laneobserver
 *
 *
 */

import java.util.Map;

import com.bowling.alley.model.Bowler;
import com.bowling.alley.model.Party;

public class LaneEvent
{
	private Party p;
	private int frame;
	private int ball;
	private Bowler bowler;
	private int[][] cumulScore;
	private Map<Bowler, int[]> score;
	private int index;
	private int frameNum;
	private int[] curScores;
	private boolean mechProb;

	public LaneEvent(Party pty, int theIndex, Bowler theBowler, int[][] theCumulScore, Map<Bowler, int[]> theScore, int theFrameNum,
					 int[] theCurScores, int theBall, boolean mechProblem)
	{
		p = pty;
		index = theIndex;
		bowler = theBowler;
		cumulScore = theCumulScore;
		score = theScore;
		curScores = theCurScores;
		frameNum = theFrameNum;
		ball = theBall;
		mechProb = mechProblem;
	}

	public boolean isMechanicalProblem()
	{
		return mechProb;
	}

	public int getFrameNum()
	{
		return frameNum;
	}

	public Map<Bowler, int[]> getScore()
	{
		return score;
	}


	public int[] getCurScores()
	{
		return curScores;
	}

	public int getIndex() {
		return index;
	}

	public int getFrame()
	{
		return frame;
	}

	public int getBall()
	{
		return ball;
	}

	public int[][] getCumulScore()
	{
		return cumulScore;
	}

	public Party getParty()
	{
		return p;
	}

	public Bowler getBowler()
	{
		return bowler;
	}

}

