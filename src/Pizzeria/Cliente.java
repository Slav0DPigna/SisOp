package Pizzeria;

public class Cliente extends Thread{

    private Pizzeria p;

    public Cliente(Pizzeria p){
        this.p=p;
    }//costruttore

    public void run(){
        try{
            p.mangiaPizza();
            p.pizzaMangiata();
        }catch (InterruptedException e){
           e.printStackTrace();
        }
    }//run
}//Cliente
