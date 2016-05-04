import java.util.*;
import java.io.*;

public class TreeUtils {
    private static ArrayList<Tweet> fullDeck = new ArrayList<Tweet>();
    private static long  startTime;
    private static HashMap<String, Integer> stops = Stops.buildList(); //stopword list
    public static Leaf readAndAddTweets(Leaf root, int currentValue) {
        startTime = System.currentTimeMillis();
        Leaf first = new Leaf(Main.size);
        Tweet tweet = null;
        Nodes node = null;
        
        // int endValue = 11000000;
        int endValue = 5000000;
  
        int count = 0;
              
        try {        
            FileInputStream f_in = new FileInputStream(/*"D:\\PreProcessed\\TweetObjects\\tweetObjs" + currentValue + ".ser"*/ "tweetObjs.ser");
            
            // Write object with ObjectOutputStream
            ObjectInputStream obj_in = new
                ObjectInputStream(f_in);
            
            while ((tweet = (Tweet) obj_in.readObject()) != null) {    
                HashMap<String, ArrayDeque<Integer>> feats = tweet.getFeats();
                count++;
                int id = fullDeck.size();
                
                //Add to deck, get size;
                fullDeck.add(tweet);
                
                if (count == 5000) break;
  
                for (String phrase : feats.keySet()) {
                    
                    //phrase = phrase.toLowerCase();
     
                    if (count % 250000 == 0) {
                        System.out.println(count + " Elapsed TIme: " + 
                                           (System.currentTimeMillis() - 
                                            startTime) + 
                                           " Average Inserts per second: " + 
                                           count / ((System.currentTimeMillis() 
                                                         - startTime) / 1000));
                    }
                    
                    //  System.out.println("This is it!: " + phrase);
                    //  if (!phrase.equals(" "))
                    root = Leaf.addToTree(root, phrase, Main.size, id);
                    //        System.out.println(root.getPointers().size());
                }
                
            }
        } catch(EOFException e)
        {
            System.out.println("ERROR! EOF");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println("file done!");
        
        currentValue += 500000;
        
        
        if (currentValue < endValue)
            root = readAndAddTweets(root, currentValue);
        
        return root;
    }
    
    public static ArrayList<Tweet> getFullDeck()
    {
        return fullDeck;
    }
    
    public static PriorityQueue<Tweet> searchTree(String query, TweetTree tree)
    {
        HashSet<Integer> visited = new HashSet<Integer>();
        String[] q = query.toLowerCase().split("\\s+");
        PriorityQueue<Tweet> tweets = new PriorityQueue<Tweet>(11, Tweet.SENTI_ORDER);
        for (int m = 0; m < q.length; m++){
            if (!stops.containsKey(q[m])){
                Nodes node = tree.getRoot().getNodeFromKey(q[m]);
                if (node != null)
                {
                    /*
                     int[] list = node.getTweetList();
                     System.out.println(list.length);
                     for (int i = 0; i < list.length/10; i++)
                     {
                     int index = list[i];
                     total = fullDeck.get(index).getText();
                     System.out.println(total);
                     }
                     */
                    
                    ArrayList<Integer> list = node.getTweet();
                    System.out.println(list.size());
                    int num = 1000;
                    
                    
                    if (list.size() < num)
                    {
                        num = list.size();
                    }
                    
                    for (int i = 0; i < num && !visited.contains(list.get(i)); i++){
                        //System.out.println(fullDeck.get(list.get(i)).getText() + " id: " + list.get(i));
                        tweets.add(fullDeck.get(list.get(i)));
                        visited.add(i);
                    }
                    
                    for (Tweet t : tweets) {
                        double tfidf = 0;
                        for (String s : q) {
                            tfidf += TFIDF.calc(s, t, tree);
                        }
                        t.setTF_IDF(tfidf);
                    }
                }
            }
        }
        return tweets;
    }
}