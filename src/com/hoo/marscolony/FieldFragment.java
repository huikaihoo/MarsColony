package com.hoo.marscolony;

import com.hoo.marscolony.manage.GameGrid;
import com.hoo.marscolony.manage.MatchManager;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

public class FieldFragment extends Fragment{
	
	private final String TAG = "FieldFragment";
	
	private static final int GRID_SIZE = 4;
	private static String[] GRID_TYPE = 
		{ "SHAREDWAYPOINT", "WAYPOINT3",
		  "WAYPOINT1", 		"WAYPOINT2"};
	
    public static final String ARG_BLANK = "blank";
    private RelativeLayout mainView;
    private View mLayout;
	private int screenWidth, screenHeight;
	private boolean isLandscape;
	private final int row = 2;
	private final int col = 2;
	private GameGrid[] fieldGrids;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        mLayout = (View) inflater.inflate(R.layout.fragment_field, container, false);
        
    	mainView = (RelativeLayout)mLayout.findViewById(R.id.main_wrapper);
    	mainView.post(new Runnable() {
    		public void run() {
    			Log.i(TAG, "post() starts");
    			MatchManager m = MatchManager.getManager();
    			Display display = getActivity().getWindowManager().getDefaultDisplay();
    			Point size = new Point();
    			display.getSize(size);
    			screenWidth = size.x;
    			screenHeight = size.y;
    			isLandscape = (screenWidth > screenHeight);
    			Log.w(TAG, "w = " + screenWidth + ", h = " + screenHeight );
    			//GRID_TYPE[1] =  "w = " + screenWidth + ", h = " + screenHeight;
    			if (isLandscape)
    			{
    				GRID_TYPE[0] = "WAYPOINT1";
    				GRID_TYPE[1] = "SHAREDWAYPOINT";
    				GRID_TYPE[2] = "WAYPOINT2";
    				GRID_TYPE[3] = "WAYPOINT3";
    			}
    			Resources r = getResources();
    	                             		float px = TypedValue.applyDimension(
    					TypedValue.COMPLEX_UNIT_DIP, 35, r.getDisplayMetrics());
    			Window win = getActivity().getWindow(); // Get the Window
    			int contentViewTop = win
    					.findViewById(Window.ID_ANDROID_CONTENT).getTop();
    			screenHeight -= contentViewTop;
    			int width = (int) ((screenWidth) / col);
    			int height = (int) ((screenHeight ) / row);
    			//int width = (int) ((screenWidth - (px * (col + 1))) / col);
    			//int height = (int) ((screenHeight - (px * (row + 1))) / row);

    			fieldGrids = new GameGrid[GRID_SIZE];
    			for (int x = 0; x < row; x++) {
    				for (int y = 0; y < col; y++) {
    					GameGrid fieldGrid = new GameGrid(mainView.getContext(), GRID_TYPE[y+x*col]);
    					fieldGrid.setSize(width, height);
    					fieldGrid.getView().setX(y * width);
    					fieldGrid.getView().setY(x * height);
    					//fieldGrid.getView().setX((y + 1) * px + y * width);
    					//fieldGrid.getView().setY((x + 1) * px + x * height);
    					fieldGrids[y + x * col] = fieldGrid;
    					mainView.addView(fieldGrid.getView());
    				}
    			}
    			//fieldGrids[0].getView().setOnClickListener(MainActivity.this);
    			m.pushGameGrid(fieldGrids);
    			Log.i(TAG, "post() ends");
    			
    		}
    	});
    	Log.i(TAG, "onCreateView() ends");
    	return mLayout;
    }
        
}
    

