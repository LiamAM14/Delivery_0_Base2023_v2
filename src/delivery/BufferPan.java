package delivery;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferPan {
    int cuantos = 0;
    int N = 3;
    int recurso[] = new int[N];
    int frente = 0;
    int cola = 0;

    final Lock lock = new ReentrantLock(true);
    final Condition lleno = lock.newCondition();
    final Condition vacio = lock.newCondition();

    public BufferPan(){}

    public void insertar(int pan) throws InterruptedException{
        lock.lock();

        try {
            while (cuantos == N)
                lleno.await();
            System.out.println("Cogiendo pan...");
            recurso[frente] = pan;
            frente = (frente+1)%N;
            cuantos++;
            System.out.println("OKpan");
            vacio.signal();
        }finally {
            lock.unlock();
        }
    }

    public int extraer() throws InterruptedException{
        lock.lock();
        try{
            while (cuantos == N)
                vacio.await();
            System.out.println("Sacando Pan...");
            int result = recurso[cola];
            cola = (cola+1)%N;
            cuantos--;
            System.out.println("Sacando pan: "+result);
            lleno.signal();
            return result;
        }finally {
            lock.unlock();
        }
    }
}
