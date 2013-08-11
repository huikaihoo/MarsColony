package com.hoo.marscolony.manage;

import com.hoo.marscolony.MainActivity.ScoreAdapter;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Spinner;

public class Match extends CountDownTimer{
	
	private final String TAG = "Match"; 
	
	private MenuItem mtimer, mscore;
	private int timeleft, fieldscore;
	private ScoreAdapter sa;
	private Boolean showClock;
	
	public Match(MenuItem timer, MenuItem score)
	{
		super(300000, 1000);
		showClock = true;
		mtimer = timer;
		mscore = score;
		timeleft = 300;
		fieldscore = 0;
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
		fieldscore = 0;
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
    
    public int getScore (int i)
    {
    	switch (i)
    	{
    		case 0:
    			return fieldscore;
    		case 1:
    			return timeleft;
    		default:
    			return fieldscore + timeleft;
    	}
    }
    
    public CountDownTimer reset()
    {
    	Log.w(TAG, "reset()");
    	timeleft = 300;
		fieldscore = 0;
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
