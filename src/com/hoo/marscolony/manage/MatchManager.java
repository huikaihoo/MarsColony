package com.hoo.marscolony.manage;

import android.view.MenuItem;

public class MatchManager {
	private static MatchManager m;
	private Match currMatch;
	private MenuItem mtimer, mscore;
	private Boolean isRotate;
	
	public MatchManager(MenuItem timer, MenuItem score) {
		mtimer = timer;
		mscore = score;
		isRotate = false;
	}
	
	public synchronized static 
	MatchManager getManager(MenuItem timer, MenuItem score) {
		if (m == null) {
			m = new MatchManager(timer, score);
		}		
		return m;
	}
	
	
	public Match getcurrMatch()
	{
		if (currMatch == null){		
			return  currMatch = new Match(mtimer, mscore);
		}
		return currMatch;
	}
	
	public Boolean getIsRotate()
	{
		return isRotate;
	}
	
	public void toggleIsRotate()
	{
		isRotate = !isRotate;
	}
}