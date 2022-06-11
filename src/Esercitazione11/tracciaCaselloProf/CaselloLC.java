package Esercitazione11.tracciaCaselloProf;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CaselloLC extends Casello {

	private Lock lock = new ReentrantLock();
	private Condition[] portaC;

	private LinkedList<Thread>[] fila;

	@SuppressWarnings("unchecked")
	public CaselloLC(int numPorte, int tariffa) {
		super(numPorte, tariffa);
		portaC = new Condition[numPorte];
		fila = new LinkedList[numPorte];

		for (int i = 0; i < numPorte; i++) {
			portaC[i] = lock.newCondition();
			fila[i] = new LinkedList<Thread>();
		}
		System.out.println(this);
	}

	@Override
	public void accedi(int numPorta) throws InterruptedException {
		Thread t = Thread.currentThread();
		lock.lock();
		try {
			fila[numPorta].add(t);

			while (!mioTurno(t, numPorta)) {
				portaC[numPorta].await();
			}
			System.out.format("Veicolo %d � entrato nella porta %d%n", t.getId(), numPorta);
			System.out.println(this);
		} finally {
			lock.unlock();
		}
	}

	private boolean mioTurno(Thread t, int numPorta) {
		return fila[numPorta].getFirst() == t;
	}

	@Override
	public void pagaEAbbandona(int numPorta, int km) throws InterruptedException {
		Thread t = Thread.currentThread();
		lock.lock();
		try {
			incasso += km * tariffa;

			fila[numPorta].remove(t);
			if (!fila[numPorta].isEmpty()) {
				portaC[numPorta].signalAll();
			}
			System.out.format("Veicolo %d ha abbandonato la porta %d pagando la tariffa di %d%n", t.getId(), numPorta, km * tariffa);
			System.out.println(this);

		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public String toString() {
		String ret = "Casello: ";
		for (int i = 0; i < fila.length; i++) {
			ret+=i+":[";
			for (Thread t:fila[i]) {
				ret+=t.getId()+" ";
			}
			ret = ret.trim();
			ret+="] ";
		}
		
		ret+="incasso: "+incasso;
		
		return ret;
	}

	public static void main(String[] args) {
		int N = 5;
		int T = 10;
		int V = 10;

		Casello casello = new CaselloLC(N, T);
		casello.test(V);
	}

}
