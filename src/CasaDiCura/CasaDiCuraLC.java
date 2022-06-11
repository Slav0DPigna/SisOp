package CasaDiCura;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CasaDiCuraLC extends CasaDiCura {

    private Lock l=new ReentrantLock();
    private Condition opera=l.newCondition();
    private boolean possoOperare=false;
    private Condition finisci=l.newCondition();
    private boolean possoFinire=false;
    private Condition entraInSala=l.newCondition();
    private Condition entra=l.newCondition();
    private Condition esci=l.newCondition();
    private boolean possoUscire=false;
    private Thread clienteDaOperare;

    @Override
    public void chiamaEIniziaOperazione() throws InterruptedException {
        l.lock();
        try {
            if(codaClientiSalaDAttesa.size()<3)
                entraInSala.signalAll();
            possoOperare=true;
            while (!(codaClientiSalaDAttesa.size()!=0 && codaClientiSalaDAttesa.getFirst().equals(clienteDaOperare)))
                opera.await();
            System.out.println("Il dottore sta operando il paziente "+clienteDaOperare.getId());
            //TimeUnit.SECONDS.sleep(new Random().nextInt(1,6));
            possoFinire=true;
            finisci.signal();
        }finally {
            l.unlock();
        }
    }//chiamaEIniziaOperazione

    @Override
    public void pazienteEntra() throws InterruptedException {
        l.lock();
        try{
            while (codaClientiSalaDAttesa.size()==3)
                entraInSala.await();
            codaClientiSalaDAttesa.add(Thread.currentThread());
            System.out.println("Il paziente "+Thread.currentThread().getId()+" é in sala d'attesa");
            while (!possoOperare)
                entra.await();
            possoOperare=false;
            clienteDaOperare=codaClientiSalaDAttesa.getFirst();
            System.out.println("Il paziente "+clienteDaOperare.getId()+" é entrato in sala operatoria");
            opera.signal();
        }finally {
            l.unlock();
        }
    }//pazienteEntra

    @Override
    public void fineOperazione() throws InterruptedException {
        l.lock();
        try{
            while(!possoFinire)
                finisci.await();
            incasso+=10;
            System.out.println("Il dottore ha finito col paziente "+ clienteDaOperare.getId()+" incasso attuale "+incasso);
            //TimeUnit.SECONDS.sleep(new Random().nextInt(1,6));
            clienteDaOperare=null;
            possoFinire=false;
            possoUscire=true;
            esci.signal();
            entraInSala.signalAll();
            if(codaClientiSalaDAttesa.size()!=0)
                entra.signal();
        }finally {
            l.unlock();
        }
    }//fineOperazione

    @Override
    public void pazienteEsci() throws InterruptedException {
        l.lock();
        try{
            while (!possoUscire)
                esci.await();
            possoUscire=false;
            System.out.println("Il paziente "+codaClientiSalaDAttesa.removeFirst().getId()+" é uscito");
        }finally {
            l.unlock();
        }
    }//pazienteEsci

    public static void main(String[] args){
        while (true) {
            new CasaDiCuraLC().test(100);
        }
    }//main
}//CasaDiCuraLC
