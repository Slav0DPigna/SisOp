package Esercitazione10.ProduttoreConsumatoreLC;

import java.util.ArrayList;
import java.util.LinkedList;

public class BufferFIFO extends BufferLC{

    private ArrayList<Thread> codaProduttori=new ArrayList<>();
    private ArrayList<Thread> codaConsumatori=new ArrayList<>();

    public BufferFIFO(int dimensione) {
        super(dimensione);
    }//Costruttore

    @Override
    public void put(int i) throws InterruptedException {
        l.lock();
        try{
            codaProduttori.add(Thread.currentThread());
            /*la parte difficile in sostanza oltre a far attenzione di inserire il thread prima
              ma il controllo che facciamo nel while essendo che dobbiamo prendere il thread con l'id minore
             */
            while(!possoInserire())
                bufferPieno.await();
            codaProduttori.remove(Thread.currentThread());
            buffer[in]=i;
            in=(in+1)%buffer.length;
            numElementi++;
            bufferVuoto.signalAll();
        }finally {
            l.unlock();
        }
    }//put

    private boolean possoInserire(){
        if(numElementi>=buffer.length)
            return false;
        for(int i=0;i<codaProduttori.size();i++)
            if(Thread.currentThread().getId()>codaProduttori.get(i).getId())
                return false;
        return true;
    }//possoInserire

    @Override
    public int get() throws InterruptedException {
        int item;
        l.lock();
        try{
            codaConsumatori.add(Thread.currentThread());
            while(!possoConsumare())
                bufferVuoto.await();
            codaConsumatori.remove(Thread.currentThread());
            item=buffer[out];
            out=(out+1)%buffer.length;
            numElementi--;
            bufferPieno.signal();
        }finally {
            l.unlock();
        }
        return item;
    }//get

    private boolean possoConsumare(){
        if(numElementi==0)
            return false;
        for (int i=0;i<codaConsumatori.size();i++)
            if(Thread.currentThread().getId()>codaConsumatori.get(i).getId())
                return false;
        return true;
    }//possoConsumare
}//BufferFIFO
