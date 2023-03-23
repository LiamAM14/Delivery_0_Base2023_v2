package delivery;

import pcd.util.ColoresConsola;
import pcd.util.Traza;

import java.util.List;

public class Cocina {
    private Restaurante r;
    private List<Producto> pedidoCocina;
    private BufferPan bufferPan;
    private BufferPollo bufferPollo;
    private int pan;
    private int pollo;

    public Cocina(Restaurante _r, BufferPan bPan, BufferPollo bPollo) {
        bufferPan = bPan;
        bufferPollo = bPollo;
        r = _r;
    }

    public void cocinar(Pedido p) {
        pedidoCocina = p.getProductos();
        Traza.traza(ColoresConsola.GREEN, 2, "Cocinando el pedido: " + p.printConRetorno());
        for (Producto producto : pedidoCocina) {
            if(producto.getId().equals(""+0)) {
                try {
                    pan = bufferPan.extraer();
                    pollo = bufferPollo.extraer();
                    System.out.println("Hamburguesa cocinada con pan:" + pan + " y pollo:" + pollo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}