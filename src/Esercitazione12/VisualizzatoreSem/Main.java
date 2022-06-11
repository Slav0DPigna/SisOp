package Esercitazione12.VisualizzatoreSem;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
        Semaphore possoLeggere=new Semaphore(0);
        Semaphore possoScrivere=new Semaphore(1);
        Semaphore mutex=new Semaphore(1);
        LinkedList<String> coda=new LinkedList<>();
        UtenteSem[] utenti=new UtenteSem[10];
        VisualizzatoreSem visualizzatore=new VisualizzatoreSem(coda,possoScrivere,possoLeggere,mutex);
        for(int i=0;i<10;i++){
            utenti[i]=new UtenteSem(coda,possoScrivere,possoLeggere,mutex);
            utenti[i].setName("Utente "+i);
            utenti[i].start();
        }
        visualizzatore.start();
    }//main
}//Main
