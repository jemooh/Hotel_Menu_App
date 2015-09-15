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
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.syssoft.foodmenu.adapter.FoodsAdapter;
import com.syssoft.foodmenu.listener.FoodClickListener;
import com.syssoft.foodmenu.listener.ItemLongClickListener;
import com.syssoft.foodmenu.model.Food;
import com.syssoft.foodmenu.model.FoodLibrary;
import com.syssoft.foodmenu.service.task.GetColdDrinksTask;
import com.syssoft.foodmenu.service.task.GetHotDrinksTask;
import com.syssoft.foodmenu.service.task.GetSavedColdDrinksTask;
import com.syssoft.foodmenu.service.task.GetSavedFoodsTask;
import com.syssoft.foodmenu.service.task.GetSavedHotDrinksTask;
import com.syssoft.foodmenu.util.DatabaseHandler;
import com.syssoft.foodmenu.widget.ItemsListView;
import com.syssoft.foodmenu.R;

@SuppressLint("HandlerLeak")

		public class ListHotDrinksFragment extends Fragment implements FoodClickListener,ItemLongClickListener {
			// if run on phone, isSinglePane = true
			 // if run on tablet, isSinglePane = false
	         //implements ItemClickListener
	
	        private FoodsAdapter ItemAdapter;
	        private DatabaseHandler dbHandler;
			private ItemsListView listView;
			HashMap<String,String>  mealsMap;
			public static String  jsonString;
			int today = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);
		    String Day_id,dayTitle;
			static String parseNull(Object obj){
		        return obj == null?"null"  : "Object";
		    }
			private static final int ENTER_DATA_REQUEST_CODE = 1;
			 private List<Food> items;
			int mCurCheckPosition = 0;
			ArrayList<HashMap<String, String>> feedList;
			
			//String[] daysOfWeek = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", };
		    // ListFragment is a very useful class that provides a simple ListView inside of a Fragment.
		    // This class is meant to be sub-classed and allows you to quickly build up list interfaces
		    // in your app.
			
		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		    	Log.d("FragmentCycle===>", "onCreateView: bundle="+parseNull(savedInstanceState));
		    	View view=inflater.inflate(R.layout.list_items, container, false);
		       
		  		/*Calendar c = Calendar.getInstance();
			    SimpleDateFormat currentDate2 = new SimpleDateFormat("MMM/dd");
		  		String currentDate = currentDate2.format(c.getTime());
		  	    String Day_id = String.valueOf(today-1);
		        Intent in=getActivity().getIntent();
		        String dyT = null:
		        	//this.dayId = today+"";
		        	dayId = String.valueOf(today-1);	
		        	dayTitle = daysOfWeek[today];
		        	dyT = dayTitle + ", " + currentDate;
		        TextView txtDateToday = (TextView) view.findViewById(R.id.txtDateToday);
		  		txtDateToday.setText(dyT);*/
		    	
		    	dbHandler = new DatabaseHandler(getActivity());
		    	HashMap<String,String> users = new HashMap<String,String>();
		    	users = dbHandler.getH();
		       jsonString = users.get("jsonString");
				//create a listview to hold data
		        listView = (ItemsListView) view.findViewById(R.id.todayListView);
				
				// Here we are adding this activity as a listener for when any row in the List is 'clicked'
		        // The activity will be sent back the video that has been pressed to do whatever it wants with
		        // in this case we will retrieve the URL of the video and fire off an intent to view it
		       listView.setOnItemClickListener(this);
		       listView.setOnItemLongClickListener(this);
		       
		        getItemsFeed(listView);
		        
		      /** Button btnsubmit = (Button) view.findViewById(R.id.btnSubmit);
		        mealsMap = new HashMap<String,String>();
				btnsubmit.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										int j = 0;
										dbHandler = new DatabaseHandler(getActivity());
										String[] val = null;
										Log.i("FIRST:", "First visible index: " + listView.getFirstVisiblePosition());
										Log.i("LAST:","Last visible index: " + listView.getLastVisiblePosition());
										int first=listView.getFirstVisiblePosition();
										int last=listView.getLastVisiblePosition();
										
										for (int i = first; i <=last; ++i) {
										   // String tag = "asdf"; // Remove when bug is fixed.
							        		//Log.i("position", "position is : " + i);
							        		View vw;
							        		String nv=null;
							        		vw=listView.getChildAt(i-first).findViewById(R.id.checkBoxStatus);
						        			Log.i("vw", "vw is : " + vw.toString());
						        			if(((CompoundButton) vw).isChecked()) {
						        				feedList= new ArrayList<HashMap<String, String>>();
						        				//HashMap<String, String> map = new HashMap<String, String>();
						        				Log.i("check", "check:true");
						        				//val[i] = i+"";
//						                        HashMap<String,Object> preventivoMap=new HashMap<String, Object>();
						        				Food Item = items.get(i);
						        				mealsMap.put("food_name", Item.getFoodname());
						        				mealsMap.put("food_price", Item.getFoodprice());
						        				Log.i("check.......", Item.getFoodname());
						        				
						        				nv = "1";
						        				j++;
						        			} else {
						        				Log.i("check", "check:false");
						        				nv = "0";
						        			}
										
						        		
						        		//here we add one because our listview starts from zero while the database list starts from 1
						        		//feedList.add(mealsMap);
						        		dbHandler.insertMealsOrder(mealsMap);
						        	//Log.i(mealsMap);
										}
										//dbHandler.insertMealsOrder(mealsMap);
										//List<Food> Items;
									}	
								});
				*/
				
		        
				        
				        return view;
				    }
					// This is the XML onClick listener to retreive a users video feed
				    public void getItemsFeed(View v){
				        // We start a new task that does its work on its own thread
				        // We pass in a handler that will be called when the task has finished
				        // We also pass in the name of the user we are searching YouTube for
				    	
				    	
				    	if(jsonString!=null){
			    			//Log.i("current Server Lessons", Day_id);
			    	   new Thread(new GetSavedHotDrinksTask(responseHandler2,   null,null)).start();
			    	
			    	   }else new Thread(new GetHotDrinksTask(responseHandler,   null,null)).start();} 
			   
				    	
				   
				    
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
					        FoodLibrary lib = (FoodLibrary) msg.getData().get(GetSavedHotDrinksTask.LIBRARY);
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
				        FoodLibrary lib = (FoodLibrary) msg.getData().get(GetHotDrinksTask.LIBRARY);
				        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
				        // we can just call our custom method with the list of items we want to display
				        ProgressBar pbpp = (ProgressBar) getView().findViewById(R.id.pbppl);
				        TextView txtMsg = (TextView) getView().findViewById(R.id.progressMsg2);
				        listView.setItems(lib.getfoods());
				        String jsonString = GetHotDrinksTask.json;
				        Log.i("jsonString", jsonString);
				        dbHandler.addH(jsonString);
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
				    	
				    	// get entire list, update dbase
						//db.execSQL("UPDATE Categories SET title = , status = ;");
						
				    	Log.i("clicked pos",""+mCurCheckPosition);
				    }
				 
				   	
					   void showDetails(Food item,int position){
					    	//look for data in position index on array Lessons
						  DatabaseHandler db = new DatabaseHandler(getActivity());
					    	String foodname = item.getFoodname();
							String price =item.getFoodprice();
							String ingredients = item.getFoodingredients();
							String foodimage_url =item.getFoodimage_url();
							//String status ="true";
							
							
							//orders.add(new Myorder(food_name,food_price)); 
							 db.addMeals(foodname, price);
							  
					   
							 //items.set(position,new Food( foodname,price,ingredients,foodimage_url,true));
							  Log.i("clicked pos",foodname);
							  Log.i("clicked pos", price);
							  //listView.setItems(items);
					          //customAdapter.changeCursor(db.getAllMealsData());
							
							 //Log.i("Item Array.....",orders); 
							//FoodLibrary lib = new FoodLibrary("br", items);
				            // Pack the Library into the bundle to send back to the Activity	            
				            //data.putSerializable(LIBRARY, lib);
					    	
				
				   }	
					
			
				    	//customListAdapter.notifyDataSetChanged();
				    /*@Override  
			      public void onActivityResult(int requestCode, int resultCode, Intent data)  
			       
			       {  
			                 super.onActivityResult(requestCode, resultCode, data);  
			                  // check if the request code is same as what is passed  here it is 2  
			                 if(resultCode == Activity.RESULT_OK )
			                         {  
			                	
			                            int  editPos = Integer.parseInt(data.getStringExtra("position_D")); 
			                            String  item_price = data.getStringExtra("price");
			                            String  status_image_url = data.getStringExtra("status_uri_image");
			                            String  itemimage_url = data.getStringExtra("itemimage_url");
			                            String  itemtime = data.getStringExtra("delivery_date");
			                            String  item_name = data.getStringExtra("item_name");
			                            String  client_name = data.getStringExtra("client_name");
			                            String  location = data.getStringExtra("location");
			                            String  phoneno = data.getStringExtra("phoneno");
			                            String  product_id = data.getStringExtra("product_id");
			                            int  order_id = Integer.parseInt(data.getStringExtra("order_id"));
			                            items.set(editPos, new Food(order_id,product_id,client_name,phoneno,item_name,item_price,itemtime,location,itemimage_url,status_image_url));
			                            
			        			        listView.setItems(items);
			                            //ItemAdapter.notifyDataSetChanged(); 
			                         }  
			                 if (resultCode == Activity.RESULT_CANCELED) {
			                     return;
			                 }
			                        	 
			            }/**/
			
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
		
		
					@Override
					public void onItemLongClicked(Food item, int position) {
						// TODO Auto-generated method stub
						
					}
		
					
		}
		    	
		    	
		    	
    	