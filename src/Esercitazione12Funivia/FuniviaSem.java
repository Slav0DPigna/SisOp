package Esercitazione12Funivia;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FuniviaSem extends Funivia{

    //per la mutua esclusione
    private Semaphore mutex=new Semaphore(1);
    //per regolare l'entrata dei turisti a piedi o in bici nella funivia
    private Semaphore permettiEntrataAPiedi=new Semaphore(0);
    private Semaphore permettiEntrataInBici=new Semaphore(0);
    /*
    verifichiamo il numero dei viaggi num pari a piedi
    dispari in bici
     */
    private int numViaggio=-1;
    //per regolare l'uscita dei turisti dalla funivia
    private Semaphore permettiUscitaAPiedi=new Semaphore(0);
    private Semaphore permettiUScitaInBici=new Semaphore(0);
    private int postiOccupati=0;
    //regola partenza funivia
    private Semaphore permettiPartenza=new Semaphore(0);
    //permette di completare il metodo pilotaEnd() e comincia la discesa
    private Semaphore permettiFine=new Semaphore(0);
    public ArrayList<Long> idTuristi=new ArrayList<>();
    @Override
    public void pilotaStart() throws InterruptedException {
        numViaggio++;
        System.out.println("Sta iniziando il viaggio "+numViaggio);
        if(numViaggio%2==0){
            permettiEntrataAPiedi.release(6);
        }else{
            permettiEntrataInBici.release(3);
        }
        //il pilota parte solo quando l'ultimo turista é a bordo
        permettiPartenza.acquire();
    }//pilotaStart

    @Override
    public void pilotaEnd() throws InterruptedException {
        System.out.println("Viaggio numero "+ numViaggio);
        for(Long l:idTuristi)
            System.out.print(l+" ");
        System.out.println();
        if(numViaggio%2==0)
            permettiUscitaAPiedi.release(6);
        else
            permettiUScitaInBici.release(3);
        permettiFine.acquire();
        idTuristi.clear();
    }//pilotaEnd

    @Override
    public void turistaSali(int t) throws InterruptedException {
        if(t==TURISTA_A_PIEDI){
            permettiEntrataAPiedi.acquire();
            mutex.acquire();
            postiOccupati++;
            idTuristi.add(Thread.currentThread().getId());
        }else {//turista in bici
            permettiEntrataInBici.acquire();
            mutex.acquire();
            postiOccupati=postiOccupati+2;//un posto é del turista e l'altro é della bici
            idTuristi.add(Thread.currentThread().getId());
        }
        if(postiOccupati==6)
            permettiPartenza.release();
        mutex.release();
    }//turistaSali

    @Override
    public void turistaScendi(int t) throws InterruptedException {
        if(t==TURISTA_A_PIEDI){
            permettiUscitaAPiedi.acquire();
            mutex.acquire();
            postiOccupati--;
        }else {
            permettiUScitaInBici.acquire();
            mutex.acquire();
            postiOccupati-=2;
        }
        if(postiOccupati==0){//comunico che la funivia é vuota e puó scendere a valle
            System.out.println("Il viaggio "+numViaggio+" è terminato");
            permettiFine.release();}
        mutex.release();
    }//turistaScendi

    public static void main(String[] args){
        Funivia f=new FuniviaSem();
        f.test(18,9);
    }//main
}//FuniviaSem
