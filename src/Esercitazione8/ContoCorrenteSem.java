package Esercitazione8;

import Esercitazione6.ContoCorrente;

import java.util.concurrent.Semaphore;

public class ContoCorrenteSem extends ContoCorrente {

    private Semaphore mutex=new Semaphore(1);

    public ContoCorrenteSem(int depositoIniziale){
         super(depositoIniziale);
    }//deposita

    @Override
    public void deposita(int importo) {
        try{
            mutex.acquire();
            deposito+=importo;
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//deposita

    @Override
    public void preleva(int importo) {
        try{
            mutex.acquire();
            deposito-=importo;
            mutex.release();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }//preleva
}//ContoCorrenteSem
/*
da notare come si usa in questo caso il semaforo con i relativi metodi acquire e
release invece degli atomic integer
 */
