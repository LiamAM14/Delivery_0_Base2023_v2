package delivery;

public class Moteros extends Thread{

    int id;
    ControlMoteros controlMoteros;
    public Moteros(int _id, ControlMoteros CM){
        id = _id;
        controlMoteros = CM;
    }
    public void run(){
        while(true){
            //controlMoteros.getPedido(id);
        }
    }

}
