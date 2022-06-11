package AziendaAgricola;

import java.sql.Time;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AziendaAgricolaSem extends AziendaAgricola{
    private Semaphore mutex=new Semaphore(1);
    private Semaphore possoPagare=new Semaphore(1);
    private Semaphore possoPortareVia=new Semaphore(0);
    private Semaphore possoMettereAPosto=new Semaphore(0);
    private Semaphore sacchiASufficienza=new Semaphore(30);
    @Override
    public void paga(int sacchiDaPagare) throws InterruptedException {
        possoPagare.acquire();
        mutex.acquire();
        this.sacchiDaDare=sacchiDaPagare;
        incasso+=sacchiDaPagare;
        System.out.println("Il cliente con id "+Thread.currentThread().getId()+" ha richiesto "+sacchiDaPagare+" sacchi\nNe restano "+sacchiInInventario+"\nincasso: "+incasso);
        mutex.release();
        possoPortareVia.release();
    }//paga

    @Override
    public void portaVia(int sacchiDaPortareVia) throws InterruptedException {
        possoPortareVia.acquire();
        for(int i=0;i<sacchiDaPortareVia;i++)
           TimeUnit.SECONDS.sleep(1);
        possoMettereAPosto.release();
    }//portaVia

    @Override
    public void mettiAPosto(int sacchiDaDare) throws InterruptedException {
        possoMettereAPosto.acquire();
        mutex.acquire();
        if(sacchiDaDare>=sacchiInInventario) {
            System.out.println("Il magazziniere sta andando a riempire il magazzino");
            sacchiInInventario += 20;
            sacchiASufficienza.release(20);
        }
        sacchiASufficienza.acquire(sacchiDaDare);
        sacchiInInventario-=sacchiDaDare;
        System.out.println("Il magazziniere ha finito di servire il cliente dandogli "+sacchiDaDare+" sacchi");
        mutex.release();
        possoPagare.release();
    }//riempi

    public static void main(String[] args){
        new AziendaAgricolaSem().test(100);
    }
}//AziendaAgricolaSem
