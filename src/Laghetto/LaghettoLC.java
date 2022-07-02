package Laghetto;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LaghettoLC extends Laghetto{

    private Lock l=new ReentrantLock();
    private Condition possoPescare=l.newCondition();
    private Condition possoRipopolare=l.newCondition();
    private int numeroPesciPresenti;

    public LaghettoLC(int minPesci, int maxPesci, int numPesciIniziali) {
        super(minPesci, maxPesci, numPesciIniziali);
        numeroPesciPresenti=numPesciIniziali;
    }//costruttore


    @Override
    void iniziaPesca() throws InterruptedException {
        l.lock();
        try{
            while (!(numeroPesciPresenti>minPesci))
                possoPescare.await();
            System.out.println("Il pescatore con id "+Thread.currentThread().getId()+" sta iniziando la pesca");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200,801));
        }finally {
            l.unlock();
        }
    }//iniziaPesca

    @Override
    void finisciPesca() throws InterruptedException {
        l.lock();
        try{
            numeroPesciPresenti--;
            System.out.println("Il pescatore con id "+Thread.currentThread().getId()+" ha finito di pescare" +
                    "\nI pesci rimanenti sono: "+numeroPesciPresenti);
            if(numeroPesciPresenti<=(maxPesci-10))
                possoRipopolare.signalAll();
        }finally {
            l.unlock();
            TimeUnit.SECONDS.sleep(1);
        }
    }//finisciPesca

    @Override
    void iniziaRipopolamento() throws InterruptedException {
        l.lock();
        try{
            while(numeroPesciPresenti>=(maxPesci-10))
                possoRipopolare.await();
            System.out.println("L'addetto con id "+Thread.currentThread().getId()+" inizia a mettere pesci nel laghetto");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(300,601));
        }finally {
            l.unlock();
        }
    }//iniziaRipopolamento

    @Override
    void finisciRipopolamento() throws InterruptedException {
        l.lock();
        try{
            numeroPesciPresenti+=10;
            System.out.println("L'addetto con id "+Thread.currentThread().getId()+" ha aggiunto i pesci al laghetto");
            System.out.println("Pesci disponibili nel laghetto "+ numeroPesciPresenti);
            possoPescare.signalAll();
        }finally {
            l.unlock();
            TimeUnit.SECONDS.sleep(3);
        }
    }//fineRipopolamento

    public static void main(String[] args){
        new LaghettoLC(50,200,100).test(40,5);
    }//main
}//LaghettoLC
