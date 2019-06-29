import javafx.scene.effect.Light;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class ScanCoversionClass {
    private static int flag=0;
    private static JButton b1,b2,b3,b4,b5,b6,b7;
    private List list = new ArrayList();



    public static void main(String args[]) throws Exception {
        JFrame f = new JFrame("Scan Conversion Techniques");
        f.setSize(1100, 750);
        f.setLocation(50, 25);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel p = new JPanel() {
            Point pointStart = null;
            Point pointEnd   = null;
            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        pointStart = e.getPoint();
                        System.out.println(pointStart.x+"  "+pointStart.y);
                    }

                    public void mouseReleased(MouseEvent e) {
                        pointStart = null;
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseMoved(MouseEvent e) {
                        pointEnd = e.getPoint();
                        System.out.println(pointEnd.x+"  "+pointEnd.y);
                    }

                    public void mouseDragged(MouseEvent e) {
                        pointEnd = e.getPoint();
                        repaint();
                    }
                });
            }
            public void paint(Graphics g) {
                super.paint(g);
                float x1,y1,x2,y2,tx,ty;
                float m,c;

                //for direct line
                if(pointStart != null && flag==1){
                    g.setColor(Color.BLACK);
                    x1 = pointStart.x;
                    y1 = pointStart.y;
                    x2 = pointEnd.x;
                    y2 = pointEnd.y;
                    m = (y2-y1)/(x2-x1);
                    c = y1 - m*x1;
                    if(x1<x2 && y1<y2){
                        if(abs(m)<=1){
                            while(x1<x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 + 1;
                                y1 = m*x1 + c;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1<y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 + 1;
                                x1 = (y1 - c)/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }

                    else if(x1<x2 && y1>y2){
                        if(abs(m)<=1){
                            while(x1<x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 + 1;
                                y1 = m * x1 + c;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1>y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 - 1;
                                x1 = (y1 - c)/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }

                    else if(x1>x2 && y1<y2){
                        if(abs(m)<=1){
                            while(x1>x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 - 1;
                                y1 = m*x1 +c;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1<y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 + 1;
                                x1 = (y1 - c)/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }

                    else if(x1>x2 && y1>y2){
                        if(abs(m)<=1){
                            while(x1>x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 - 1;
                                y1 = m*x1 +c;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1>y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 - 1;
                                x1 = (y1 - c)/m;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }
                }

                //for dda algorithm line
                else if (pointStart != null && flag==2) {
                    g.setColor(Color.RED);
                    x1 = pointStart.x;
                    y1 = pointStart.y;
                    x2 = pointEnd.x;
                    y2 = pointEnd.y;
                    m = abs((y2-y1)/(x2-x1));
                    //System.out.println(m);
                    if(x1<x2 && y1<y2){
                        if(m<=1){
                            while(x1<x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 + 1;
                                y1 = y1 + m;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1<y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 + 1;
                                x1 = x1 + 1/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }

                    else if(x1<x2 && y1>y2){
                        if(m<=1){
                            while(x1<x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 + 1;
                                y1 = y1 - m;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1>y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 - 1;
                                x1 = x1 + 1/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }

                    else if(x1>x2 && y1<y2){
                        if(m<=1){
                            while(x1>x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 - 1;
                                y1 = y1 + m;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1<y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 + 1;
                                x1 = x1 - 1/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }

                    else if(x1>x2 && y1>y2){
                        if(m<=1){
                            while(x1>x2){
                                tx = x1;
                                ty = y1;
                                x1 = x1 - 1;
                                y1 = y1 - m;
                                System.out.println(tx+" "+ty+" "+x1+" "+y1);
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else{
                            while(y1>y2){
                                tx = x1;
                                ty = y1;
                                y1 = y1 - 1;
                                x1 = x1 - 1/m;
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }
                }

                //Bresenham Line Algorithm
                else if(pointStart != null && flag==3){
                    g.setColor(Color.BLUE);
                    x1 = pointStart.x;
                    y1 = pointStart.y;
                    x2 = pointEnd.x;
                    y2 = pointEnd.y;
                    float dx,dy,dT,dS,d;

                    m = abs((y2-y1)/(x2-x1));
                    if(m<=1) {
                        if(x1<x2 && y1<y2) {
                            dx = x2 - x1;
                            dy = y2 - y1;
                            dT = 2 * (dy - dx);
                            dS = 2 * dy;
                            d = 2 * dy - dx;
                            while (x1 < x2) {
                                tx = x1;
                                ty = y1;
                                x1 = x1 + 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    y1 = y1 + 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else if(x1<x2 && y1>y2){
                            dx = x2 - x1;
                            dy = y1 - y2;
                            dT = 2 * (dy - dx);
                            dS = 2 * dy;
                            d = 2 * dy - dx;
                            while (x1 < x2) {
                                tx = x1;
                                ty = y1;
                                x1 = x1 + 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    y1 = y1 - 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }

                        else if(x1>x2 && y1<y2){
                            dx = x1 - x2;
                            dy = y2 - y1;
                            dT = 2 * (dy - dx);
                            dS = 2 * dy;
                            d = 2 * dy - dx;
                            while (x1 > x2) {
                                tx = x1;
                                ty = y1;
                                x1 = x1 - 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    y1 = y1 + 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else if(x1>x2 && y1>y2){
                            dx = x1 - x2;
                            dy = y1 - y2;
                            dT = 2 * (dy - dx);
                            dS = 2 * dy;
                            d = 2 * dy - dx;
                            while (x1 > x2) {
                                tx = x1;
                                ty = y1;
                                x1 = x1 - 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    y1 = y1 - 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }
                    else{
                        if(x1<x2 && y1<y2) {
                            dx = x2 - x1;
                            dy = y2 - y1;
                            dT = 2 * (dx - dy);
                            dS = 2 * dx;
                            d = 2 * dx - dy;
                            while (y1 < y2) {
                                tx = x1;
                                ty = y1;
                                y1 = y1 + 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    x1 = x1 + 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else if(x1<x2 && y1>y2){
                            dx = x2 - x1;
                            dy = y1 - y2;
                            dT = 2 * (dx - dy);
                            dS = 2 * dx;
                            d = 2 * dx - dy;
                            while (y1 > y2) {
                                tx = x1;
                                ty = y1;
                                y1 = y1 - 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    x1 = x1 + 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }

                        else if (x1>x2 && y1<y2){
                            dx = x1 - x2;
                            dy = y2 - y1;
                            dT = 2 * (dx - dy);
                            dS = 2 * dx;
                            d = 2 * dx - dy;
                            while (y1 < y2) {
                                tx = x1;
                                ty = y1;
                                y1 = y1 + 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    x1 = x1 - 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                        else if(x2<x1 && y2<y1) {
                            dx = x1 - x2;
                            dy = y1 - y2;
                            dT = 2 * (dx - dy);
                            dS = 2 * dx;
                            d = 2 * dx - dy;
                            while (y1 > y2) {
                                tx = x1;
                                ty = y1;
                                y1 = y1 - 1;
                                if (d < 0)
                                    d = d + dS;
                                else {
                                    x1 = x1 - 1;
                                    d = d + dT;
                                }
                                g.drawLine(round(tx), round(ty), round(x1), round(y1));
                            }
                        }
                    }
                }

                //Bresenham Circle Algorithm
                else if (pointStart!=null && flag==4){
                    g.setColor(Color.BLUE);
                    x1 = pointStart.x;
                    y1 = pointStart.y;
                    x2 = pointEnd.x;
                    y2 = pointEnd.y;
                    //double r1 = sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                    int r = (int)(round(sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))));
                    //System.out.println(r+"  "+r1);
                    int x,y,d;
                    x = 0;
                    y = r;
                    d = 3 - 2*r;
                    while(x<=y){
                        tx = x;
                        ty = y;
                        if(d<0)
                            d = d + 4*x + 6;
                        else{
                            d = d + 4*(x-y) + 10;
                            y = y - 1;
                        }
                        x = x + 1;
                        g.drawLine((int)(tx+x1),(int)(ty+y1),(int)(x+x1),(int)(y+y1));
                        g.drawLine((int)(ty+x1),(int)(tx+y1),(int)(y+x1),(int)(x+y1));
                        g.drawLine((int)(-tx+x1),(int)(ty+y1),(int)(-x+x1),(int)(y+y1));
                        g.drawLine((int)(tx+x1),(int)(-ty+y1),(int)(x+x1),(int)(-y+y1));
                        g.drawLine((int)(-tx+x1),(int)(-ty+y1),(int)(-x+x1),(int)(-y+y1));
                        g.drawLine((int)(-ty+x1),(int)(tx+y1),(int)(-y+x1),(int)(x+y1));
                        g.drawLine((int)(ty+x1),(int)(-tx+y1),(int)(y+x1),(int)(-x+y1));
                        g.drawLine((int)(-ty+x1),(int)(-tx+y1),(int)(-y+x1),(int)(-x+y1));
                    }
                }

                else if (pointStart!=null && flag==5){
                    g.setColor(Color.GREEN);
                    x1 = pointStart.x;
                    y1 = pointStart.y;
                    x2 = pointEnd.x;
                    y2 = pointEnd.y;
                    //double r1 = sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                    int r = (int)(round(sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))));
                    //System.out.println(r+"  "+r1);
                    int x,y,p;
                    x = 0;
                    y = r;
                    p = 1 - r;
                    while(x<=y){
                        tx = x;
                        ty = y;
                        if(p<0)
                            p = p + 2*x + 3;
                        else{
                            p = p + 2*(x-y) + 5;
                            y = y - 1;
                        }
                        x = x + 1;
                        g.drawLine((int)(tx+x1),(int)(ty+y1),(int)(x+x1),(int)(y+y1));
                        g.drawLine((int)(ty+x1),(int)(tx+y1),(int)(y+x1),(int)(x+y1));
                        g.drawLine((int)(-tx+x1),(int)(ty+y1),(int)(-x+x1),(int)(y+y1));
                        g.drawLine((int)(tx+x1),(int)(-ty+y1),(int)(x+x1),(int)(-y+y1));
                        g.drawLine((int)(-tx+x1),(int)(-ty+y1),(int)(-x+x1),(int)(-y+y1));
                        g.drawLine((int)(-ty+x1),(int)(tx+y1),(int)(-y+x1),(int)(x+y1));
                        g.drawLine((int)(ty+x1),(int)(-tx+y1),(int)(y+x1),(int)(-x+y1));
                        g.drawLine((int)(-ty+x1),(int)(-tx+y1),(int)(-y+x1),(int)(-x+y1));
                    }
                }

                else if(pointStart != null && flag==6){
                    ;
                }

            }
        };
        b1 = new JButton("Direct Line");
        b2 = new JButton("DDA");
        b3 = new JButton("Bresesnham Line");
        b4 = new JButton("Bresenham Circle");
        b5 = new JButton("Midpoint Circle");
        b6 = new JButton("Multiple Line");
        b7 = new JButton("Line Clipping");

        p.add(b1);
        b1.addActionListener(new Action());
        p.add(b2);
        b2.addActionListener(new Action());
        p.add(b3);
        b3.addActionListener(new Action());
        p.add(b4);
        b4.addActionListener(new Action());
        p.add(b5);
        b5.addActionListener(new Action());
        p.add(b6);
        b6.addActionListener(new Action());
        p.add(b7);
        b7.addActionListener(new Action());
        f.add(p);
        f.setVisible(true);
    }

    static class Action implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == b1){
                flag=1;
            }
            else if(e.getSource() == b2){
                flag=2;
            }
            else if(e.getSource() == b3){
                flag=3;
            }
            else if(e.getSource() == b4){
                flag=4;
            }
            else if(e.getSource() == b5){
                flag=5;
            }
            else if(e.getSource() == b6){
                flag=6;
            }
            else if(e.getSource() == b7){
                flag=7;
            }
        }
    }

}


