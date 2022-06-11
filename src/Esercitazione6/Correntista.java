package Esercitazione6;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Correntista implements Runnable{

    private final static int MIN_ATTESA=1;
    private final static int MAX_ATTESA=3;

    private Random random=new Random();
    private ContoCorrente cc;
    private int importo;
    private int numOperazioni;

    public Correntista(ContoCorrente cc,int importo,int numOperazioni){
        if(numOperazioni%2!=0)
            throw new RuntimeException("Il numero delle operazioni deve essere pari");
        this.cc=cc;
        this.importo=importo;
        this.numOperazioni=numOperazioni;
    }//costruttore

    private void attesaCasule() throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(MIN_ATTESA,MAX_ATTESA));
    }//attesaCasuale

    @Override
    public void run() {
        try{
            for(int i=0;i<numOperazioni;i++){
                attesaCasule();
                if(i%2==0)
                    cc.deposita(importo);//operiazioni da proteggere
                else
                    cc.preleva(importo);//operiazioni da proteggere
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Correntista "+ Thread.currentThread().getName()+ " ha termianto le sue operazioni");
    }//run
}
