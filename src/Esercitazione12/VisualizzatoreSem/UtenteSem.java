package Esercitazione12.VisualizzatoreSem;

import Esercitazione12.Utente;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class UtenteSem extends Utente {

    private Semaphore possoScrivere,possoLeggere,mutex;

    public UtenteSem(LinkedList<String> coda,Semaphore possoScrivere,Semaphore possoLeggere,Semaphore mutex) {
        super(coda);
        this.possoScrivere=possoScrivere;
        this.possoLeggere=possoLeggere;
        this.mutex=mutex;
    }//costruttore

    public void run(){
        try{
            possoScrivere.acquire();
            int numStringhe=new Random().nextInt(1,6);
            while(coda.size()+numStringhe>100)
                TimeUnit.SECONDS.sleep(1);
            mutex.acquire();
            for(int i=0;i<numStringhe;i++)
                coda.add("L'utente "+Thread.currentThread().getName()+" ha scritto "+(i+1)+" stringa/e");
            possoLeggere.release();
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//UtenteSem
