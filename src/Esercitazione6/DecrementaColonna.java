package Esercitazione6;

import java.util.concurrent.atomic.AtomicInteger;

public class DecrementaColonna extends Thread{

    int indiceColonna;
    AtomicInteger[][] matrice;

    public DecrementaColonna(AtomicInteger[][] matrice,int indiceColonna) {
        this.indiceColonna = indiceColonna;
        this.matrice = matrice;
    }//costruttore

    public void run(){
        for(int i=0;i<matrice.length;i++)
            this.matrice[i][indiceColonna].addAndGet(-1);
    }//run

}//DecrementaColonna
