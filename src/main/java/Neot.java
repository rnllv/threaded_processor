import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Neot {

    private List<String> ff = Arrays.asList("a", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o", "b", "c", "e", "f", "g", "i", "j", "k", "m", "n", "o");
    private Iterator<String> fff = ff.iterator();

    public boolean getNext(List<String> aa) {
        if (fff.hasNext()){
            aa.add(fff.next());
            return true;
        }
        return false;
    }
}
