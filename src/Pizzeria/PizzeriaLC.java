package Pizzeria;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PizzeriaLC extends Pizzeria{

    private Lock l=new ReentrantLock();
    private Condition sedermi=l.newCondition();
    private Condition preparare=l.newCondition();
    private Condition mangiare=l.newCondition();
    private boolean possoMangiare=false;
    private boolean possoSedermi=true;
    private boolean possoPreparare=false;

    public PizzeriaLC(){
        super();
    }//costruttore

    @Override
    void mangiaPizza() throws InterruptedException {
        l.lock();
        try{
            while (!possoSedermi || clientiSeduti.size()==5)
                sedermi.await();
            clientiSeduti.add(Thread.currentThread());
            System.out.println("Il cliente con id "+Thread.currentThread().getId()+" si è seduto");
            if(clientiSeduti.size()==5) {
                possoPreparare = true;
                preparare.signal();
            }
            while (!possoMangiare)
                mangiare.await();
            System.out.println("Il cliente con id "+Thread.currentThread().getId()+" sta mangiado");
            TimeUnit.SECONDS.sleep(new Random().nextInt(3,7));
        }finally {
            l.unlock();
        }
    }//mangiaPizza

    @Override
    void pizzaMangiata() throws InterruptedException {
        l.lock();
        try{
            System.out.println("Il cliente con id "+Thread.currentThread().getId()+" ha finito di mangiare la pizza");
            clientiSeduti.remove(Thread.currentThread());
            if(clientiSeduti.size()==0) {
                possoMangiare=false;
                possoSedermi = true;
                sedermi.signalAll();
                System.out.println("Tutti i clienti si sono alzati si possono sedere i prossimi");
            }
        }finally {
            l.unlock();
        }
    }//pizzaMangiata

    @Override
    void preparaPizza() throws InterruptedException {
        l.lock();
        try{
            while (!possoPreparare)
                preparare.await();
            System.out.println("Il pizzaiolo sta preparando la pizza");
            TimeUnit.SECONDS.sleep(5);
        }finally {
            l.unlock();
        }
    }//preparaPizza

    @Override
    void pizzaPronta() throws InterruptedException {
        l.lock();
        try{
            System.out.println("Il pizzaiolo ha preparato la pizza");
            possoPreparare=false;
            possoMangiare=true;
            mangiare.signalAll();
        }finally {
            l.unlock();
        }
    }//pizzaPronta

    public static void main(String[] args){
        PizzeriaLC p=new PizzeriaLC();
        p.test(30);
    }//main
}//PizzeriaLC
