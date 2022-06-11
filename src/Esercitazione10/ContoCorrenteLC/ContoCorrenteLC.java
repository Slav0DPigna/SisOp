package Esercitazione10.ContoCorrenteLC;

import Esercitazione6.ContoCorrente;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContoCorrenteLC extends ContoCorrente {

    private Lock l=new ReentrantLock();

    public ContoCorrenteLC(int depositoIniziale) {
        super(depositoIniziale);
    }//costruttore

    @Override
    public void deposita(int importo) {
        l.lock();
        try{
            deposito+=importo;
        }finally {
            l.unlock();
        }
    }//deposita

    @Override
    public void preleva(int importo) {
        l.lock();
        try {
            deposito-=importo;
        }finally {
            l.unlock();
        }
    }//preleva
}//ContoCorrenteLC

/*
Il lock(per def) permette a un solo thread per volta di eseguire operazioni
nella zona protetta, infatti questa é una soluzione thread safe.
prima implementazione dei Lock vediamo nel prossimo esempio come si usano anche le condition.
 */
