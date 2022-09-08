package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

class mythread extends Thread
{
    mythread(JSlider sl, int desiredpos)
    {
        if(sl == null)
            throw new RuntimeException("slider is null");
        slider = sl;
        if(desiredpos >= sl.getMinimum() && desiredpos<= sl.getMaximum())
        pos = desiredpos;
        else pos = sl.getMinimum();

        isStopped = false;
    }
    @Override
    public void run() {

            while(!isStopped)
            {
                synchronized (slider) {
                    if(slider.getValue() == pos)
                        break;
                    if (slider.getValue() < pos)
                        slider.setValue(slider.getValue() + 1);
                    else slider.setValue(slider.getValue() - 1);
                    System.out.println(Long.toString(this.getId()) + " " + slider.getValue());

                    try {
                        //   Thread.yield();
                        sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


            }

    }

    public void setStopped()
    {
        isStopped = true;
    }
    private JSlider slider;
    private int pos ;

    private boolean isStopped;
}

class SpinnerListener implements ChangeListener
{

    @Override
    public void stateChanged(ChangeEvent evt) {
        mythread thread =null;
        JSpinner spinner = null;

        if(evt.getSource() == this.spinner_first)
        {
            thread = Thread1;
            spinner = this.spinner_first;
        }else {
            thread = Thread2;
            spinner = this.spinner_second;
        }
        if(thread==null && spinner!=null)
            spinner.setValue(1);
        if(thread==null || spinner==null)
            return;

        thread.setPriority((int)spinner.getValue());
        System.out.println("PRIORITY: "+ Long.toString(thread.getId()) +" "+ (int)spinner.getValue());
      //  spinner.setValue(prior);

    }

    public void setThread(int id, mythread thread)
    {
        switch(id)
        {
            case(1):
                Thread1 = thread;
                break;
            case(2):
                Thread2 = thread;
                break;
            default:
                return;
        }
    }

    public void setSpinner(int id, JSpinner spinner)
    {
        switch(id)
        {
            case(1):
                spinner_first = spinner;
                break;
            case(2):
                spinner_second = spinner;
                break;
            default:
                return;
        }
    }

    public mythread getThread(int id)
    {
        switch(id)
        {
            case(1):
                return Thread1;
            case(2):
                return Thread2;
            default:
                return null;
        }

    }


    private mythread Thread1;
    private mythread Thread2;

  JSpinner spinner_first;
  JSpinner spinner_second;
}

public class Main {
    public static void main (String[]args)
    {


        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(400,500);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
        panel.setSize(100,100);


        JSlider slider = new JSlider(0,100,50);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);

      //  JLabel label_first_priority = new JLabel("1");
      //  JLabel label_second_priority= new JLabel("1");

        JButton btn = new JButton("START");
      //  JButton btn_increaseFirstPriority = new JButton("1 /\\");
      //  JButton btn_increaseSecondPriority = new JButton("2 /\\");
        SpinnerModel spinner_model_first =
                new SpinnerNumberModel(1, //initial value
                        1, //minimum value
                        10, //maximum value
                        1); //step
        SpinnerModel spinner_model_second =
                new SpinnerNumberModel(1, //initial value
                        1, //minimum value
                        10, //maximum value
                        1); //step
        JSpinner spinner_first = new JSpinner(spinner_model_first);
        JSpinner spinner_second = new JSpinner(spinner_model_second);

        SpinnerListener spinnerlstnr = new SpinnerListener();
        spinnerlstnr.setSpinner(1,spinner_first);
        spinnerlstnr.setSpinner(2,spinner_second);
       // mymouse mouse = new mymouse();
       // mouse.setButton(1,btn_increaseFirstPriority);
       // mouse.setButton(2,btn_increaseSecondPriority);
       // mouse.setLabel(1,label_first_priority);
       // mouse.setLabel(2,label_second_priority);


        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Thread1!=null )
                    Thread1.setStopped();

                if(Thread2!=null )
                    Thread2.setStopped();
                if(Thread1!= null && Thread1.isAlive()) {
                    try {
                        Thread1.join();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }if(Thread2!= null && Thread2.isAlive()) {
                    try {
                        Thread2.join();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                 //   label_second_priority.setText("1");
                  //  label_first_priority.setText("1");

                    spinner_first.setValue(1);
                    spinner_second.setValue(1);


                Thread1 = new mythread(slider,10);
                Thread2 = new mythread(slider,90);
                Thread1.setPriority(1);
                Thread2.setPriority(1);
                spinnerlstnr.setThread(1,Thread1);
                spinnerlstnr.setThread(2,Thread2);
                slider.setValue((slider.getMaximum()+ slider.getMinimum())/2);

                Thread1.start();
                Thread2.start();
            }
        });
        spinner_first.addChangeListener(spinnerlstnr);
        spinner_second.addChangeListener(spinnerlstnr);
     //  btn_increaseFirstPriority.addMouseListener(mouse);
      // btn_increaseSecondPriority.addMouseListener(mouse);


        constr.gridx =0;
        constr.gridy =0;
        constr.gridwidth = 4;
        panel.add(slider,constr);
        //constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx =0;
        constr.gridy =1;
        constr.gridwidth = 4;
        panel.add(btn,constr);
        constr.weightx =1;
        constr.gridx =0;
        constr.gridy =2;
        constr.gridwidth = 2;
        panel.add(spinner_first,constr);
        constr.gridx =2;
        constr.gridy =2;
        constr.gridwidth = 2;
        panel.add(spinner_second,constr);


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

}
