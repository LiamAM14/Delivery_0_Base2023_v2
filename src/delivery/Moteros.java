package delivery;

public class Moteros extends Thread{

    int id;
    ControlMoteros controlMoteros;
    public Moteros(int _id, ControlMoteros CM){
        id = _id;
        controlMoteros = CM;
    }
    public void run(){
        controlMoteros.esperarMoteros();
        while(true){
            Pedido p = controlMoteros.getPedido(id);
            System.out.println("Repartiendo pedido "+p.idPedido+"...");
            controlMoteros.regresaMotero(id);
            try{
                Thread.currentThread().sleep(100);
            }catch (Exception e){}
        }
    }


}
