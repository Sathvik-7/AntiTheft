package com.anwar.antitheft;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class SmsReader extends BroadcastReceiver implements LocationListener {
	
	GPSTracker gps;

	
	double latitude, longitude;
	public static final String SMS_BUNDLE = "pdus";
	protected LocationManager locationManager;
	Location location;
	public static Location lastbestlocation_trck = null;
	/*private static final long MIN_DISTANCE_FOR_UPDATE = 10;
	private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

	private final static String LOG_TAG = "TinyUrl";
	private static String T_URL = "http://tinyurl.com/api-create.php?url=";
*/
	public void onReceive(Context context, Intent intent) {
         gps = new GPSTracker(context);
		Bundle intentExtras = intent.getExtras();
		if (intentExtras != null) {
			Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
			String smsMessageStr = "";
			for (int i = 0; i < sms.length; ++i) {
				SmsMessage smsMessage = SmsMessage
						.createFromPdu((byte[]) sms[i]);

				String smsBody = smsMessage.getMessageBody().toString();
				// String address = smsMessage.getOriginatingAddress();

				// smsMessageStr += "SMS From: " + address + "\n";
				smsMessageStr = smsBody;
				Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT)
						.show();
				// getting from sharedPreferences
				SharedPreferences myPrefs = context.getSharedPreferences(
						"MyPrefs1", Context.MODE_PRIVATE);
				String wordString = myPrefs.getString("wordKey", "");
				
				
				TelephonyManager tMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	    		String	mPhoneNumber = tMgr.getLine1Number();
	    		
				String phno = myPrefs.getString("phonenumber", "");
				if(mPhoneNumber.equals(phno)){
					String ph = myPrefs.getString("phoneKey", "");
		    		String	mPhoneNumber1 = tMgr.getLine1Number();

					Toast.makeText(context,"ddddddd"+ ph, Toast.LENGTH_LONG).show();
		            String stringLatitude = String.valueOf(gps.latitude);

		            String stringLongitude = String.valueOf(gps.longitude);
					String body = "http://maps.google.fr/maps?f=q&source=s_q&hl=fr&geocode=&q="
							+ stringLatitude + "," + stringLongitude;

					SmsManager sms1 = SmsManager.getDefault();
					sms1.sendTextMessage(ph, null,
							"Your Mobile Sim is Changed to "+mPhoneNumber1+"  Current Location Find in below URL :"
									+ body, null, null);

				}

				if (wordString.equals(smsMessageStr)) {
					String ph = myPrefs.getString("phoneKey", "");
					Toast.makeText(context, ph, Toast.LENGTH_LONG).show();
					 String stringLatitude = String.valueOf(gps.latitude);

			            String stringLongitude = String.valueOf(gps.longitude);
						String body = "http://maps.google.fr/maps?f=q&source=s_q&hl=fr&geocode=&q="
								+ stringLatitude + "," + stringLongitude;

					SmsManager sms1 = SmsManager.getDefault();
					sms1.sendTextMessage(ph, null,
							"Your Mobile Current Location Find in below URL :"
									+ body, null, null);
					Toast.makeText(context, wordString + body,
							Toast.LENGTH_LONG).show();

				} else {
					// Toast.makeText(context, " Sorry ",
					// Toast.LENGTH_LONG).show();
				}
			}

			// this will update the UI with message

		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		try {

			if (isBetterLocation(location, lastbestlocation_trck)) {
				lastbestlocation_trck = location;
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			if (location.getProvider().contains("gps")) {
				return true;
			} else {
			}
		}
		return false;
	}
}