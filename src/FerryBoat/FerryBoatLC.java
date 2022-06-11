package FerryBoat;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryBoatLC extends FerryBoat{

    private Lock l=new ReentrantLock();
    private Condition salire=l.newCondition();
    private Condition partire=l.newCondition();
    private Condition scendere=l.newCondition();
    private boolean fineViaggio=false;

    @Override
    void sali() throws InterruptedException {
        l.lock();
        try{
            System.out.println("L'auto "+Thread.currentThread().getId()+" si è messa in coda");
            autoInCoda.add(Thread.currentThread());
            if(autoParcheggiate.size()==50)
                partire.signal();
            while(!possoSalire())
                salire.await();
        }finally {
            l.unlock();
        }
    }//sali

    private boolean possoSalire(){
        return autoInCoda.indexOf(Thread.currentThread())==0 && autoParcheggiate.size()<50;
    }//possoSalire

    @Override
    void parcheggiatiEScendi() throws InterruptedException {
        l.lock();
        try{
            System.out.println("L'auto "+Thread.currentThread().getId()+" sta salendo");
            TimeUnit.SECONDS.sleep(5);
            Thread autoSalente=autoInCoda.removeFirst();
            System.out.println(autoSalente.getId());
            autoParcheggiate.add(autoSalente);
            System.out.println("L'auto "+Thread.currentThread().getId()+" ha parcheggiato");
            while(!possoScendere())
                scendere.await();
            System.out.println("L'auto "+Thread.currentThread().getId()+" è scesa");
        }finally {
            l.unlock();
        }
    }//parcheggiatiEScendi

    private boolean possoScendere(){
        return fineViaggio && autoParcheggiate.indexOf(Thread.currentThread())==(autoParcheggiate.size()-1);
    }//possoScendere

    @Override
    void imbarca() throws InterruptedException {
        l.lock();
        try{
            System.out.println("Imbarco le auto");
            while(autoParcheggiate.size()<50)
                salire.signal();
            partire.await();
        }finally {
            l.unlock();
        }
    }//imbarca

    @Override
    void terminaTraghettata() throws InterruptedException {
        l.lock();
        try{
            System.out.println("Il viaggio è inziato");
            TimeUnit.SECONDS.sleep(15);
            fineViaggio=true;
            scendere.signalAll();
            System.out.println("Il viaggio è terminato");
        }finally {
            l.unlock();
        }
    }//terminaTraghettata

    public static void main(String[] args){
        new FerryBoatLC().test(50);
    }//main
}//FerryBoatLC
