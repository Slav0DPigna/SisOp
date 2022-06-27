package Pizzeria;

import java.util.ArrayList;

public abstract class Pizzeria {

    protected ArrayList<Thread> clientiSeduti;

    public Pizzeria(){
        clientiSeduti=new ArrayList<>();
    }//costruttore

    abstract void mangiaPizza() throws InterruptedException;//sospende il cliente fin quando non inizia a mangiare la pizza.
    abstract void pizzaMangiata() throws InterruptedException;//segnala che il cliente ha finito di mangiare.
    abstract void preparaPizza() throws InterruptedException;
    /*Sospende il pizzaiolo fino a quando non inizia a preparare la pizza.
    Dopo che tutti i posti del tavolo sono stati occupati il pizzaiolo stampa a video che sta iniziando a preparare
    la maxi-pizza.
    */
    abstract void pizzaPronta() throws InterruptedException;//segnala che la pizza è pronta e che i clienti possono iniziare a mangiare la pizza.

    public void test(int numeroClienti){
        System.out.println("La pizzeria è aperta");
        for(int i=0;i<numeroClienti;i++)
            new Cliente(this).start();
        Pizzaiolo p=new Pizzaiolo(this);
        p.setDaemon(true);
        p.start();
    }//test
}//Pizzeria.Pizzeria
