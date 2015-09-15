package com.syssoft.foodmenu.src;

import com.syssoft.foodmenu.src.MyorderActivity;
import com.syssoft.foodmenu.util.HomeOrederDialogFragment;
import com.syssoft.foodmenu.util.HotelOrderDialogFragment;
import com.syssoft.foodmenu.adapter.TabsPagerAdapter;
import com.syssoft.foodmenu.R;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

		


		public class DashboardActivity extends ActionBarActivity   {
		
			
		    @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.dashboard_activity);
		        ActionBar actionBar = getSupportActionBar();
		        actionBar.hide();
		        TextView txtT = (TextView) findViewById(R.id.txtkaribu);
		        Typeface font_d = Typeface.createFromAsset(this.getAssets(), "fonts/Comfortaa-Bold.ttf");
		        txtT.setTypeface(font_d);
		     
		        Button btnhome = (Button) findViewById(R.id.btnhome);
		        Button btnhotel = (Button) findViewById(R.id.btnhotel);
		        btnhome.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
							DialogFragment newFragment = HomeOrederDialogFragment.newInstance(1);
							newFragment.show(getSupportFragmentManager(), "dialog");
						}
						
					
				});
		        
		        btnhotel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
							DialogFragment newFragment = HotelOrderDialogFragment.newInstance(1);
							newFragment.show(getSupportFragmentManager(), "dialog");
						}
						
					
				});
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
		    

			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				// Handle the back button
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// Ask the user if they want to quit
					/* new AlertDialog.Builder(new ContextThemeWrapper(
			    		    this,R.style.AlertDialogCustom))*/
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
											DashboardActivity.this.finish();
										}

									}).setNegativeButton("No", null).show();

					return true;
				} else {
					return super.onKeyDown(keyCode, event);
				}

			}
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		}
