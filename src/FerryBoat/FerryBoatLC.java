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
    private boolean [] possoScendere=new boolean[50];
    private boolean possoParcheggiare=true;
    private boolean possoPartire=false;

    public FerryBoatLC(){
        for(int i=0;i<50;i++)
            possoScendere[i]=false;
    }//costruttore

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
            autoParcheggiate.addLast(autoInCoda.removeFirst());
            TimeUnit.MILLISECONDS.sleep(100);
            possoParcheggiare=true;
            if(autoParcheggiate.size()==50) {
                possoPartire = true;
                finire.signal();
                possoParcheggiare=false;
            }
            System.out.println("L'auto con id "+Thread.currentThread().getId()+" si è parcheggiata, numero auto a bordo: "+autoParcheggiate.size());
            while(!(possoScendere[Integer.parseInt(Thread.currentThread().getName())]))
                scendere.await();
            possoScendere[Integer.parseInt(Thread.currentThread().getName())-1]=true;
            System.out.println(Thread.currentThread().getName());
            System.out.println("L'auto con id "+autoParcheggiate.removeLast().getId()+" è scesa dal ferry boat");
            Thread.currentThread().interrupt();
        }finally {
            l.unlock();
        }
    }//parcheggiatiEScendi

    @Override
    void imbarca() throws InterruptedException {
        l.lock();
        try{
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
            System.out.println("Il viaggio è iniziato");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Il viaggio è giunto al termine le auto possono scendere");
            possoScendere[possoScendere.length-1]=true;
            scendere.signalAll();
        }finally {
            l.unlock();
        }
    }//terminaTraghettata

    public static void main(String[] args){
        new FerryBoatLC().test(50);
    }//main
}//FerryBoatLC
