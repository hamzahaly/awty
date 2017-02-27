package hamzaha.washington.edu.awty;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    //public MyReceiver receiver;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.custom_toast);

        Activity mainActivity = MainActivity.this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mainActivity.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "Permission to send SMS not granted");

                mainActivity.requestPermissions(new String[] {
                        Manifest.permission.SEND_SMS }, 1);
            }
        }


        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final EditText message = (EditText) findViewById(R.id.message_text);
        final EditText phoneNumber = (EditText) findViewById(R.id.phone_text);
        final EditText minutes = (EditText) findViewById(R.id.minutes_text);

        final Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setEnabled(false);
        startButton.setClickable(false);

        final Intent intent = new Intent(this, MessageBroadcastReceiver.class);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);

        //Custom Toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        final TextView caption =  (TextView) layout.findViewById(R.id.caption);
        final TextView body = (TextView) layout.findViewById(R.id.body);
        final Toast customToast = new Toast(getApplicationContext());
        customToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(layout);

        message.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Check for valid message
                if (!(message.getText().toString() == null) && !(message.getText().toString().trim().isEmpty())) {
                    Log.v("TAG", message.getText().toString());
;                   flag = true;
                } else {
                    Log.v("TAG", "EMPTY");
                    flag = false;
                }

                return false;
            }
        });

        phoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Check for valid phone number
                if (!(phoneNumber.getText().toString() == null) && !(phoneNumber.getText().toString().trim().isEmpty()) && phoneNumber.getText().toString().length() == 10) {
                    Log.v("TAG", phoneNumber.getText().toString());
                    flag = true;
                } else {
                    Log.v("TAG", "Not Valid");
                    flag = false;
                }

                return false;
            }
        });

        minutes.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //Check for valid minutes entered
                if (!((minutes.getText().toString()) == null) && !(minutes.getText().toString().trim().isEmpty())) {
                    Log.v("TAG", minutes.getText().toString());
                    flag = true;
                    //Log.v("TAG", flag.toString());
                } else {
                    Log.v("TAG", "EMPTY");
                    flag = false;
                }

                //Enable start button if all values are valid.
                if (flag) {
                    startButton.setClickable(true);
                    startButton.setEnabled(true);
                } else {
                    startButton.setClickable(false);
                    startButton.setEnabled(false);
                }
                return false;
            }
        });

        //Start button to begin sending messages
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String i = (String) startButton.getTag();
                Log.v("TAG", "" + i);

                intent.putExtra("Message", message.getText().toString());
                intent.putExtra("Phone", phoneNumber.getText().toString());

                if (i == "1") {
                    startButton.setText("Start");
                    view.setTag("0");
                    alarmManager.cancel(pendingIntent);
                } else {
                    startButton.setText("Stop");
                    view.setTag("1");
                    int val = Integer.parseInt(minutes.getText().toString());
                    sendMessage(val, alarmManager, pendingIntent);
                    caption.setText("Texting" + phoneNumber);
                    body.setText("" + message);

                }
            }
        });
    }

    public void sendMessage(int minutes, AlarmManager alarmManager, PendingIntent pendingIntent) {
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 1000, 60000 * minutes, pendingIntent);
    }
}
