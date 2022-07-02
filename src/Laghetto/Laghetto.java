package Laghetto;

public abstract class Laghetto {

    protected int minPesci, maxPesci, numPesciIniziali;

    public Laghetto(int minPesci,int maxPesci,int numPesciIniziali){
        this.minPesci=minPesci;
        this.maxPesci=maxPesci;
        this.numPesciIniziali=numPesciIniziali;
    }//costruttore

    public Laghetto() {

    }


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

    public void test(int numPescatori,int numAddetti){
        for(int i=0;i<numPescatori;i++)
            new Pescatore(this).start();
        for (int i=0;i<numAddetti;i++)
            new Addetto(this).start();
    }//test
}//Laghetto
