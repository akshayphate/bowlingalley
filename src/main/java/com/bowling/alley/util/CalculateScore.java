package com.bowling.alley.util;


import com.bowling.alley.model.Bowler;
import com.bowling.alley.publisher.Lane;

public class CalculateScore {
    private final Lane lane;

    public CalculateScore(Lane lane) {
        this.lane = lane;
    }

    /**
     * getScore()
     * <p>
     * Method that calculates a bowlers score
     *
     * @param cur   The bowler that is currently up
     * @param frame The frame the current bowler is on
     * @return The bowlers total score
     */
    public int getScore(Bowler cur, int frame) {
        int[] curScore = lane.getScores().get(cur);
        int strikeballs = 0;
        int totalScore = 0;

        for (int i = 0; i != 10; i++) {
            lane.getCumulScores()[lane.getBowlIndex()][i] = 0;
        }

        int current = 2 * (frame - 1) + lane.getBall() - 1;

        //Iterate through each ball until the current one.
        for (int i = 0; i != current + 2; i++) {
            //Spare:
            if (i % 2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19) {
                //This ball was a the second of a spare.
                //Also, we're not on the current ball.
                //Add the next ball to the ith one in cumul.
                lane.getCumulScores()[lane.getBowlIndex()][(i / 2)] += curScore[i + 1] + curScore[i];
                if (i > 1) {
                    //cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 -1];
                }
            } else if (i < current && i % 2 == 0 && curScore[i] == 10 && i < 18) {
                strikeballs = 0;
                //This ball is the first ball, and was a strike.
                //If we can get 2 balls after it, good add them to cumul.
                if (curScore[i + 2] != -1) {
                    strikeballs = 1;
                    if (curScore[i + 3] != -1) {
                        //Still got em.
                        strikeballs = 2;
                    } else if (curScore[i + 4] != -1) {
                        //Ok, got it.
                        strikeballs = 2;
                    }
                }

                if (strikeballs == 2) {
                    //Add up the strike.
                    //Add the next two balls to the current cumulscore.
                    lane.getCumulScores()[lane.getBowlIndex()][i / 2] += 10;

                    if (curScore[i + 1] != -1) {
                        lane.getCumulScores()[lane.getBowlIndex()][i / 2] += curScore[i + 1] + lane.getCumulScores()[lane.getBowlIndex()][(i / 2) - 1];

                        if (curScore[i + 2] != -1) {
                            if (curScore[i + 2] != -2) {
                                lane.getCumulScores()[lane.getBowlIndex()][(i / 2)] += curScore[i + 2];
                            }
                        } else {
                            if (curScore[i + 3] != -2) {
                                lane.getCumulScores()[lane.getBowlIndex()][(i / 2)] += curScore[i + 3];
                            }
                        }
                    } else {
                        if (i / 2 > 0) {
                            lane.getCumulScores()[lane.getBowlIndex()][i / 2] += curScore[i + 2] + lane.getCumulScores()[lane.getBowlIndex()][(i / 2) - 1];
                        } else {
                            lane.getCumulScores()[lane.getBowlIndex()][i / 2] += curScore[i + 2];
                        }

                        if (curScore[i + 3] != -1) {
                            if (curScore[i + 3] != -2) {
                                lane.getCumulScores()[lane.getBowlIndex()][(i / 2)] += curScore[i + 3];
                            }
                        } else {
                            lane.getCumulScores()[lane.getBowlIndex()][(i / 2)] += curScore[i + 4];
                        }
                    }
                } else {
                    break;
                }
            } else {
                //We're dealing with a normal throw, add it and be on our way.
                if (i % 2 == 0 && i < 18) {
                    if (i / 2 == 0) {
                        //First frame, first ball.  Set his cumul score to the first ball
                        if (curScore[i] != -2) {
                            lane.getCumulScores()[lane.getBowlIndex()][i / 2] += curScore[i];
                        }
                    } else if (i / 2 != 9) {
                        //add his last frame's cumul to this ball, make it this frame's cumul.
                        if (curScore[i] != -2) {
                            lane.getCumulScores()[lane.getBowlIndex()][i / 2] += lane.getCumulScores()[lane.getBowlIndex()][i / 2 - 1] + curScore[i];
                        } else {
                            lane.getCumulScores()[lane.getBowlIndex()][i / 2] += lane.getCumulScores()[lane.getBowlIndex()][i / 2 - 1];
                        }
                    }
                } else if (i < 18) {
                    if (curScore[i] != -1 && i > 2) {
                        if (curScore[i] != -2) {
                            lane.getCumulScores()[lane.getBowlIndex()][i / 2] += curScore[i];
                        }
                    }
                }

                if (i / 2 == 9) {
                    if (i == 18) {
                        lane.getCumulScores()[lane.getBowlIndex()][9] += lane.getCumulScores()[lane.getBowlIndex()][8];
                    }

                    if (curScore[i] != -2) {
                        lane.getCumulScores()[lane.getBowlIndex()][9] += curScore[i];
                    }
                } else if (i / 2 == 10) {
                    if (curScore[i] != -2) {
                        lane.getCumulScores()[lane.getBowlIndex()][9] += curScore[i];
                    }
                }
            }
        }

        return totalScore;
    }
}