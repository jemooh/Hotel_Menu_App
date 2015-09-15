package com.syssoft.foodmenu.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.syssoft.foodmenu.adapter.MyorderAdapter;
import com.syssoft.foodmenu.model.Myorder;
import com.syssoft.foodmenu.listener.MyorderClickListener;
import com.syssoft.foodmenu.listener.ItemLongClickListener;

public class MyorderListView extends ListView implements android.widget.AdapterView.OnItemClickListener {
	private List<Myorder> Items;
    private MyorderClickListener ItemClickListener;
    
	public MyorderListView(Context context) {
        super(context);
    }
	
	public MyorderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public MyorderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    
 
    public void setItems(List<Myorder> items){
    	this.Items = items;
    	MyorderAdapter adapter = new MyorderAdapter(getContext(), items);
        setAdapter(adapter);
        // When the videos are set we also set an item click listener to the list
        // this will callback to our custom list whenever an item it pressed
        // it will tell us what position in the list is pressed
        setOnItemClickListener(this);
        //setOnItemLongClickListener(this);
        
    }
     
    // Calling this method sets a listener to the list
    // Whatever class is passed in will be notified when the list is pressed
    // (The class that is passed in just has to 'implement VideoClickListener'
    // meaning is has the methods available we want to call)
    public void setOnItemClickListener(MyorderClickListener l) {
    	ItemClickListener = l;
    }
    /*
    public void setOnItemLongClickListener(ItemLongClickListener listener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        ItemLongClickListener = listener;
    }*/
    
    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    // When we receive a notification that a list item was pressed
    // we check to see if a video listener has been set
    // if it has we can then tell the listener 'hey a video has just been clicked' also passing the video
    @Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
    	if(ItemClickListener != null){
    		ItemClickListener.onItemClicked(Items.get(position), position);
        }
		
	}
/*
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if(ItemLongClickListener != null){
			ItemLongClickListener.onItemLongClicked(Items.get(position), position);
        }
		// TODO Auto-generated method stub
		return false;
	}*/
}
