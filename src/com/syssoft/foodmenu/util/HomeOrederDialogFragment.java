package com.syssoft.foodmenu.util;
	
	import java.util.HashMap;
	
	import org.json.JSONObject;
	
	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
	
	import org.json.JSONException;
import org.json.JSONObject;
	
	import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
	
import com.syssoft.foodmenu.R;
import com.syssoft.foodmenu.src.MainActivity;
	
	
	public class HomeOrederDialogFragment extends DialogFragment {
	
		ImageButton btnFeedBack, btn_submit;
		
	
		private static final int FEEDBACK_DIALOG = 1;
		private static String KEY_SUCCESS = "success";
		AlertDialog alertDialog;
	    HashMap<Integer, Dialog> mDialogs = new HashMap<Integer, Dialog>();
		//SaveFeedbackTask feedBackTsk;
		ServerInteractions userFunction;
		DatabaseHandler db;
		JSONObject json_user;
	    JSONObject json;
	    String errorMsg, successMsg;
	    String res; 
	    DatabaseHandler dbHandler;
	    boolean mDualPane;
		
	
		public static HomeOrederDialogFragment newInstance(int id){
			
			HomeOrederDialogFragment dialogFragment = new HomeOrederDialogFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("id", id);
			dialogFragment.setArguments(bundle);
			
			return dialogFragment;
		}
	@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {	
	    	int id =getArguments().getInt("id");
			LayoutInflater inflater;
			AlertDialog.Builder dialogbuilder;
			View dialogview;
			AlertDialog dialogDetails = null;		 
			switch (id) {
				case FEEDBACK_DIALOG:
			 		inflater = LayoutInflater.from(getActivity());
			 		dialogview = inflater.inflate(R.layout.home_order, null);
		 
			 		dialogbuilder = new AlertDialog.Builder(getActivity());
			 		dialogbuilder.setTitle("HOME ORDER!");
			 		//dialogbuilder.setIcon(R.drawable.reviews);
			 		dialogbuilder.setView(dialogview);
			 		dialogDetails = dialogbuilder.create();	  
			 	break;
			 	
			 }	 
		  return dialogDetails;
	    
	}
	@Override
	public void onResume(){
		super.onResume();
		
		
		Dialog dialog =getDialog();
		int id =getArguments().getInt("id");
		  	switch (id) {
		  		case FEEDBACK_DIALOG:
		  			alertDialog = (AlertDialog) dialog;
		  			btn_submit = (ImageButton) alertDialog.findViewById(R.id.btn_submit);
		  			ImageButton cancelbutton = (ImageButton) alertDialog.findViewById(R.id.btn_cancel);
		  			final EditText txtphoneno = (EditText) alertDialog.findViewById(R.id.txtphoneno);
		  			final EditText txtlocation = (EditText) alertDialog.findViewById(R.id.txtlocation);
		  			final DatabaseHandler db = new DatabaseHandler(getActivity());
		  			btn_submit.setOnClickListener(new View.OnClickListener() {
		 
		  				@Override
		  				public void onClick(View v) {
		  					String phoneno = txtphoneno.getText().toString();
		  					String location = txtlocation.getText().toString();
		  					alertDialog.dismiss();
		  					db.addHomeorder(phoneno, location);
		  					
		  					Intent dashboard = new Intent(getActivity(), MainActivity.class);
                            
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                             
                            // Close Login Screen
                            //finish();
		  					//start task
		  	                //MyCommentParams params = new MyCommentParams(phoneno,location);
		  	                //feedBackTsk = new SaveFeedbackTask();
		  	                //feedBackTsk.execute(params);
		  				}
		  			});
		 
		  			cancelbutton.setOnClickListener(new View.OnClickListener() {
		 
		  				@Override
		  				public void onClick(View v) {
		  					alertDialog.dismiss();
		  				}
		  			});
		  			break;
		  	}
		}
		
	/**
		private class SaveFeedbackTask extends AsyncTask<MyCommentParams, Void, String> {
	        @Override
	        protected String doInBackground(MyCommentParams... params) {
	        	userFunction = new ServerInteractions();
	
	        	String phoneno = params[0].phoneno;
	        	String location = params[0].location;
	        	
	        	//get user email from database.
	        	db = new DatabaseHandler(getActivity());
	        	HashMap<String,String> user = new HashMap<String,String>();
	        	user = db.getUserDetails();
	        	String email = user.get("email");
	        	/*
	        	json = userFunction.postFeedback(feedbackContent, email); //100 refers to example user id
	            try {
	                if (json.getString(KEY_SUCCESS) != null) {
	                	errorMsg = "";
	                    res = json.getString(KEY_SUCCESS);
	                    if(Integer.parseInt(res) == 1){
	                    	successMsg = "Successfully sent a feedback, thank you";
	                    }else{
	                        // Error in login
	                    	successMsg = "Something went wrong, please verify your sentence";
	                    }
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
				return successMsg; 
	        }
	        
	        @Override
	        protected void onPostExecute(String json_user) {        	
	        	try {
	        		if(isCancelled())        	
					return;
	        		
	        		if(Integer.parseInt(res) == 1){
	                	Toast.makeText(getActivity(), "Successfully sent a feedback", Toast.LENGTH_SHORT).show();
	                	//getCommentsFeed(listView, strVideoId);
	                  	alertDialog.dismiss();
	                }
	        	} catch(Exception e){
					Log.e(this.getClass().getSimpleName(), "Error Saving feedback", e);
				}
	        }        
	    }
		
		private static class MyCommentParams {
	        String phoneno,location;
	        MyCommentParams(String phoneno,String location) {
	            this.phoneno = phoneno;
	            this.location = location;
	            
	        }
	    }*/
	    	
	}
