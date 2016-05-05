import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by kevin on 4/13/2016.
 */
public class Nodes implements Comparable<Nodes>, Serializable {

    private Object value = null;
    private static int total = 0;

    private ArrayList<Integer> tweetList = new ArrayList<Integer>();
    
    int storedValues = 0;

    public Nodes (Object value)
    {
        this.value = value;
    }

    private void setValue(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }

    public static Comparator<Nodes> nodesComparator = new Comparator<Nodes>() {

        public int compare(Nodes o1, Nodes o2) {


            return ((String) o1.value).compareTo((String) o2.value);

        }
    };

    public void incrementTotal()
    {
        total++;
    }

    public int getTotal()
    {
        return total;
    }

    @Override
    public int compareTo(Nodes o) {

        // OLD WAY, LETS DO NEW WAY!
        //   return ((String) o.value).compareTo((String) this.value);

        return ((String) this.value).compareTo((String) o.value);
    }





    public void setTweet(int tweet, int hap)
    {
        tweetList.add(tweet);
    }





    public ArrayList<Integer> getTweet()
    {
        return tweetList;
    }


    public boolean equals(Object o)
    {
        boolean isEqual = false;
        Nodes node = (Nodes)o;

        if ((o != null) && (node.getValue() != null))
        {
            return node.getValue().toString().equals(this.getValue().toString());
        }

        return false;
    }
}
