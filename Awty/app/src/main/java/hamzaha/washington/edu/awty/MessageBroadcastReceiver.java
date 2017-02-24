package hamzaha.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by iguest on 2/22/17.
 */

public class MessageBroadcastReceiver extends BroadcastReceiver {

    private String phoneNumber;
    private String message;

    public void onReceive(Context context, Intent intent) {
        Log.v("TAG", "MessageBroadCastReceiever Fired");
        phoneNumber = intent.getStringExtra("Phone");
        message = intent.getStringExtra("Message");

        Toast.makeText(context, phoneNumber + ": Are we there yet?", Toast.LENGTH_SHORT).show();
    }

    public void sendMessage(Toast toast) {

    }
}
