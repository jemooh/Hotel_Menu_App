package com.syssoft.foodmenu.adapter;

import java.text.DecimalFormat;

import com.syssoft.foodmenu.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

			public class MealsCursorAdapter extends CursorAdapter {
			   
				DecimalFormat df = new DecimalFormat("#.##");
				
			   public MealsCursorAdapter(Context context, Cursor c) {
			       super(context, c);
			      
			   }
			
			   @Override
			   public View newView(Context context, Cursor cursor, ViewGroup parent) {
			       // when the view will be created for first time,
			       // we need to tell the adapters, how each item will look
			       LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			       View retView = inflater.inflate(R.layout.orderedmeal, parent, false);
			
			       return retView;
			   }
			
			   @Override
			   public void bindView(View view, Context context, Cursor cursor) {
			       // here we are setting our data
			       // that means, take the data from the cursor and put it in views
				   TextView foodname = (TextView) view.findViewById(R.id.txtFood); 
				   TextView price = (TextView) view.findViewById(R.id.txprice);
			      
			       foodname.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
			       price.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
			       
			       
			       
			       
			   }
			}
			       