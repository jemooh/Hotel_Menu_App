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
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment.SavedState;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.syssoft.foodmenu.adapter.FoodsAdapter;
import com.syssoft.foodmenu.adapter.MealsCursorAdapter;
import com.syssoft.foodmenu.adapter.MyorderAdapter;
import com.syssoft.foodmenu.listener.MyorderClickListener;
import com.syssoft.foodmenu.model.Food;
import com.syssoft.foodmenu.model.Myorder;
import com.syssoft.foodmenu.model.MyorderLibrary;
import com.syssoft.foodmenu.service.task.GetColdDrinksTask;
import com.syssoft.foodmenu.util.DatabaseHandler;
import com.syssoft.foodmenu.util.MealsOrderDataHelper;
import com.syssoft.foodmenu.widget.ItemsListView;
import com.syssoft.foodmenu.widget.MyorderListView;
import com.syssoft.foodmenu.R;


import de.timroes.swipetodismiss.SwipeDismissList;
import de.timroes.swipetodismiss.SwipeDismissList.SwipeDirection;
import de.timroes.swipetodismiss.SwipeDismissList.UndoMode;
import de.timroes.swipetodismiss.SwipeDismissList.Undoable;

@SuppressLint("HandlerLeak")

		public class MyorderActivity extends ActionBarActivity implements MyorderClickListener {
			// if run on phone, isSinglePane = true
			 // if run on tablet, isSinglePane = false
	         //implements ItemClickListener
	        private ListView listv;
	        List<Myorder> order;
			//private MyorderListView listView;
			private  ItemsListView  listView;
			private MyorderLibrary lib;
			private MealsOrderDataHelper databaseHelper;
			private DatabaseHandler db;
			SQLiteDatabase dbb;
	        private MealsCursorAdapter customAdapter;
	        private MyorderAdapter myorderadapter;
			static String parseNull(Object obj){
		        return obj == null?"null"  : "Object";
		    }
			
			TextView txtTotals,No_Of_meals;
			ImageView  AddmoreBill;
			Spinner pay_service;
			Button btnOrder;
			private static final int ENTER_DATA_REQUEST_CODE = 1;
			//private List<Food> items;
			int mCurCheckPosition = 0;
			
		    @Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.my_order);
		       
				
				//create a listview to hold data
		        //listView = (ItemsListView) findViewById(R.id.todayListView);
				
				// Here we are adding this activity as a listener for when any row in the List is 'clicked'
		        // The activity will be sent back the video that has been pressed to do whatever it wants with
		        // in this case we will retrieve the URL of the video and fire off an intent to view it
		      //listView.setOnItemClickListener(this);
		         db = new DatabaseHandler(getApplicationContext());
				//databaseHelper = new MealsOrderDataHelper(getApplicationContext());
				//listv = (ListView) findViewById(android.R.id.list);
				MyorderListView	listView = (MyorderListView) findViewById(R.id.todayListView);
				
				txtTotals = (TextView) findViewById(R.id.txtTotals);
				No_Of_meals = (TextView) findViewById(R.id.txtNoOfMeals);
				AddmoreBill =  (ImageView) findViewById(R.id.imageViewAddMoreBill);
				//btnOrder = (Button) findViewById(R.id.btnmyorder);
				pay_service =  (Spinner) findViewById(R.id.spinpayby);
				String totals = Integer.toString(getTotalBill());
				String countOfMeals = Integer.toString(getCountOfMeals());
				
		         txtTotals.setText("Kshs:"+totals);
		         No_Of_meals.setText(countOfMeals);
		         //
		         AddmoreBill.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							finish();
							
						}
					});
		         //on click to submit order button
		         /*btnOrder.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							db.resetTables();
							finish();
							
						}
					});*/
		         
		         
		         
		         //pay order via mpesa, airtel money spinner
		         
		         ArrayAdapter<CharSequence> adp3=ArrayAdapter.createFromResource(this,
	                        R.array.pay_service, R.layout.spinner_item);

	             adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	             pay_service.setAdapter(adp3);
				//get list of order from db.
				order = getOrder();
				listView.setItems(order);
				listView.setOnItemClickListener(new OnItemClickListener() {
		
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
						
							Log.i( "clicked on item: " + position, "**");
							//databaseHelper = new MealsOrderDataHelper(getApplicationContext());
							db.remove(id);
							Log.i( "clicked on item: " + position, "**");
						}
				});
				
			    SwipeDismissList.OnDismissCallback callback = new SwipeDismissList.OnDismissCallback() {
			        @Override
			        public Undoable onDismiss(AbsListView listView, int position) {
			            View view = (View) listView.getChildAt(0);

			            view.animate().alpha(1).setDuration(200).translationX(10);
			            myorderadapter.remove(position);
			            return null;
			        }
			    };

			    UndoMode mode = SwipeDismissList.UndoMode.MULTI_UNDO;

			    SwipeDismissList swipeList = new SwipeDismissList(listView, callback,
			            mode);
			    swipeList.setSwipeDirection(SwipeDirection.BOTH);

			
		        // Database query can be a time consuming task ..
		        // so its safe to call database query in another thread
		        // Handler, will handle this stuff for you <img src="http://s0.wp.com/wp-includes/images/smilies/icon_smile.gif?m=1129645325g" alt=":)" class="wp-smiley">
		 /*
		        new Handler().post(new Runnable() {
		            @Override
		            public void run() {
		                customAdapter = new MealsCursorAdapter(getApplicationContext(), db.getAllMealsData());
		                listv.setAdapter(customAdapter);
		            }
		        });
				*/
		       
		       //MyorderLibrary lib = (MyorderLibrary) msg.getData().get(GetColdDrinksTask.LIBRARY);
		        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		        // we can just call our custom method with the list of items we want to display
		        //ProgressBar pbpp = (ProgressBar) findViewById(R.id.pbppl);
		        //TextView txtMsg = (TextView) findViewById(R.id.progressMsg2);
		          //listView.setItems(items);
		     
		        
		    }
		    
		    /**
		     * 
		     * @return meals order list.
		     */
			private List<Myorder> getOrder() {		
			    order = new ArrayList<Myorder>();
			    dbb =  getApplicationContext().openOrCreateDatabase("syssoftDatabase6.db", Context.MODE_PRIVATE, null);
		        Cursor cursor=dbb.query("Myorder", null, null, null, null, null , null);
		        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
		        	String foodname = cursor.getString(cursor.getColumnIndex("food_name"));
		        	String price = cursor.getString(cursor.getColumnIndex("food_price"));
		        	/*boolean checked = false;
		        	if(status==1) {
		        		checked = true;        		
		        	}*/
		        	order.add(new Myorder( foodname, price));
		    	Log.i("foodname....", foodname + ": price..... " + price);
		        }
		        dbb.close();
			    
			    // Initially select two of the items
		        //topics.get(0).setSelected(true);
		        //topics.get(1).setSelected(true);
			    return order;
			}
			
			// getting the totals meals price from the database
			
			private  int getTotalBill() {		
			   
			   dbb =  getApplicationContext().openOrCreateDatabase("syssoftDatabase6.db", Context.MODE_PRIVATE, null);
			    //Cursor cur = dbb.query("SELECT SUM(food_price) FROM Myorder",  null, null, null, null, null , null);
			   
				//dbb = getApplicationContext().getWritableDatabase();

			    String query = "SELECT SUM(food_price) FROM Myorder";


			    Cursor cur = dbb.rawQuery(query, null);
				//Add in the movetofirst etc here? see SO
			    cur.moveToFirst(); 
			    int i=cur.getInt(0);
			    dbb.close();

			    return i;
			}	
			
			// getting the totals meals price from the database
			
						private  int getCountOfMeals() {		
						   
						   dbb =  getApplicationContext().openOrCreateDatabase("syssoftDatabase6.db", Context.MODE_PRIVATE, null);
						    //Cursor cur = dbb.query("SELECT SUM(food_price) FROM Myorder",  null, null, null, null, null , null);
						   
							//dbb = getApplicationContext().getWritableDatabase();

						    String query = "SELECT count(*) FROM Myorder";


						    Cursor cur = dbb.rawQuery(query, null);
							//Add in the movetofirst etc here? see SO
						    cur.moveToFirst(); 
						    int i=cur.getInt(0);
						    dbb.close();

						    return i;
						}	
		 /**
			// This is the XML onClick listener to retreive a users video feed
		    public void getItemsFeed(View v){
		        // We start a new task that does its work on its own thread
		        // We pass in a handler that will be called when the task has finished
		        // We also pass in the name of the user we are searching YouTube for
		    	
		    			//Log.i("current Server Lessons", Day_id);
		    	  new Thread(new GetColdDrinksTask(responseHandler,   null,null)).start();
		    	}
		   
		   
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
		     
		    private void populateListWithItems(Message msg) {
		        // Retreive the videos are task found from the data bundle sent back
		    	MyorderLibrary lib = (MyorderLibrary) msg.getData().get(GetColdDrinksTask.LIBRARY);
		        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		        // we can just call our custom method with the list of items we want to display
		        ProgressBar pbpp = (ProgressBar) findViewById(R.id.pbppl);
		        TextView txtMsg = (TextView) findViewById(R.id.progressMsg2);
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
		    
		    
		    }*/
		    
		    @Override
		    public void onItemClicked(Myorder Item,int position){
		    	//showDetails(Item, position);
		    	mCurCheckPosition = position;
		    	  //pos =items.get(position);
		    	//setItemChecked(position, true);
		    	Log.i("clicked pos",""+mCurCheckPosition);
		    	
		    }

			
		    	
		   /*  	
		   void showDetails(Food item,int position){
		    	//look for data in position index on array Lessons
			    
		    	String client_name = item.getClientname();
				String item_name =item.getItemname();
				String delivery_date =item.getItemtime();
				//String time = Integer.toString(item.getItemtime());
				String itemimage_url = item.getItemimage_url();
				String price = item.getItemprice();
				//String time = item.getItemtime();
				String positionDel = Integer.toString(position);
				String location = item.getLocation();
				String phoneno = item.getPhoneno();
				String order_id = String.valueOf(item.getOrder_id());
				String product_id = item.getProduct_id();
				
					     Intent si = new Intent(getActivity(), SingleItemActivity.class);
					     Bundle b=new Bundle();
					     
					     b.putString("client_name", client_name);
					     b.putString("item_name", item_name);
					     //b.putString("time", time);
					     b.putString("delivery_date", delivery_date);
					     b.putString("itemimage_url", itemimage_url);
					     b.putString("price", price);
					     b.putString("positionDel", positionDel);
					     b.putString("location", location);
					     b.putString("phoneno", phoneno);
					     b.putString("order_id", order_id);
					     b.putString("product_id", product_id);
					                
					     si.putExtras(b);
					     //startActivity(si);
					     startActivityForResult(si,2);
				    }	
		   
		    	//customListAdapter.notifyDataSetChanged();
		  
		  
	                        	 
	            }/**/
	
		 
			
}
    	
    	
    	
    	