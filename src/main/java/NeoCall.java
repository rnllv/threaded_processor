import java.util.List;
import java.util.concurrent.Callable;

public class NeoCall implements Runnable {

    private List<String> nn;

    public NeoCall(List<String> nn) {
        this.nn = nn;
    }

    @Override
    public void run() {
        String pload = nn.get(0);
        System.out.println("The thread id is: "+pload+"++>"+Thread.currentThread().getName());
        if (pload.charAt(0) % 2 ==0){
            //System.out.println("I'm goint to sleep");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.println("I'm awake");
        }
        return;
    }
}
