package BarMod;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BarModSem extends BarMod{

    private Semaphore mutex=new Semaphore(1);
    private Semaphore possoPagare=new Semaphore(1);
    private Semaphore possoConsumare=new Semaphore(4);
    private Semaphore possoFinire=new Semaphore(0);
    private Thread clientePagante;
    private LinkedList<Thread> clientiConsumanti;

    public BarModSem(){
        clientiConsumanti=new LinkedList<>();
    }//costruttore
    @Override
    public int scengli() throws InterruptedException{
        try{
            mutex.acquire();
            if(clientePagante==null)
                return PAGA;
            if(clientiConsumanti.size()<4)
                return BEVI;
            if(codaAlBar.size()<codaAllaCassa.size())
                return BEVI;
            if(codaAlBar.size()>codaAllaCassa.size())
                return PAGA;
            else{
                int[] scelte={PAGA,BEVI};
                return scelte[new Random().nextInt(0,2)];
            }
        }finally {
            mutex.release();
        }
    }//scegli

    @Override
    public void inizia(int i) throws InterruptedException {
        mutex.acquire();
        if(i==BEVI){
            codaAlBar.add(Thread.currentThread());
            System.out.println("Il cliente "+Thread.currentThread().getId()+" é in coda per bere");
            possoConsumare.acquire();
            System.out.println("Il cliente "+Thread.currentThread().getId()+" sta consumando");
            //TimeUnit.SECONDS.sleep(new Random().nextInt(1,3));
            clientiConsumanti.add(Thread.currentThread());
            codaAlBar.remove(Thread.currentThread());
            possoConsumare.release();
            possoFinire.release();
        }else{
            codaAllaCassa.add(Thread.currentThread());
            System.out.println("Il cliente "+Thread.currentThread().getId()+" sta aspettando per pagare ");
            possoPagare.acquire();
            clientePagante=Thread.currentThread();
            incasso++;
            System.out.println("Il cliente "+Thread.currentThread().getId()+" sta pagando, incasso attuale "+incasso);
            //TimeUnit.SECONDS.sleep(new Random().nextInt(1,3));
            possoPagare.release();
            possoFinire.release();
        }
        mutex.release();
    }//inizia

    @Override
    public void finisci(int i) throws InterruptedException {
        mutex.acquire();
        possoFinire.acquire();
        if(i==BEVI){
            possoConsumare.acquire();
            System.out.println("Il cliente "+Thread.currentThread().getId()+" ha finito di bere");
            clientiConsumanti.remove(Thread.currentThread());
            possoConsumare.release();
        }else {
            possoPagare.acquire();
            incasso++;
            System.out.println("Il cliente "+Thread.currentThread().getId()+" ha finito di pagare, incasso attuale "+incasso);
            clientePagante=null;
            codaAllaCassa.remove(Thread.currentThread());
            possoPagare.release();
        }
        mutex.release();
    }//finisci

    public static void main(String[] args){
        new BarModSem().test(100000);
    }//main
}//BarModSem
