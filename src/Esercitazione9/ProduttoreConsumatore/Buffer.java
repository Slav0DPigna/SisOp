package Esercitazione9.ProduttoreConsumatore;

import Esercitazione8.Esercizio41;

public abstract class Buffer {

    protected int[] buffer;
    protected int in=0;
    protected int out=0;

    public Buffer(int dimensione){
        buffer = new int[dimensione];
    }//costruttore

    public abstract void put(int i) throws InterruptedException;

    public abstract int get() throws InterruptedException;

    public void test(int numProduttori, int numConsumatori){
        for(int i=0;i<numProduttori;i++)
            new Thread(new Produttore(this)).start();
        for(int i=0;i<numConsumatori;i++)
            new Thread(new Consumatore(this)).start();
    }//test
}//Buffer
