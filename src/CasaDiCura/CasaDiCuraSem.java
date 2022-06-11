package CasaDiCura;

import java.util.concurrent.Semaphore;

public class CasaDiCuraSem  extends CasaDiCura{

    private Semaphore mutex = new Semaphore(1);
    //Semafori del cliente
    private Semaphore possoEntrare=new Semaphore(3);
    private Semaphore possoUscire=new Semaphore(0);
    //Semafori del dottore
    private Semaphore possoIniziare=new Semaphore(0);
    private Semaphore possoFinire=new Semaphore(0);
    private Thread clienteDaoperare;
    
    @Override
    public void chiamaEIniziaOperazione() throws InterruptedException{
        possoIniziare.acquire();
        mutex.acquire();
        clienteDaoperare=codaClientiSalaDAttesa.removeFirst();
        System.out.println("Il dottore sta operando il paziente "+clienteDaoperare.getId());
        mutex.release();
        possoFinire.release();
    }//chiamaEIniziaOperazione

    @Override
    public void fineOperazione() throws InterruptedException {
        possoFinire.acquire();
        mutex.acquire();
        mutex.release();
        possoUscire.release();
        possoEntrare.release();
    }//fineOperazione

    @Override
    public void pazienteEntra() throws InterruptedException {
        possoEntrare.acquire();
        mutex.acquire();
        System.out.println("Sta entrando il paziente con id "+Thread.currentThread().getId());
        codaClientiSalaDAttesa.add(Thread.currentThread());
        System.out.println("Pazienti in coda:");
        for(Thread t :codaClientiSalaDAttesa)
            System.out.println(t.getId());
        mutex.release();
        possoIniziare.release();
    }//pazienteEntra

    @Override
    public void pazienteEsci() throws InterruptedException {
        possoUscire.acquire();
        mutex.acquire();
        incasso+=10;
        System.out.println("Il paziente "+Thread.currentThread().getId()+" è uscito\nincasso: "+incasso);
        mutex.release();
    }//pazienteEsci

    public static void main(String[] args){
        CasaDiCuraSem c=new CasaDiCuraSem();
        c.test(100000);
    }//main
}//CasaDiCuraSem
