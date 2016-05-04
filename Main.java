import java.io.*;
import java.util.*;

/**
 * Created by kevin on 4/13/2016.
 */
public class Main {

    public static int total = 0;
    public static int wordTotal = 0;
    public static int size = 50;
    public static int repeatedWords = 0;
    public static int fileNum = 0;
    private static String serializeFile = "D:\\PreProcessed\\myobjectTweetStuff";

    private static int removalEnd = 20;
    private static int removalStart = 0;

    public static void main(String[] args)
    {
     //   String values[] = {"dog", "cat", "stri", "shp", "she", "peo", "lit", "nud", "wre", "pie"};
        //String values[] = {"19", "25", "5"};
       // String values = "dog cat stri shp she peo lit nud wre pie";

        //String value = "Squarespace is looking for an inquisitive, data-driven market research intern with a keen eye for details to join our Business Strategy team this summer. This is a hands-on internship with a focus on customer insights and market research that will expose you to many different parts of the company. You will support the team in conducting qualitative and quantitative research, synthesizing key takeaways from large data sets, and communicating findings that could drive change throughout the company. ";
       // String[] values = value.split(" ");
       // int size = 200;

   //    readAndAddToTree();

  //     DeSerializeAndRebuildTree();

        Leaf root = null;
        root = new Leaf(size);
        TweetTree builder = new TweetTree(root);
        root = builder.getRoot();
        ArrayList<Tweet> tweets = TreeUtils.getFullDeck();
        System.out.println(root.getPointers().size());
   //     serializeTreeNonRecursive(root);
   //     root = deserializeTreeNonRecursive(root);
   //     System.out.println("Size: " + root.getSize());



        String fullBack = "test";
        while (fullBack != null)
        {
            System.out.println("Type search term!:" );
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            PriorityQueue<Tweet> tweetQueue = TreeUtils.searchTree(s, builder);
            while (!tweetQueue.isEmpty()) {
                String tweetText = tweetQueue.poll().getText();
                System.out.println(tweetText);
            }
        }
    }

    private static Leaf deserializeTreeNonRecursive(Leaf root)
    {
        //  Leaf root = null;
        // Read from disk using FileInputStream
        FileInputStream f_in = null;
        Leaf leaf = null;

        Leaf currentStart;
        total = 0;
        fileNum = 0;
        try {
            f_in = new
                    FileInputStream(serializeFile);

            ObjectInputStream obj_in =
                    new ObjectInputStream (f_in);

            Queue<Leaf> queue = new LinkedList<Leaf>();
            //    queue.add(root);


            removalEnd = root.getPointers().size()-2;

            for (int i = removalStart; i < removalEnd; i++) {

                int totalNode = 0;
                f_in = new FileInputStream(serializeFile + fileNum + ".data");
                fileNum++;
                // Write object with ObjectOutputStream
                obj_in = new
                        ObjectInputStream(f_in);
                System.out.println("file done!");


                if ((leaf = (Leaf) obj_in.readObject()) != null) {
                    if (total == 0) {
                        root = leaf;
                    }
                    else if (totalNode == 0)
                    {
                        queue.add(leaf);
                    }
                    total += 1;
                    //   System.out.println(total);
                    totalNode++;
                }


            }

            for (Leaf e : queue)
            {
                root.insertPointers(e);
                System.out.println("DONE");

            }

            System.out.println("Size: " + root.getMaxSize(0));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e)
        {
            System.out.println("Next file!");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return root;

    }
    private static void serializeTreeNonRecursive(Leaf root)
    {
        FileOutputStream f_out = null;

        try {
            f_out = new
                    FileOutputStream(serializeFile);

            // Write object with ObjectOutputStream
            ObjectOutputStream obj_out = new
                    ObjectOutputStream(f_out);

            Queue<Leaf> queue = new LinkedList<Leaf>();

            Queue<Leaf> removedQueue = new LinkedList<Leaf>();

            removedQueue.add(root);

            removalEnd = root.getPointers().size()-2;

            for (int i = removalStart; i < removalEnd; i ++)
            {
                Leaf l = root.deleteLeaf(0);
                removedQueue.add(l);
            }


            while (!removedQueue.isEmpty()) {
                queue.add(removedQueue.remove());

                    obj_out.close();
                    f_out = new FileOutputStream(serializeFile + fileNum + ".data");
                    fileNum++;
                    // Write object with ObjectOutputStream
                    obj_out = new
                            ObjectOutputStream(f_out);


                while (!queue.isEmpty()) {
                    Leaf leaf = queue.remove();
                    queue.addAll(leaf.getPointers());
                    obj_out.writeObject(leaf);

                    obj_out.flush();
                    total += 1;

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
