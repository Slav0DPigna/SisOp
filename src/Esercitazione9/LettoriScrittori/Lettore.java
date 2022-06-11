package Esercitazione9.LettoriScrittori;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Lettore implements Runnable{

    private MemoriaCondivisa memoria;

    private Random random=new Random();

    private final static int MAX_TEMPO_LETTURA=4,
                             MIN_TEMPO_LETTURA=1,
                             MIN_TEMPO_ALTRO=6,
                             MAX_TEMPO_ALTRO=10;

    public Lettore(MemoriaCondivisa mem){
        memoria=mem;
    }//costruttore

    public void run(){
        try{
            while(true){
                memoria.inizioLettura();
                leggi();
                memoria.fineLettura();
                faiAltro();
            }
        }catch (InterruptedException e){}
    }//run

    private void leggi() throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(MIN_TEMPO_LETTURA,MAX_TEMPO_LETTURA));
    }//leggi

    private void faiAltro() throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(MIN_TEMPO_ALTRO,MAX_TEMPO_ALTRO));
    }//faiAltro
}//Lettore
