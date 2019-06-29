import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class LineClipping {
    private static int flag=0;
    private static int flag1=0;
    private static JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11;
    private static ArrayList listx,listy;
    private static ArrayList lines = new ArrayList();
    private static Point P0=null,P1=null;



    public static void main(String args[]) throws Exception {
        JFrame f = new JFrame("Scan Conversion Techniques");
        f.setSize(1100, 750);
        f.setLocation(50, 25);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel() {
            Point pointStart = null;
            Point pointEnd = null;
            int xMax, yMax, xMin, yMin;

            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        pointStart = e.getPoint();

                    }

                    public void mouseReleased(MouseEvent e) {
                        if(flag==6) {
                            Line line = new Line(pointStart, pointEnd);
                            //System.out.println("Start "+pointStart.x+"  "+pointStart.y);
                            //System.out.println("End"+pointEnd.x+"  "+pointEnd.y);
                            lines.add(line);
                        }
                        if(flag == 10){
                            //int len = lines.size();
                            //if(len==0){
                                Line line = new Line(pointStart, pointEnd);
                                System.out.println("Start "+pointStart.x+"  "+pointStart.y);
                                System.out.println("End"+pointEnd.x+"  "+pointEnd.y);
                                lines.add(line);
                            //}
                            /*else{
                                Line previous = (Line)lines.get(len-1);
                                Line line = new Line(previous.getP2(), pointEnd);
                                System.out.println("Start "+previous.getP2().x+"  "+previous.getP2().y);
                                System.out.println("End "+pointEnd.x+"  "+pointEnd.y);
                                lines.add(line);
                            }*/

                        }
                        pointStart = null;

                    }
                });
                addMouseMotionListener(new MouseMotionAdapter()
                {
                    public void mouseMoved(MouseEvent e) {
                        pointEnd = e.getPoint();
                        //System.out.println(pointEnd.x+"  "+pointEnd.y);

                    }

                    public void mouseDragged(MouseEvent e) {
                        pointEnd = e.getPoint();
                        repaint();
                    }
                });
            }
            public void paint(Graphics g)
            {
                super.paint(g);
                float x1,y1,x2,y2,tx,ty;
                float m,c;
                if(pointStart!=null) {
                    x1 = pointStart.x;
                    y1 = pointStart.y;
                    x2 = pointEnd.x;
                    y2 = pointEnd.y;
                    m = (float)(y2 - y1) / (x2 - x1);
                    c = y1 - m * x1;

                    //for direct line
                    if (flag == 1) {
                        g.setColor(Color.BLACK);
                        directMethod(x1, y1, x2, y2, m, c);
                        for (int i = 0; i < listx.size() - 1; i++) {
                            g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                        }
                    }

                    //for dda algorithm line
                    else if (flag == 2) {
                        g.setColor(Color.RED);
                        DDAmethod(x1, y1, x2, y2, abs(m));
                        for (int i = 0; i < listx.size() - 1; i++) {
                            g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                        }
                    }

                    //Bresenham Line Algorithm
                    else if (flag == 3) {
                        g.setColor(Color.BLUE);

                        BresenhamMethod(x1, y1, x2, y2, abs(m));
                        for (int i = 0; i < listx.size() - 1; i++) {
                            g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                        }
                    }

                    //Bresenham Circle Algorithm
                    else if (flag == 4) {
                        g.setColor(Color.BLUE);
                        //double r1 = sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                        int r = (int) (round(sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))));
                        //System.out.println(r+"  "+r1);
                        int x, y, d;
                        x = 0;
                        y = r;
                        d = 3 - 2 * r;
                        while (x <= y) {
                            tx = x;
                            ty = y;
                            if (d < 0)
                                d = d + 4 * x + 6;
                            else {
                                d = d + 4 * (x - y) + 10;
                                y = y - 1;
                            }
                            x = x + 1;
                            g.drawLine((int) (tx + x1), (int) (ty + y1), (int) (x + x1), (int) (y + y1));
                            g.drawLine((int) (ty + x1), (int) (tx + y1), (int) (y + x1), (int) (x + y1));
                            g.drawLine((int) (-tx + x1), (int) (ty + y1), (int) (-x + x1), (int) (y + y1));
                            g.drawLine((int) (tx + x1), (int) (-ty + y1), (int) (x + x1), (int) (-y + y1));
                            g.drawLine((int) (-tx + x1), (int) (-ty + y1), (int) (-x + x1), (int) (-y + y1));
                            g.drawLine((int) (-ty + x1), (int) (tx + y1), (int) (-y + x1), (int) (x + y1));
                            g.drawLine((int) (ty + x1), (int) (-tx + y1), (int) (y + x1), (int) (-x + y1));
                            g.drawLine((int) (-ty + x1), (int) (-tx + y1), (int) (-y + x1), (int) (-x + y1));
                        }
                    } else if (flag == 5) {
                        g.setColor(Color.GREEN);
                        //double r1 = sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                        int r = (int) (round(sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))));
                        //System.out.println(r+"  "+r1);
                        int x, y, p;
                        x = 0;
                        y = r;
                        p = 1 - r;
                        while (x <= y) {
                            tx = x;
                            ty = y;
                            if (p < 0)
                                p = p + 2 * x + 3;
                            else {
                                p = p + 2 * (x - y) + 5;
                                y = y - 1;
                            }
                            x = x + 1;
                            g.drawLine((int) (tx + x1), (int) (ty + y1), (int) (x + x1), (int) (y + y1));
                            g.drawLine((int) (ty + x1), (int) (tx + y1), (int) (y + x1), (int) (x + y1));
                            g.drawLine((int) (-tx + x1), (int) (ty + y1), (int) (-x + x1), (int) (y + y1));
                            g.drawLine((int) (tx + x1), (int) (-ty + y1), (int) (x + x1), (int) (-y + y1));
                            g.drawLine((int) (-tx + x1), (int) (-ty + y1), (int) (-x + x1), (int) (-y + y1));
                            g.drawLine((int) (-ty + x1), (int) (tx + y1), (int) (-y + x1), (int) (x + y1));
                            g.drawLine((int) (ty + x1), (int) (-tx + y1), (int) (y + x1), (int) (-x + y1));
                            g.drawLine((int) (-ty + x1), (int) (-tx + y1), (int) (-y + x1), (int) (-x + y1));
                        }
                    }
                    else if (flag == 6) {
                        g.setColor(Color.RED);
                        //DDAmethod(x1, y1, x2, y2, abs(m));
                        BresenhamMethod(x1, y1, x2, y2, abs(m));
                        for (int i = 0; i < listx.size() - 1; i++) {
                            g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                        }
                        g.setColor(Color.BLUE);
                        Line currentLine;
                        for(int j=0; j<lines.size(); j++){
                            currentLine = (Line)lines.get(j);
                            Point start = currentLine.getP1();
                            Point end = currentLine.getP2();
                            //System.out.println("After adding "+j+" "+start.x+" "+start.y+"   "+end.x+" "+end.y);

                            m = (float)(end.y - start.y)/( end.x- start.x);
                            //System.out.println(m);
                            BresenhamMethod(start.x, start.y, end.x, end.y, abs(m));

                            for (int i = 0; i < listx.size() - 1; i++) {
                                g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                                //System.out.println((int) listx.get(i)+" "+(int) listy.get(i)+" "+(int) listx.get(i + 1)+" "+(int) listy.get(i + 1));
                            }
                        }


                    }

                    else if(flag == 7){
                        g.setColor(Color.BLUE);
                        Line currentLine,previousLine;
                        if(flag1==0) {
                            for (int j = 0; j < lines.size(); j++) {
                                //currentLine = (Line) lines.get(j);
                                Point start,end;
                                if(j==0) {
                                    currentLine = (Line) lines.get(j);
                                    start = currentLine.getP1();
                                    end = currentLine.getP2();
                                    //System.out.println("After adding "+j+" "+start.x+" "+start.y+"   "+end.x+" "+end.y);
                                }
                                else{
                                    currentLine = (Line) lines.get(j);
                                    previousLine = (Line) lines.get(j-1);
                                    start = previousLine.getP2();
                                    end = currentLine.getP2();
                                }

                                m = (float)(end.y - start.y) / (end.x - start.x);
                                BresenhamMethod(start.x, start.y, end.x, end.y, abs(m));
                                for (int i = 0; i < listx.size() - 1; i++) {
                                    g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                                }
                            }
                        }
                        g.setColor(Color.RED);

                        if(x1<x2 && y1<y2){
                            g.drawRect((int) x1, (int) y1, (int) abs(x2 - x1), (int) abs(y2 - y1));
                            xMax = (int)x2;
                            yMax = (int) y2;
                            xMin = (int) x1;
                            yMin = (int) y1;
                        }
                        else if(x1>x2 && y1>y2){
                            g.drawRect((int) x2, (int) y2, (int) abs(x2 - x1), (int) abs(y2 - y1));
                            xMax = (int)x2;
                            yMax = (int) y2;
                            xMin = (int) x1;
                            yMin = (int) y1;
                        }
                        else if(x1<x2 && y1>y2){
                            g.drawRect((int) x1, (int) (y2), (int) abs(x2 - x1), (int) abs(y2 - y1));
                            xMax = (int)x2;
                            yMax = (int) y2;
                            xMin = (int) x1;
                            yMin = (int) y1;
                        }
                        else{
                            g.drawRect((int) x2, (int) (y1), (int) abs(x2 - x1), (int) abs(y2 - y1));
                            xMax = (int)x2;
                            yMax = (int) y2;
                            xMin = (int) x1;
                            yMin = (int) y1;
                        }
                    }

                    else if( flag == 8){
                        g.setColor(Color.GREEN);
                        g.drawRect(xMin, yMin, abs(xMax - xMin), abs(yMax - yMin));

                        g.setColor(Color.BLUE);
                        Line currentLine;
                        for(int j = 0; j < lines.size(); j++) {
                            currentLine = (Line) lines.get(j);
                            P0 = currentLine.getP1();
                            P1 = currentLine.getP2();
                            if (CohenSutherlandClipper(yMax, yMin, xMax, xMin)) {
                                m = (float) (P1.y - P0.y) / (P1.x - P0.x);
                                BresenhamMethod(P0.x, P0.y, P1.x, P1.y, abs(m));
                                for (int i = 0; i < listx.size() - 1; i++) {
                                    g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                                }
                            }

                        }

                    }
                    else if(flag==9){
                        g.setColor(Color.GREEN);
                        g.drawRect(xMin, yMin, abs(xMax - xMin), abs(yMax - yMin));

                        g.setColor(Color.BLUE);
                        Line currentLine;
                        for(int j = 0; j < lines.size(); j++) {
                            currentLine = (Line) lines.get(j);
                            P0 = currentLine.getP1();
                            P1 = currentLine.getP2();
                            if (MidpointSubdivisionClipper(yMax, yMin, xMax, xMin)) {
                                m = (float)(P1.y - P0.y) / (P1.x - P0.x);
                                BresenhamMethod(P0.x, P0.y, P1.x, P1.y, abs(m));
                                for (int i = 0; i < listx.size() - 1; i++) {
                                    g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                                }
                            }

                        }
                    }
                    else if(flag == 10){
                        g.setColor(Color.RED);
                        //DDAmethod(x1, y1, x2, y2, abs(m));
                        BresenhamMethod(x1, y1, x2, y2, abs(m));
                        for (int i = 0; i < listx.size() - 1; i++) {
                            g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                        }
                        g.setColor(Color.BLUE);
                        Line currentLine,previousLine;
                        for(int j=0; j<lines.size(); j++){
                            Point start,end;
                            if(j==0) {
                                currentLine = (Line) lines.get(j);
                                start = currentLine.getP1();
                                end = currentLine.getP2();
                                //System.out.println("After adding "+j+" "+start.x+" "+start.y+"   "+end.x+" "+end.y);
                            }
                            else{
                                currentLine = (Line) lines.get(j);
                                previousLine = (Line) lines.get(j-1);
                                start = previousLine.getP2();
                                end = currentLine.getP2();
                            }
                            /*currentLine = (Line)lines.get(j);
                            Point start = currentLine.getP1();
                            Point end = currentLine.getP2();*/
                            //System.out.println("After adding "+j+" "+start.x+" "+start.y+"   "+end.x+" "+end.y);
                            m = (float)(end.y - start.y)/( end.x- start.x);
                            //System.out.println(m);
                            BresenhamMethod(start.x, start.y, end.x, end.y, abs(m));

                            for (int i = 0; i < listx.size() - 1; i++) {
                                g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                                //System.out.println((int) listx.get(i)+" "+(int) listy.get(i)+" "+(int) listx.get(i + 1)+" "+(int) listy.get(i + 1));
                            }
                        }
                    }
                    else if(flag ==11){
                        g.setColor(Color.GREEN);
                        g.drawRect(xMin, yMin, abs(xMax - xMin), abs(yMax - yMin));

                        g.setColor(Color.BLUE);
                        Line currentLine;
                        for(int j = 0; j < lines.size(); j++) {
                            currentLine = (Line) lines.get(j);
                            P0 = currentLine.getP1();
                            P1 = currentLine.getP2();
                            System.out.println("Lines "+j+"  "+P0.x+" "+P0.y+" "+P1.x+" "+P1.y);
                            if (CohenSutherlandClipper(yMax, yMin, xMax, xMin)) {
                                m = (float) (P1.y - P0.y) / (P1.x - P0.x);

                                BresenhamMethod(P0.x, P0.y, P1.x, P1.y, abs(m));

                                for (int i = 0; i < listx.size() - 1; i++) {
                                    g.drawLine((int) listx.get(i), (int) listy.get(i), (int) listx.get(i + 1), (int) listy.get(i + 1));
                                }
                            }

                        }
                    }
                }
            }
        };

        b1 = new JButton("Direct Line");
        b2 = new JButton("DDA");
        b3 = new JButton("Bresesnham Line");
        b4 = new JButton("Bresenham Circle");
        b5 = new JButton("Midpoint Circle");
        b6 = new JButton("Multiple Line");
        b7 = new JButton("Rectangle");
        b8 = new JButton("CS Clipping");
        b9 = new JButton("Midpoint Clipping");
        b10 = new JButton("Polygonal Line");
        b11 = new JButton("Polygon Clipping");

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
        p.add(b8);
        b8.addActionListener(new Action());
        p.add(b9);
        b9.addActionListener(new Action());
        p.add(b10);
        b10.addActionListener(new Action());
        p.add(b11);
        b11.addActionListener(new Action());
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
                lines.clear();
            }
            else if(e.getSource() == b7){
                flag=7;
            }
            else if(e.getSource() == b8){
                flag=8;

            }
            else if(e.getSource() == b9){
                flag=9;
            }
            else if(e.getSource() == b10){
                flag=10;
                lines.clear();
            }
            else if(e.getSource() == b11){
                flag=11;
            }
        }
    }

    private static void directMethod(float x1, float y1, float x2, float y2, float m, float c){
        {
            listx = new ArrayList();
            listy = new ArrayList();
            if(x1<x2 && y1<y2){
                if(abs(m)<=1){
                    while(x1<x2){
                        x1 = x1 + 1;
                        y1 = m*x1 + c;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
                else{
                    while(y1<y2){
                        y1 = y1 + 1;
                        x1 = (y1 - c)/m;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
            }

            else if(x1<x2 && y1>y2){
                if(abs(m)<=1){
                    while(x1<x2){
                        x1 = x1 + 1;
                        y1 = m * x1 + c;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
                else{
                    while(y1>y2){
                        y1 = y1 - 1;
                        x1 = (y1 - c)/m;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
            }

            else if(x1>x2 && y1<y2){
                if(abs(m)<=1){
                    while(x1>x2){
                        x1 = x1 - 1;
                        y1 = m*x1 +c;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
                else{
                    while(y1<y2){
                        y1 = y1 + 1;
                        x1 = (y1 - c)/m;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
            }

            else if(x1>x2 && y1>y2){
                if(abs(m)<=1){
                    while(x1>x2){
                        x1 = x1 - 1;
                        y1 = m*x1 +c;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
                else{
                    while(y1>y2){
                        y1 = y1 - 1;
                        x1 = (y1 - c)/m;
                        listx.add(round(x1));
                        listy.add(round(y1));
                    }
                }
            }
        }
    }

    private static void DDAmethod(float x1, float y1, float x2, float y2, float m){
        listx = new ArrayList();
        listy = new ArrayList();

        if(x1<x2 && y1<y2){
            if(m<=1){
                while(x1<x2){
                    x1 = x1 + 1;
                    y1 = y1 + m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else{
                while(y1<y2){
                    y1 = y1 + 1;
                    x1 = x1 + 1/m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
        }

        else if(x1<x2 && y1>y2){
            if(m<=1){
                while(x1<x2){
                    x1 = x1 + 1;
                    y1 = y1 - m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else{
                while(y1>y2){
                    y1 = y1 - 1;
                    x1 = x1 + 1/m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
        }

        else if(x1>x2 && y1<y2){
            if(m<=1){
                while(x1>x2){
                    x1 = x1 - 1;
                    y1 = y1 + m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else{
                while(y1<y2){
                    y1 = y1 + 1;
                    x1 = x1 - 1/m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
        }

        else if(x1>x2 && y1>y2){
            if(m<=1){
                while(x1>x2){
                    x1 = x1 - 1;
                    y1 = y1 - m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else{
                while(y1>y2){
                    y1 = y1 - 1;
                    x1 = x1 - 1/m;
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
        }

    }

    private static void BresenhamMethod (float x1, float y1, float x2, float y2, float m){

        listx = new ArrayList();
        listy = new ArrayList();

        float dx,dy,dT,dS,d;
        listx.add(round(x1));
        listy.add(round(y1));
        if(m<=1) {
            if(x1<x2 && y1<y2) {
                dx = x2 - x1;
                dy = y2 - y1;
                dT = 2 * (dy - dx);
                dS = 2 * dy;
                d = 2 * dy - dx;
                while (x1 < x2) {
                    x1 = x1 + 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        y1 = y1 + 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else if(x1<x2 && y1>y2){
                dx = x2 - x1;
                dy = y1 - y2;
                dT = 2 * (dy - dx);
                dS = 2 * dy;
                d = 2 * dy - dx;
                while (x1 < x2) {
                    x1 = x1 + 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        y1 = y1 - 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }

            else if(x1>x2 && y1<y2){
                dx = x1 - x2;
                dy = y2 - y1;
                dT = 2 * (dy - dx);
                dS = 2 * dy;
                d = 2 * dy - dx;
                while (x1 > x2) {
                    x1 = x1 - 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        y1 = y1 + 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else if(x1>x2 && y1>y2){
                dx = x1 - x2;
                dy = y1 - y2;
                dT = 2 * (dy - dx);
                dS = 2 * dy;
                d = 2 * dy - dx;
                while (x1 > x2) {
                    x1 = x1 - 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        y1 = y1 - 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
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
                    y1 = y1 + 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        x1 = x1 + 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else if(x1<x2 && y1>y2){
                dx = x2 - x1;
                dy = y1 - y2;
                dT = 2 * (dx - dy);
                dS = 2 * dx;
                d = 2 * dx - dy;
                while (y1 > y2) {
                    y1 = y1 - 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        x1 = x1 + 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }

            else if (x1>x2 && y1<y2){
                dx = x1 - x2;
                dy = y2 - y1;
                dT = 2 * (dx - dy);
                dS = 2 * dx;
                d = 2 * dx - dy;
                while (y1 < y2) {
                    y1 = y1 + 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        x1 = x1 - 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
            else if(x2<x1 && y2<y1) {
                dx = x1 - x2;
                dy = y1 - y2;
                dT = 2 * (dx - dy);
                dS = 2 * dx;
                d = 2 * dx - dy;
                while (y1 > y2) {
                    y1 = y1 - 1;
                    if (d < 0)
                        d = d + dS;
                    else {
                        x1 = x1 - 1;
                        d = d + dT;
                    }
                    listx.add(round(x1));
                    listy.add(round(y1));
                }
            }
        }
    }

    private static int outCodes(Point P, int yMax, int yMin, int xMax, int xMin)
    {
        int Code = 0;

        if(P.y > yMax) Code += 1; /* code for above */
        else if(P.y < yMin) Code += 2; /* code for below */

        if(P.x > xMax) Code += 4; /* code for right */
        else if(P.x < xMin) Code += 8; /* code for left */

        return Code;
    }

    private static boolean rejectCheck(int outCode1, int outCode2)
    {
        if ((outCode1 & outCode2) != 0 ) return true;
        return(false);
    }


    private static boolean acceptCheck(int outCode1, int outCode2)
    {
        if ( (outCode1 == 0) && (outCode2 == 0) ) return(true);
        return(false);
    }


    private static boolean CohenSutherlandClipper(int yMax, int yMin, int xMax, int xMin)
    {
        int outCode0,outCode1;
        while(true)
        {
            outCode0 = outCodes(P0, yMax, yMin, xMax, xMin);
            outCode1 = outCodes(P1, yMax, yMin, xMax, xMin);

            if( rejectCheck(outCode0,outCode1) )
                return(false);
            if( acceptCheck(outCode0,outCode1))
                return(true);

            if(outCode0 == 0)
            {
                double tempCoord; int tempCode;
                tempCoord = P0.x; P0.x= P1.x; P1.x = (int)tempCoord;
                tempCoord = P0.y; P0.y= P1.y; P1.y = (int)tempCoord;
                tempCode = outCode0; outCode0 = outCode1; outCode1 = tempCode;
            }

            if( (outCode0 & 1) != 0 )
            {
                P0.x += (P1.x - P0.x)*(yMax - P0.y)/(P1.y - P0.y);
                P0.y = yMax;
            }

            else if( (outCode0 & 2) != 0 )
            {
                P0.x += (P1.x - P0.x)*(yMin - P0.y)/(P1.y - P0.y);
                P0.y = yMin;
            }

            else if( (outCode0 & 4) != 0 )
            {
                P0.y += (P1.y - P0.y)*(xMax - P0.x)/(P1.x - P0.x);
                P0.x = xMax;
            }

            else if( (outCode0 & 8) != 0 )
            {
                P0.y += (P1.y - P0.y)*(xMin - P0.x)/(P1.x - P0.x);
                P0.x = xMin;
            }
        }
    }


    private static boolean MidpointSubdivisionClipper(int yMax, int yMin, int xMax, int xMin)
    {
        int outCode0,outCode1;
        outCode0 = outCodes(P0, yMax, yMin, xMax, xMin);
        outCode1 = outCodes(P1, yMax, yMin, xMax, xMin);
        Point p0=P0,p1=P1,mid=P0;

        if( rejectCheck(outCode0,outCode1) )
            return(false);
        if( acceptCheck(outCode0,outCode1))
            return(true);
        System.out.println("Initial and last point:  "+P0.x +" "+P0.y+" "+P1.x+" "+P1.y);
        int t=10;
        if(outCode0 == 0){
            while(t>0){

                mid.x = (p0.x + p1.x)/2;
                mid.y = (p0.y + p1.y)/2;
                System.out.println("First point : "+mid.x +" "+mid.y+" "+xMax+" "+xMin+" "+yMax+" "+yMin);
                if(mid.x == xMin || mid.x == xMax || mid.y == yMax || mid.y == yMin){
                    break;
                }

                int outCodep0 = outCodes(mid, yMax, yMin, xMax, xMin);
                if(outCodep0 == 0){
                    p0.x = mid.x;
                    p0.y = mid.y;
                }
                else{
                    p1.x = mid.x;
                    p1.y = mid.y;
                }
                t--;
            }
            P1.x = mid.x;
            P1.y = mid.y;
        }

        if(outCode1 == 0){
            t=10;
            while(t>0){
                mid.x = (p0.x + p1.x)/2;
                mid.y = (p0.y + p1.y)/2;
                System.out.println(mid.x +" "+mid.y+" "+xMax+" "+xMin+" "+yMax+" "+yMin);
                if(mid.x == xMin || mid.x == xMax || mid.y == yMax || mid.y == yMin){
                    break;
                }

                int outCodep0 = outCodes(mid, yMax, yMin, xMax, xMin);
                if(outCodep0 == 0){
                    p0.x = mid.x;
                    p0.y = mid.y;
                }
                else{
                    p1.x = mid.x;
                    p1.y = mid.y;
                }
                t--;
            }
            P0.x = mid.x;
            P1.y = mid.y;

        }
        return true;
    }

}

class Line {

    private Point p1;
    private Point p2;

    public Line() {
    }

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }
}




