package Esercitazione8;

import java.util.concurrent.Semaphore;

public class Esercizio43 {
    public static void main(String[] args){
        Semaphore mutex=new Semaphore(1);
        int[][] m={ {0,0,0,0},
                    {0,0,0,0},
                    {0,0,0,0},
                    {0,0,0,0},
                    {0,0,0,0},
                    {0,0,0,0}};

    }//main
}//Esercizio4.3

/*
TODO
Finire questo esercizio e notare che avere un semaforo solo non ha senso perché
il programma si trasforma in un programma sequenziale cercare un modo efficiente
per far funzionare la baracca
 */
