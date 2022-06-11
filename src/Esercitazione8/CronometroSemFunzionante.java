package Esercitazione8;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CronometroSemFunzionante extends Thread{

    public static void main(String[] args){
        sc=new ScannerSem();
        sc.setName("Scanner");
        sc.start();
    }//main

    public static Semaphore semCron=new Semaphore(0);

    public static ScannerSem sc;

    private float numSecondi;

    public CronometroSemFunzionante(){
        numSecondi=0;
    }//costruttore

    public CronometroSemFunzionante(float numSecondi){
        this.numSecondi=numSecondi;
    }

    public void run(){
        try{
            semCron.acquire();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        while(!isInterrupted()){
            try{
                //Thread.sleep(1);oppure
                TimeUnit.MILLISECONDS.sleep(1);
            }catch (InterruptedException e){
                break;
            }
            //System.out.println("\n"+ numSecondi/1000);//da stampare quando il timer viene fermato, per uscire o per fermare
            //temporaneamente il timer
            numSecondi++;
            //if(sc.s.equals("a")|| sc.s.equals("f") ||sc.s.equals("r")|| sc.s.equals("")|| sc.s.equals("e"))
            //    semScan.release();
        }
    }//run

    public static class ScannerSem extends Thread{
        private Scanner sc;
        private String s;

        public ScannerSem(){
            sc=new Scanner(System.in);
            s="";
        }//costruttore normale

        public ScannerSem(String s) {
            sc=new Scanner(System.in);
            this.s=s;
        }//costruttore

        public void run(){
            float park;
            CronometroSemFunzionante c=new CronometroSemFunzionante();
            c.setDaemon(true);
            c.setName("Cronometro");
            c.start();
            while(true) {
                System.out.println("Scrivi\na per avviare\nf per fermare\ns per vedere quanti secondi sono passati\nr per reset\ne per uscire ");
                s = sc.nextLine();
                if (s.equals("a")) {
                    System.out.println("Sto avviando il timer...");
                    semCron.release();
                }//a
                if (s.equals("f")) {
                    if(c.isInterrupted()){
                        System.out.println("Prima di fermalo fai partire il cronometro");
                    }
                    else {
                    park = c.numSecondi;
                    s="x";
                    System.out.println("I secodi trascorsi fino ad ora sono: " + park / 1000);
                    while (s.length()!=0 || !s.equals("e")) {
                        System.out.println("premi invio per continuare o 'e' per chiudere");
                        s = sc.nextLine();
                        if(s.equals("e")) {
                            System.out.println("Uscita in corso");
                            System.exit(0);
                        }
                        if(s.length()==0)
                            break;
                    }
                    c = new CronometroSemFunzionante(park);
                    c.start();
                    semCron.release();
                    }
                }//f
                if(s.equals("r")){
                    park=c.numSecondi;
                    System.out.println("Sono passati "+park/1000+" secondi\nSto eseguaendo il reset...");
                    c=new CronometroSemFunzionante();
                    c.start();
                    semCron.release();
                }//r
                if(s.equals("e")){
                    park=c.numSecondi;
                    System.out.println("Uscita in corso");
                    try{
                        System.out.println(".");
                        Thread.sleep(500);
                        System.out.println("..");
                        Thread.sleep(500);
                        System.out.println("...");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("In totale il tempo trascorso è di "+park/1000+" secondi");
                    System.exit(0);
                }
                if(s.equals("s")){
                    park=c.numSecondi;
                    System.out.println("I secondi trascorsi fino ad ora sono: "+park/1000);
                }//s
                else if(!(s.equals("a")|| s.equals("e")|| s.equals("f") ||s.equals("r")|| s.equals("")| s.equals("s")))
                    System.out.println("Riprova comando non riconosciuto");
            }//while
        }//run
    }//ScannerSem
}//CronometroSem3
/*
Per fare questo esercizio alla fine serviva solo un semaforo per il cronometro che veniva coordinato dall'oggetto scanner
che restava sempre in esecuzione per gestire come il cronometro doveva partire.
 */