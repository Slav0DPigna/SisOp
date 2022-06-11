package AziendaAgricola;

import Esercitazione8.Esercizio41;

public abstract class AziendaAgricola {

    protected int incasso;
    protected int sacchiInInventario;
    protected int sacchiDaDare;

    public AziendaAgricola(){
        sacchiDaDare=0;
        incasso=0;
        sacchiInInventario=30;
    }//costruttore

    //metodi del cliente
    public abstract void paga(int sacchiDaPagare) throws InterruptedException;
    public abstract void portaVia(int sacchiDaPortareVia) throws InterruptedException;
    //metodi del magazziniere
    public abstract void mettiAPosto(int sacchiDaDare) throws InterruptedException;
//---------------------------
    public void test(int numClienti){
        Magazziniere magazziniere=new Magazziniere(this);
        magazziniere.setDaemon(true);
        magazziniere.start();
        Cliente[] clienti=new Cliente[numClienti];
        for(int i=0;i<numClienti;i++)
            clienti[i]=new Cliente(this);
        for(int i=0;i<clienti.length;i++)
            clienti[i].start();
    }//test
}//AziendaAgricola
