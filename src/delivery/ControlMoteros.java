package delivery;

import pcd.util.Ventana;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ControlMoteros {
    private int numeroMoteros;
    private Semaphore semaphore;
    private int moterosLibres;
    private Restaurante r;
    private List<Pedido> listaPedidos = new ArrayList<Pedido>();
    private List<Moteros> moteros = new ArrayList<Moteros>();
    private Boolean finPedidos = false;


    public ControlMoteros(Restaurante _r, int _numeroMoteros) {
        r = _r;
        numeroMoteros = _numeroMoteros;
        moterosLibres = Config.numeroMoteros;
        semaphore = new Semaphore(numeroMoteros);

        for (int i = 0; i < numeroMoteros; i++) {
            Moteros m = new Moteros(i, this);
            moteros.add(m);
            m.start();
        }

    }

    public synchronized void enviarPedido(Pedido p) {
        listaPedidos.add(p);
        notifyAll();
    }

    public synchronized Pedido getPedido(int id) {
        while (listaPedidos.size() == 0&&!finPedidos) try {
            System.out.println("Esperando a que se realice un pedido nuevo.");
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pedido p = listaPedidos.get(0);
        System.out.println("El motero " + id + " se encargará de repartir el pedido " + p.idPedido);
        listaPedidos.remove(0);
        return p;
    }

    public synchronized void asignarMotero() {
        while (moterosLibres == 0) try {
            System.out.println("No quedan moteros disponibles, por favor espere a que regresen de sus entregas.");
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        moterosLibres--;
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Se ha reservado un motero para su pedido.");
    }

    public synchronized void esperarMoteros(){
        while(semaphore.availablePermits()!=numeroMoteros){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void regresaMotero(int id) {
        moterosLibres++;
        System.out.println("El motero " + id + " ha regresado y esta disponible para repartir un pedido nuevo.");
        notifyAll();
        semaphore.release();
    }

}
