package Esercitazione3;

import java.util.Scanner;
public class AttendiInput {
    public static void main(String[] args){
        System.out.println("Prima istruzione del main");
        System.out.println("Attendi fino a quando l'utente non inserisce una stringa");
        Scanner sc=new Scanner(System.in);
        String stringa=sc.nextLine();
        System.out.println("L'utente ha scritto "+stringa);
        System.out.println("Il main termina.");
    }//main
}//AttendiInput
