package Esercitazione9.LettoriScrittori;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scrittore implements Runnable{

    private MemoriaCondivisa memoria;

    private Random random=new Random();

    private final static int MAX_TEMPO_SCRITTURA=3,
                             MIN_TEMPO_SCRITTURA=2,
                             MIN_TEMPO_ALTRO=10,
                             MAX_TEMPO_ALTRO=20;
    public Scrittore(MemoriaCondivisa mem){
        this.memoria=mem;
    }//costruttore

    public void run(){
        try{
            while(true){
                memoria.inizioLettura();
                scrivi();
                memoria.fineLettura();
                faiAltro();
            }
        }catch (InterruptedException e){}
    }//run

    private void scrivi() throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(MIN_TEMPO_SCRITTURA,MAX_TEMPO_SCRITTURA));
    }//scrivi

    private void faiAltro() throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(MIN_TEMPO_ALTRO,MAX_TEMPO_ALTRO));
    }

}//Scrittore
