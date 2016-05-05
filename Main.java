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

    public static void main(String[] args) {


        Leaf root = null;
        root = new Leaf(size);
        TweetTree builder = new TweetTree(root);
        root = builder.getRoot();
        ArrayList<Tweet> tweets = TreeUtils.getFullDeck();
       // System.out.println(root.getPointers().size());
        //     serializeTreeNonRecursive(root);
        //     root = deserializeTreeNonRecursive(root);
             System.out.println("Size: " + root.getMaxSize(0));


        String fullBack = "test";


        while (fullBack != null) {
            System.out.println("Type search term!:");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            ArrayList<Tweet> tweetList= TreeUtils.searchTree(s, builder);

            int count = 0;
            for (Tweet tweet : tweetList) {

                if (count == 10)
                    break;
                String tweetText = tweet.getText();
                System.out.println(tweetText + " ID " + tweet.getTF_IDF() + " SENTI " + tweet.getSentiScore());
                count++;
            }
        }
    }

    private static Leaf deserializeTreeNonRecursive(Leaf root) {
        //  Leaf root = null;
        // Read from disk using FileInputStream
        FileInputStream f_in = null;
        Leaf leaf = null;

        Leaf currentStart;
        total = 0;
        fileNum = 0;

        Queue<Leaf> queue = new LinkedList<Leaf>();
        try {


            //    queue.add(root);


            //         removalEnd = root.getPointers().size()-2;
            // removalEnd = 29;

            for (int i = removalStart; i < removalEnd; i++) {

                int totalNode = 0;
                f_in = new FileInputStream(serializeFile + fileNum + ".data");
                fileNum++;
                // Write object with ObjectOutputStream
                ObjectInputStream obj_in = new
                        ObjectInputStream(new BufferedInputStream(f_in));
                System.out.println("file done!" + fileNum);


                if ((leaf = (Leaf) obj_in.readObject()) != null) {
                    if (total == 0) {
                        root = leaf;
                    } else if (totalNode == 0) {
                        queue.add(leaf);
                    }
                    total += 1;
                    //   System.out.println(total);
                    totalNode++;
                }


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            System.out.println("Next file!");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Leaf e : queue) {
            root.insertPointers(e);
            System.out.println("DONE");

        }

        System.out.println("Size: " + root.getMaxSize(0));


        return root;

    }


    private static ArrayList<Tweet> deserializeTweets(ArrayList<Tweet> tweets) {
        int start = 0;

        long startTime = System.currentTimeMillis();

        ArrayList<Tweet> tweetToReturn = null;
        System.out.println("I'M INSIDE! ");
        try {

            String fileName = serializeFile + "tweets" + start + ".ser";
            System.out.println(fileName);

            FileInputStream f_in = new
                    FileInputStream(fileName);

            ObjectInputStream obj_in =
                    new ObjectInputStream(new BufferedInputStream(f_in));


            tweetToReturn = (ArrayList<Tweet>) obj_in.readObject();
            System.out.println("SIZE! " + tweetToReturn.size());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            System.out.println("END OF FILE! IN THING!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return tweetToReturn;
    }


    private static void serializeTweets(ArrayList<Tweet> tweets) {
        try {
            FileOutputStream f_out = new
                    FileOutputStream(serializeFile + "tweets" + "0" + ".ser");


            // Write object with ObjectOutputStream
            ObjectOutputStream obj_out = new
                    ObjectOutputStream(new BufferedOutputStream(f_out));

            obj_out.writeObject(tweets);

            obj_out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serializeTreeNonRecursive(Leaf root) {

        FileOutputStream f_out = null;
        ObjectOutputStream obj_out = null;

        try {


            Queue<Leaf> queue = new LinkedList<Leaf>();


            Queue<Leaf> removedQueue = new LinkedList<Leaf>();

            removedQueue.add(root);


            removalEnd = root.getPointers().size() - 2;

            for (int i = removalStart; i < removalEnd; i++) {

                Leaf l = root.deleteLeaf(0);
                removedQueue.add(l);
            }


            while (!removedQueue.isEmpty()) {
                queue.add(removedQueue.remove());


                f_out = new FileOutputStream(serializeFile + fileNum + ".data");
                fileNum++;
                // Write object with ObjectOutputStream
                obj_out = new
                        ObjectOutputStream(new BufferedOutputStream(f_out));


                while (!queue.isEmpty()) {
                    Leaf leaf = queue.remove();
                    queue.addAll(leaf.getPointers());
                    obj_out.writeObject(leaf);

                    obj_out.flush();
                    total += 1;

                }

                obj_out.close();


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}