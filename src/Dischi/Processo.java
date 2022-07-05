package Dischi;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Processo extends Thread{

    Controllore c;

    public Processo(Controllore c){
        this.c=c;
    }//Costruttore

    public void run(){
        try{
            while (true) {
                int a = new Random().nextInt(1, 4);
                int b = new Random().nextInt(1, 4);
                while (b == a)
                    b = new Random().nextInt(1, 4);
                c.allocaDischi(a, b);
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100,1001));
                c.rilasciaDischi(a, b);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Processo
