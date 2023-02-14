package delivery;

import java.util.LinkedList;

public class PedidoThread extends Thread {
    Pedido pedido;
    Restaurante restaurante;
    public PedidoThread (Pedido _p, LinkedList<Restaurante> restaurantes) {
        pedido = _p;
        restaurante = restaurantes.get(pedido.getRestaurante());
    }
    public void run () {
        restaurante.tramitarPedido(pedido);
    }
}