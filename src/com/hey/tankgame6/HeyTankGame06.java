package com.hey.tankgame6;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/*
    @author 何恩运
*/
public class HeyTankGame06 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HeyTankGame06 heyTankGame02 = new HeyTankGame06();
    }

    public HeyTankGame06() {

        System.out.println("请输入选择：1 新游戏 2 继续上局 3 退出");
        while (true) {
            String key = scanner.next();
            if (key.equals("1") || key.equals("2") || key.equals("3")) {
                mp = new MyPanel(key);
                //将mp放入到Thread并启动
                new Thread(mp).start();
                this.add(mp);//加入面板(即游戏绘图区域)
                this.setSize(1300, 795);
                this.addKeyListener(mp);//让JFrame监听mp的键盘事件
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(true);

                //在JFrame中增加响应关闭窗口的处理
                this.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        Recorder.keepRecord();
                        System.out.println("退出游戏");
                        System.exit(0);
                    }
                });
                break;
            } else {
                System.out.println("你的输入有误，请重新输入");
            }
        }
    }
}
