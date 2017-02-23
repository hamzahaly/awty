package hamzaha.washington.edu.awty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    public MyReceiver receiver;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupServiceReceiver();

        final EditText message = (EditText) findViewById(R.id.message_text);
        final EditText phoneNumber = (EditText) findViewById(R.id.phone_text);
        final EditText minutes = (EditText) findViewById(R.id.minutes_text);


        final Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setEnabled(false);
        startButton.setClickable(false);

        final Intent intent = new Intent(this, SendMessagesService.class);
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
                    Toast.makeText(getBaseContext(), "Please Enter Valid Values for Fields", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String i = (String) startButton.getTag();
                Log.v("TAG", "" + i);
                intent.putExtra("Minutes", minutes.getText().toString());
                intent.putExtra("Message", message.getText().toString());
                intent.putExtra("receiver", receiver);
                startService(intent);
                if (i == "1") {
                    startButton.setText("Start");
                    view.setTag("0");
                    //sendMessage();
                } else {
                    startButton.setText("Stop");
                    view.setTag("1");
                    sendMessage();
                }

            }
        });
    }

    public void sendMessage() {
        Intent intent = new Intent(this, MessageBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 1000, AlarmManager., pendingIntent);
        Toast.makeText(this, "Alarm set in 1 second", Toast.LENGTH_SHORT).show();

    }

    public void setupServiceReceiver() {
        receiver = new MyReceiver(new Handler());

        receiver.setReceiver(new MyReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == RESULT_OK) {
                    String resultValue = resultData.getString("resultValue");
                    Toast.makeText(MainActivity.this, resultValue, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
