package delivery;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferPan {
    private int cuantos = 0;
    private int N = 3;
    private int recurso[] = new int[N];
    private int frente = 0;
    private int cola = 0;

    final Lock lock = new ReentrantLock(true);
    final Condition lleno = lock.newCondition();
    final Condition vacio = lock.newCondition();

    public BufferPan(){}

    public void insertar(int pan) throws InterruptedException{
        lock.lock();

        try {
            while (cuantos == N)
                lleno.await();
            recurso[frente] = pan;
            frente = (frente+1)%N;
            cuantos++;
            vacio.signal();
        }finally {
            lock.unlock();
        }
    }

    public int extraer() throws InterruptedException{
        lock.lock();
        try{
            while (cuantos == 0)
                vacio.await();
            int result = recurso[cola];
            cola = (cola+1)%N;
            cuantos--;
            lleno.signal();
            return result;
        }finally {
            lock.unlock();
        }
    }
}
