package FerryBoat;

public class Auto extends Thread{

    private FerryBoat f;

    public Auto(FerryBoat f){
        this.f=f;
    }//costruttore

    public void run(){
        try{
            f.sali();
            f.parcheggiatiEScendi();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Auto
