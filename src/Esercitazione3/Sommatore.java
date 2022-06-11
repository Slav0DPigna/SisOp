package Esercitazione3;

public class Sommatore extends Thread{

    private int da,a,somma;

    public Sommatore(int da,int a){
        if(da>=a)
            throw new IllegalArgumentException();
        this.da=da;
        this.a=a;
    }//costruttore

    public int getSomma() throws InterruptedException{
        this.join();//questa join serve per far finire il lavoro dell'oggetto prima di stampare
        //questa join é essenziale o la mettiamo qua o la mettiamo nel main ma da qualche parte, prima di utilizzare il risultato,
        //la dobbiamo inserire.
        //ricordiamo che join fa finire il lavoro del thread.
        //se invochiamo la join su un thread giá terminato ritorna subito il controllo al main.
        return somma;
    }//getSomma

    public void run(){
        somma=0;
        for(int i=da;i<=a;i++)
            somma+=i;
    }

}//Sommatore
