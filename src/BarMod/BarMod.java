package BarMod;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class BarMod {

    protected LinkedList<Thread> codaAlBar;
    protected LinkedList<Thread> codaAllaCassa;
    protected final int BEVI=-1,PAGA=1;
    protected int incasso;

    public BarMod(){
        codaAlBar=new LinkedList<>();
        codaAllaCassa=new LinkedList<>();
        incasso=0;
    }//costruttore

    public abstract int scengli() throws InterruptedException;
    public abstract void inizia(int i) throws InterruptedException;
    public abstract void finisci(int i) throws InterruptedException;

    public void test(int numClienti){
        for(int i=0;i<numClienti;i++){
            Cliente c=new Cliente(this);
            c.start();
        }
    }//test
}//BarMod
