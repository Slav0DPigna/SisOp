package Esercitazione12.VisualizzatoreLC;

import Esercitazione12.Utente;
import Esercitazione12.Visualizzatore;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VisualizzatoreLC extends Visualizzatore {

    public static Lock l=new ReentrantLock();
    public static Condition possoLeggere=l.newCondition();
    public static Condition possoScrivere=l.newCondition();

    public VisualizzatoreLC(LinkedList<String> coda) {
        super(coda);
    }//costruttore

    public void run(){
        l.lock();
        try{
            while (coda.size()==0)
                possoLeggere.await();
            System.out.println(coda.removeFirst());
            possoScrivere.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            l.unlock();
        }
    }//run

    public static class UtenteLC extends Utente{

        public UtenteLC(LinkedList<String> coda) {
            super(coda);
        }//costruttore

        public void run(){
            l.lock();
            int numString=new Random().nextInt(1,6);
            try{
                while (coda.size()+numString>100)
                    possoScrivere.await();
                for(int i=0;i<numString;i++)
                    coda.add("x");
                possoLeggere.signal();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                l.unlock();
            }
        }//run
    }//UtenteLC

    public static void main(String[] args){
        LinkedList<String> coda=new LinkedList<>();
        VisualizzatoreLC v=new VisualizzatoreLC(coda);
        v.start();
        UtenteLC[] utenti=new UtenteLC[10];
        for(int i=0;i<10;i++){
            utenti[i]=new UtenteLC(coda);
            utenti[i].setName("Utente "+i);
            utenti[i].start();
        }
    }//main
}//UtenteLC
