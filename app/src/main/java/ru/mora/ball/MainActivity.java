package ru.mora.ball;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

// implements SensorEventListener - слушатель на все сенсоры
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // менеджер сенсоров
    private SensorManager mSensorManager;
    // Акселерометр
    private Sensor mAccelerometer;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // запрет на выключение экрана
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // замена отображения на канву для рисования
        setContentView(new MyDraw(this));
        //setContentView(R.layout.activity_main);

        // получение менеджера
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // получение акселероментра
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        // регистрация слешателя со скоростью получения значений для игр
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        // отключения слушателя
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
    // метод отслеживающий изменения сенсоров
        // switch по типу сенсора
        switch (event.sensor.getType()) {
            // если инменения произошли в акселерометре
            case Sensor.TYPE_ACCELEROMETER:
                // берем значения по Х
                MyDraw.aX= event.values[1];
                // берем значения по Y
                // *5 - без этого инменение этого параметра минимальное
                MyDraw.aY= event.values[0]*5;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // метод изменения точности сенсоров
    }

}