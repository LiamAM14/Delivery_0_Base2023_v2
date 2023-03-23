package delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pcd.util.Traza;

public class MyDelivery {

    public MyDelivery() {
        // para facilitar las trazas
        Traza.setNivel(Config.modoTraza);
        int nucleos = Runtime.getRuntime().availableProcessors();
        Executor executor = Executors.newFixedThreadPool(nucleos);


        // Creando los restaurantes
        CadenaRestaurantes cadenaRestaurantes = null;
        cadenaRestaurantes = new CadenaRestaurantes(Config.numeroRestaurantes);
        cadenaRestaurantes.crearRestaurantes();
        List<Thread> botsPan = new ArrayList<>();
        List<Thread> botsPollo = new ArrayList<>();
        int indx = 0;
        for (Restaurante r : cadenaRestaurantes.getRestaurantes()){
            BufferPan bufferPan = new BufferPan();
            BufferPollo bufferPollo = new BufferPollo();

            Thread bp = new Thread(new BotPan(indx, bufferPan));
            botsPan.add(bp);
            bp.start();

            Thread bo = new Thread(new BotPollo(indx, bufferPollo));
            botsPollo.add(bo);
            bo.start();

            r.setCocina(new Cocina(r, bufferPan, bufferPollo));
            indx++;
        }

        // CARGAR PEDIDOS DE FICHERO
        // Obtenemos una lista de pedidos
        List<Pedido> lp;
        lp = new LinkedList<>();
        lp = Pedido.pedidosDesdeFichero("src/pedidos5.bin"); // Pon aquí la ruta y nombre de tu fichero

        // También puedes crear tus propios pedidos usando el método generaPedidos de la clase Pedido.
        // En la clase Pedido también tienes un método para volcar esos pedidos a un fichero.

        // LANZAR PEDIDOS
        // Los estamos lanzando secuencialmente
        long initialTime = new Date().getTime();
        LinkedList<Restaurante> listaRestaurantes = cadenaRestaurantes.getRestaurantes();
        List<Thread> threads = new ArrayList<Thread>();
        for (Pedido p : lp) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    PedidoThread thread = new PedidoThread(p, listaRestaurantes);
                    thread.run();
                }
            };
            executor.execute(runnable);
        }

        try {
            for (int i = 0; i < lp.size(); i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // AUDITORÍAS
        for (Restaurante r : listaRestaurantes)
            System.out.print("\nAuditoría Restaurante " + r.getNombre() + " " + r.getBalance());

        System.out.println("\nAuditoria Cadena: " + cadenaRestaurantes.getBank().audit(0, Config.numeroRestaurantes));

        System.out.println("Tiempo total invertido en la tramitación: " + (new Date().getTime() - initialTime));

        List<Pedido> pedidos7euros = lp.stream().filter(a -> a.getPrecioPedido()<7).toList();
        for (Pedido p : pedidos7euros){
            System.out.println(p.getId()+" -> "+p.getPrecioPedido());
        }
    }

    public static void main(String[] args) {
        new MyDelivery();
    }
}
