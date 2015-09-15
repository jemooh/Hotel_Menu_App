package com.syssoft.foodmenu.src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment.SavedState;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.syssoft.foodmenu.adapter.FoodsAdapter;
import com.syssoft.foodmenu.listener.FoodClickListener;
import com.syssoft.foodmenu.model.Food;
import com.syssoft.foodmenu.model.FoodLibrary;
import com.syssoft.foodmenu.service.task.GetSavedFoodsTask;
import com.syssoft.foodmenu.service.task.GetSavedSoupTask;
import com.syssoft.foodmenu.service.task.GetSoupTask;
import com.syssoft.foodmenu.util.DatabaseHandler;
import com.syssoft.foodmenu.widget.ItemsListView;
import com.syssoft.foodmenu.R;

@SuppressLint("HandlerLeak")

		public class ListSoupFragment extends Fragment implements FoodClickListener {
			// if run on phone, isSinglePane = true
			 // if run on tablet, isSinglePane = false
	         //implements ItemClickListener
	        private FoodsAdapter ItemAdapter;
			private ItemsListView listView;
			public static String  jsonString;
			private DatabaseHandler dbHandler;
			int today = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);
		    String Day_id,dayTitle;
			static String parseNull(Object obj){
		        return obj == null?"null"  : "Object";
		    }
			private static final int ENTER_DATA_REQUEST_CODE = 1;
			 private List<Food> items;
			int mCurCheckPosition = 0;
			//String[] daysOfWeek = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", };
			//private String[] tabs = {  "Main Courses", "Drinks" };
		
		    // ListFragment is a very useful class that provides a simple ListView inside of a Fragment.
		    // This class is meant to be sub-classed and allows you to quickly build up list interfaces
		    // in your app.
		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		    	Log.d("FragmentCycle===>", "onCreateView: bundle="+parseNull(savedInstanceState));
		    	View view=inflater.inflate(R.layout.list_items, container, false);
		       /*
		  		Calendar c = Calendar.getInstance();
			    SimpleDateFormat currentDate2 = new SimpleDateFormat("MMM/dd");
		  		String currentDate = currentDate2.format(c.getTime());
		  	    String Day_id = String.valueOf(today-1);
		        Intent in=getActivity().getIntent();
		        String dyT = null;
		      
		        	//this.dayId = today+"";
		        	dayId = String.valueOf(today-1);	
		        	dayTitle = daysOfWeek[today];
		        	dyT = dayTitle + ", " + currentDate;
		       
		        
		        TextView txtDateToday = (TextView) view.findViewById(R.id.txtDateToday);
		  		txtDateToday.setText(dyT);*/
		  		
		    	dbHandler = new DatabaseHandler(getActivity());
		    	HashMap<String,String> users = new HashMap<String,String>();
		    	users = dbHandler.getS();
		       jsonString = users.get("jsonString");
		       
		    	
				
				
				//create a listview to hold data
		        listView = (ItemsListView) view.findViewById(R.id.todayListView);
				
				// Here we are adding this activity as a listener for when any row in the List is 'clicked'
		        // The activity will be sent back the video that has been pressed to do whatever it wants with
		        // in this case we will retrieve the URL of the video and fire off an intent to view it
		       listView.setOnItemClickListener(this);
		       
		       
		        getItemsFeed(listView);
		       
		        registerForContextMenu(listView);
		        
		        return view;
		    }
		 
			// This is the XML onClick listener to retreive a users video feed
		    public void getItemsFeed(View v){
		        // We start a new task that does its work on its own thread
		        // We pass in a handler that will be called when the task has finished
		        // We also pass in the name of the user we are searching YouTube for
		    	if(jsonString!=null){
		    			//Log.i("current Server Lessons", Day_id);
		    	  new Thread(new GetSavedSoupTask(responseHandler2,   null,null)).start();
		    	
		    	}else new Thread(new GetSoupTask(responseHandler,   null,null)).start();} 
		   

		    // This is the handler that receives the response when the YouTube task has finished
		
		    @SuppressLint("HandlerLeak")
			Handler responseHandler = new Handler() {
		        public void handleMessage(Message msg) {
		        	populateListWithItems(msg);
		        };
		    };
		   
		 
		    
		    /**
		     * This method retrieves the Library of videos from the task and passes them to our ListView
		     * @param msg
		     */
		    private void populateListWithItems(Message msg) {
		        // Retreive the videos are task found from the data bundle sent back
		        FoodLibrary lib = (FoodLibrary) msg.getData().get(GetSoupTask.LIBRARY);
		        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		        // we can just call our custom method with the list of items we want to display
		        ProgressBar pbpp = (ProgressBar) getView().findViewById(R.id.pbppl);
		        TextView txtMsg = (TextView) getView().findViewById(R.id.progressMsg2);
		        listView.setItems(lib.getfoods());
		        String jsonString = GetSoupTask.json;
		        Log.i("jsonString", jsonString);
		        dbHandler.addS(jsonString);
				if(lib.getfoods().isEmpty()){
					txtMsg.setText("No foods on the menu");	
					pbpp.setVisibility(View.GONE);
				} else {
					pbpp.setVisibility(View.GONE);
					txtMsg.setVisibility(View.GONE);
					items = lib.getfoods();
			        listView.setItems(items);
				}
		    
		    
		    }
		    
		    
		    
		    @SuppressLint("HandlerLeak")
				Handler responseHandler2 = new Handler() {
			        public void handleMessage(Message msg) {
			        	populateListWithItems2(msg);
			        };
			    };
			   
			 
			    
			    /**
			     * This method retrieves the Library of videos from the task and passes them to our ListView
			     * @param msg
			     */
			    private void populateListWithItems2(Message msg) {
			        // Retreive the videos are task found from the data bundle sent back
			        FoodLibrary lib = (FoodLibrary) msg.getData().get(GetSavedSoupTask.LIBRARY);
			        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
			        // we can just call our custom method with the list of items we want to display
			        ProgressBar pbpp = (ProgressBar) getView().findViewById(R.id.pbppl);
			        TextView txtMsg = (TextView) getView().findViewById(R.id.progressMsg2);
			        listView.setItems(lib.getfoods());
					if(lib.getfoods().isEmpty()){
						txtMsg.setText("No foods on the menu");	
						pbpp.setVisibility(View.GONE);
					} else {
						pbpp.setVisibility(View.GONE);
						txtMsg.setVisibility(View.GONE);
						items = lib.getfoods();
				        listView.setItems(items);
					}
			    
			    
			    }
		    
			    
			    @Override
			    public void onItemClicked(Food Item,int position){
			    	showDetails(Item, position);
			    	mCurCheckPosition = position;
			    	//CheckBox chck = (CheckBox) getView().findViewById(R.id.checkBoxorder);
			    	//chck.setChecked(true);
			    	  //pos =items.get(position);
			    	//setItemChecked(position, true);
			    	Log.i("clicked pos",""+mCurCheckPosition);
			    }
			 
			   	
				   void showDetails(Food item,int position){
				    	//look for data in position index on array Lessons
					  // List<Myorder> orders = new ArrayList<Myorder>();
					  DatabaseHandler db = new DatabaseHandler(getActivity());
				    	String foodname = item.getFoodname();
						String price =item.getFoodprice();
						//String ingredients = item.getFoodingredients();
						//String foodimage_url =item.getFoodimage_url();
						//String status ="@drawable/add";
						
						
						//orders.add(new Myorder(food_name,food_price)); 
						  db.addMeals(foodname, price);
						  
				   
						// items.set(mCurCheckPosition,new Food( foodname,price,ingredients,foodimage_url,status));
						  Log.i("clicked pos",foodname);
						  Log.i("clicked pos", price);
				          //customAdapter.changeCursor(db.getAllMealsData());
				   }
						 //Log.i("Item Array.....",orders); 
						//FoodLibrary lib = new FoodLibrary("br", items);
			            // Pack the Library into the bundle to send back to the Activity	            
			            //data.putSerializable(LIBRARY, lib);
				    	
			          	 
	            /**/
	
		   @Override
		    public void onCreate(Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onCreate: bundle="+parseNull(savedInstanceState));
		        super.onCreate(savedInstanceState);
		    }
		
		    @Override
		    public void onAttach(Activity activity) {
		        Log.d("FragmentCycle===>", "onAttach");
		        super.onAttach(activity);
		    }
		
		    @Override
		    public void onViewCreated(View view, Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onViewCreated: bundle="+parseNull(savedInstanceState));
		        super.onViewCreated(view, savedInstanceState);
		    }
		
		    @Override
		    public void onActivityCreated(Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onActivityCreated: bundle="+parseNull(savedInstanceState));
		        super.onActivityCreated(savedInstanceState);
		    }
		
		    @Override
		    public void onViewStateRestored(Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onViewStateRestored: bundle="+parseNull(savedInstanceState));
		        super.onViewStateRestored(savedInstanceState);
		    }
		
		    @Override
		    public void onStart() {
		        Log.d("FragmentCycle===>", "onStart");
		        super.onStart();
		    }
		
		    @Override
		    public void onResume() {
		        Log.d("FragmentCycle===>", "onResume");
		        super.onResume();
		    }
		
		    @Override
		    public void onPause() {
		        Log.d("FragmentCycle===>", "onPause");
		        super.onPause();
		    }
		
		    @Override
		    public void onStop() {
		        Log.d("FragmentCycle===>", "onStop");
		        super.onStop();
		    }
		
		    @Override
		    public void onDestroyView() {
		        Log.d("FragmentCycle===>", "onDestroyView");
		        super.onDestroyView();
		    }
		
		    @Override
		    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onInflate: bundle="+parseNull(savedInstanceState));
		        super.onInflate(activity, attrs, savedInstanceState);
		    }
		
		    @Override
		    public void onSaveInstanceState(Bundle outState) {
		        Log.d("FragmentCycle===>", "onSaveInstanceState: outState="+parseNull(outState));
		       
		        outState.putInt("index", mCurCheckPosition);
		        super.onSaveInstanceState(outState);
		    }
		
		    @Override
		    public void onConfigurationChanged(Configuration newConfig) {
		        Log.d("FragmentCycle===>", "onConfigurationChanged");
		        super.onConfigurationChanged(newConfig);
		    }
		
		    @Override
		    public void onDestroy() {
		        Log.d("FragmentCycle===>", "onDestroy");
		        super.onDestroy();
		    }
		
		    @Override
		    public void onDetach() {
		        Log.d("FragmentCycle===>", "onDetach");
		        super.onDetach();
		    }
		
		    @Override
		    public void setInitialSavedState(SavedState state) {
		        Log.d("FragmentCycle===>", "setInitialSavedState");
		        super.setInitialSavedState(state);
		    }
		
		    @Override
		    public void onHiddenChanged(boolean hidden) {
		        Log.d("FragmentCycle===>", "onHiddenChanged");
		        super.onHiddenChanged(hidden);
		    }
		
		    @Override
		    public void onLowMemory() {
		        Log.d("FragmentCycle===>", "onLowMemory");
		        super.onLowMemory();
		    }

			
}
    	
    	
    	
    	