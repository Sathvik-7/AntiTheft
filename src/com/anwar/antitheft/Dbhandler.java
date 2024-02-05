package com.anwar.antitheft;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhandler extends SQLiteOpenHelper{

		//The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.anwar.antitheft/databases/";
	 
    private static String DB_NAME = "SimChangeAlert.sqlite";
    private SQLiteDatabase myDataBase;   
    int trainid ;
    private final Context myContext;
    Cursor c1,c2;
    String a[];
	public Dbhandler(Context context) {
		super(context, DB_NAME, null, 1);
        this.myContext = context;
	}
	public int onCreateDataBase() throws IOException
	{
		boolean dbExist=checkDatabase();
		if(dbExist){
    		 return 0;
    	}else{
    		 System.out.println("onCreateDataBase method execution starts");
    	
        	this.getReadableDatabase();
 
        	
 
    			copyDataBase();
    			return 1;	
    		
    	}
	}

	private boolean checkDatabase() {
		// TODO Auto-generated method stub
		SQLiteDatabase checkDB=null;
		try
		{

			String myPath = DB_PATH + DB_NAME;
			
			checkDB=SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(checkDB != null){
			 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true: false ;
		
	
	}
	
	private void copyDataBase() throws IOException{
		
	InputStream myInput=myContext.getAssets().open(DB_NAME);
	
	
	// Path to the just created empty db
	String outFileName = DB_PATH + DB_NAME;

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
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    
    }
 
   
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 

	
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		

	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
public ArrayList<String> getModes(SQLiteDatabase db){
	System.out.println("$$$$$$$$$$$$########"+DB_NAME);
	System.out.println("$$$$$$$$$$$$########"+myDataBase);
	String P_query123=" SELECT * FROM PhoneNumber ";

	 ArrayList<String> list=new ArrayList<String>();
	 try{
	 Cursor c=db.rawQuery(P_query123, null);
	 if(c.moveToFirst())  
		{  
			do{ 	   
				list.add(c.getString(c.getColumnIndex("PhoneNumberr")));           
			}while(c.moveToNext());  
		}
			c.close();
	 }catch(SQLiteException se){
		 myDataBase.close();
		 
		 
	 }
	 
	 return list;
	
	
}

}

	



