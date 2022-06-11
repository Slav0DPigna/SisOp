package Esercitazione8;

import java.util.concurrent.Semaphore;

public class Esercizio41 {

    static Semaphore semA=new Semaphore(1);
    static Semaphore semB=new Semaphore(0);

    public static void main(String[] args){
        while(true){
            new B().start();
            new A().start();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }//while
    }//main

    public static class A extends Thread{
        public A(){}//costruttore

        public void run(){
            try {
                semA.acquire();
                for (int i = 1; i <= 3; i++) {
                    System.out.print("A" + i);
                    if(i<=2)
                        System.out.println();
                }
                //this.join();
                semB.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }//A

    public static class B extends Thread{
        public B(){}//costruttore

        public void run(){
            try {
                semB.acquire();
                for (int i = 1; i <= 3; i++) {
                    System.out.print("B" + i);
                    if(i<=2)
                        System.out.println();
                }
                //this.join();
                semA.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }//B
}
