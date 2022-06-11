package Esercitazione10.ProduttoreConsumatoreLC;

import Esercitazione9.ProduttoreConsumatore.Buffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferList extends Buffer {

    private ArrayList<Integer> buffer;
    private int limite,numElementi=0;

    private Lock l=new ReentrantLock();
    private Condition bufferPieno=l.newCondition();
    private Condition bufferVuoto=l.newCondition();

    public BufferList(int dimensione,int limite) {
        super(dimensione);
        buffer=new ArrayList<>();
        this.limite=limite;
    }//costruttore

    @Override
    public void put(int i) throws InterruptedException {
        l.lock();
        try{
            while(numElementi==limite)
                bufferPieno.await();
            buffer.add(in,i);
            in=(in+1)%limite;
            numElementi++;
            bufferVuoto.signal();
        }finally {
            l.unlock();
        }
    }//put

    @Override
    public int get() throws InterruptedException {
        int item;
        l.lock();
        try{
            while(numElementi==0)
                bufferVuoto.await();
            item=buffer.get(out);
            out=(out+1)%limite;
            bufferPieno.signal();
        }finally {
            l.unlock();
        }
        return item;
    }//get

    public static void main(String[] args){
        BufferList bl=new BufferList(100,50);
        bl.test(30,30);
    }//main
}//BufferList
