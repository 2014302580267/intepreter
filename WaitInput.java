/**
 * Created by fannie on 2017/1/1.
 */
public class WaitInput extends Thread {
    public void run(){
        int j = 0;
        for (int i = 0; i < 10000000; i++){
            j = i;
        }
    }
}
