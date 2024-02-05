package com.anwar.antitheft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootListener extends BroadcastReceiver {

@Override
public void onReceive(Context context, Intent arg1) {
    Intent intent = new Intent(context,MyService.class);
    context.startService(intent);
}
}