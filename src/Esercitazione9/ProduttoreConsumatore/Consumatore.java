package Esercitazione9.ProduttoreConsumatore;

import java.util.concurrent.TimeUnit;

public class Consumatore implements Runnable{

    private Buffer buffer;
    public Consumatore(Buffer b){
        buffer=b;
    }//costruttore

    public void run(){
        try{
            while (true){
                int i=buffer.get();
                consuma(i);
            }
        }catch (InterruptedException e){
            System.out.println("Il consumatore non può continuare");
        }
    }//run

    public void consuma(int i) throws InterruptedException{
        TimeUnit.SECONDS.sleep(i);
    }//consuma
}//Consumatore
