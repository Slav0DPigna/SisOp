package Esercitazione8;

import java.util.concurrent.Semaphore;

import static Esercitazione8.Esercizio44.AB.mutexA;
import static Esercitazione8.Esercizio44.AB.mutexB;

public class Esercizio44 {

    public static void main(String[] args){
        while (true){
            new B().start();
            new A().start();
            try{
                Thread.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }//for
    }//main

    public static class AB{
        public static Semaphore mutexA=new Semaphore(2) ,mutexB=new Semaphore(0);
    }
    public static class A extends Thread{

        public A(){

        }

        public void run(){
            try{
                mutexA.acquire(2);
                System.out.print("A");
                System.out.print("A");
                mutexB.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }//run
    }//A
    public static class B extends Thread{

        public B(){

        }

        public void run(){
            try {
                mutexB.acquire();
                System.out.print("B\n");
                mutexA.release(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }//run
    }//B
}//Esercizi44
/*
da notare come si usano i semafori per sincronizzare più thread può sembrare un po controintuitivo
ma alla fine dei conti anche la macchine quando aspettano a un incrocio fanno così, ovvero sono in attesa
(delle macchine che stanno passando ovvero hanno fatto un acquire )fino a quando tutte le altre macchine non
 sono passate(hanno fatto una releas).
 Questo poi si estende con l'esercizio 4.6 che dice di stampare due A e una B e io al semaforo si a ho dato 2
 e al semaforo di b sempre 0 e facendo apportunamente le acquire e release
 */