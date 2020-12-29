package ru.mora.ball;

public class Ball {
    public int x; // центр круга по х
    public int y; // центр круга по y
    public int r; // радиус

    public Ball(int width, int height){
        r = width/30; //вычисление радиуса относительно ширины экрана
        // стартовое положение в центре экрана
        x = width/2;
        y = height/2;
    }
}
