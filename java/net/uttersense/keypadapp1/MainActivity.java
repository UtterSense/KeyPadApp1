package net.uttersense.keypadapp1;

import androidx.appcompat.app.AlertDialog;
import net.uttersense.libkeypad.KeypadManager;
import net.uttersense.libkeypad.IKeypadManagerListener;
import net.uttersense.libds3231.DS3231Manager;
import net.uttersense.libds3231.IDS3231Listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public String DEBUG_LOG_TAG ="LOG";

    //KeypadManager attributes:
    private KeypadManager mKeypadManager;
    private KeypadHandler mKeypadHandler;

    //DS3231Manager attributes;
    private DS3231Manager mDS3231Manager;
    private DS3231Handler mDS3231Handler;

    //Attributes:
    TextView tv_keypad,tv_ds3231;
    String text_keypad,text_ds3231;
    int mTimerInt = 5; //Timer interval (s)



    //Define handler for keypad driver events:
    class KeypadHandler implements IKeypadManagerListener {

        @Override
        public void onKeypadEvent(String str)
        {
            updateTextView(str);

        }

    }

    //Define handler for keypad driver events:
    class DS3231Handler implements IDS3231Listener {

        @Override
        public void onDS3231Event(String str)
        {
            updateDS3231TextView(str);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // Example of a call to a native method
        tv_keypad = findViewById(R.id.tv_text1);

        //Add scroller functionality to textview:
        tv_keypad.setMovementMethod(new ScrollingMovementMethod());
        tv_keypad.setVerticalScrollBarEnabled(true);

        //text = text + "Last line!";
        text_keypad = "";
        tv_keypad.setText(text_keypad);

        tv_ds3231 = findViewById(R.id.tv_text2);

        //Add scroller functionality to textview:
        tv_ds3231.setMovementMethod(new ScrollingMovementMethod());
        tv_ds3231.setVerticalScrollBarEnabled(true);

        text_ds3231 = "";
        tv_ds3231.setText(text_ds3231);


    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.i(DEBUG_LOG_TAG, "In onStart()");

        //Instantiate Keypad manager:
        mKeypadManager = KeypadManager.getInstance();
        Log.i(DEBUG_LOG_TAG, "Instantiated KeypadManager");
        //Instantiate handler:
        mKeypadHandler = new KeypadHandler();
        Log.i(DEBUG_LOG_TAG, "Instantiated KeypadHandler");
        //Register the event handler with KeypadManager:
        mKeypadManager.registerListener(mKeypadHandler);
        Log.i(DEBUG_LOG_TAG, "Registered Keypad Listener");

        //Instantiate DS3231 manager:
        mDS3231Manager = DS3231Manager.getInstance();
        Log.i(DEBUG_LOG_TAG, "Instantiated DS3231Manager");
        //Instantiate handler:
        mDS3231Handler = new DS3231Handler();
        Log.i(DEBUG_LOG_TAG, "Instantiated DS3231Handler");
        //Register the event handler with DS3231Manager:
        mDS3231Manager.registerListener(mDS3231Handler);
        Log.i(DEBUG_LOG_TAG, "Registered DS3231 Listener");

   }

    @Override
    protected void onStop() {
        super.onStop();

        //Unregister the event handler with KeypadManager:
        mKeypadManager.unregisterListener(mKeypadHandler);
        //Unregister the event handler with DS3231Manager:
        mDS3231Manager.unregisterListener(mDS3231Handler);


    }


    //Click handler for buttons
    public void onClick(View view) {
        String mes;
        switch (view.getId()) {
            //Keypad events
            case R.id.clearBtn1:
                tv_keypad.setText("");
                tv_keypad.setText(mKeypadManager.stringFromJNI());
                break;
            case R.id.initBtn1:
                //initialise();
                mKeypadManager.initialise();
                mes = "Initialising device..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();

                break;
            case R.id.toggleBtn1:
                //toggle();
                mKeypadManager.toggle();
                mes = "Toggling LED..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.switchOnBtn1:
                //switchLED(1);
                mKeypadManager.switchLED(1);
                mes = "Switching ON LED..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                String prev_text = String.valueOf(tv_keypad.getText());
                break;
            case R.id.switchOffBtn1:
                //switchLED(0);
                mKeypadManager.switchLED(0);
                mes = "Switching OFF LED..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.closeBtn1:
                //Intent tele_intent = new Intent(this, SetUpActivity1.class);
                //startActivity(tele_intent);
                //close();
                mKeypadManager.close();
                mes = "Closing device..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
                //DS3231 Events:
            case R.id.clearBtn2:
                tv_ds3231.setText("");
                tv_ds3231.setText(mDS3231Manager.stringFromJNI());
                break;

            case R.id.initBtn2:
                //initialise();
                mDS3231Manager.initialise();
                mes = "Initialising DS3231 device..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.timerIntBtn:

                /*
                mDS3231Manager.setTimerInt();
                mes = "Setting DS32231 timer interval..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show()
                        ;
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Set DS3232 Timer Interval");

                // Set up the input
                final EditText input = new EditText(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str_int = input.getText().toString();
                        int interval = Integer.parseInt(str_int);
                        mDS3231Manager.setTimerInt(interval);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;
            case R.id.startBtn:
                //switchLED(1);
                mDS3231Manager.startDataFeed(mTimerInt);
                mes = "Start data feed from DS3231 ..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.stopBtn:
                //switchLED(0);
                mDS3231Manager.stopDataFeed();
                mes = "Stopping data feed from DS3231..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.closeBtn2:
                //Intent tele_intent = new Intent(this, SetUpActivity1.class);
                //startActivity(tele_intent);
                //close();
                mDS3231Manager.close();
                mes = "Closing DS3231 device..";
                Toast.makeText(getApplicationContext(), mes,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void updateTextView(char text)
    {
         String prev_text = String.valueOf(tv_keypad.getText());
        //Add the latest text:
        String curr_text = prev_text + text;
        tv_keypad.setText(curr_text);
    }


    public void updateTextView(String str)
    {
        String prev_text = String.valueOf(tv_keypad.getText());
        //Add the latest text:
        String curr_text = prev_text + str;
        tv_keypad.setText(curr_text);
        int scrollAmount = tv_keypad.getLayout().getLineTop(tv_keypad.getLineCount()) - tv_keypad.getHeight();
        // if there is no need to scroll, scrollAmount will be <=0
        if (scrollAmount > 0)
            tv_keypad.scrollTo(0, scrollAmount);
        else
            tv_keypad.scrollTo(0, 0);


    }

    public void updateDS3231TextView(String str)
    {

        String prev_text = String.valueOf(tv_ds3231.getText());
        //Add the latest text:
        String curr_text = prev_text + str;
        tv_ds3231.setText(curr_text);
        int scrollAmount = tv_ds3231.getLayout().getLineTop(tv_ds3231.getLineCount()) - tv_ds3231.getHeight();
        // if there is no need to scroll, scrollAmount will be <=0
        if (scrollAmount > 0)
            tv_ds3231.scrollTo(0, scrollAmount);
        else
            tv_ds3231.scrollTo(0, 0);


    }

}
