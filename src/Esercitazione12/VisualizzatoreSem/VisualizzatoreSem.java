package Esercitazione12.VisualizzatoreSem;

import Esercitazione12.Visualizzatore;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class VisualizzatoreSem extends Visualizzatore {

    private Semaphore possoScrivere,possoLeggere,mutex;

    public VisualizzatoreSem(LinkedList<String> coda,Semaphore possoScrivere,Semaphore possoLeggere,Semaphore mutex) {
        super(coda);
        this.possoScrivere=possoScrivere;
        this.possoLeggere=possoLeggere;
        this.mutex=mutex;
    }//costruttore

    public void run(){
        try{
            possoLeggere.acquire();
            if (coda.size() == 0)
                TimeUnit.SECONDS.sleep(1);
            mutex.acquire();
            System.out.println(coda.removeFirst());
            possoScrivere.release();
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//VisualizzatoreSem
