package Esercitazione11.tracciaCasello;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CaselloSem extends Thread{

    private static Semaphore caselli=new Semaphore(5);
    private static Semaphore mutex=new Semaphore(1);
    private static int incasso,moltiplicatorePerChilometro;
    private static LinkedList<Veicolo> codaVeicoli=new LinkedList<>();

    public CaselloSem(Veicolo[] veicoli){
        incasso=0;
        moltiplicatorePerChilometro=2;
        for(int i=0;i<veicoli.length;i++)
            codaVeicoli.add(veicoli[i]);
    }//Costruttore

    @Override
    public void run() {
        for(Veicolo v:codaVeicoli)
            v.start();
    }//run

    public static class Veicolo extends Thread{
        private int chilometri;

        public Veicolo(int chilometri){
            this.chilometri=chilometri;
        }//costruttore

        @Override
        public void run() {
            try{
                int tempoDiArrivo=new Random().nextInt(10,51);
                TimeUnit.SECONDS.sleep(tempoDiArrivo/* *40 ma altrimenti l'attesa è troppo lunga*/);
                caselli.acquire();
                int tempoTrascorsoAlCasello=new Random().nextInt(3,7);
                System.out.println("Il veicolo "+Thread.currentThread().getName()+" è arrivato al casello dopo "+tempoDiArrivo+" s");
                TimeUnit.SECONDS.sleep(tempoTrascorsoAlCasello);
                System.out.println("Il veicolo "+Thread.currentThread().getName()+" ha trascorso al casello "+tempoTrascorsoAlCasello+" s");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            try{
                mutex.acquire();
                incasso=incasso+chilometri*moltiplicatorePerChilometro;
                System.out.println("Il veicolo "+Thread.currentThread().getName()+" è uscito dal casello, l'incasso in questo istante è di "+incasso);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            caselli.release();
            mutex.release();
        }//run
    }//Veicolo

    public static void main(String[] args) throws InterruptedException{
        Veicolo[] veicoli=new Veicolo[10];
        for(int i=0;i<10;i++) {
            veicoli[i] = new Veicolo((i + 1) * 10);
            veicoli[i].setName("Veicolo "+i);
        }
        CaselloSem c=new CaselloSem(veicoli);
        c.start();
    }//main

}//CaselloSem
