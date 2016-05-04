import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by kevin on 4/14/2016.
 */
public class Leaf implements Comparable<Leaf>, Serializable {
    private int size;
    private ArrayList<Nodes> inLeaf = new ArrayList<Nodes>();
    private ArrayList<Leaf> pointers = new ArrayList<Leaf>();



    private Leaf upPointer = null;

    private int maxSize = 0;

    public Leaf(int size)
    {
        maxSize = size;
        //n + 1
        pointers = newPointers();
    }

    // return new size;
    public int insertNode(Nodes node)
    {


        inLeaf.add(node);
        Collections.sort(inLeaf);
        size++;
        return size;
    }

    // contains pointer
    public boolean containsNode(Nodes node)
    {
        if (inLeaf.contains(node))
            return true;
      //  for (Nodes n : inLeaf)
    //    {
     //       if (n.getValue().toString().equals(node.getValue().toString()))
      //          return true;
//

      //  }
        return false;
       // return this.getNodes().contains(node);
    }
    
    public void insertPointers(Leaf pointer)
    {
        pointer.setUpPointer(this);

        Nodes sample = pointer.getFirstValue();
        String insertValue = ((String)sample.getValue());

        int i = 0;
      /*  for (Nodes e : this.inLeaf)
        {

            if (e.getValue().toString().compareTo(sample.getValue().toString()) > 0)
          //  if (e.compareTo(sample) > 0)
            {
                pointers.add(i, pointer);
                pointers.sort(leafComparator);
                return;
            }
            i++;

        }
        */
      //  pointers.add(i, pointer);
        pointers.add(pointer);
        Collections.sort(pointers);
    }



    public Leaf getRelevantPointer(Nodes node)
    {
        String value = (String)node.getValue();

        int i = 0;
      //  inLeaf.

     //   int pointerSize = pointers.size() -1;
        int pointerSize = inLeaf.size() - 1;
        int currentTraversal = 0;

      //  currentTraversal = pointerSize/2;
/*

     //   return binarySearchRelevantPointer(node, currentTraversal, pointerSize);

        inLeaf.sort(Nodes::compareTo);
     //   pointers.sort(Leaf::compareTo);
        int result = Collections.binarySearch(inLeaf, node);


        if (result < 0)
        {
            result += 1;
            result *= -1;
        }

        System.out.println("I value: " + i + "Result: " + result);
        for (Nodes n : inLeaf)
        {
            System.out.println("Search term: " + value + " Node value: " + n.getValue());
        }

    //    System.out.println(result);


        if ((pointers.size() > result)) //&& (i != 0))
        {
            //   System.out.println("Return: " + pointers.get(i));
            System.out.println("INSERT");
            return pointers.get(result);
        }

        return null;

    */


        for (Nodes e : inLeaf)
        {
       //     System.out.println("E: " + e.getValue() + " node: " + node.getValue());
       //     System.out.println(e.compareTo(node));

            // Will be > 0 if node is > e
            // IF WE CALL inLeaf.sort(Nodse::compareTo), we can use e.compareTo(node) >= 0;
            if (e.compareTo(node) <= 0)
        //    if (e.getValue().toString().compareTo(node.getValue().toString()) > 0)
            {
           //     System.out.println("break");
                break;
            }

            i++;
        }
    //    Collections.sort(pointers);
        if ((pointers.size() > i)) //&& (i != 0))
        {
         //   System.out.println("Return: " + pointers.get(i));
            return pointers.get(i);
        }
        return null;
    }

    /*

    private Leaf binarySearchRelevantPointer(Nodes node, int lo, int max)
    {

        int currentTraversal = lo + (max- lo)/2;


        if (currentTraversal > max)
            return null;

        if (currentTraversal == 0)
        {
            if ((pointers.get(currentTraversal).getFirstValue().compareTo(node) <= 0))
                return pointers.get(currentTraversal);
        }
        else if ((pointers.get(currentTraversal).getFirstValue().compareTo(node) <= 0) && (pointers.get(currentTraversal - 1).getFirstValue().compareTo(node) >= 0))
        {
            return pointers.get(currentTraversal);
        }
        else if (pointers.get(currentTraversal - 1).getFirstValue().compareTo(node) <= 0)
        {
            binarySearchRelevantPointer(node, lo, currentTraversal - 1);
        }
        else
            binarySearchRelevantPointer(node, currentTraversal + 1, max);


        return null;

    }

*/

    private Leaf binarySearchRelevantPointer(Nodes node, int lo, int max)
    {

        int currentTraversal = lo + (max- lo)/2;
        
        if (lo > max || (currentTraversal >= pointers.size()))
            return null;

        if (inLeaf.size() == 0)
            return pointers.get(currentTraversal);

        
        else if (currentTraversal == 0)
        {
            if (inLeaf.get(currentTraversal).compareTo(node) <= 0)
                return pointers.get(currentTraversal);
        }
        else if ((inLeaf.get(currentTraversal).compareTo(node) <= 0) && (inLeaf.get(currentTraversal-1).compareTo(node) >= 0))
        {
            return pointers.get(currentTraversal - 1);
        }
        else if (inLeaf.get(currentTraversal - 1).compareTo(node) <= 0)
        {
            binarySearchRelevantPointer(node, lo, currentTraversal - 1);
        }
        else
            binarySearchRelevantPointer(node, currentTraversal + 1, max);
        return null;
    }

    public static Comparator<Leaf> leafComparator = new Comparator<Leaf>() {
        public int compare(Leaf o1, Leaf o2) {

            if (((o1 == null) || (o2 == null)))
                return Integer.MAX_VALUE;

            return (o1.getFirstValue().getValue().toString()).compareTo(o2.getFirstValue().getValue().toString());

        }
    };

    public Leaf getUpPointer()
    {
        return upPointer;
    }

    public void setUpPointer(Leaf up)
    {
        upPointer = up;
    }

    public int getSize()
    {
        return size;
    }

    public ArrayList<Nodes> getNodes()
    {
        return inLeaf;
    }

    public Nodes getFirstValue()
    {
        if (inLeaf.size() > 0)
            return inLeaf.get(0);

        return null;
    }

    public Nodes getLastValue()
    {
        return inLeaf.get(inLeaf.size());
    }


    public Nodes getMiddleValue()
    {
        if (inLeaf.size()%2. != 0)
        {
            int i = 0;
            i = inLeaf.size() - 1;
            i /= 2;
            return inLeaf.get(i);
        }
        return inLeaf.get(inLeaf.size()/2);
    }

    public ArrayList<Leaf> getPointers()
    {
        return this.pointers;
    }


    public void insertLeaf(Leaf leafIn)
    {

    }

    public Leaf deleteLeaf(int num)
    {
        Leaf l = this.pointers.remove(num);
        Collections.sort(this.pointers);
        l.setUpPointer(null);

        return l;


    }

    public void deleteNode(Nodes node)
    {
        for (int i = 0; i < this.inLeaf.size(); i++)
        {
            if (inLeaf.get(i).equals(node))
            {
                inLeaf.remove(i);
                Collections.sort(inLeaf);
                return;
            }
        }
    }

    public String toString()
    {
        String concat = "PRINTING: ";

        for (Nodes e : this.inLeaf)
        {
            concat += (e.getValue()) + " , ";
        }

        concat += "\n" ;
        int i = 0;
        for (Leaf e : this.pointers)
        {
            if (e != null) {
                for (Nodes n : e.getNodes()) {
                    concat += ((n.getValue()) + " , ");
                }

                concat += "\n";
            }
        }

       for (Leaf e : this.pointers)
       {
           if (e != null)
            for (Leaf f : e.getPointers())
            {
                if (f != null)
                    concat += "Leaf " + e.getFirstValue().getValue()+ " In leaf " + f.toString() + "\n";
            }
        }
        return concat;
    }

    public int getMaxSize(int startSize)
    {
        for (Nodes e : this.inLeaf)
        {
            startSize++;
        }

        for (Leaf e : this.pointers)
        {
            startSize += e.getMaxSize(0);
        }

        return startSize;
    }


    /* Finds the direct path to insert into */
    public static Leaf findLeafToInsert(Leaf root, Nodes node)
    {
        Leaf toReturn = null;
        Leaf toReturn1;

        if ((toReturn1 = root.getRelevantPointer(node)) != null)
        {
            toReturn = toReturn1;
            toReturn = findLeafToInsert(toReturn, node);

        }

        if (toReturn != null)
        {
            return toReturn;
        }

        return root;
    }

    public Nodes getNodeFromKey(String value)
    {
        Nodes node = new Nodes(value);
        Leaf leaf = findLeafToInsert(this, node);
        for (Nodes e: leaf.getNodes())
        {
            System.out.println("NOde hold: " + e.getValue() + " Search term:" + value);
            if (e.getValue().equals(value))
            {
                return e;
            }
        }

        return null;
    }

    public static Leaf addToTree(Leaf root, Object obj, int leafSize, int tweet)//String[] values)//
    {
        Nodes node = new Nodes(obj);
        Leaf first= null;

        // System.out.println("root: " + root);


        //      long currentTIme = System.currentTimeMillis();

        first = Leaf.findLeafToInsert(root, node);
        //      System.out.println("DELAY IN ADD: " + (currentTIme - System.currentTimeMillis()));
        //if (i == 6)
        //  System.out.println("First: " + first);

        insertNode(first, node, leafSize, null, tweet);

        while ((first = root.getUpPointer()) != null)
        {
            //System.out.println("")
            root = first;
            //  System.out.println(root);
        }
        //    System.out.println(" STEPS!!: " + total);
        //    System.out.println(" NUMBER OF UNIQUE!: " + (wordTotal - repeatedWords));
        //     System.out.println(" Number of Repeated " + repeatedWords);
        return root;
    }

    private static void insertNode(Leaf first, Nodes node, int leafSize, Leaf pointer, Integer tweet)
    {
        if (first.containsNode(node))
        {
            if (tweet != null)
                first.getNodes().get(first.getNodes().indexOf(node)).setTweet(tweet, 0);
            return;
        }
        
        Leaf root = first.getUpPointer();

        Leaf newLeaf;
        if (first.getSize() < leafSize)
        {

            if (tweet != null)
                node.setTweet(tweet, 0);

            first.insertNode(node);

            if (pointer != null) {
                //      System.out.println("POINTER INSERT " + first + "INSERT: " + pointer);
                first.insertPointers(pointer);
                pointer.setUpPointer(first);
                //  System.out.println("Pointer size: " + first.getPointers().size());
                // System.out.println("NEW LEAF " + pointer);
            }
        }
        else
        {
            if (tweet != null)
                node.setTweet(tweet, 0);

            //   System.out.println("Size " + first.getSize());
            if (pointer != null)
            {
                //  System.out.println("POINTER INSERT " + first + " pointer " + pointer);
                first.insertPointers(pointer);
            }
            if (pointer == null)
                // IS IN LEAF LAYER SO FALSE IN BOOLEAN
                newLeaf = first.overflowSplit(node, false);
            else
            {
                // IS IN INDEX LAYER SO HAVE TRUE AS BOOLEAN
                newLeaf = first.overflowSplit(node, true);
            }


            // If we don't have a root, make a new one and push up
            if (root == null) {
                root = new Leaf(leafSize);
                first.setUpPointer(root);
                root.insertPointers(first);
            }

            // If pointer is not null, that means it is in the leaf layer
            if (pointer == null)
                insertNode(root, newLeaf.getFirstValue(), leafSize, newLeaf, null);
            else
            {
                Nodes tempNode = newLeaf.getFirstValue();

                // If index split, we don't want duplicates because we are taking first value of the leaf
                newLeaf.deleteNode(newLeaf.getFirstValue());
                insertNode(root, tempNode, leafSize, newLeaf, null);
            }
        }
    }

    // Insert node in overflow case
    // BOOLEAN INDICATES WHETHER IN LEAF OR INDEX LAYER. If we are in index layer, boolean is true.
    // Indicates that we have a value we will remove-- so we perform the decrement size operator
    // on the original leaf and the new leaf.
    public Leaf overflowSplit(Nodes node, Boolean isItIndex)
    {
   //     System.out.println("split");
        int i = 0;
        Leaf newLeaf = new Leaf(size);
        ArrayList<Nodes> newList = new ArrayList<Nodes>();
        ArrayList<Leaf> thisPointers = newPointers();
      //  ArrayList<Leaf> newPointers = new ArrayList<Leaf>();

      //  System.out.println(node.getValue());

        int correctSize = size;

        if (node != null)
        {
            insertNode(node);
            if (isItIndex)
                decrementSize();
        }

        int nodeIncrement = 0;
        for (int x = 0; x < inLeaf.size() + 1; x++)
        {
            if (i < inLeaf.size())
            {
                if ((i + 1) > (correctSize / 2)) {
                    newLeaf.insertNode(this.inLeaf.get(i));

                        /*
                        if (pointers.size() >= (i))
                        {
                            if (pointers.get(i-1) != null)
                                newLeaf.insertPointers(pointers.get(i-1));
                        }
                        */
                }
                else {
                    newList.add(this.inLeaf.get(i));
                        /*
                        if (pointers.size() >= (i))
                            thisPointers.add(pointers.get(i-1));
                            */
                }
                i++;
            }
            if (x < pointers.size())
            {
                // Original
                if (pointers.get(x) == null)
                    continue;
                if (x >= (pointers.size() / 2))
                    newLeaf.insertPointers(pointers.get(x));
                else
                    thisPointers.add(pointers.get(x));
            }
        }
/*
        for (Nodes e : this.inLeaf)
        {
          //  System.out.println(e.getValue());
            i++;
            if (i > (correctSize)/2)
            {
                newLeaf.insertNode(e);



            }
            else
            {
                newList.add(e);

            }

        }
        i++;

        */
        // To fix corner case of index splitting
        /*
        if (node == null)
            if (pointers.size() >= (i))
            {
                if (pointers.get(i-1) != null)
                    newLeaf.insertPointers(pointers.get(i-1));
            }
*/
        if (isItIndex)
            newLeaf.decrementSize();
        this.pointers = thisPointers;
        this.inLeaf = newList;
        this.size = (correctSize/2);

        newLeaf.setUpPointer(this.upPointer);

        return newLeaf;

    }

    private ArrayList<Leaf> newPointers()
    {
      //  for (int i = 0; i <= size; i++)
       //     toReturn.add(null);
        return new ArrayList<Leaf>();
    }

    public void decrementSize()
    {
        size--;
    }

    @Override
    public int compareTo(Leaf o) {
        if (((this == null) || (o == null)))
            return Integer.MAX_VALUE;

        return ( this.getFirstValue().getValue().toString()).compareTo((String) o.getFirstValue().getValue().toString());
    }
}