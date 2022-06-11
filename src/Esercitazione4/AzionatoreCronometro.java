package Esercitazione4;

import java.util.Scanner;

public class AzionatoreCronometro {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        Cronometro cronometro = new Cronometro();

        System.out.println("Premi invio per iniziare");
        in.nextLine();
        cronometro.start();
        System.out.println("Premi invio pre terminare");
        in.nextLine();
        cronometro.interrupt();
        System.out.println("Programma terminato");
    }//main
}//AzionatoreCronometro

/*
THREAD DEMONI
I thread demoni sono thread che hanno lo scopo di servire altri thread,
un thread si trasforma in un thread demone con il metodo setDemon(true).
Un demone non deve mai accedere a una risorsa persistente in quanto puó essere
terminato in qualsiasi momento da un thread.
I thread appena creati non sono demoni.
(esempio del cronometro demone)
 */
