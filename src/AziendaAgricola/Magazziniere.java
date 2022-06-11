package AziendaAgricola;

public class Magazziniere extends Thread{

    private AziendaAgricola aziendaAgricola;

    public Magazziniere(AziendaAgricola aziendaAgricola){
        this.aziendaAgricola=aziendaAgricola;
    }//costruttore

    public void run(){
        try{
            while(true){
                aziendaAgricola.mettiAPosto(aziendaAgricola.sacchiDaDare);}
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Magazziniere
