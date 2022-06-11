package Esercitazione6;

public abstract class ContoCorrente {

    protected int deposito;//nella classe astratta si mettono le variabili che
    //si vogliono proteggere

    public ContoCorrente(int depositoIniziale){
        this.deposito=depositoIniziale;
    }//costruttore

    public abstract void deposita(int importo);

    public abstract void preleva(int importo);

    public int getDeposito(){
        return deposito;
    }//getDeposito
}//ContoCorrente
