package Esercitazione11.tracciaCasello2;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Veicolo extends Thread {

    private final int MINKM=1,MAXKM=8;
    private final int TEMPOPERKM=2;
    private Casello c;

    public Veicolo(Casello c){
        this.c=c;
    }

    @Override
    public void run() {
        try{
            int kmPercorsi=new Random().nextInt(MINKM,MAXKM);
            TimeUnit.SECONDS.sleep(kmPercorsi*TEMPOPERKM);
            int numPorta=new Random().nextInt(0,c.getNumeroPorte());
            System.out.println("Il veicolo "+Thread.currentThread().getName()+" è arrivato alla porta "+numPorta);
            c.entra(numPorta);
            TimeUnit.SECONDS.sleep(new Random().nextInt(3,7));
            c.pagaEEsci(numPorta,kmPercorsi);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Veicolo
