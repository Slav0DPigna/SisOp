package FerryBoat;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryBoatLC extends FerryBoat{

    private Lock l=new ReentrantLock();
    private Condition possoImbarcare=l.newCondition();
    private Condition possoParcheggiare=l.newCondition();
    private Condition possoScendere=l.newCondition();
    private Condition possoPartire=l.newCondition();

    private boolean fineViaggio=false;

    @Override
    void sali() throws InterruptedException {
        l.lock();
        try{
            autoInCoda.add(Thread.currentThread());
        }finally {
            l.unlock();
        }
    }//sali

    @Override
    void parcheggiatiEScendi() throws InterruptedException {
        l.lock();
        try{
            while(autoParcheggiate.size()>=50 && !fineViaggio)
                possoParcheggiare.await();
            autoInCoda.remove(Thread.currentThread());
            autoParcheggiate.add(Thread.currentThread());
            System.out.println("Sta salendo l'auto "+Thread.currentThread().getId());
            l.unlock();
            TimeUnit.SECONDS.sleep(3);
            l.lock();
            System.out.println("Ha parcheggiato l'auto "+Thread.currentThread().getId()+" auto a bordo: "+autoParcheggiate.size());
            while (fineViaggio && autoParcheggiate.indexOf(Thread.currentThread())!=(autoParcheggiate.size()-1))
                possoScendere.await();
            System.out.println("Sta scendendo l'auto "+Thread.currentThread().getId());
            autoParcheggiate.remove(Thread.currentThread());
        }finally {
            l.unlock();
        }
    }//parcheggiaEScendi

    @Override
    void imbarca() throws InterruptedException {
        l.lock();
        try{
            while(autoInCoda.size()<=0 || autoParcheggiate.size()>=50)
                possoImbarcare.await();
            possoParcheggiare.signalAll();
            if(autoParcheggiate.size()==50)
                possoPartire.signal();
        }finally {
            l.unlock();
        }
    }//imbarca

    @Override
    void terminaTraghettata() throws InterruptedException {
        while(autoParcheggiate.size()<50)
            possoPartire.await();
        System.out.println("Il traghetto sta partendo");
        TimeUnit.SECONDS.sleep(20);
        System.out.println("Il traghetto é arrivato");
        fineViaggio=true;
    }//terminaTraghettata

    public static void main(String[] args){
        new FerryBoatLC().test(50);
    }//main
}//FerryBoatLC
