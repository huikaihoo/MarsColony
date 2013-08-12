package com.hoo.marscolony.manage;

import android.view.MenuItem;

public class MatchManager {
	private static MatchManager m;
	private Match currMatch;
	private MenuItem mtimer, mscore;
	private Boolean isRotate;
	private int currState; // 0: not start; 1: started; 2:end
	private GameGrid[] fieldGrids;
	
	public MatchManager(MenuItem timer, MenuItem score) {
		mtimer = timer;
		mscore = score;
		isRotate = false;
		currState = 0;
	}
	
	public synchronized static 
	MatchManager getManager(MenuItem timer, MenuItem score) {
		if (m == null) {
			m = new MatchManager(timer, score);
		}		
		return m;
	}
	public synchronized static MatchManager getManager() {
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
	
	public int getCurrState()
	{
		return currState;
	}
	
	public void setCurrState(int num)
	{
		currState = num;
	}
	
	public void pushGameGrid (GameGrid[] g)
	{
		fieldGrids = g;
	}
	public GameGrid[] getGameGrid()
	{
		return fieldGrids;
	}
}