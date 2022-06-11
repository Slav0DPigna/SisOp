package BarMod;

public class Cliente extends Thread{

    private BarMod bar;

    public Cliente(BarMod bar){
        this.bar=bar;
    }//costruttore

    public void run(){
        try{
            int cosaDaFare=bar.scengli();
            bar.inizia(cosaDaFare);
            bar.finisci(cosaDaFare*(-1));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run

}//Cliente
