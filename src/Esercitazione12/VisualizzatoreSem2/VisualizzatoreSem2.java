package Esercitazione12.VisualizzatoreSem2;

import Esercitazione12.Utente;
import Esercitazione12.Visualizzatore;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class VisualizzatoreSem2  extends Visualizzatore {

    public static Semaphore possoLeggere=new Semaphore(0);
    public static Semaphore mutex=new Semaphore(1);
    public static Semaphore[] possoScrivere=new Semaphore[10];

    public VisualizzatoreSem2(LinkedList<String> coda) {
        super(coda);
        possoScrivere[0]=new Semaphore(1);
        for(int i=1;i<10;i++)
            possoScrivere[i]=new Semaphore(0);
    }//costruttore

    public void run(){
        try {
            possoLeggere.acquire();
            mutex.acquire();
            System.out.println(coda.removeFirst());
            while (coda.size()>95)
                System.out.println(coda.removeFirst());
            for(int i=0;i<possoScrivere.length;i++)
                possoScrivere[i].release();
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run

    public static class UtenteSem2 extends Utente{
        private int id;

        public UtenteSem2(LinkedList<String> coda,int id) {
            super(coda);
            this.id=id;
        }//costruttore

        public void run(){
            try{
                possoScrivere[id].acquire();
                mutex.acquire();
                int numString =new Random().nextInt(1,6);
                for(int i=0;i<numString;i++)
                    coda.add("x");
                possoScrivere[(id+1)% possoScrivere.length].release();
                possoLeggere.release();
                mutex.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }//run
    }//UtenteSem2
    public static void main(String[] args){
        LinkedList<String> coda=new LinkedList<>();
        UtenteSem2[] utenti=new UtenteSem2[10];
        VisualizzatoreSem2 v=new VisualizzatoreSem2(coda);
        v.start();
        for(int i=0;i<10;i++){
            utenti[i]=new UtenteSem2(coda,i);
            utenti[i].setName(""+i);
            utenti[i].start();
        }
    }//main
}//VisualizzatoreSem2
