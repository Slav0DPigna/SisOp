package Laghetto;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LaghettoSem extends Laghetto{

    private Semaphore mutex=new Semaphore(1);
    private Semaphore pesciPescabili=new Semaphore(numPesciIniziali-minPesci);
    private Semaphore pesciAggiungibili=new Semaphore(maxPesci-numPesciIniziali);
    private int numPesciDisponibili;

    public LaghettoSem(int minPesci, int maxPesci,int numPesciIniziali) {
        super(minPesci, maxPesci,numPesciIniziali);
        numPesciDisponibili=numPesciIniziali;
    }//costruttore


    @Override
    void iniziaPesca() throws InterruptedException {
        pesciPescabili.acquire();
        System.out.println("Il pescatore con id "+Thread.currentThread().getId()+" sta inziando la pesca ");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200,801));
    }//iniziaPesca

    @Override
    void iniziaRipopolamento() throws InterruptedException {
        pesciAggiungibili.acquire(10);
        System.out.println("L'addetto con id "+Thread.currentThread().getId()+" inizia a mettere pesci nel laghetto");
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(300,601));
        mutex.release();
    }//inizaRipopolamento

    @Override
    void finisciPesca() throws InterruptedException {
        mutex.acquire();
        numPesciDisponibili--;
        pesciAggiungibili.release();
        System.out.println("Pesci disponibili nel laghetto "+ numPesciDisponibili);
        System.out.println("Il pescatore con id "+ Thread.currentThread().getId()+" si allontana dal laghetto");
        TimeUnit.SECONDS.sleep(1);
        mutex.release();
    }//finisciPesca

    @Override
    void finisciRipopolamento() throws InterruptedException {
    mutex.acquire();
    pesciPescabili.release(10);
    numPesciDisponibili+=10;
    System.out.println("L'addetto con id "+Thread.currentThread().getId()+" ha aggiunto i pesci al laghetto");
    System.out.println("Pesci disponibili nel laghetto "+ numPesciDisponibili);
    TimeUnit.SECONDS.sleep(3);
    mutex.release();
    }//finisciRipopolamento

    public static void test(int minPesci, int maxPesci,int numPesciIniziali,int numPescatori,int numAddetti){
        LaghettoSem l=new LaghettoSem(minPesci,maxPesci,numPesciIniziali);
        for(int i=0;i<numPescatori;i++)
            new Pescatore(l).start();
        for (int i=0;i<numAddetti;i++)
            new Addetto(l).start();
    }//test

    public static void main(String[] args){
        LaghettoSem.test(50,200,100,40,5);
    }//main
}//LaghettoSem
