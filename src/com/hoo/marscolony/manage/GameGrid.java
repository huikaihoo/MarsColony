package com.hoo.marscolony.manage;

import com.hoo.marscolony.R;
import com.hoo.marscolony.R.color;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

public class GameGrid{
	
	private View view;
	private String tag;
	private TextView text;
	private TableLayout table;

	public GameGrid(Context context, String str) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tag = str;	
		//if (str.startsWith("WAYPOINT"))
		//{
			view = inflater.inflate(R.layout.grid_waypoint, null, false);
			table = (TableLayout) view.findViewById(R.id.table);
			switch (str.charAt(8))
			{
				case '1':
					table.setBackgroundResource(color.blue);
					break;
				case '2':
					table.setBackgroundResource(color.orange);
					break;
				case '3':
					table.setBackgroundResource(color.purple);
					break;
				default:
					table.setBackgroundResource(color.yellow);
			}
				
			
			text = (TextView) view.findViewById(R.id.text);
			text.setText(str);		
		//}

	}

	public View getView() {
		return view;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setSize(int width, int height) {
		view.setLayoutParams(new LayoutParams(width, height));
	}
}
