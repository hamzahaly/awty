package hamzaha.washington.edu.awty;

import android.app.AlarmManager;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by iguest on 2/22/17.
 */

public class SendMessagesService extends IntentService {

    public SendMessagesService() {
        super("SendMessagesService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String message = workIntent.getStringExtra("Message");
        String minutes = workIntent.getStringExtra("Minutes");

        //Alarm Manager goes here
        ResultReceiver receiver = workIntent.getParcelableExtra("receiver");

        Bundle bundle = new Bundle();
        bundle.putString("resultValue", "Every " + minutes + " minutes " + "I say: " + message);

        receiver.send(MainActivity.RESULT_OK, bundle);
    }
}
