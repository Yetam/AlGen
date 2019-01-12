import java.util.Comparator;

public class CustomComparator implements Comparator<Meduse> {
    @Override
    public int compare(Meduse o1, Meduse o2) {
        int out;
        if(o1.coveredDistance > o2.coveredDistance ){
        	out = 1;
        }
        else{
        	out = -1;
        }
        return out;
        }
}
