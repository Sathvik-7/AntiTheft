package com.anwar.antitheft;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyService extends Service {
	SQLiteDatabase mydb;
	Dbhandler myDbhelper;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		super.onCreate();
	}

	private void FetchingData() {
		// TODO Auto-generated method stub
		try {

			myDbhelper.onCreateDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}
		try {

			myDbhelper.openDataBase();
			mydb = myDbhelper.getWritableDatabase();
			System.out.println("executed");

		} catch (SQLException sqle) {

			throw sqle;

		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub

		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSS");
		TelephonyManager telephoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = telephoneMgr.getLine1Number();
		this.myDbhelper = new Dbhandler(this);
		FetchingData();
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSS");

		mydb = myDbhelper.getWritableDatabase();
		Cursor c = mydb.rawQuery("select * from SimNumber", null);
		c.moveToFirst();
		if (c != null) {
			do {
				int c1 = c.getColumnIndex("SimNo");
				String SimNo = c.getString(c1);
				System.out.println("SSSSSSSSS@@@@SSSSSSSSSSSSSS" + SimNo);

				if (phoneNumber.equals(SimNo)) {
					System.out.println("SSSSSSvdgdfgdfgdSSS@@@@SSSSSSSSSSSSSS"
							+ SimNo);

					Toast.makeText(getApplicationContext(), "yesss", Toast.LENGTH_LONG).show();

				} else {
					Cursor c2 = mydb
							.rawQuery("select * from PhoneNumber", null);
					c2.moveToFirst();
					if (c2 != null) {
						do {
							int c3 = c.getColumnIndex("PhoneNumberr");
							String phno = c.getString(c3);
							SmsManager sms = SmsManager.getDefault();
							sms.sendTextMessage(phno, null,
									"Your Lost Mobile Sim Changed to :"
											+ phoneNumber, null, null);
						} while (c.moveToNext());
					}
				}
			} while (c.moveToNext());
		}

		return super.onStartCommand(intent, flags, startId);
	}

}
