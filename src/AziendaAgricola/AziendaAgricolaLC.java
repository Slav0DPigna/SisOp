package AziendaAgricola;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AziendaAgricolaLC extends AziendaAgricola{

    private Lock l=new ReentrantLock();
    private Condition possoPagare=l.newCondition();
    private Condition possoPortareVia=l.newCondition();
    private Condition possoMettereAPosto=l.newCondition();
    @Override
    public void paga(int sacchiDaPagare) throws InterruptedException {
        l.lock();
        try{
        while (sacchiDaPagare>=sacchiInInventario) {
            possoMettereAPosto.signal();
            possoPagare.await();
        }
        incasso+=sacchiDaPagare;
        System.out.println("Il cliente "+Thread.currentThread().getId()+" richiede "+sacchiDaPagare+" sacchi\nL'incasso attuale é di "+incasso);
        possoPortareVia.signal();
        }finally {
            l.unlock();
        }
    }//paga

    @Override
    public void portaVia(int sacchiDaPortareVia) throws InterruptedException {
        l.lock();
        try{
        while (sacchiDaPortareVia>=sacchiInInventario) {
            possoMettereAPosto.signal();
            possoPortareVia.await();
        }
        for(int i=sacchiDaPortareVia;i>0;i--) {
            System.out.println(i);
            TimeUnit.SECONDS.sleep(1);
        }
        sacchiInInventario-=sacchiDaPortareVia;
        System.out.println("Il cliente "+Thread.currentThread().getId()+" ha portato via "+sacchiDaPortareVia+"\nIn inventario ne restano "+sacchiInInventario);
        possoPagare.signal();
        }finally {
            l.unlock();
        }
    }//portaVia

    @Override
    public void mettiAPosto(int sacchiDaDare) throws InterruptedException {
        l.lock();
        try {
            possoMettereAPosto.await();
            System.out.println("Sta intervenendo il magazziniere");
            sacchiInInventario += 20;
            possoPagare.signal();
        }finally{
        l.unlock();
        }
    }//mettiAPosto

    public static void main(String[] args){
        AziendaAgricolaLC a=new AziendaAgricolaLC();
        a.test(100);
    }//main
}//AziendaAgricolaLC
