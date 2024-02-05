package com.anwar.antitheft;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends Activity {
EditText word,number;
Button ok,cancel;
public static final String MyPREFERENCES = "MyPrefs1" ;
public static final String Word = "wordKey";
public static final String Phone = "phoneKey";
SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first);
		word=(EditText)findViewById(R.id.magicWord);
		number=(EditText)findViewById(R.id.phNumber);
		ok=(Button)findViewById(R.id.button);
		
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		word.setText(sharedpreferences.getString("wordKey", ""));
		number.setText(sharedpreferences.getString("phoneKey", ""));
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String wd  = word.getText().toString();
	            String ph  = number.getText().toString();
	            TelephonyManager tMgr=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    		String	mPhoneNumber = tMgr.getLine1Number();
	    		
	            SharedPreferences.Editor editor = sharedpreferences.edit();
	            editor.putString(Word, wd);
	            editor.putString(Phone, ph);
	            editor.putString("phonenumber", mPhoneNumber);
	            editor.commit();
	            Toast.makeText(FirstActivity.this,"Successfully Stored",Toast.LENGTH_LONG).show();
	            finish();
	            
	            
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_first, menu);
		return true;
	}

}
