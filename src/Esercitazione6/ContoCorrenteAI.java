package Esercitazione6;
/*
Questa classe così costruita è thread save nel senso che siamo sicuri che così
costruendo i parametri nella nostra classe non si verificherà mai un problema in fatti anche
mettendo in difficoltà il codice con molti accessi non dovremmo mai avere problemi.
Ovviamente il problema del codice NTS è che le operazioni ad alto livello vengono tradotte a operazioni a basso livello,
e questo porta a un interfogliamento dell'accesso dei dati e di conseguenza un'inconsistenza degli stessi.
 */

import java.util.concurrent.atomic.AtomicInteger;

public class ContoCorrenteAI extends ContoCorrente{

    private AtomicInteger deposito;

    public ContoCorrenteAI(int depositoIniziale){
        super(depositoIniziale);
        deposito=new AtomicInteger(depositoIniziale);
    }//costruttore

    public void deposita(int importo){
        deposito.addAndGet(importo);//aggiunge atomicamente il parametro alla variabile
    }//deposita

    public void preleva(int importo){
        deposito.addAndGet(-importo);
    }//preleva
/*
TODO
andare a leggere le librerie degli oggetti atomici
 */
}//ContoCorrenteAI
