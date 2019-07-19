package com.example.a10017078.trumpbot;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView state, condition;
    BroadcastReceiver br;
    Bundle bundle;
    Object[] pdus;
    SmsMessage[] sms;
    SmsManager smsManager;
    Handler handler;
    String msg;
    int rotate;
    String[] greetings, news, explanation, closing;

    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = findViewById(R.id.id_state);
        condition = findViewById(R.id.id_condition);
        handler = new Handler();
        msg = "";
        rotate = 0;
        greetings = new String[]{"Hello, this is Donald Trump.", "This is really me. The president of the US", "I got your number to tell you something"};
        news = new String[]{"This is some unsettling news", "You're going to have to leave the US", "You are being deported. We will give you two weeks to pack your belongings"};
        explanation = new String[]{"You are actually an illegal immigrant", "Your great-grandfather had escaped Armenia in 1916 during the Armenian genocide and smuggled to the US illegally", "Well as per my new immigration policy, any descendant of an illegal immigrant is to be deported to their ancestral country"};
        closing = new String[]{"I hate all immigrants. DEPORT 'EM ALL!", "I'm the president. I do whatever I want", "Me no habla ingles"};

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d("TAG","Here");

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        else {

            br = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    bundle = intent.getExtras();
                    bundle.putSerializable("pdus", intent.getSerializableExtra("pdus"));
                    pdus = (Object[]) bundle.get("pdus");
                    sms = new SmsMessage[pdus.length];

                    for (int i = 0; i < pdus.length; i++) {
                        sms[i] = SmsMessage.createFromPdu((byte[]) pdus[i], (String)bundle.get("format"));
                        msg = (sms[i].getMessageBody());
                    }
                    handler.postDelayed(getRunnable(), 3000);
                }
            };

        }

    }

    public Runnable getRunnable(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                smsManager = SmsManager.getDefault();

                if (rotate < 3) {
                    state.setText("Greeting state");
                    condition.setText("");

                    if (msg.contains("Hi") || msg.contains("Hello") || msg.contains("Yo") || msg.contains("Hey") || msg.contains("Who")) {
                        smsManager.sendTextMessage("5556", null, greetings[0], null, null);
                        rotate++;
                    } else if ((msg.contains("What") || msg.contains("real") || msg.contains("Really") || msg.contains("No way") || msg.contains("Ok")) && rotate == 1) {
                        smsManager.sendTextMessage("5556", null, greetings[1], null, null);
                        rotate++;
                    } else if ((msg.contains("What") || msg.contains("Why") || msg.contains("Ok")) && rotate == 2) {
                        smsManager.sendTextMessage("5556", null, greetings[2], null, null);
                        rotate++;
                    } else if (msg.contains("Bye")) {
                        smsManager.sendTextMessage("5556", null, "No do not say bye! I need to tell you something important!", null, null);
                        condition.setText("User said bye. Generating response");
                    } else {
                        condition.setText("Unknown Message Generating random response");
                        int random = (int) (Math.random() * 2) + 1;
                        if (random == 1)
                            smsManager.sendTextMessage("5556", null, "I don't understand", null, null);
                        else
                            smsManager.sendTextMessage("5556", null, "Stick to the point", null, null);
                    }
                }

                else if (rotate < 6) {
                    state.setText("Informing state");
                    condition.setText("");

                    if ((msg.contains("What") || msg.contains("Huh") || msg.contains("Ok")) && rotate == 3) {
                        smsManager.sendTextMessage("5556", null, news[0], null, null);
                        rotate++;
                    } else if ((msg.contains("What") || msg.contains("Huh") || msg.contains("is it") || msg.contains("Tell me") || msg.contains("Ok")) && rotate == 4) {
                        smsManager.sendTextMessage("5556", null, news[1], null, null);
                        rotate++;
                    } else if ((msg.contains("Why") || msg.contains("What") || msg.contains("For") || msg.contains("Huh") || msg.contains("Ok")) && rotate == 5) {
                        smsManager.sendTextMessage("5556", null, news[2], null, null);
                        rotate++;
                    } else if (msg.contains("Bye")) {
                        smsManager.sendTextMessage("5556", null, "No do not say bye! I am not done yet!", null, null);
                        condition.setText("User said bye. Generating response");
                    } else {
                        condition.setText("Unknown Message Generating random response");
                        int random = (int) (Math.random() * 2) + 1;
                        if (random == 1)
                            smsManager.sendTextMessage("5556", null, "I don't understand", null, null);
                        else
                            smsManager.sendTextMessage("5556", null, "Stick to the point", null, null);
                    }
                }

                else if (rotate < 9) {
                    state.setText("Explanation state");
                    condition.setText("");

                    if ((msg.contains("Why") || msg.contains("Huh") || msg.contains("what") || msg.contains("What") || msg.contains("Ok")) && rotate == 6) {
                        smsManager.sendTextMessage("5556", null, explanation[0], null, null);
                        rotate++;
                    } else if ((msg.contains("How") || msg.contains("But") || msg.contains("citizen") || msg.contains("born") || msg.contains("American") || msg.contains("Ok")) && rotate == 7) {
                        smsManager.sendTextMessage("5556", null, explanation[1], null, null);
                        rotate++;
                    } else if ((msg.contains("But") || msg.contains("citizen") || msg.contains("born") || msg.contains("raised") || msg.contains("So") || msg.contains("Ok")) && rotate == 8) {
                        smsManager.sendTextMessage("5556", null, explanation[2], null, null);
                        rotate++;
                    } else if (msg.contains("Bye")) {
                        smsManager.sendTextMessage("5556", null, "No do not say bye! I am not done yet!", null, null);
                        condition.setText("User said bye. Generating response");
                    } else {
                        condition.setText("Unknown Message Generating random response");
                        int random = (int) (Math.random() * 2) + 1;
                        if (random == 1)
                            smsManager.sendTextMessage("5556", null, "I don't understand", null, null);
                        else
                            smsManager.sendTextMessage("5556", null, "Stick to the point", null, null);
                    }
                }

                else if (rotate < 12) {
                    state.setText("Closing state");
                    condition.setText("");

                    if ((msg.contains("Why") || msg.contains("I don't care") || msg.contains("not fair") || msg.contains("No") || msg.contains("Ok")) && rotate == 9) {
                        smsManager.sendTextMessage("5556", null, closing[0], null, null);
                        rotate++;
                    } else if ((msg.contains("But") || msg.contains("can't") || msg.contains("citizen") || msg.contains("hate") || msg.contains("You") || msg.contains("Bye") || msg.contains("Ok")) && rotate == 10) {
                        smsManager.sendTextMessage("5556", null, closing[1], null, null);
                        rotate++;
                    } else if ((msg.contains("Stop") || msg.contains("Help") || msg.contains("can't") || msg.contains("hate") || msg.contains("You") || msg.contains("Bye") || msg.contains("Ok")) && rotate == 11) {
                        smsManager.sendTextMessage("5556", null, closing[2], null, null);
                        rotate++;
                    } else {
                        condition.setText("Unknown Message Generating random response");
                        int random = (int) (Math.random() * 2) + 1;
                        if (random == 1)
                            smsManager.sendTextMessage("5556", null, "I don't understand", null, null);
                        else
                            smsManager.sendTextMessage("5556", null, "Stick to the point", null, null);
                    }
                }
            }
        };
        return runnable;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(br, intentFilter);
    }

}
