package Pizzeria;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PizzeriaSem extends Pizzeria{

    private Semaphore mutex=new Semaphore(1);
    private Semaphore possoMangiare=new Semaphore(0);
    private Semaphore possoSedermi=new Semaphore(5);
    private Semaphore possoPreparare=new Semaphore(0);

    public PizzeriaSem(){
        super();
    }//costruttore
    @Override
    void mangiaPizza() throws InterruptedException {
        possoSedermi.acquire();
        mutex.acquire();
        clientiSeduti.add(Thread.currentThread());
        if(clientiSeduti.size()==5)
            possoPreparare.release();
        System.out.println("Il cliente con id "+Thread.currentThread().getId()+" si è seduto");
        mutex.release();
        possoMangiare.acquire();
        System.out.println("Il cliente con id "+Thread.currentThread().getId()+" sta mangiando");
        TimeUnit.SECONDS.sleep(new Random().nextInt(3,6));
    }//mangiaPizza

    @Override
    void pizzaMangiata() throws InterruptedException {
        mutex.acquire();
        System.out.println("Il cliente "+Thread.currentThread().getId()+" ha finito di mangiare la pizza ");
        clientiSeduti.remove(Thread.currentThread());
        if(clientiSeduti.size()==0)
            possoSedermi.release(5);
        mutex.release();
    }//pizzaMangiata

    @Override
    void preparaPizza() throws InterruptedException {
        possoPreparare.acquire();
        mutex.acquire();
        System.out.println("Il pizzaiolo sta preperando la pizza");
        mutex.release();
    }//preparaPizza

    @Override
    void pizzaPronta() throws InterruptedException {
        mutex.acquire();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Il pizzaiolo ha preparato la pizza i clienti possono mangiare");
        possoMangiare.release(5);
        mutex.release();
    }//pizzaPronta

    public static void main(String[] args){
        PizzeriaSem p=new PizzeriaSem();
        p.test(20);
    }//main
}//PizzeriaSem
