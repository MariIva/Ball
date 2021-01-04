package ru.mora.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.view.View;

import static android.content.Context.SENSOR_SERVICE;

public class MyDraw extends View {
    Ball ball; // катающийся шар
    Circle circles[]; // массив колец (овалов)
    int count = 0; // счетчик собраных колец

    Paint paint = new Paint(); // перо

    private int viewWidth; // ширина экрана
    private int viewHeight; // длиина экрана

    private final int timerInterval = 10; // обновление картинки каждые 10 милисекунд


    public static float aX; // значение акселерометра по Х
    public static float aY; // значение акселерометра по Y

    // конструктор, получающий контекст активности
    public MyDraw (Context context) {
        super(context);
    }

    // изменение размера экрана (канвы)
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewWidth = w;  // ширина экрана
        viewHeight = h; // длина экрана
        // объект шара
        ball = new Ball(viewWidth, viewHeight);
        //объекты колец
        circles = new Circle[1];
        for (int i = 0; i<circles.length; i++){
            circles[i] = new Circle(viewWidth, viewHeight);
        }
        // создание и запуск таймера
        Timer t = new Timer();
        t.start();

    }

    // метод отрисовки на канве
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Закрашиваем холст
        canvas.drawColor(Color.WHITE);
        // Включаем антиальясинг
        paint.setAntiAlias(true);
        // Выбираем кисть
        paint.setStyle(Paint.Style.FILL); // заливка
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1); // ширина пера
        // круг
        canvas.drawCircle(ball.x, ball.y, ball.r, paint);
        // Выбираем кисть
        paint.setStyle(Paint.Style.STROKE); // контур
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10);
        // круг
        for (int i = 0; i<circles.length; i++) {
            canvas.drawOval(circles[i].rect, paint);
        }
        // Выбираем кисть
        paint.setColor(Color.BLACK);
        paint.setTextSize(60.0f); // размер текста
        paint.setStrokeWidth(2.0f);
        // пишим текст
        canvas.drawText(""+count,10, 50, paint);
    }

    // изменение положения шара
    public void moveBall(){
        int vx, vy; // смещение по Х и Y
        if (aX!=0){
            vx = 3;
        }else vx = 0;
        if (aY!=0){
            vy = 3;
        } else vy = 0;

        vx *= aX;
        vy *= aY;

        // если не упираемся границы экрана изменяем значения
        if (ball.x-ball.r+vx>=0 && ball.x+ball.r+vx<=viewWidth){
            ball.x +=vx;
        }
        if (ball.y-ball.r+vy>=0 && ball.y+ball.r+vy<=viewHeight){
            ball.y +=vy;
        }
    }

    // изменение положения колец
    public void moveCircle(){
        for (int i = 0; i<circles.length; i++) {
            // получаем прямоугольник кольца
            RectF rect = circles[i].rect;
            // получаем угловые значения
            float x1c = rect.left;
            float y1c = rect.top;
            float x2c = rect.right;
            float y2c = rect.bottom;
            // получаем прямоугольник шара
            int x1b = ball.x - ball.r;
            int y1b = ball.y;
            int x2b = ball.x + ball.r;
            int y2b = ball.y;
            // если шар прошел в кольцо
            if (x1c<x1b && x2c>x2b && y1c<y1b && y2c>y2b){
                // создаем новое кольцо
                circles[i] = new Circle(viewWidth, viewHeight);
                count++;
            }
        }
    }

    public void update(){
        moveCircle(); // меняем положение кольца
        moveBall(); // меняем положение шара
        invalidate(); // перерисоваем канву
    }

    // таймер
    class Timer extends CountDownTimer {
        // настройки тайтера
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // каждый тик обновляем канву
            update();
        }

        @Override
        public void onFinish() {
        }
    }
}
