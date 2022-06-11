package CasaDiCura;

public class Paziente extends Thread{

    private CasaDiCura casaDiCura;

    public Paziente(CasaDiCura casaDiCura){
        this.casaDiCura=casaDiCura;
    }//costruttore

    public void run(){
        try{
            casaDiCura.pazienteEntra();
            casaDiCura.pazienteEsci();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//

}//Paziente
