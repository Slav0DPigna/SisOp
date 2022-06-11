package Esercitazione12;

import java.util.LinkedList;

public abstract class Visualizzatore extends Thread {

    protected LinkedList<String> coda;

    public Visualizzatore(LinkedList<String> coda){
        this.coda=coda;
    }//costruttore

}//Visualizzatore
