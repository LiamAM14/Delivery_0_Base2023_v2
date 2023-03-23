package delivery;

public class BotPollo implements Runnable{
    private int id;
    private BufferPollo buffer;
    private Cocina cocina;
    public BotPollo(int _id, BufferPollo _buffer){
        id = _id;
        buffer = _buffer;
    }

    public void run(){
        try {
            while(true) {
                buffer.insertar(1);
            }
        }catch (Exception e){}
    }
}
