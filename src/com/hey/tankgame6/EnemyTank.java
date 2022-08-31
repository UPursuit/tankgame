package com.hey.tankgame6;

import java.util.Vector;

/*
    @author 何恩运
*/
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    //使用Vector保存多个Shot
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank可以得到敌人坦克的Vector
    //分析：
    //
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //这里提供一个方法，可以将MyPanel的成员Vector<EnemyTank> enemyTanks = new Vector<>()
    // 设置到EnemyTank的成员enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前的这个敌人坦克是否和enemyTanks中的其他坦克发生重叠或碰撞
    public boolean isTouchEnemyTank() {
        //判断当前敌人坦克方向
        switch (this.getDirect()) {
            case 0:
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //1.如果敌人坦克是上/下
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 40]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //(2)当前坦克左上角坐标 [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //(3)当前坦克右上角坐标 [this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //2.如果敌人坦克是右/左
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 60]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //(2)当前坦克左上角坐标 [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //(3)当前坦克右上角坐标 [this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1:
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //1.如果敌人坦克是上/下
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 40]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //(2)当前坦克右上角坐标 [this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //(3)当前坦克右下角坐标 [this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //2.如果敌人坦克是右/左
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 60]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //(2)当前坦克右上角坐标 [this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //(3)当前坦克右下角坐标 [this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2:
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //1.如果敌人坦克是上/下
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 40]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //(2)当前坦克左下角坐标 [this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //(3)当前坦克右下角坐标 [this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //2.如果敌人坦克是右/左
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 60]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //(2)当前坦克左下角坐标 [this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //(3)当前坦克右下角坐标 [this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3:
                //让当前敌人坦克和其他所有敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //1.如果敌人坦克是上/下
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 40]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //(2)当前坦克左上角坐标 [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //(3)当前坦克左下角坐标 [this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //2.如果敌人坦克是右/左
                        //(1)x的范围 [enemyTank.getX(), enemyTank.getX() + 60]
                        //   y的范围 [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //(2)当前坦克左上角坐标 [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //(3)当前坦克左下角坐标 [this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {

            //判断 如果shots.size() == 0，创建一颗子弹，放入到shots集合并启动
            if (isLive && shots.size() == 0) {
                Shot s = null;

                //判断坦克方向，创建对应子弹
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            //根据坦克方向来继续移动
            switch (getDirect()) {
                case 0:
                    //让坦克保持一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            //随机改变坦克方向 0-3
            setDirect((int) (Math.random() * 4));

            //写并发程序一定要考虑什么时候结束该线程
            if (!isLive) {
                break;
            }
        }
    }
}
