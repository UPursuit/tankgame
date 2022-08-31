package com.hey.tankgame6;

import java.util.Vector;

/*
    @author 何恩运
    自己的坦克
*/
@SuppressWarnings({"all"})
public class Hero extends Tank {
    //定义一个Shot对象，表示一个射击(线程)
    Shot shot = null;
    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {

        //在面板上控制最多发射5颗子弹
        if (shots.size() == 5) {
            return;
        }

        //根据当前Hero对象的位置和方向来创建Shot对象
        switch (getDirect()) {//得到Hero对象方向
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }

        //把新创建的shot放入到shots
        shots.add(shot);
        //启动Shot线程
        new Thread(shot).start();
    }
}
