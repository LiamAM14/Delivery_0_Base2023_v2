package delivery;

public class BotPan implements Runnable{
    private int id;
    private BufferPan buffer;
    private Cocina cocina;

    public BotPan(int _id, BufferPan _buffer){
        id = _id;
        buffer = _buffer;
    }

    public void run(){

            try {
                while(true) {
                    for (int i=0; i<3; i++) {
                        buffer.insertar(i);
                    }
                }
            }catch (Exception e){}
        }

}
