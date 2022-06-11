package AziendaAgricola;

import java.util.Random;

public class Cliente extends Thread{

    private AziendaAgricola aziendaAgricola;
    private int sacchiDaComprare;

    public Cliente(AziendaAgricola aziendaAgricola){
        this.aziendaAgricola=aziendaAgricola;
        sacchiDaComprare=new Random().nextInt(1,11);
    }//costruttore

    public void run(){
        try{
            aziendaAgricola.paga(sacchiDaComprare);
            aziendaAgricola.portaVia(sacchiDaComprare);
        }catch (InterruptedException e){

        }
    }//run
}//Cliente
