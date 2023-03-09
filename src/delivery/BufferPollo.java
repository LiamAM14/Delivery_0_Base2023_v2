package delivery;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferPollo {
    int cuantos = 0;
    int N = 1;
    int recurso [] = new int[N];

    final Lock lock = new ReentrantLock(true);
    final Condition lleno = lock.newCondition();
    final Condition vacio = lock.newCondition();

    public BufferPollo(){}

    public void insertar(int pollo) throws InterruptedException{
        lock.lock();
        try {
            while (cuantos == N)
                lleno.await();

            System.out.println("Cogiendo pollo...");
            recurso[0] = pollo; //Se inserta en el 0 porque solo hay espacio para un elemento en este caso
            cuantos++;
            System.out.println("OKpollo");
            vacio.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public int extraer() throws InterruptedException{
        lock.lock();
        try{
            while (cuantos == 0)
                vacio.await();

            System.out.println("Sacando Pollo...");
            int result = recurso[0]; //recurso[0] porque solo hay espacio para una pieza
            cuantos--;
            System.out.println("Extraida pieza de pollo: "+result);
            lleno.signal();
            return result;
        }
        finally {
            lock.unlock();
        }
    }
}
