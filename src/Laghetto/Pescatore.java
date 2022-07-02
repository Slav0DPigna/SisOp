package Laghetto;

public class Pescatore extends Thread{

    private Laghetto laghetto;

    public Pescatore(Laghetto laghetto){
        this.laghetto=laghetto;
    }//costruttore

    public void run(){
        try{
            while(true){
                laghetto.inizia(0);
                laghetto.finisci(0);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Pescatore
