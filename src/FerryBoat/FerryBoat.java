package FerryBoat;

import java.util.LinkedList;

public abstract class FerryBoat {

    protected LinkedList<Thread> autoInCoda;
    protected LinkedList<Thread> autoParcheggiate;

    public FerryBoat(){
        autoInCoda=new LinkedList<>();
        autoParcheggiate=new LinkedList<>();
    }//costruttore

    abstract void sali() throws InterruptedException;
    /*
    Permette all'auto d'imbarcarsi.L'auto si deve mettere in fila in ordine FIFO e attendere
    che l'addetto gli dia l'ordine di salire.
     */
    abstract void parcheggiatiEScendi() throws InterruptedException;
    /*
    Permette all'auto di parcheggiarsi all'interno del ferry boat e di attendere di scendere in ordine LIFO quando il
    viaggio sarà terminato
     */
    abstract void imbarca() throws InterruptedException;
    /*
    Permette all'addetto d'imbarcare un'auto e attendere che questa parcheggi.Finché l'auto non ha finito il
    parcheggio l'addetto non puó far entrare un'altra auto
     */
    abstract void terminaTraghettata() throws InterruptedException;
    /*
    Permette all'addetto di terminare il viaggio e di e far scendere la prima auto dal ferry boat in ordine LIFO.
    ------------------------------------------------------------
    -Il traghetto puó partire solo dopo aver raggiunto una capacità di 50 auto.
    -Il tempo di parcheggio dell'auto varia tra 1 e 2 minuti.
    -Il tempo di viaggio del ferry boat é di 20 minuti.
    Si consideri la seguente variante in cui il ferry-boat fornisce un servizio continuo
    di traghetto. Quando termina il viaggio da Reggio a messina, l'addetto attende che tutte le auto
    scendono dal ferry-boat prima di far salire le auto dall'altra sponda ed effettuare il viaggio di
    ritorno da messina a Reggio.
    FerryBoatSem sarebbe anche interessante da implementare
     */

    public void test(int numeroAuto){
        Addetto a=new Addetto(this);
        a.setDaemon(true);
        a.start();
        for(int i=0;i<numeroAuto;i++){
            Auto auto=new Auto(this);
            auto.setName(String.valueOf(i%50));
            auto.start();
        }
    }//test
}//FerryBoat
