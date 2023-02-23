package delivery;

import bank.*;
import pcd.util.ColoresConsola;
import pcd.util.Traza;

 public class Restaurante{
	private String nombre;					// nombre del restaurante
	private Account account;				// cuenta bancaria para registrar la recaudación
	private Cocina cocina; 					// la cocina de este restaurante
	private ControlMoteros controlMoteros;  // los moteros de este restaurante

	
	public Restaurante (Account _ac, String _nombre, int _numeroMoteros) {
		account = _ac;
		nombre = _nombre;
		controlMoteros = new ControlMoteros (this, _numeroMoteros);
		cocina = new Cocina (this);
		Traza.traza(ColoresConsola.GREEN_BOLD_BRIGHT, 1,"Creando restaurante: "+nombre);
	}


	 public String getNombre () {
		return nombre;
	}
	
	public double getBalance (){
		return account.getBalance();
	}
	
	public Account getAccount () {
		return account;
	}
	
	public void tramitarPedido (Pedido _p) {
		// Tramitar un pedido es:
		System.out.println("hola");
		account.deposit(_p.getPrecioPedido()); 	// añadir la cantidad abonada a la cuenta del banco
		controlMoteros.asignarMotero();
		cocina.cocinar(_p);						// mandar el pedido a cocina
		controlMoteros.enviarPedido(_p);		// una vez cocinado, mandarlo a los moteros para que uno lo coja
	}
}
