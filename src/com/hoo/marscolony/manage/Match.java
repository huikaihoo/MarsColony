package com.hoo.marscolony.manage;

import com.hoo.marscolony.MainActivity.ScoreAdapter;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Spinner;

public class Match extends CountDownTimer{
	
	private final String TAG = "Match"; 
	private final int[][] scoreBase = { {40, 20}, {20, 40}, {20, 20}, {100, 50}};
	private int[][] numBalls = { {0, 0}, {0, 0}, {0, 0}, {0, 0}};
	
	private MenuItem mtimer, mscore;
	private int timeleft;
	private ScoreAdapter sa;
	private Boolean showClock;
	
	public Match(MenuItem timer, MenuItem score)
	{
		super(300000, 1000);
		showClock = true;
		mtimer = timer;
		mscore = score;
		timeleft = 300;
	}
	
    public Match(long millisInFuture, long countDownInterval) {     
        super(millisInFuture, countDownInterval);     
    }
    
    public void updateMenuItem(MenuItem timer, MenuItem score)
    {
		mtimer = timer;
		mscore = score;
		sa = (ScoreAdapter)((Spinner) mscore.getActionView()).getAdapter();
    }

    public CountDownTimer onStart()
    {
    	if (sa == null)
    	{
    		sa = (ScoreAdapter)((Spinner) mscore.getActionView()).getAdapter();
    	}
    	timeleft = 300;
		for (int i=0; i<4; i++)
			for (int j=0; j<2; j++)
				numBalls[i][j] = 0;
		sa.notifyDataSetChanged();
    	return start();
    }
    
    @Override     
    public void onFinish() {
    	mtimer.setTitle("0:00");
    	timeleft = 0;
    	sa.notifyDataSetChanged();
    }
    
    @Override     
    public void onTick(long millisUntilFinished) { 
    	if (showClock){
    		mtimer.setTitle(millisUntilFinished/60000 + ":" + String.format("%02d", (millisUntilFinished/1000)%60));
    	}else {
    		mtimer.setTitle("" + millisUntilFinished/1000);
    	}
    	timeleft = (int)millisUntilFinished/1000;
    	sa.notifyDataSetChanged();
    }
    
    public int getFieldScore(){
    	
    	int fieldScore = 0;
    	
		for (int i=0; i<4; i++)
			for (int j=0; j<2; j++)
				fieldScore += numBalls[i][j]*scoreBase[i][j];
    	
		return fieldScore;
    }
    
    public int changeBallNum(int n, int x, int y)
    {
    	if (n==0)
    		return numBalls[x][y];
    	numBalls[x][y] += n;
    	if (numBalls[x][y] < 0){
    		numBalls[x][y] = 0;
    	}
    	sa.notifyDataSetChanged();
    	return numBalls[x][y];
    }
    
    public int getScoreBase(int x, int y)
    {
    	return scoreBase[x][y];
    }
    
    public int getScore (int i)
    {
    	switch (i)
    	{
    		case 0:
    			return getFieldScore();
    		case 1:
    			return timeleft;
    		default:
    			return getFieldScore() + timeleft;
    	}
    }
    
    public CountDownTimer reset()
    {
    	Log.w(TAG, "reset()");
    	timeleft = 300;
		for (int i=0; i<4; i++)
			for (int j=0; j<2; j++)
				numBalls[i][j] = 0;
		if (sa != null){
			sa.notifyDataSetChanged();
		}
		mtimer.setTitle("5:00");
    	return this;
    }
    
    public void toggleTimer ()
    {
    	showClock = !showClock;
    	if (showClock){
    		mtimer.setTitle(timeleft/60 + ":" + String.format("%02d", timeleft%60));
    	}else {
    		mtimer.setTitle("" + timeleft);
    	}
    }
    
}
