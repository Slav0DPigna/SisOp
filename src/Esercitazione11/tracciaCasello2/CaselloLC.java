package Esercitazione11.tracciaCasello2;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CaselloLC extends Casello {

    private Lock l=new ReentrantLock();
    private Condition[] porteC;

    private LinkedList<Thread>[] codePorte;

    public CaselloLC(int numeroPorte,int tariffa){
        super(numeroPorte,tariffa);
        codePorte=new LinkedList[numeroPorte];
        porteC=new Condition[numeroPorte];
        for(int i=0;i<numeroPorte;i++)
            codePorte[i]=new LinkedList<>();
        for(int i=0;i<numeroPorte;i++)
            porteC[i]= l.newCondition();
    }//costruttore

    @Override
    public void entra(int porta) throws InterruptedException {
        Thread t=Thread.currentThread();
        codePorte[porta].add(t);
        l.lock();
        try{
            while(!(codePorte[porta].getFirst()==t))     //per accedere al casello devi essere il primo della fila
                porteC[porta].await();
            System.out.println("Il veicolo "+ t.getName()+" é entrato nel casello");
        }finally {
            l.unlock();
        }
    }//entra

    @Override
    public void pagaEEsci(int porta, int km) throws InterruptedException {
        Thread t=Thread.currentThread();
        l.lock();
        try{
            incasso=incasso+km*this.T;
            codePorte[porta].remove(t);
            if(!codePorte[porta].isEmpty())
                porteC[porta].signalAll();
            System.out.println("Il veicolo "+t.getName()+" ha lasciato la porta "+porta+" l'incasso é di "+ incasso);
        }finally {
            l.unlock();
        }
    }//pagaEEsci

    public static void main(String[] args){
        Casello c=new CaselloLC(5,2);
        Veicolo[] v=new Veicolo[10];
        for(int i=0;i<10;i++){
            v[i] = new Veicolo(c);
            v[i].setName("Veicolo "+i);
            v[i].start();
        }
    }//main
}//CaselloLC
