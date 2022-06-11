package Esercitazione4;

public class CronometroDemone {
    public static void main(String[] args){
        Cronometro cronometro=new Cronometro();
        cronometro.setDaemon(true);
        cronometro.start();

        System.out.println("Il main termina");
        System.out.println(Thread.currentThread().getName()+" é un thead demone: "+ Thread.currentThread().isDaemon());
    }//main
}//CronometroDemone

/*
Notiamo che essendo il cronometro in questo caso un demone
termina subito essendo l'unico thread attivo oltre al main.
Vediamo che il main non é un demone con una print.
Da notare che ogni tanto il cronometro parte ma viene soppresso subito.
 */
