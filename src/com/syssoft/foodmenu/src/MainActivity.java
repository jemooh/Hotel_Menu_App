package com.syssoft.foodmenu.src;

import com.syssoft.foodmenu.src.MyorderActivity;
import com.syssoft.foodmenu.util.DatabaseHandler;
import com.syssoft.foodmenu.adapter.TabsPagerAdapter;
import com.syssoft.foodmenu.R;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

		


		public class MainActivity extends ActionBarActivity implements ActionBar.TabListener  {
		
			private String[] tabs = {  "Hot Drinks", "Cold Drinks","Main Courses", "Soups" };
			private ViewPager viewPager;
		    private TabsPagerAdapter mAdapter;
		    private ActionBar actionBar;
			private  Button btnmyorder;
			public  Button  btnsubmit;
			
			
			
		    @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_main);
		        
		        ActionBar ab = getSupportActionBar();
		        //ab.setTitle("My Title");
		        ab.setSubtitle("Food Menu"); 
		     
		        btnmyorder = (Button) findViewById(R.id.btnmyorder);
		        //btnsubmit = (Button) findViewById(R.id.btnSubmit);
		        btnmyorder.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent myorderAct = new Intent(getApplicationContext(), MyorderActivity.class);
			            // Close all views before launching Dashboard
			            startActivity(myorderAct);
						
					}
				});
		        
		        
		        viewPager = (ViewPager) findViewById(R.id.pager);
		        actionBar = getSupportActionBar();
		        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		 
		        viewPager.setAdapter(mAdapter);
		        actionBar.setHomeButtonEnabled(false);
		        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
		 
		        // Adding Tabs
		        for (String tab_name : tabs) {
		            actionBar.addTab(actionBar.newTab().setText(tab_name)
		                    .setTabListener(this));
		        }
		 
		        /**
		         * on swiping the viewpager make respective tab selected
		        */
		        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		 
		            @Override
		            public void onPageSelected(int position) {
		                // on changing the page
		                // make respected tab selected
		                actionBar.setSelectedNavigationItem(position);
		            }
		 
		            @Override
		            public void onPageScrolled(int arg0, float arg1, int arg2) {
		            }
		 
		            @Override
		            public void onPageScrollStateChanged(int arg0) {
		            }
		        });
		        
		        
		        
			    }
		 
		    @Override
		    public void onTabReselected(Tab tab, FragmentTransaction ft) {
		    }
		 
		    @Override
		    public void onTabSelected(Tab tab, FragmentTransaction ft) {
		        // on tab selected
		        // show respected fragment view
		        viewPager.setCurrentItem(tab.getPosition());
		    }
		 
		    @Override
		    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		    }
		        
		   
		
		    @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		        // Inflate the menu; this adds items to the action bar if it is present.
		        getMenuInflater().inflate(R.menu.main, menu);
		        return true;
		        
		    }
		
		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        // Handle action bar item clicks here. The action bar will
		        // automatically handle clicks on the Home/Up button, so long
		        // as you specify a parent activity in AndroidManifest.xml.
		        int id = item.getItemId();
		        if (id == R.id.action_settings) {
		            return true;
		        }
		        return super.onOptionsItemSelected(item);
		    }
		    
          /**
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				// Handle the back button
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// Ask the user if they want to quit
					/* new AlertDialog.Builder(new ContextThemeWrapper(
			    		    this,R.style.AlertDialogCustom))
					new AlertDialog.Builder(this)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle("Quit?")
							.setMessage("Do you want to quit Boucan Restaurant?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
												int which) {

											// Stop the activity
											MainActivity.this.finish();
										}

									}).setNegativeButton("No", null).show();

					return true;
				} else {
					return super.onKeyDown(keyCode, event);
				}
*/
			
		    
		}  
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		
