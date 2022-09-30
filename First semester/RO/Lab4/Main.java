package Lab4;


import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.*;
class Person implements Serializable{

    Person(String fullName, String phoneNumber)
    {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;

    }

    public String getFullName()
    {
        return this.fullName;
    }
    public String getNumber(){
        return this.phoneNumber;
    }



    private String fullName;
    private String phoneNumber;

}

class RWLock
{
    public RWLock(){
        readCounter=0;
        writeCounter=0;

    }
    public synchronized void readingLock(){
        if(this.writeCounter >0) {
            try {
                System.out.println("Thread number " + Thread.currentThread().getId() +" is waiting to read...");
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        readCounter++;
    }
    public synchronized void readingUnlock()
    {

        if(readCounter>0)
            readCounter--;
        else throw new RuntimeException("Reading lock is unlocked");
        this.notify();
    }
    public synchronized void writingLock(){
        if(this.readCounter>0 || this.writeCounter>0) {
            try {
                System.out.println("Thread number " + Thread.currentThread().getId() +" is waiting to write...");
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        writeCounter++;

    }
    public synchronized void writingUnlock()
    {
        if(writeCounter>0)
            writeCounter--;
        else throw new RuntimeException("Reading lock is unlocked");
        this.notify();
    }

    private int readCounter;
    private int writeCounter;
}

class manager
{
       public manager(String filename)
        {
            this.filename = filename;
            lock = new RWLock();

        }

public void read(readingState state, String target)
{
    lock.readingLock();

    System.out.println("Thread number " + Thread.currentThread().getId() +" is reading...");
    try {
       //ObjectInputStream reader = new ObjectInputStream(new FileInputStream(this.filename));
          RandomAccessFile stream = new RandomAccessFile(filename, "rw");
          String name = stream.readLine();
          String phone = stream.readLine();


        while(name!=null)
        {
            Person person = new Person(name,phone);
            switch(state){
                case FIND_BY_PHONE:
                        if(target.compareTo(person.getNumber())==0) {
                            System.out.println("Thread number " + Thread.currentThread().getId() + " found person: "
                                    + person.getFullName() +" "+person.getNumber());
                            lock.readingUnlock();
                            return;
                        }
                    break;
                case FIND_BY_SURNAME:
                    if(target.compareTo(person.getFullName().split(" ")[0])==0) {
                        System.out.println("Thread number " + Thread.currentThread().getId() + " found person: "
                                + person.getFullName() +" "+person.getNumber());
                        lock.readingUnlock();
                        return;
                    }
                    break;
            }
           // person = (Person)reader.readObject();
            name = stream.readLine();
            phone = stream.readLine();

        }

            stream.close();

    } catch (IOException e) {
        throw new RuntimeException(e);
    }



    lock.readingUnlock();

}

public void write(writingState state, Person person)
{
    lock.writingLock();
    System.out.println("Thread number " + Thread.currentThread().getId() +" is writing...");
    try {
        RandomAccessFile stream = new RandomAccessFile(filename, "rw");

        if(state == writingState.ADD)
        {
            stream.seek(stream.length());
            stream.writeBytes(person.getFullName() +"\n"+
                    person.getNumber()+"\n");

        }
        else if(state == writingState.DELETE)
        {
            ArrayList<String> lines = new ArrayList<String>();
            stream.seek(0);
            String line = stream.readLine();
            while(line!=null)
            {
                lines.add(line);
                line = stream.readLine();
            }

            int size = lines.size();
            for(int i=0;i<size;i++)
            {
                if(lines.get(i).compareTo(person.getFullName())==0)
                {
                    lines.remove(i);
                    lines.remove(i);
                    break;
                }
            }
            stream.setLength(0);
            for(String i:lines)
            {
                stream.writeBytes(i + '\n');
            }

        }
        stream.close();

    } catch (IOException e) {
        System.out.println("File wasn't found");
        throw new RuntimeException(e);
    }


    lock.writingUnlock();
}

    public enum readingState{FIND_BY_PHONE, FIND_BY_SURNAME};

    public enum writingState{DELETE, ADD};

    private String filename="C:\\Users\\Admin\\IdeaProjects\\lab1\\src\\Lab4\\lab4.txt";
    private RWLock lock;

}


public class Main {
    public static void main(String[]args)
    {
        Person person = new Person("YES", "123534");
        Person person1 = new Person("No", "2");
        Person person2 = new Person("CHECK", "380");

                manager mngr = new manager("C:\\Users\\Admin\\IdeaProjects\\lab1\\src\\Lab4\\lab4.txt");
            Thread thr1 = new Thread(()->{
                mngr.read(manager.readingState.FIND_BY_PHONE,"2");
            });

            Thread thr2 = new Thread(()->{
                mngr.read(manager.readingState.FIND_BY_SURNAME,"aaaaa");
            });
            Thread thr3 = new Thread(()->{
                mngr.write(manager.writingState.DELETE,person2);
            });
        Thread thr4 = new Thread(()->{
            mngr.write(manager.writingState.ADD,person);
        });
       thr1.start();
       thr4.start();
       thr2.start();
       thr3.start();

    }
}
