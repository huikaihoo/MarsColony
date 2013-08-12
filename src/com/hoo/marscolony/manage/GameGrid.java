package com.hoo.marscolony.manage;

import com.hoo.marscolony.FieldFragment;
import com.hoo.marscolony.R;
import com.hoo.marscolony.R.color;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameGrid implements OnClickListener, OnCheckedChangeListener{
	
	private View view;
	private TextView text;
	private TableLayout table;
	private int id;

	public GameGrid(Context context, String str) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//if (str.startsWith("WAYPOINT"))
		//{
		if (str.startsWith("WAYPOINT"))	
			view = inflater.inflate(R.layout.grid_waypoint, null, false);
		else 
			view = inflater.inflate(R.layout.grid_sharepoint, null, false);
		
			table = (TableLayout) view.findViewById(R.id.table);
			switch (str.charAt(8))
			{
				case '1':
					table.setBackgroundResource(color.blue);
					id = 0;
					view.setTag(0);
					break;
				case '2':
					table.setBackgroundResource(color.orange);
					id = 1;
					view.setTag(1);
					break;
				case '3':
					table.setBackgroundResource(color.purple);
					id = 2;
					view.setTag(2);
					break;
				default:
					table.setBackgroundResource(color.yellow);
					id  = 3;
					view.setTag(3);
			}
				

			text = (TextView) view.findViewById(R.id.text);
			text.setText(str);
			
			// Set number of ball and Score
			Match currMatch;
			MatchManager m = MatchManager.getManager();
			if (m == null)
				return;
			else
				currMatch = m.getcurrMatch();
			text = (TextView) view.findViewById(R.id.red_num);
			text.setText(""+currMatch.changeBallNum(0, id, 0));
			text = (TextView) view.findViewById(R.id.blue_num);
			text.setText(""+currMatch.changeBallNum(0, id, 1));
			text = (TextView) view.findViewById(R.id.red_each_score);
			text.setText(""+currMatch.getScoreBase(id, 0));
			text = (TextView) view.findViewById(R.id.blue_each_score);
			text.setText(""+currMatch.getScoreBase(id, 1));
		if (str.startsWith("WAYPOINT"))	
		{
			// Set button listener
			Button b = (Button) view.findViewById(R.id.red_increase);
			b.setTag(view);
			b.setOnClickListener(GameGrid.this);
			b = (Button) view.findViewById(R.id.red_decrease);
			b.setTag(view);
			b.setOnClickListener(GameGrid.this);
			b = (Button) view.findViewById(R.id.blue_increase);
			b.setTag(view);
			b.setOnClickListener(GameGrid.this);
			b = (Button) view.findViewById(R.id.blue_decrease);
			b.setTag(view);
			b.setOnClickListener(GameGrid.this);
		}else
		{
			Switch b = (Switch) view.findViewById(R.id.toggle_100);
			b.setTag(view);
			if (currMatch.changeBallNum(0, id, 0) == 1)
				b.setChecked(true);
			else
				b.setChecked(false);
			b.setOnCheckedChangeListener(GameGrid.this);
			b = (Switch) view.findViewById(R.id.toggle_50);
			b.setTag(view);
			if (currMatch.changeBallNum(0, id, 1) == 1)
				b.setChecked(true);
			else
				b.setChecked(false);
			b.setOnCheckedChangeListener(GameGrid.this);
		}

	}
	
	public void updateScore()
	{
		Match currMatch;
		MatchManager m = MatchManager.getManager();
		if (m == null)
			return;
		else
			currMatch = m.getcurrMatch();
		
		text = (TextView) view.findViewById(R.id.red_num);
		text.setText(""+currMatch.changeBallNum(0, id, 0));
		text = (TextView) view.findViewById(R.id.blue_num);
		text.setText(""+currMatch.changeBallNum(0, id, 1));
		text = (TextView) view.findViewById(R.id.red_each_score);
		text.setText(""+currMatch.getScoreBase(id, 0));
		text = (TextView) view.findViewById(R.id.blue_each_score);
		text.setText(""+currMatch.getScoreBase(id, 1));
		
		if (id == 3){
			Switch b = (Switch) view.findViewById(R.id.toggle_100);
			if (currMatch.changeBallNum(0, id, 0) == 1)
				b.setChecked(true);
			else
				b.setChecked(false);
			b = (Switch) view.findViewById(R.id.toggle_50);
			if (currMatch.changeBallNum(0, id, 1) == 1)
				b.setChecked(true);
			else
				b.setChecked(false);
		}
	}

	public View getView() {
		return view;
	}
	
	public int getId(){
		return id;
	}
	
	public void setSize(int width, int height) {
		view.setLayoutParams(new LayoutParams(width, height));
	}
	
	@Override
	public void onClick(View buttonView) {
		
		Match currMatch;
		MatchManager m = MatchManager.getManager();
		if (m == null)
			return;
		else
			currMatch = m.getcurrMatch();
			
		
		View gameGridView = (View) buttonView.getTag();
		TextView scoreText;
		int gameGridId = (Integer) gameGridView.getTag();
		int factor, ball, updatedScore;
		
		switch (buttonView.getId())
		{
			case R.id.red_increase:
				ball = 0;
				factor = 1;
				scoreText = (TextView) gameGridView.findViewById(R.id.red_num);
				break;
			case R.id.red_decrease:
				ball = 0;
				factor = -1;
				scoreText = (TextView) gameGridView.findViewById(R.id.red_num);
				break;
			case R.id.blue_increase:
				ball = 1;
				factor = 1;
				scoreText = (TextView) gameGridView.findViewById(R.id.blue_num);
				break;
			case R.id.blue_decrease:
				ball = 1;
				factor = -1;
				scoreText = (TextView) gameGridView.findViewById(R.id.blue_num);
				break;
			default:
				gameGridId = ball = factor = 0;
				scoreText = (TextView) gameGridView.findViewById(R.id.text);
		}
		
		updatedScore = currMatch.changeBallNum(factor, gameGridId, ball);
		if (updatedScore != -1)
		{
			scoreText.setText(""+updatedScore);
		}
	}
	
	@Override
    public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
		Match currMatch;
		MatchManager m = MatchManager.getManager();
		if (m == null)
			return;
		else
			currMatch = m.getcurrMatch();
			
		
		View gameGridView = (View) toggleButton.getTag();
		TextView scoreText;
		int gameGridId = (Integer) gameGridView.getTag();
		int factor, ball, updatedScore;
		
		if (isChecked)
			factor = 1;
		else
			factor = -1;
		
		switch (toggleButton.getId())
		{
			case R.id.toggle_100:
				ball = 0;
				scoreText = (TextView) gameGridView.findViewById(R.id.red_num);
				break;
			case R.id.toggle_50:
				ball = 1;
				scoreText = (TextView) gameGridView.findViewById(R.id.blue_num);
				break;
			default:
				gameGridId = ball = factor = 0;
				scoreText = (TextView) gameGridView.findViewById(R.id.text);
		}
		
		updatedScore = currMatch.changeBallNum(factor, gameGridId, ball);
		if (updatedScore != -1)
		{
			scoreText.setText(""+updatedScore);
		}
    }
}
