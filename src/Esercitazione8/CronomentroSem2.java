package Esercitazione8;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CronomentroSem2 extends Thread{
    private static Semaphore
            semMain= new Semaphore(1),
            semScanner=new Semaphore(0),
            semCron=new Semaphore(0);

    private float numSecondi;
    public static ScannerSem sc=new ScannerSem();

    public CronomentroSem2(){
        this.numSecondi=0;
        sc=new ScannerSem();
    }//costruttore normale

    public CronomentroSem2(float numSecondi){
        sc=new ScannerSem();
        this.numSecondi=numSecondi;
    }//costruttore

    public void run(){
        try{
            semScanner.acquire();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        while(!isInterrupted()){
            try{
                TimeUnit.MILLISECONDS.sleep(1);
            }catch (InterruptedException e){
                break;
            }
            System.out.println("\n"+ numSecondi/1000);
            numSecondi++;
            if(sc.s.equals("a")|| sc.s.equals("e")|| sc.s.equals("f")|| sc.s.equals("r")){
                semMain.release();
            }
        }
    }//run

    public static void main(String[] args) throws InterruptedException{
        semMain.acquire();
        sc.start();
        System.out.println("Main partito");
        CronomentroSem2 c=new CronomentroSem2();
        c.start();
        semScanner.release();
    }//main

    public static class ScannerSem extends Thread{
        private Scanner sc;
        private String s;

        public ScannerSem(){
            sc=new Scanner(System.in);
        }//costruttore

        public void run(){
            try{
            semScanner.acquire();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Scrivi\n a per avviare\n f per fermare\n r per il reset\n e per uscire");
            s=sc.nextLine();
            if(s.equals("a"))
                semCron.release();
        }//run
    }//ScannerSem
}//CronometroSem2
/*
anche questo è stato un fallimento vado avanti con le esercitazioni con un po di esperienza in più ma senza aver capito nulla
fino in fondo.
riprovo l'ultima volta a scrivere questo esercizio che in teoria dovrei riuscirci.
 */