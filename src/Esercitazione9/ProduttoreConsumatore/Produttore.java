package Esercitazione9.ProduttoreConsumatore;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Produttore implements Runnable{
    private static final int MAX_RANDOM=10;
    private Random random=new Random();
    private Buffer buffer;

    public Produttore(Buffer b){
        buffer=b;
    }//costruttore

    public void run(){
        try{
            while(true){
                int i=produci();
                buffer.put(i);
            }
        }catch (InterruptedException e){System.out.println("Il produttore non può continuare");}
    }//run

    private int produci() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(random.nextInt(1,2000));
        return random.nextInt(MAX_RANDOM);
    }//produci
}//Produttore
