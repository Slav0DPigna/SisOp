package FerryBoat;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryBoatLC extends FerryBoat {

    private Lock l=new ReentrantLock();
    private Condition salire=l.newCondition();
    private Condition parcheggiare=l.newCondition();
    private Condition scendere=l.newCondition();
    private Condition finire=l.newCondition();
    private boolean viaggioFinito=false;
    private boolean possoParcheggiare=true;
    private boolean possoPartire=false;


    @Override
    void sali() throws InterruptedException {
        l.lock();
        try{
            System.out.println("L'auto con id "+Thread.currentThread().getId()+" si è messa in fila");
            autoInCoda.add(Thread.currentThread());
            while (autoParcheggiate.size()>=50 && autoInCoda.indexOf(Thread.currentThread())!=0)
                salire.await();
        }finally {
            l.unlock();
        }
    }//sali

    @Override
    void parcheggiatiEScendi() throws InterruptedException {
        l.lock();
        try{
            System.out.println("L'auto con id "+Thread.currentThread().getId()+" è salita a bordo");
            while(!possoParcheggiare)
                parcheggiare.await();
            possoParcheggiare=false;
            autoParcheggiate.add(autoInCoda.removeFirst());
            TimeUnit.SECONDS.sleep(2);
            possoParcheggiare=true;
            if(autoParcheggiate.size()==50) {
                possoPartire = true;
                finire.signal();
                possoParcheggiare=false;
            }
            System.out.println("L'auto con id "+Thread.currentThread().getId()+" si è parcheggiata");
            while(!viaggioFinito && autoParcheggiate.indexOf(Thread.currentThread())!=(autoParcheggiate.size()-1))
                scendere.await();
            autoParcheggiate.removeFirst();
            System.out.println("L'auto con id "+Thread.currentThread().getId()+" è sceda dal ferry boat");
        }finally {
            l.unlock();
        }
    }//parcheggiatiEScendi

    @Override
    void imbarca() throws InterruptedException {
        l.lock();
        try{
            if(autoParcheggiate.size()<50)
                salire.signal();
            if(possoParcheggiare)
                parcheggiare.signal();
        }finally {
            l.unlock();
        }
    }//imbarca

    @Override
    void terminaTraghettata() throws InterruptedException {
        l.lock();
        try{
            while (!possoPartire)
                finire.await();
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Il viaggio è giunto al termine le auto possono scendere");
            viaggioFinito=true;
            scendere.signalAll();
        }finally {
            l.unlock();
        }
    }//terminaTraghettata

    public static void main(String[] args){
        new FerryBoatLC().test(55);
    }//main
}//FerryBoatLC
