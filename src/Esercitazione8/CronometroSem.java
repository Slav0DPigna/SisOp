package Esercitazione8;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CronometroSem extends Thread{

    private float numSecondi;
    private static Semaphore semC=new Semaphore(0), semM=new Semaphore(1), semS=new Semaphore(0);
    private String s;
    public static ScannerSincrono sc=new ScannerSincrono();
    public CronometroSem(){
        numSecondi=0;
        s="";
    }//costruttore

    public CronometroSem(float numSecondi){
        this.numSecondi=numSecondi;
        s="";
    }

    public void run(){
        try {
            semC.acquire();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        while (true) {
            try {
                //Thread.sleep(1);oppure
                TimeUnit.MILLISECONDS.sleep(1);
                System.out.println("\n" + numSecondi / 1000);
                numSecondi++;
                if (sc.s.equals("a") || sc.s.equals("e") || sc.s.equals("f") || sc.s.equals("r"))
                    semM.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }//run

    public static void main(String[] args) throws InterruptedException{
        semM.acquire();
        sc.setDaemon(true);
        sc.start();
        semS.release();
        CronometroSem c=new CronometroSem();
        System.out.println("Inserisci: \n-a per avviare\n-f per fermare\n-r reset\n-e exit");
        c.s=sc.s;
        System.out.println("!"+c.s+"!");
        while (true) {
            if(c.s.equals("a")) {
                semM.acquire();
                if(!c.isAlive()) {
                    c.start();
                    c.s="";
                    semC.release();
                }else {
                    c.numSecondi = 0;
                    semC.release();
                }
            }
            if(c.s.equals("f")) {
                semM.acquire();
                float numSecondi=c.numSecondi;
                System.out.println("Scrivi vai per ripartire");
                c.s="";
                while(!c.s.equals("vai"))
                    c.s=sc.s;
                c=new CronometroSem(numSecondi);
                c.start();
                semC.release();
            }
            if(c.s.equals("r")) {
                c.s="";
                c.numSecondi = 0;
                semC.release();
            }
            if(c.s.equals("e")) {
                c.s="";
                c.interrupt();
                System.exit(0);
            }
            semS.release();
        }//while
    }//main

    public static class ScannerSincrono extends Thread{

        private Scanner sc;
        private String s;

        public ScannerSincrono(){
            sc=new Scanner(System.in);
            s="";
        }//costruttore

        public void run() {
            try {
            semS.acquire();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("scrivi");
            s = sc.nextLine();
            semM.release();
        }//run

    }//ScannerSincrono
}//CronometroSem
/*
sto codice è una merda procedo con un'altra implementazione
 */