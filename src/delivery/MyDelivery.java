package delivery;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pcd.util.Traza;

public class MyDelivery {
	
	public MyDelivery () {
		// para facilitar las trazas
		Traza.setNivel(Config.modoTraza);
		
		// Creando los restaurantes
		CadenaRestaurantes cadenaRestaurantes = null;
		cadenaRestaurantes = new CadenaRestaurantes (Config.numeroRestaurantes);
		cadenaRestaurantes.crearRestaurantes();

		// CARGAR PEDIDOS DE FICHERO
		// Obtenemos una lista de pedidos
		List<Pedido> lp;
		lp = new LinkedList<>();
		lp = Pedido.pedidosDesdeFichero ("C:/Users/mauch/Desktop/UNIVERSIDAD/2�/PCD/Delivery_0_Base2023_v2/src/pedidos5.bin"); // Pon aqu� la ruta y nombre de tu fichero
		
		// Tambi�n puedes crear tus propios pedidos usando el m�todo generaPedidos de la clase Pedido. 
		// En la clase Pedido tambi�n tienes un m�todo para volcar esos pedidos a un fichero.
		
		// LANZAR PEDIDOS
		// Los estamos lanzando secuencialmente
		long initialTime = new Date().getTime();
		LinkedList<Restaurante> listaRestaurantes = cadenaRestaurantes.getRestaurantes();
		List<Thread> threads = new ArrayList<Thread>();
		for (Pedido p:lp) {
			PedidoThread pedido = new PedidoThread(p, listaRestaurantes);
			threads.add(pedido);
			pedido.start();
		}
		
		// AUDITOR�AS
		for (Restaurante r:listaRestaurantes) 
			System.out.print ("\nAuditor�a Restaurante "+r.getNombre()+" "+r.getBalance());
		
		System.out.println ("\nAuditoria Cadena: "+ cadenaRestaurantes.getBank().audit(0, Config.numeroRestaurantes));
		
		System.out.println ("Tiempo total invertido en la tramitaci�n: "+(new Date().getTime() - initialTime));
	}

	public static void main(String[] args) {
		new MyDelivery();
	}
}
