package com.syssoft.foodmenu.util;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
			public class DatabaseHandler extends SQLiteOpenHelper {
			 
			    // All Static variables
			    // Database Version
			    private static final int DATABASE_VERSION = 2;
			 
			    // Database Name
			    private static final String DATABASE_NAME = "syssoftDatabase6.db";
			 
			 // Login table name
			    private static final String TABLE_LOGIN = "user";
			    private static final String TABLE_TODAY_LESSONS = "List_Lessons";
			    private static final String TABLE_GRID_LESSONS = "grid_Lessons";
			    private static final String TABLE_LESSONS = "Lessons";
			    private static final String TABLE_COMMENTS = "Comments";
			    private static final String TABLE_MEALS = "Myorder";
			    
			    private static final String TABLE_HOME_ORDER = "home_order";
			    private static final String TABLE_HOTEL_ORDER = "hotel_order";
			    private static final String TABLE_H = "hot_H";
			    private static final String TABLE_S = "soup";
			    private static final String TABLE_M = "main_d";
			    private static final String TABLE_C = "cold_c";
			 // Login table name
			    private static final String TABLE_UNI_PREFS = "student_pref";
			    //The Android's default system path of your application database.
			    private static String DB_PATH = "/data/data/com.syssoft.foodmenu/databases/";
			     
			    private SQLiteDatabase myDataBase;
			    private DatabaseHandler openHelper;
			     
			    private final Context myContext;
			 
			    // Login Table Columns names
			    private static final String KEY_ID = "_id";
			    private static final String KEY_JSON = "jsonString";
			    private static final String KEY_GRID_JSON = "grid_jsonString";
			    private static final String KEY_FNAME = "fname";
			    private static final String KEY_LNAME = "lname";
			    private static final String KEY_EMAIL = "email";
			    private static final String KEY_PASSWORD = "password";
			    private static final String KEY_UID = "uid";
			    private static final String KEY_SCHOOL_ID = "school_id";
			    private static final String KEY_INST_NAME = "inst_name";
			    private static final String KEY_COURSE_NAME = "course_name";
			    private static final String KEY_CAMPUS_NAME = "campus_name";
			    private static final String KEY_YEAR = "year";
			    private static final String KEY_INST_ID = "inst_id";
			    private static final String KEY_SEMESTER_NAME = "semester"; 
			    private static final String KEY_GLOB_LEARNINGDAYS = "learningDays";
			    private static final String KEY_GLOB_STARTTIME = "startTime";
			    private static final String KEY_GLOB_DURATION = "duration";
			    private static final String KEY_GLOB_ENDTIME = "endTime";
			    private static final String KEY_CREATED_AT = "created_at";
			    
			    
			    private static final String KEY_UNIT_ID = "unit_id";
			    private static final String KEY_UNIT_DAY = "day_id";
			    private static final String KEY_UNIT_STARTTIME = "starttime"; 
			    private static final String KEY_UNIT_ENDTIME = "endtime";
			    private static final String KEY_UNIT_CODE = "code";
			    private static final String KEY_UNIT_COLOR = "color";
			    private static final String KEY_UNIT_TITLE = "title";
			    private static final String KEY_UNIT_TEACHER = "teacher";
			    private static final String KEY_UNIT_LOCATION = "location";
			    
			    private static final String KEY_PHONENO = "phoneno";
			    private static final String KEY_LOCATION = "location";
			    
			    private static final String TABLE_FOODNAME = "food_name";
			    private static final String TABLE_FOODPRICE = "food_price";
			    private static final String TABLE_STATUS = "status";
			     
			    private static final String KEY_TABLE_NO = "tableno";
			    private static final String TABLE_COLUMN_ID = "_id";
			    
                private static final String KEY_JSON_H = "hot_drinks";
			    private static final String KEY_JSON_C = "cold_drinks";
			    private static final String KEY_JSON_S = "soup";
			    private static final String KEY_JSON_M = "main_courses";
			    
			    
			 
			    public DatabaseHandler(Context context) {
			        super(context, DATABASE_NAME, null, DATABASE_VERSION);
			        this.myContext = context;
			    }
			 
			    /**
			     * Creates a empty database on the system and rewrites it with your own database.
			     * */
			    public void createDataBase() throws IOException{
			    
			    	boolean dbExist = checkDataBase();
			    
			    	if(dbExist){
			    		 //do nothing - database already exist
			    	} else {
			    	  
			    		//By calling this method and empty database will be created into the default system path
					 	//of your application so we are gonna be able to overwrite that database with our database.
			    		this.getReadableDatabase();
			    		this.close(); 
			    		try {		  
			    			copyDataBase();		  
			    		} catch (IOException e) {
					  
			    			throw new Error(e.toString());
					  
			    		}
			    	}    
			    }
			    
			    /**
			     * Check if the database already exist to avoid re-copying the file each time you open the application.
			     * @return true if it exists, false if it doesn't
			     */
			    private boolean checkDataBase(){
			    
			    	File dbFile = new File(DB_PATH + DATABASE_NAME);
			        return dbFile.exists();
			    }
			    
			    /**
			     * Copies your database from your local assets-folder to the just created empty database in the
			     * system folder, from where it can be accessed and handled.
			     * This is done by transfering bytestream.
			     * */
			    private void copyDataBase() throws IOException{
			    
			    	//Open your local db as the input stream
			    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
			    
			    	// Path to the just created empty db
			    	String outFileName = DB_PATH + DATABASE_NAME;
			    
			    	//Open the empty db as the output stream
			    	OutputStream myOutput = new FileOutputStream(outFileName);
			    
			    	//transfer bytes from the inputfile to the outputfile
			    	byte[] buffer = new byte[1024];
			    	int length;
			    	while ((length = myInput.read(buffer))>0){
			    		myOutput.write(buffer, 0, length);
			    	}
			    
			    	//Close the streams
			    	myOutput.flush();
			    	myOutput.close();
			    	myInput.close();
			    
			    }
			    
			    public void openDataBase() throws SQLException{
			    
			    	//Open the database
			    	String myPath = DB_PATH + DATABASE_NAME;
			    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			    
			    }
			    
			   	@Override
			   	public synchronized void close() {
			    
			   		if(myDataBase != null)
			   			myDataBase.close();
			    
			   		super.close();
			    
			   	}
			    
			
			    // Creating Tables
			    @Override
			    public void onCreate(SQLiteDatabase db) {
			    	
			    	String  CREATE_HOME_ORDER = "CREATE TABLE " + TABLE_HOME_ORDER + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_PHONENO + " TEXT,"
			                + KEY_LOCATION + " TEXT,"
			    	        + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_HOME_ORDER);
			        
			        
			        String  CREATE_HOTEL_ORDER = "CREATE TABLE " + TABLE_HOTEL_ORDER + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_TABLE_NO + " TEXT,"
			    	        + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_HOTEL_ORDER);
			        
			        String  CREATE_C = "CREATE TABLE " + TABLE_C + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_JSON_C + " TEXT,"
			    	        + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_C);
			        
			        String  CREATE_H = "CREATE TABLE " + TABLE_H + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_JSON_H + " TEXT,"
			    	        + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_H);
			        
			        String  CREATE_S = "CREATE TABLE " + TABLE_S + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_JSON_S + " TEXT,"
			    	        + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_S);
			        
			        String  CREATE_M = "CREATE TABLE " + TABLE_M + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_JSON_M + " TEXT,"
			    	        + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_M);
			        
			        
			        
			        
			        String buildSQL = "CREATE TABLE " + TABLE_MEALS + "( " 
				            + KEY_ID + " INTEGER PRIMARY KEY, "
				            + TABLE_FOODNAME + " TEXT, " 
				            + TABLE_FOODPRICE + " TEXT, " 
				            + TABLE_STATUS + " TEXT)";
		
		
		            db.execSQL(buildSQL);
			        
			        
			    }
			        /*
			        String CREATE_HOTEL_ORDER = "CREATE TABLE " + TABLE_HOTEL_ORDER + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_TABLENO + " TEXT,"
			                + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_HOTEL_ORDER);
			    }*/
			    	
			    	/**
			        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_FNAME + " TEXT,"
			                + KEY_LNAME + " TEXT,"
			                + KEY_EMAIL + " TEXT UNIQUE,"
			                + KEY_PASSWORD + " TEXT,"
			                + KEY_UID + " TEXT,"
			                + KEY_COURSE_NAME + " TEXT,"
			                + KEY_YEAR + " TEXT,"
			                + KEY_INST_NAME + " TEXT,"
			                + KEY_CAMPUS_NAME + " TEXT,"
			                + KEY_GLOB_ENDTIME + " TEXT,"
			                + KEY_GLOB_STARTTIME + " TEXT,"
			                + KEY_GLOB_DURATION + " TEXT,"
			                + KEY_INST_ID + " TEXT,"
			                + KEY_SCHOOL_ID + " TEXT,"
			                + KEY_SEMESTER_NAME + " TEXT,"
			                + KEY_GLOB_LEARNINGDAYS + " TEXT,"
			                + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_LOGIN_TABLE);
			        
			        String CREATE_TODAY_LESSONS_TABLE = "CREATE TABLE " + TABLE_TODAY_LESSONS + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_JSON + " TEXT,"
			                + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_TODAY_LESSONS_TABLE);
			        
			        String CREATE_GRID_LESSONS_TABLE = "CREATE TABLE " + TABLE_GRID_LESSONS + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_GRID_JSON + " TEXT,"
			                + KEY_CREATED_AT + " TEXT)";
			        db.execSQL(CREATE_GRID_LESSONS_TABLE);
			        
			        String CREATE_TABLE_LESSONS = "CREATE TABLE " + TABLE_LESSONS + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_UNIT_DAY + " TEXT,"
			                + KEY_UNIT_ID + " TEXT,"
			                + KEY_UNIT_CODE + " TEXT,"
			                + KEY_UNIT_TITLE + " TEXT,"
			                + KEY_UNIT_STARTTIME + " TEXT,"
			                + KEY_UNIT_ENDTIME + " TEXT,"
			                + KEY_UNIT_COLOR + " TEXT,"
			                + KEY_UNIT_TEACHER + " TEXT,"
			                + KEY_UNIT_LOCATION + " TEXT)";
			        db.execSQL(CREATE_TABLE_LESSONS);
			 
			    }*/
			 /*
			  * 
			  * 
			    private static final String TABLE_JSONMAINCOURSES = "main_dishes";
			    private static final String TABLE_JSONCOLDDRINKS = "cold_drinks";
			    private static final String TABLE_JSONHOTDRINKS = "hot_drinks";
			    private static final String TABLE_JSONSOUP = "Myorder";*/
			    // Upgrading database
			    @Override
			    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			        // Drop older table if existed
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME_ORDER);
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL_ORDER);
			        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
			        db.execSQL("DROP TABLE IF EXISTS " + TABLE_H);
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_C);
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_S);
			        db.execSQL("DROP TABLE IF EXISTS " + TABLE_M);
			       
			 
			        // Create tables again
			        onCreate(db);
			    }
			    /**
			     * Storing user details in database
			     * */
			    public void addUser(String fname, String lname, String email, String password, String uid, String inst_id, String school_id,String campus_name,String course_name,String year,String inst_name,String semester,  String learningDays, String startTime, String endTime, String duration) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_UID, uid); // user id
			        values.put(KEY_FNAME, fname); // First Name
			        values.put(KEY_LNAME, lname); // Last Name
			        values.put(KEY_EMAIL, email); // Email
			        values.put(KEY_PASSWORD, password); // Email
			        values.put(KEY_INST_ID, inst_id); // University
			        values.put(KEY_SCHOOL_ID, school_id); // Faculty
			        values.put(KEY_INST_NAME, inst_name); // Faculty
			        values.put(KEY_YEAR, year); // Faculty
			        values.put(KEY_COURSE_NAME, course_name); // Faculty
			        values.put(KEY_CAMPUS_NAME, campus_name); 
			        values.put(KEY_SEMESTER_NAME, semester); 
			        values.put(KEY_GLOB_LEARNINGDAYS, learningDays); 
			        values.put(KEY_GLOB_STARTTIME, startTime); 
			        values.put(KEY_GLOB_ENDTIME, endTime); 
			        values.put(KEY_GLOB_DURATION, duration); 
			 
			        // Inserting Row , 
			        db.insert(TABLE_LOGIN, null, values);
			        db.close(); // Closing database connection
			    }
			 
			    
			    /**
			     * Storing user details in database
			     * */
			    public void addHomeorder(String phoneno, String location) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_PHONENO, phoneno); // user id
			        values.put(KEY_LOCATION, location); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_HOME_ORDER, null, values);
			        db.close(); // Closing database connection
			    }
			    
			    public void addMeals(String foodname, String price) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(TABLE_FOODNAME, foodname); // user id
			        values.put(TABLE_FOODPRICE, price); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_MEALS, null, values);
			        db.close(); // Closing database connection
			    }
			    
			  
			    
			    /**
			     * Storing user details in database
			     * */
			    public void addMainfood(String Jsonmain_dishe) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_JSON_M, Jsonmain_dishe); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_M, null, values);
			        db.close(); // Closing database connection
			    }

			    /**
			     * Storing user details in database
			     * */
			    public void addH(String JsonH) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_JSON_H, JsonH); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_H, null, values);
			        db.close(); // Closing database connection
			    }
			    
			    /**
			     * Storing user details in database
			     * */
			    public void addC(String JsonC) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_JSON_C, JsonC); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_C, null, values);
			        db.close(); // Closing database connection
			    }
			    
			    
			    /**
			     * Storing user details in database
			     * */
			    public void addS(String JsonS) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_JSON_S, JsonS); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_S, null, values);
			        db.close(); // Closing database connection
			    }
			    
			    
			    /**
			     * Storing user details in database
			     * */
			    public void addHotelorder(String tableno) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_TABLE_NO, tableno); // user id
			        
			        // Inserting Row , 
			        db.insert(TABLE_HOTEL_ORDER, null, values);
			        db.close(); // Closing database connection
			    }
			    
			    public void insertMealsOrder(HashMap<String, String> queryValues) {
					SQLiteDatabase database = this.getWritableDatabase();
					ContentValues values = new ContentValues();
					 values.put(TABLE_FOODNAME, queryValues.get("food_name")); // user id
				     values.put(TABLE_FOODPRICE, queryValues.get("food_price")); // user id
				        
				        // Inserting Row , 
				    database.insert(TABLE_MEALS, null, values);
					database.close();
				}


			//Read more: http://mrbool.com/how-to-insert-data-into-a-sqlite-database-in-android/28895#ixzz3i1yxOvU1

			    
			    
			    public void addLessons(String day_id,String unit_id,String code, String title,String starttime,String endtime,String color,String teacher,String location) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_UNIT_ID, unit_id); // user id
			        values.put(KEY_UNIT_DAY, day_id); // user id
			        values.put(KEY_UNIT_CODE, code); // user 
			        values.put(KEY_UNIT_TITLE, title); // user id
			        values.put(KEY_UNIT_STARTTIME, starttime);
			        values.put(KEY_UNIT_ENDTIME, endtime); // user id
			        values.put(KEY_UNIT_COLOR, color);
			        values.put(KEY_UNIT_TEACHER, teacher); // user id
			        values.put(KEY_UNIT_LOCATION, location);
			        // Inserting Row , 
			        db.insert(TABLE_LESSONS, null, values);
			        db.close(); // Closing database connection
			    }
			    
			    /**
			     * Storing user details in database
			     * 
			    public void addUserUniDetails(String schoolId, String courseId, String year, String intake, String semester, String userId) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues scValues = new ContentValues();
			        scValues.put("course_id", courseId);
			        
			        ContentValues values = new ContentValues();
			        values.put("year", year); 
			        values.put("intake", intake); 
			        values.put("semester", semester);
			        
			        // Inserting Row
			        db.update(TABLE_LOGIN, scValues, "uid ='" + userId + "'", null); //insert(TABLE_LOGIN, null, values);
			        
			        // Inserting Row
			        db.insert(TABLE_UNI_PREFS, null, values);
			        db.close(); // Closing database connection
			    }
			 
			    /**
			     * Getting user data from database
			     * */
			    /*public HashMap<String, String> getUserDetails(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        	user.put("uid", cursor.getString(5));
			        	user.put("email", cursor.getString(3));
			        	user.put("password", cursor.getString(4));
			        	user.put("fname", cursor.getString(1));
			        	user.put("lname", cursor.getString(2));
			        	user.put("inst_id", cursor.getString(13));
			        	user.put("school_id", cursor.getString(14));
			        	user.put("learningDays", cursor.getString(16)); 
			        	user.put("duration", cursor.getString(12));
			        	user.put("startTime", cursor.getString(11)); 
			        	user.put("endTime", cursor.getString(10));
			        	user.put("course_name", cursor.getString(6));
			        	user.put("year", cursor.getString(7));
			        	user.put("inst_name", cursor.getString(8));
			        	user.put("campus_name", cursor.getString(9));
			        	user.put("semester", cursor.getString(15));
			        	
			            
			        }
			        cursor.close();
			        db.close();
			        // return user
			        return user;
			    }*/   
			    /**
			     * Get list of Users from SQLite DB as Array List
			     * @return
			    
			    public List<Lesson> getAllLessons() {
			        List<Lesson> wordList;
			        wordList = new ArrayList<Lesson>();
			        String day_id = DayListLessonsFragment.dayId;
			        String selectQuery = "SELECT * FROM " + TABLE_LESSONS + " WHERE " + KEY_UNIT_DAY + " = '" + day_id + "'";
			        Log.i("getAllDayLessons SQL.........: " + selectQuery, selectQuery);
			        SQLiteDatabase database = this.getWritableDatabase();
			        Cursor cursor = database.rawQuery(selectQuery, null);
			        if (cursor.moveToFirst()) {
			            do {
			                //Lesson lesson = new Lesson();
			            	Log.i("Data...Day Lessons.........  ", day_id);
			                String lessonId = cursor.getString(2);
				    		String lessonCode = cursor.getString(3);
				    		String lessonColorband = cursor.getString(7);
				        	String lessonTitle = cursor.getString(4);
				        	String lessonTeacher = cursor.getString(8);
				        	String lessonStartTime = cursor.getString(5);
				        	String lessonEndTime = cursor.getString(6);
				        	String  lessonLocation = cursor.getString(9);
				        	
				            // Loop round our JSON list of lessons creating Lesson objects to use within our app
			                // Create the video object and add it to our list
				        	wordList.add(new Lesson(lessonId, lessonCode,lessonColorband, lessonTitle, lessonTeacher, lessonStartTime, lessonEndTime, lessonLocation, null, null));
			            
				        	
			            } while (cursor.moveToNext());
			        }
			        database.close();
			        return wordList;
			    } */
			    
			    public void delete_byID(int id){
			    	myDataBase.delete(TABLE_MEALS, KEY_ID+"="+id, null);
			    	}
				
			    /**
			     * Getting user data from database
			     * */
			    public HashMap<String, String> getC(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_C;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        
			        	user.put("jsonString", cursor.getString(1));
			        	
			            
			        }
			        cursor.close();
			        db.close();
			        // return user
			        return user;
			    }
			 
			    /**
			     * Getting user data from database
			     * */
			    public HashMap<String, String> getH(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_H;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        
			        	user.put("jsonString", cursor.getString(1));
			        	
			            
			        }
			        cursor.close();
			        db.close();
			        // return user
			        return user;
			    }
			
			    /**
			     * Getting user data from database
			     * */
			    public HashMap<String, String> getS(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_S;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        
			        	user.put("jsonString", cursor.getString(1));
			        	
			            
			        }
			        cursor.close();
			        db.close();
			        // return user
			        return user;
			    }
			 
			    /**
			     * Getting user data from database
			     * */
			    public HashMap<String, String> getM(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_M;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        
			        	user.put("jsonString", cursor.getString(1));
			        	
			            
			        }
			        cursor.close();
			        db.close();
			        // return user
			        return user;
			    }
			    
			    /**
			     * Getting user data from database
			     * */
			    public HashMap<String, String> getUserUniDetails(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_UNI_PREFS;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        	user.put("year", cursor.getString(1));
			        	user.put("intake", cursor.getString(2));
			        	user.put("semester", cursor.getString(3));            
			        }
			        cursor.close();
			        db.close();
			        // return user
			        return user;
			    }
			 
			    
			    public Cursor getAllMealsData () {
			    	String buildSQL = "SELECT * FROM " + TABLE_MEALS ;
			        //Log.d(TAG, "getAllMealsData SQL: " + buildSQL);
			
			        return  myDataBase.rawQuery(buildSQL, null);
			    }
			   
			  
			    public void delete(long id) {

			    	 myDataBase = openHelper.getWritableDatabase();
			    	 myDataBase.delete(TABLE_MEALS, TABLE_COLUMN_ID + "=?",
			                new String[] { String.valueOf(id) }); // KEY_ID= id of row and third parameter is argument.
			    	 myDataBase.close();
			    } 
			    
			    
			    
			    public void remove(long id){
			    	
			    	 myDataBase = openHelper.getWritableDatabase();
			    	 myDataBase.delete(TABLE_MEALS, TABLE_COLUMN_ID + "=?",
			                new String[] { String.valueOf(id) }); // KEY_ID= id of row and third parameter is argument.
			    	 myDataBase.close();
			        //String string =String.valueOf(id);
			       // database.execSQL("DELETE FROM Comments WHERE _id = '" + string + "'");
			    }
			    
			    
			    /**
			     * Getting user login status
			     * return true if rows are there in table
			     * */
			    
			    public int getRowCount() { 
			        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(countQuery, null);
			        int rowCount = cursor.getCount();
			        db.close();
			        cursor.close();
			 
			        // return row count
			        return rowCount;
			    }
			 
			    /**
			     * Getting user login status
			     * return true if rows are there in table
			     * */
			    public int TbDayLessonsgetRowCount() { 
			        String countQuery = "SELECT  * FROM " + TABLE_LESSONS;
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(countQuery, null);
			        int rowCount = cursor.getCount();
			        db.close();
			        cursor.close();
			 
			        // return row count
			        return rowCount;
			    }
			 
			    /**
			     * db.execSQL("DROP TABLE IF EXISTS " + TABLE_JSONMAINCOURSES);
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_JSONCOLDDRINKS);
			    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_JSONHOTDRINKS);
			        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JSONSOUP);
			     * Re crate database
			     * Delete all tables and create them again
			     * */
			    public void resetTables(){
			        SQLiteDatabase db = this.getWritableDatabase();
			        // Delete All Rows
			        db.delete(TABLE_LOGIN, null, null);
			        db.delete(TABLE_TODAY_LESSONS, null, null);
			        db.delete(TABLE_HOME_ORDER, null, null);
			        db.delete(TABLE_HOTEL_ORDER, null, null);
			        db.delete(TABLE_C, null, null);
			        db.delete(TABLE_M, null, null);
			        db.delete(TABLE_S, null, null);
			        db.delete(TABLE_H, null, null);
			        db.delete(TABLE_GRID_LESSONS, null, null);
			        db.close();
			    }
			    
			    /* Re crate database
			     * Delete all tables and create them again
			     * */
			    public void resetTable_Myorder(){
			        SQLiteDatabase db = this.getWritableDatabase();
			        // Delete All Rows
			        db.delete(TABLE_MEALS, null, null);
			        db.close();
			    }
			 
}