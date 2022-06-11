package CasaDiCura;

import java.util.LinkedList;

public abstract class CasaDiCura {

    protected LinkedList<Thread> codaClientiSalaDAttesa;
    protected int incasso;
    protected int numPazienti=0;

    public CasaDiCura(){
        codaClientiSalaDAttesa=new LinkedList<>();
        incasso=0;
    }//costruttore

    public abstract void chiamaEIniziaOperazione() throws InterruptedException;
    public abstract void fineOperazione() throws InterruptedException;
    public abstract void pazienteEntra() throws InterruptedException;
    public abstract void pazienteEsci() throws InterruptedException;

    public void test(int numPazienti){
        Dottore d=new Dottore(this);
        d.setDaemon(true);
        d.start();
        this.numPazienti=numPazienti;
        for(int i=0;i<numPazienti;i++){
            Paziente p=new Paziente(this);
            p.setName("Paziente "+i);
            p.start();
        }
    }//test

}//CasaDiCura
