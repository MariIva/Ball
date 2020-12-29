package ru.mora.ball;

import android.graphics.RectF;

public class Circle {
    // прямоугольник, в которос рисуется овал
    RectF rect;
    int w; // ширина овала
    int h; // длина овала

    public Circle(int w, int h){
        // ширина и длина овала относительно размеров экрана
        this.w = w/10;
        this.h = h/10;
        // случайная точка (правая верхняя)
        // в рамке меньше экрана на размеры овала
        int x1 = (int) (Math.random() * (w - 2 * this.w) + this.w);
        int y1 = (int) (Math.random() * (h - 2 * this.h) + this.h);
        // левая нижняя точка
        int x2 = x1 + this.w;
        int y2 = y1 + this.h;
        // сохрание прямоугольника
        rect = new RectF(x1, y1, x2, y2);
    }
}
