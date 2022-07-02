package Laghetto;

public class Addetto extends Thread{

    private Laghetto laghetto;

    public Addetto(Laghetto laghetto){
        this.laghetto=laghetto;
    }//costruttore

    public void run(){
        try{
            while (true) {
                laghetto.inizia(1);
                laghetto.finisci(1);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Addetto
