package Dischi;

public class Disco extends Thread{

    private Controllore c;
    private int processiAttivi;

    public Disco(Controllore c){
        this.c=c;
        processiAttivi=0;
    }//costruttore

    public int getProcessiAttivi(){
        return processiAttivi;
    }//getProcessiAttvi

    public void allocaProcesso(){
        processiAttivi++;
    }//allocaProcesso

    public void deallocaProcesso(){
        processiAttivi--;
    }//deallocaProcesso

}//Disco
