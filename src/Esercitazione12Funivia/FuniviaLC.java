package Esercitazione12Funivia;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FuniviaLC extends Funivia{

    private int numViaggio=-1;
    private int postiOccupati=0;

    private ArrayList<Long> idTuristi=new ArrayList<>();

    private Lock l=new ReentrantLock();
    //regolare l'entrata dei turisti
    private Condition entrataTuristiAPiedi=l.newCondition();
    private Condition entrataTuristiInBici=l.newCondition();
    //regolare l'uscita dei turisti
    private Condition uscitaTuristiAPiedi=l.newCondition();
    private Condition uscitaTuristiInBici=l.newCondition();
    //regolare la partenza della finivia per il pilota
    private Condition inizioViaggio=l.newCondition();
    private Condition fineViaggio=l.newCondition();

    private boolean permessoEntrata=false;
    private boolean permessoUscita=false;

    @Override
    public void pilotaStart() throws InterruptedException {
        l.lock();
        try {
            numViaggio++;
            permessoEntrata=true;
            if(numViaggio%2==0)
                entrataTuristiAPiedi.signalAll();
            else
                entrataTuristiInBici.signalAll();
            while (!possoIniziareIlViaggio())
                inizioViaggio.await();
        }finally {
            l.unlock();
        }
    }//pilotaStart

    private boolean possoIniziareIlViaggio(){
        return postiOccupati==6;
    }//possoIniziareIlViaggio

    @Override
    public void pilotaEnd() throws InterruptedException {
        l.lock();
        try{
            for(Long l:idTuristi)
                System.out.print(l+" ");
            System.out.println();
            permessoUscita=true;
            if(numViaggio%2==0)
                uscitaTuristiAPiedi.signalAll();
            else
                uscitaTuristiInBici.signalAll();
            idTuristi.clear();
            while (!possoFinireIlViaggio())
                fineViaggio.await();
        }finally {
            l.unlock();
        }
    }//pilotaEnd

    private boolean possoFinireIlViaggio() {
        return postiOccupati==0;
    }//possoFinireIlViaggio

    @Override
    public void turistaSali(int t) throws InterruptedException {
        l.lock();
        try{
            if(t==TURISTA_A_PIEDI){
                while(!possoSalireAPiedi())
                    entrataTuristiAPiedi.await();
                postiOccupati++;
                idTuristi.add(Thread.currentThread().getId());
                if(postiOccupati==6){
                    permessoEntrata=false;
                    inizioViaggio.signal();
                }
            }else {
                while (!possoSalireInBici())
                    entrataTuristiInBici.await();
                postiOccupati+=2;
                idTuristi.add(Thread.currentThread().getId());
                if(postiOccupati==6){
                    permessoEntrata=false;
                    inizioViaggio.signal();}
            }
        }finally {
            l.unlock();
        }
    }//turistaSali

    private boolean possoSalireInBici() {
        return permessoEntrata && numViaggio%2==1 && postiOccupati<6;
    }//possoSalireInBici

    private boolean possoSalireAPiedi() {
        return permessoEntrata && numViaggio%2==0 && postiOccupati<6 ;
    }//possoSalireAPiedi

    @Override
    public void turistaScendi(int t) throws InterruptedException {
        l.lock();
        try{
            if(t==TURISTA_A_PIEDI){
                while(!possoScendere())
                    uscitaTuristiAPiedi.await();
                postiOccupati--;
                if(postiOccupati==0){
                    fineViaggio.signal();
                    permessoUscita=false;
                }
            }else{
                while(!possoScendere())
                    uscitaTuristiInBici.await();
                postiOccupati-=2;
                if(postiOccupati==0){
                    fineViaggio.signal();
                    permessoUscita=false;
                }
            }
        }finally {
            l.unlock();
        }
    }//turistaScendi

    private boolean possoScendere() {
        return permessoUscita;
    }//possoScendere

    public static void main(String[] args){
        Funivia f=new FuniviaLC();
        f.test(18,9);
    }//main
}//FuniviaLC
/*
il tipo qua é particolarmente prolisso con l'uso delle variabili
di conseguenza potrebbe funzionare con la metá delle cose dichiarate
 */