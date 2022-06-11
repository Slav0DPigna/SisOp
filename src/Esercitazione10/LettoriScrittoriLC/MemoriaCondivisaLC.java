package Esercitazione10.LettoriScrittoriLC;

import Esercitazione9.LettoriScrittori.MemoriaCondivisa;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MemoriaCondivisaLC extends MemoriaCondivisa {
    private int numLettoriInLettura=0;
    private boolean scrittoreInScrittura=false;

    private Lock l=new ReentrantLock();
    private Condition possoScrivere=l.newCondition();
    private Condition possoLeggere=l.newCondition();
    /*
    Notiamo come di costruisce il castello del sistema lock condition,
    il sistema migliore per usare i lock e le condition è di usare un solo lock e associare tutte le condition
    di cui abbiamo bisogno nel programma.
    Per usare i lock possiamo far aspettare il thread su una condizione in un while ovviamente prima di aver usato l.lock
    prima della sezione critica
    Ovviamente non è l'unico modo di usare i monitor tenendo anche presente che non esistono solo i monitor nativi.
     */

    @Override
    public void inizioScrittura() throws InterruptedException {
        l.lock();
        try{
            while(/*numLettoriInLettura>0 || scrittoreInScrittura*/ !possoScrivere())
                possoScrivere.await();
            scrittoreInScrittura=true;
        }finally {
            l.unlock();
        }
    }//inizioScrittura

    private boolean possoScrivere(){
        return numLettoriInLettura==0 && !scrittoreInScrittura;
    }//possoScrivere

    @Override
    public void fineScrittura() throws InterruptedException {
        l.lock();
        try {
            scrittoreInScrittura=false;
            possoLeggere.signalAll();
            possoScrivere.signal();
        }finally {
            l.unlock();
        }
    }//fineScrittura

    @Override
    public void inizioLettura() throws InterruptedException {
        l.lock();
        try{
            while (scrittoreInScrittura){
                possoLeggere.await();//se c'é uno scrittore che scrive aspettiamo
            }
            numLettoriInLettura++;
        }finally {
            l.unlock();
        }
    }//inizioLettura

    @Override
    public void fineLettura() throws InterruptedException {
        l.lock();
        try {
            numLettoriInLettura--;
            if(numLettoriInLettura==0)
                possoScrivere.signal();//se non ci sono lettori segnaliamo che si puó scrivere
        }finally {
            l.unlock();
        }
    }//fineLettura
}//LettoriScrittoriLC
/*
Dopo aver passato un po di tempo a vedere come si scrive il codice ho capito il concetto ma sono lontano dal riuscire
a fare l'esame ora si provano ad usare oggetti che si possono usare sia con lock che con semafori e si mette quello che
si vuole proteggere in una classe astratta.(vai nel packege tracciaCasello2)
 */
