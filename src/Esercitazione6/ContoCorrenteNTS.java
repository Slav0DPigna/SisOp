package Esercitazione6;

public class ContoCorrenteNTS extends ContoCorrente{//Non Thread Safe

    public ContoCorrenteNTS(int importoIniziale){
        super(importoIniziale);
    }//costruttore

    @Override
    public void deposita(int importo) {
        super.deposito=super.deposito+importo;
    }

    @Override
    public void preleva(int importo) {
        super.deposito=super.deposito-importo;
    }
}
