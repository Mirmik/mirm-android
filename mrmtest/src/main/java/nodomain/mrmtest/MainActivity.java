package nodomain.mrmtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AlertDialog;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.net.ServerSocket;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button haveToButton;
    SensorManager sm = null;

    TextView xViewA = null;
    TextView yViewA = null;
    TextView zViewA = null;
    TextView xViewO = null;
    TextView yViewO = null;
    TextView zViewO = null;

    Sensor accSensor;
    Sensor gyroSensor;
    Sensor uncGyroSensor;
    Sensor magnetSensor;
    Sensor yetOneSensor;
    Sensor yetTwoSensor;

    ServerSocket server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        uncGyroSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        magnetSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        yetOneSensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        yetTwoSensor = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, uncGyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, yetOneSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, yetTwoSensor, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            server = new ServerSocket(6789);
            server.accept();
        } catch (java.io.IOException except) {}



        xViewA = (TextView) findViewById(R.id.xbox);
        yViewA = (TextView) findViewById(R.id.ybox);
        zViewA = (TextView) findViewById(R.id.zbox);
        xViewO = (TextView) findViewById(R.id.xboxo);
        yViewO = (TextView) findViewById(R.id.yboxo);
        zViewO = (TextView) findViewById(R.id.zboxo);
        TextView debugText = (TextView) findViewById(R.id.debugText);
        TextView debugText2 = (TextView) findViewById(R.id.debugText2);

        List<Sensor> deviceSensors = sm.getSensorList(Sensor.TYPE_ALL);
        String debugString = "";
        String debugString2 = "";

        for(Sensor s : deviceSensors) {
            debugString += String.format("%s\n", s.getName());
            debugString2 += String.format("%s\n", s.getStringType().substring(15));
        }

        debugText.setText(debugString);
        debugText2.setText(debugString2);

        //xViewA.setText("HelloWorld");

        haveToButton = (Button) findViewById(R.id.haveToButton);

        final android.content.Context context = this;

        OnClickListener haveToOnclick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("It's amazing!");
                dlgAlert.setTitle("HelloWorld, spidy");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        };

        haveToButton.setOnClickListener(haveToOnclick);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    //float[] accelData;
    float[] f;
    float[] s;

    @Override
    public void onSensorChanged(SensorEvent event) {
        //yViewA.setText("AllGood");
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            f = event.values.clone();
            xViewA.setText(String.valueOf(f[0]));
            yViewA.setText(String.valueOf(f[1]));
            zViewA.setText(String.valueOf(f[2]));
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            s = event.values.clone();
            xViewO.setText(String.valueOf(s[0]));
            yViewO.setText(String.valueOf(s[1]));
            zViewO.setText(String.valueOf(s[2]));
        }

        /*if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geoMagnetic = event.values.clone();

        if (gravity != null && geoMagnetic != null) {

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geoMagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = 57.29578F * orientation[0];
                pitch = 57.29578F * orientation[1];
                roll = 57.29578F * orientation[2];

                float dist = Math.abs((float) (1.4f * Math.tan(pitch * Math.PI / 180)));

                Log.d("log", "orientation values: " + azimut + " / " + pitch + " / " + roll + " dist = " + dist);
            }
        }*/
    }
}
