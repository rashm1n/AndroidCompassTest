package com.fyp.ble.compass_test_1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private int currentDegree = 220;
    public int requiredDegree = 280;

    private SensorManager mSensorManager;
    TextView textView;
    TextToSpeech tts;
    Button b;
    EditText e;
    boolean flag1 = false;
    boolean flag2 = false;
    boolean flag3 = false;

    int sensorCount = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        int degreeInt = (int)degree;
//        this.currentDegree = degree;
        textView.setText(Float.toString(degree));


//        if (this.requiredDegree<degreeInt+5 && this.requiredDegree>degreeInt-5 && !flag1){
//            Log.d("test","correct path");
//            convertTextToSpeech("go straight");
//            flag1 =true;
//            flag2 = false;
//            flag3 = false;
//
//        }else if (this.requiredDegree<degreeInt-5 && !flag2){
//            Log.d("test","turn left");
//            convertTextToSpeech("turn left");
//            flag2 = true;
//            flag1 = false;
//            flag3 = false;
//
//        }else if (this.requiredDegree>degreeInt+5 && !flag3){
//            Log.d("test","turn right");
//            convertTextToSpeech("turn right");
//            flag3 = true;
//            flag2 = false;
//            flag1 = false;
//        }

        if (sensorCount==100){
            if (this.requiredDegree<degreeInt+5 && this.requiredDegree>degreeInt-5){
                Log.d("test","correct path");
                convertTextToSpeech("go straight");


            }else if (this.requiredDegree<degreeInt-5){
                Log.d("test","turn left");
                convertTextToSpeech("turn left");


            }else if (this.requiredDegree>degreeInt+5){
                Log.d("test","turn right");
                convertTextToSpeech("turn right");

            }

            sensorCount=0;
        }else {
            sensorCount++;
        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button)findViewById(R.id.button);
        e = (EditText)findViewById(R.id.editText);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requiredDegree = Integer.parseInt(e.getText().toString());
            }
        });


        //Configure text to speech
        tts=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    tts.setSpeechRate((float) 0.92);
//                    tts.speak("Indoor Navigation Application Opened. Tap Anywhere on the screen to begin initializing", TextToSpeech.QUEUE_FLUSH, null);

                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });

        textView =(TextView)findViewById(R.id.newbox);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }


    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    private void convertTextToSpeech(String s) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }
}
