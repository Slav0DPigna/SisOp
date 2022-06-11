package Esercitazione12Funivia;

import java.util.concurrent.TimeUnit;

public class Turista implements Runnable{

    private Funivia funivia;
    private int tipo;

    public Turista(Funivia f,int tipo){
        funivia=f;
        this.tipo=tipo;
    }//costruttore

    public void run(){
        try{
            funivia.turistaSali(tipo);
            funivia.turistaScendi(tipo);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run

    public int getTipo(){
        return tipo;
    }//getTipo

}//Turista
