package Esercitazione4;

import java.util.concurrent.TimeUnit;

public class Cronometro extends Thread{

    public void run(){
        float numSecondi = 0;
        while(!isInterrupted()){
            try{
                //Thread.sleep(1);oppure
                TimeUnit.MILLISECONDS.sleep(1);
            }catch (InterruptedException e){
                break;
            }
            System.out.println("\n"+ numSecondi/1000);
            numSecondi++;
        }
    }//run
}//Cronometro

/*
* L'esempio del cronometro serve per far vedere come funziona l'interruzione
* di un thread in java infatti sappiamo che un thread non ne puó interrompere
* un altro bruscamente in java ma ne gli deve chiedere di interrompersi.
* A per capire se un thread é stato interrotto esiste il metodo isInterrupted che é
* diverso da Interruped, si ricorda che se la classe estende l'interfaccia
* Runnable non si puó richiamare direttamente il metodo sull'ogetto ma si deve richiamare
* la classe Thread
*/
