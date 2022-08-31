package com.hey.tankgame6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/*
    @author 何恩运
    坦克大战的绘图区域
*/
@SuppressWarnings({"all"})
//为了监听键盘事件，应实现KeyListener
//为了让Panel不停的重绘子弹，需要将MyPanel实现Runnable当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克，放入到Vector集合
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector用于存放炸弹
    //说明：当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankNum = 3;

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        //先判断记录的文件是否存在，若存在就正常执行，若不存在就提示只能开启新游戏(key = "1")
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("文件不存在，只能开启新游戏");
            key = "1";
        }
        //将MyPanel对象的enemyTanks设置给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        //初始化自己的坦克
        hero = new Hero(0, 690);
        hero.setSpeed(5);

        switch (key) {
            case "1"://新游戏
                //初始化敌人坦克
                for (int i = 0; i < enemyTankNum; i++) {
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(200 * (i + 1), 0);
                    //将enemyTanks设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程使其动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一个子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                    enemyTank.setSpeed(5);
                    Recorder.setAllEnemyTankNum(0);
                }
                break;
            case "2"://继续上局
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks设置给enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程使其动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一个子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                    enemyTank.setSpeed(5);
                }
                break;
            case "3":
                System.out.println("退出游戏");
                System.exit(0);
                break;
            default:
                System.out.println("你的输入有误");
        }

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

        //播放指定的音乐
        new AePlayWave("src\\111.wav").start();
    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);//画出一个敌方坦克
        g.setColor(Color.BLACK);//这里需要重新设置成黑色
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色
        showInfo(g);

        if (hero != null && hero.isLive) {
            //画出坦克(封装方法)
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //画出hero射击的子弹，将hero的子弹集合shots遍历取出并绘制
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else {//如果该shot对象已经无效，就从shots集合中拿掉
                hero.shots.remove(shot);
            }
        }

        //如果bombs集合中有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if(bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb的life为0，就从bombs的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //画出敌人坦克，遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //从Vector取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否还存活
            if (enemyTank.isLive) {//当敌人坦克存活才画
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出enemyTank所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {//isLive == true
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从Vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    /**
     * 编写方法，画出坦克
     * @param x      坦克左上角x坐标
     * @param y      坦克左上角y坐标
     * @param g      画笔
     * @param direct 坦克方向(上下左右)
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //根据不同类型坦克，设置不同颜色
        switch (type) {
            case 0: //敌方坦克
                g.setColor(Color.cyan);//青色
                break;
            case 1: //己方坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，绘制对应形状的坦克
        //direct表示方向：0 上 1 右 2 下 3 左
        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克身体
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克身体
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克身体
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克身体
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
                break;
            default:
                System.out.println("暂时未处理");
        }
    }

    //如果我方坦克可以发射多颗子弹，在判断我方子弹是否击中敌人坦克时，
    //就需要把我方子弹集合中所有子弹都取出，并和敌方所有坦克进行判断
    public void hitEnemyTank() {
        //遍历我们的子弹
        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            //判断是否击中了敌人坦克
            if (shot != null && shot.isLive) {//当我方子弹还存活
                //遍历敌人所有坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    //编写方法，判断敌人坦克是否击中我方坦克
    public void hitHero() {
        //遍历所有敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank对象所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断shot是否击中我方坦克
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    //编写方法，判断我方的子弹是否击中敌人坦克
    public void hitTank(Shot s, Tank enemyTank) {
        //判断s击中坦克
        switch (enemyTank.getDirect()) {
            case 0://坦克向上
            case 2://坦克向下
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当子弹击中敌人坦克后，将enemyTank从Vector中拿掉
                    enemyTanks.remove(enemyTank);
                    //当我方击毁一个敌人坦克时，就allEnemyTankNum++
                    //因为enemyTank可以是Hero也可以是EnemyTank，所以需要加个判断
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    //同上
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wasd键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }

        //如果用户按下J键就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
//            //判断hero的子弹是否销毁(发射一颗子弹)
//            if (hero.shot == null || !hero.shot.isLive) {
//                hero.shotEnemyTank();
//            }
            //发射多颗子弹
            hero.shotEnemyTank();
        }

        //让面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100ms重绘，刷新绘图区域，子弹就会移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断我方子弹是否击中敌人坦克
            hitEnemyTank();
            //判断敌人坦克是否集中我们
            hitHero();

            this.repaint();
        }
    }
}
