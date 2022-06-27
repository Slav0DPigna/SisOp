package Pizzeria;

public class Pizzaiolo extends Thread{

    private Pizzeria p;

    public Pizzaiolo(Pizzeria p){
        this.p=p;
    }//costruttore

    public void run(){
        try{
            while (true){
                p.preparaPizza();
                p.pizzaPronta();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run
}//Pizzaiolo
