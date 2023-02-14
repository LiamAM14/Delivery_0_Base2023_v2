package delivery;

import pcd.util.Ventana;

public class ControlMoteros {
	int numeroMoteros;
	Restaurante r;
	Ventana v;
	static int posicionVentana = 10;
	
	public ControlMoteros (Restaurante _r, int _numeroMoteros) {
		r=_r;
		numeroMoteros = _numeroMoteros;
		
		// Creamos una ventana para los mensajes de este objeto.
		v =  new Ventana ("Motero de Rest."+r.getNombre(), posicionVentana,10);
		posicionVentana+=250;
	}

	public void enviarPedido (Pedido p) {
		v.addText("REPARTIENDO PEDIDO : "+p.getId());
		try {
			Thread.sleep(500); // Simulamos que tarda 0,5 segundos en repartir
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
