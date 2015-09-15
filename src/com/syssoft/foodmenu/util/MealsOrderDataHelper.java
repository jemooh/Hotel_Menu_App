package com.syssoft.foodmenu.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
		
		public class MealsOrderDataHelper {
		
		    private static final String TAG = MealsOrderDataHelper.class.getSimpleName();
		
		    // database configuration
		    // if you want the onUpgrade to run then change the database_version
		    private static final int DATABASE_VERSION = 1;
		    private static final String DATABASE_NAME = "MealsOrderDatabase.db";
		
		    // table configuration
		    private static final String TABLE_MEALS = "Myorder"; 
		    private static final String TABLE_COMMENT = "Comments"; // Table name
		    
		    private static final String TABLE_COLUMN_ID = "_id";
		    private static final String TABLE_COLUMN_NOTES = "note";
		    private static final String TABLE_COLUMN_TIME = "time";
		    private static final String TABLE_COLUMN_UNIT_ID = "unit_id";
		    
		    private static final String TABLE_KEY_ID = "_id";
		    private static final String TABLE_KEY_COMMENT = "comment";
		    private static final String TABLE_KEY_TIME = "time";
		    private static final String TABLE_KEY_USERID = "user";
		    private static final String TABLE_KEY_UNIT_ID = "unit_id";
		    private static final String TABLE_KEY_CREATOR = "creator";
		    
		    
		    private static final String TABLE_FOODNAME = "food_name";
		    private static final String TABLE_FOODPRICE = "food_price";
		    private static final String TABLE_STATUS = "status";
		    
		    private DatabaseOpenHelper openHelper;
		    private SQLiteDatabase database;
		
		    
		    
		    // this is a wrapper class. that means, from outside world, anyone will communicate with PersonDatabaseHelper,
			// but under the hood actually DatabaseOpenHelper class will perform database CRUD operations 
		    public MealsOrderDataHelper(Context aContext) {
				
		        openHelper = new DatabaseOpenHelper(aContext);
		        database = openHelper.getWritableDatabase();
		    }
		
		    public void insertData (String food_name,String food_price) {
		
		        // we are using ContentValues to avoid sql format errors
		
		        ContentValues contentValues = new ContentValues();
		        contentValues.put(TABLE_FOODNAME, food_name);
		        contentValues.put(TABLE_FOODPRICE, food_price);
		        //contentValues.put(TABLE_STATUS, status);
		
		        database.insert(TABLE_MEALS, null, contentValues);
		    }
		        
			    public void insertComment(String userId, String currentDateTime,String unit_id, String comment, String creator ) {
				
			        ContentValues contentValues = new ContentValues();
			        contentValues.put(TABLE_KEY_USERID, userId);
			        contentValues.put(TABLE_KEY_TIME, currentDateTime);
			        contentValues.put(TABLE_KEY_UNIT_ID, unit_id);
			        contentValues.put(TABLE_KEY_COMMENT, comment);
			        contentValues.put(TABLE_KEY_CREATOR, creator);
			        
			        
			        database.insert(TABLE_COMMENT, null, contentValues);
		        
		        
		    }
		/*
		    public Cursor getAllData () {
		    	String unit_id = SingleLessonActivity1.unit_id;
		    	//String buildSQL ="SELECT * FROM" + TABLE_NAME + "WHERE" +TABLE_COLUMN_UNIT_ID+ , new String[]{unit_id});
		        String buildSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_UNIT_ID + " = '" + unit_id + "'";
		        //COLUMN_BID + "=" + Bid + " AND " + COLUMN_CID + "=" + Cid.
		        Log.d(TAG, "getAllData SQL: " + buildSQL);
		
		        return database.rawQuery(buildSQL, null);
		    }
		    
		    
		    public Cursor getAllComments () {
		    	String unit_id = SingleLessonActivity1.unit_id;
		    	//String buildSQL ="SELECT * FROM" + TABLE_NAME + "WHERE" +TABLE_COLUMN_UNIT_ID+ , new String[]{unit_id});
		        String buildSQL = "SELECT * FROM " + TABLE_COMMENT + " WHERE " + TABLE_KEY_UNIT_ID + " = '" + unit_id + "'";
		        //COLUMN_BID + "=" + Bid + " AND " + COLUMN_CID + "=" + Cid.
		        Log.d(TAG, "getAllData SQL: " + buildSQL);
		
		        return database.rawQuery(buildSQL, null);
		    }*/
		
		    
		    public Cursor getAllMealsData () {
		    	String buildSQL = "SELECT * FROM " + TABLE_MEALS ;
		        Log.d(TAG, "getAllMealsData SQL: " + buildSQL);
		
		        return database.rawQuery(buildSQL, null);
		    }
		    
		  
		    public void delete(long id) {

		    	database = openHelper.getWritableDatabase();
		    	database.delete(TABLE_MEALS, TABLE_COLUMN_ID + "=?",
		                new String[] { String.valueOf(id) }); // KEY_ID= id of row and third parameter is argument.
		    	database.close();
		    } 
		    
		    
		    
		    public void remove(long id){
		    	
		    	database = openHelper.getWritableDatabase();
		    	database.delete(TABLE_MEALS, TABLE_COLUMN_ID + "=?",
		                new String[] { String.valueOf(id) }); // KEY_ID= id of row and third parameter is argument.
		    	database.close();
		        //String string =String.valueOf(id);
		       // database.execSQL("DELETE FROM Comments WHERE _id = '" + string + "'");
		    }
			// this DatabaseOpenHelper class will actually be used to perform database related operation 
			
		    private class DatabaseOpenHelper extends SQLiteOpenHelper {
		
		        public DatabaseOpenHelper(Context aContext) {
		            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
		        }
		
		        @Override
		        public void onCreate(SQLiteDatabase sqLiteDatabase) {
		            // Create your tables here
		
		            String buildSQL = "CREATE TABLE " + TABLE_MEALS + "( " 
				            + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
				            + TABLE_FOODNAME + " TEXT, " 
				            + TABLE_FOODPRICE + " TEXT, " 
				            + TABLE_STATUS + " TEXT)";
		
		            Log.d(TAG, "onCreate SQL: " + buildSQL);
		
		            sqLiteDatabase.execSQL(buildSQL);
		            
		            
		            String buildSQLCOM = "CREATE TABLE " + TABLE_COMMENT + "( " 
				            + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
				            + TABLE_KEY_USERID + " TEXT, " 
				            + TABLE_KEY_TIME + " TEXT, "  
				            + TABLE_KEY_UNIT_ID + " TEXT, " 
				            + TABLE_KEY_CREATOR + " TEXT, " 
				            + TABLE_KEY_COMMENT + " TEXT)";
		
		            Log.d(TAG, "onCreate SQL: " + buildSQLCOM);
		
		            sqLiteDatabase.execSQL(buildSQLCOM);
		            
		            
		            
		        }
		
		        @Override
		        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		            // Database schema upgrade code goes here
		
		            String buildSQL = "DROP TABLE IF EXISTS " + TABLE_MEALS;
		
		            Log.d(TAG, "onUpgrade SQL: " + buildSQL);
		
		            sqLiteDatabase.execSQL(buildSQL);       // drop previous table
		            
		            
		            String buildSQLCOM = "DROP TABLE IF EXISTS " + TABLE_COMMENT;
		    		
		            Log.d(TAG, "onUpgrade SQL: " + buildSQLCOM);
		
		            sqLiteDatabase.execSQL(buildSQLCOM); 
		
		            onCreate(sqLiteDatabase);     
		            // create the table from the beginning
		           database.close();
		        }
		    }
		}
