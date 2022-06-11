package Esercitazione12Funivia;

import java.util.concurrent.TimeUnit;

public class Pilota implements Runnable{

    private Funivia funivia;
    private final int TEMPO_SALITA=5,TEMPO_DISCESA=2;

    public Pilota(Funivia f){
        funivia=f;
    }//Costruttore

    public void run(){
        try{
            while(true){
                //il pilota attende che la funivia si riempie
                funivia.pilotaStart();
                TimeUnit.SECONDS.sleep(TEMPO_SALITA);
                //Il pilota attende che i turisti scendono dalla funivia per tornare a valle
                funivia.pilotaEnd();
                TimeUnit.SECONDS.sleep(TEMPO_DISCESA);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run

}//Pilota
