package Esercitazione12;

import java.util.LinkedList;
import java.util.Random;

public abstract class Utente extends Thread{

    protected LinkedList<String> coda;

    public Utente(LinkedList<String> coda){
        this.coda=coda;
    }//costruttore

}//Utente
