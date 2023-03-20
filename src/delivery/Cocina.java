package delivery;

import pcd.util.ColoresConsola;
import pcd.util.Traza;

import java.util.List;

public class Cocina implements Runnable {
    Restaurante r;
    List<Producto> pedidoCocina;
    BufferPan bufferPan;
    BufferPollo bufferPollo;
    int pan;
    int pollo;

    public Cocina(Restaurante _r, BufferPan bPan, BufferPollo bPollo) {
        bufferPan = bPan;
        bufferPollo = bPollo;
        r = _r;
    }

    public void cocinar(Pedido p) {
        pedidoCocina = p.getProductos();
        Traza.traza(ColoresConsola.GREEN, 2, "Cocinando el pedido: " + p.printConRetorno());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            if (pedidoCocina != null) {
                for (Producto producto : pedidoCocina) {
                    if (producto.getId().equals("" + 0)) {
                        try {
                            pan = bufferPan.extraer();
                            pollo = bufferPollo.extraer();
                            System.out.println("Hamburguesa cocinada con pan:" + pan + " y pollo:" + pollo);
                        } catch (Exception e) {
                        }
                    }
                }
                break;
            }
        }
    }

}