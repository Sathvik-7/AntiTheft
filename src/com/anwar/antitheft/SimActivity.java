package com.anwar.antitheft;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimActivity extends Activity {
	Dbhandler myDbHelper;
	SQLiteDatabase Mydatabase;
	ArrayList<String> aa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sim);
		Button bt = (Button) findViewById(R.id.button1);
		final EditText et = (EditText) findViewById(R.id.editText1);

		this.myDbHelper = new Dbhandler(this);
		FetchingData();
		Mydatabase = myDbHelper.getReadableDatabase();
		aa = this.myDbHelper.getModes(Mydatabase);
		System.out.println("values of Db      " + aa);
		String s1 = aa.toString();
		s1 = s1.substring(1, s1.length() - 1).trim();

		et.setText(s1);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String stxt = et.getText().toString();
				TelephonyManager telephoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String phoneNumber = telephoneMgr.getLine1Number();
				myDbHelper = new Dbhandler(SimActivity.this);
				FetchingData();
				Mydatabase = myDbHelper.getReadableDatabase();
				Mydatabase.execSQL("update SimNumber set SimNo='" + phoneNumber
						+ "' where SNO='1'");
				Mydatabase.execSQL("update PhoneNumber set PhoneNumberr='"
						+ stxt + "' where SNO='1'");

				Toast.makeText(getApplicationContext(), "Updated Successfully",
						Toast.LENGTH_LONG).show();
			}
		});

	}

	private void FetchingData() {

		// TODO Auto-generated method stub
		try {

			myDbHelper.onCreateDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}
		try {

			myDbHelper.openDataBase();
			Mydatabase = myDbHelper.getWritableDatabase();
			System.out.println("executed");

		} catch (SQLException sqle) {

			throw sqle;

		}
		// TODO Auto-generated method stub

	}

	
}
