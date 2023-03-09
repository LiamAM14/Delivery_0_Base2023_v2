package delivery;

public class BotPan implements Runnable{
    int id;
    BufferPan buffer;

    public BotPan(int _id, BufferPan _buffer){
        id = _id;
        buffer = _buffer;
    }

    public void run(){
        for (int i=0; i<3; i++){
            try {
                buffer.insertar(i);
            }catch (Exception e){}
        }
    }
}
