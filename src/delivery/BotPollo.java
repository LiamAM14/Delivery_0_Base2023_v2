package delivery;

public class BotPollo implements Runnable{
    int id;
    BufferPollo buffer;

    public BotPollo(int _id, BufferPollo _buffer){
        id = _id;
        buffer = _buffer;
    }

    public void run(){
        try {
            buffer.insertar(1);
        }catch (Exception e){}
    }
}
