package FerryBoat;

public class Addetto extends Thread{

    private FerryBoat f;

    public Addetto(FerryBoat f){
        this.f=f;
    }//costruttore

    public void run(){
        try{
            while (true){
                f.imbarca();
                f.terminaTraghettata();}
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Addetto
