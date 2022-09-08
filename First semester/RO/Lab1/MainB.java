package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;




public class MainB {
    public static void main (String[]args)
    {
        semaphore =1;

        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(400,500);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
        panel.setSize(100,100);


        JSlider slider = new JSlider(SliderMin,SliderMax,(SliderMax + SliderMin)/2);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);

        JButton btn = new JButton("STOP");
        JButton start_first = new JButton("Start first");
        JButton start_second = new JButton("Start second");
        JButton stop_first = new JButton("Stop first");
        JButton stop_second = new JButton("Stop second");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        start_first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Thread1= start(10,  slider);
            }
        });
        start_second.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Thread2 = start(90,  slider);
            }
        });

        stop_first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread1 =  stop(Thread1);

            }
        });
        stop_second.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread2 =  stop(Thread2);

            }
        });

        constr.gridx =0;
        constr.gridy =0;
        constr.gridwidth = 4;
        panel.add(slider,constr);
        constr.weightx =1;
        //constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx =0;
        constr.gridy =1;
        constr.gridwidth = 2;
        panel.add(start_first,constr);
        constr.gridx =2;
        constr.gridy =1;
        constr.gridwidth = 2;
        panel.add(start_second,constr);
        constr.gridx =0;
        constr.gridy =2;
        constr.gridwidth = 2;
        panel.add(stop_first,constr);
        constr.gridx =2;
        constr.gridy =2;
        constr.gridwidth = 2;
        panel.add(stop_second,constr);


        win.add(panel);
        win.setVisible(true);

        if(Thread1!=null && Thread1.isAlive()) {
            Thread1.setStopped();
            try {
                Thread1.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(Thread2!=null &&Thread2.isAlive()) {
            Thread2.setStopped();
            try {
                Thread2.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private static mythread Thread1;
    private static mythread Thread2;

    private static mythread start(int desiredPos, JSlider slider)
    {
        if(semaphore==0 || desiredPos<0 || desiredPos>SliderMax)
            return null;
        semaphore = 0;

        mythread thr = new mythread(slider,desiredPos);
        thr.setPriority(Thread.MAX_PRIORITY);
        thr.start();
        return thr;
    }
    private static mythread stop(mythread thr)
    {
        if(semaphore==1 ||thr==null)
            return null;

        thr.setStopped();
        try {
            thr.join();
            semaphore =1;

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
    private static int semaphore;

    private static int SliderMax = 100;

    private static int SliderMin = 0;

}
