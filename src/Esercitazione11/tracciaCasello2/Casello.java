package Esercitazione11.tracciaCasello2;

public abstract class Casello {

    protected int N;      //Numero di porte
    protected int T;      //Tariffa
    protected int incasso;

    public Casello(int N,int T){
        incasso=0;
        this.N=N;
        this.T=T;
    }//costruttore

    public int getNumeroPorte(){
        return N;
    }//getNumeroPorte

    public abstract void entra(int porta) throws InterruptedException;

    public abstract void pagaEEsci(int porta,int km) throws InterruptedException;

}//Casello
