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
    public void onReceive(Context context, Intent intent) {
        Log.v("TAG", "MessageBroadCastReceiever Fired");

        Toast.makeText(context, "ALARM", Toast.LENGTH_SHORT).show();
    }
}
