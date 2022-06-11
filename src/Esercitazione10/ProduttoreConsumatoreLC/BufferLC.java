package Esercitazione10.ProduttoreConsumatoreLC;

import Esercitazione9.ProduttoreConsumatore.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferLC extends Buffer {

    protected int numElementi=0;

    protected Lock l=new ReentrantLock();
    protected Condition bufferPieno=l.newCondition();
    protected Condition bufferVuoto=l.newCondition();

    public BufferLC(int dimensione) {
        super(dimensione);
    }

    @Override
    public void put(int i) throws InterruptedException {
        l.lock();
        try{
            while(numElementi==buffer.length)
                bufferPieno.await();//aspettiamo se il buffer é pieno
            buffer[in]=i;
            in=(in+1)%buffer.length;
            numElementi++;
            bufferVuoto.signal();//segnaliamo che il buffer non é piú vuoto
        }finally {
            l.unlock();
        }
    }//put

    @Override
    public int get() throws InterruptedException {
        int item;
        l.lock();
        try{
            while (numElementi==0)
                bufferVuoto.await();//aspettiamo che il buffer non sia vuoto
            item=buffer[out];
            out=(out+1)%buffer.length;
            numElementi--;
            bufferPieno.signal();//segnaliamo che il buffer non é piú pieno
        }finally {
            l.unlock();
        }
        return item;
    }//get

    public static void main(String[] args){
        BufferLC b=new BufferLC(30);
        b.test(30,50);
    }//main
}//ProduttoreConsumatoreLC
