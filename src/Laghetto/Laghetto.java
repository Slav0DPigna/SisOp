package Laghetto;

public abstract class Laghetto {

    protected int minPesci, maxPesci, numPesciIniziali;

    public Laghetto(int minPesci,int maxPesci,int numPesciIniziali){
        this.minPesci=minPesci;
        this.maxPesci=maxPesci;
        this.numPesciIniziali=numPesciIniziali;
    }//costruttore


    abstract void iniziaPesca() throws InterruptedException;
    abstract void iniziaRipopolamento() throws InterruptedException;
    abstract void finisciPesca() throws InterruptedException;
    abstract void finisciRipopolamento() throws InterruptedException;

    public void inizia(int t) throws InterruptedException{
        if(t==0)
            iniziaPesca();
        else
            iniziaRipopolamento();
    }//inizia

    public void finisci(int t) throws InterruptedException{
        if(t==0)
            finisciPesca();
        else
            finisciRipopolamento();
    }//finisci
}//Laghetto
