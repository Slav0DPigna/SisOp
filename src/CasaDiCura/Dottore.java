package CasaDiCura;

public class Dottore extends Thread{

    private CasaDiCura casaDiCura;

    public Dottore(CasaDiCura casaDiCura){
        this.casaDiCura=casaDiCura;
    }//costruttore

    public void run(){
        try{
            while (true){
            casaDiCura.chiamaEIniziaOperazione();
            casaDiCura.fineOperazione();}
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run

}//Dottore
