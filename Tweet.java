import java.util.*;
import java.util.regex.*;

public class Tweet implements java.io.Serializable{
    //static regex vars to save a little space
    private static Pattern clearPunc = Pattern.compile("[^A-Za-z0-9#]");
    private static Pattern clearAmp = Pattern.compile("&amp;");
    private static String urlRegex = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
    private static Pattern clearURL = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
    
    //static data structures
    //private static HashMap<String, Integer> corpusFreqTable = new HashMap<String, Integer>(); //all non-stopword terms in corpus w/ frequencies
    private static HashMap<String, Double> sentiments = Sentiment.buildScoreList(); //sentiment scorer
    private static HashMap<String, Integer> stops = Stops.buildList(); //stopword list
    
    private String text; //text field
    private int wordCount;
    private String timestamp; //timestamp field
    //private String username; //username field
    //private String ID; //Tweet ID field
    private HashMap<String, ArrayDeque<Integer>> features; //mapping of terms to their positions in tweet
    private double tf_idf;
    private double sentiScore; //sentiment score
    
    //compares on sentiScore
    public static Comparator<Tweet> SENTI_ORDER = new Comparator<Tweet>() {
        public int compare(Tweet a, Tweet b) {
            double comp = a.sentiScore - b.sentiScore;
            if (comp > 0) return 1;
            else if (comp < 0) return -1;
            else return 0;
        }
    };
    
    public static Comparator<Tweet> TFIDF_ORDER= new Comparator<Tweet>() {
        public int compare(Tweet a, Tweet b) {
            double comp = a.tf_idf - b.tf_idf;
            if (comp > 0) return 1;
            else if (comp < 0) return -1;
            else return 0;
        }
    };
    
    public double getTF_IDF(){
        return this.tf_idf;
    }
    
    public void setTF_IDF(double val) {
        this.tf_idf = val;
    } 
    
    public Tweet(){}
    
    public Tweet(String[] splitTweet) {
        //if (splitTweet == null) throw new NullPointerException();
        
        text = splitTweet[0];
        timestamp = splitTweet[1];
//        username = splitTweet[2];
//        ID = splitTweet[3];
        features = textFeaturizer(text.toLowerCase());
        tf_idf = 0;
        
//        for (String s : features.keySet()) {
//            if (!corpusFreqTable.containsKey(s.toLowerCase())) corpusFreqTable.put(s.toLowerCase(), 1);
//            else corpusFreqTable.put(s.toLowerCase(), corpusFreqTable.get(s) + 1);
//        }
        sentiScore = Sentiment.getScore(this, sentiments);
        
    }
    public Tweet(String t, String time, String usr, String id) {
        if (t == null || time == null || usr == null || id == null) throw new NullPointerException();
        text = t;
        timestamp = time;
//        username = usr;
//        ID = id;
        features = textFeaturizer(text);
//        for (String s : features.keySet()) {
//            if (!corpusFreqTable.containsKey(s.toLowerCase())) corpusFreqTable.put(s.toLowerCase(), 1);
//            else corpusFreqTable.put(s.toLowerCase(), corpusFreqTable.get(s) + 1);
//        }
        sentiScore = Sentiment.getScore(this, sentiments);
        
    }
    
    public String getText(){ return text; }
    public String getTime(){ return timestamp; }
//    public String getUser(){ return username; }
    public int wordCount() {return wordCount; }
    public HashMap<String, ArrayDeque<Integer>> getFeats(){
        return features;
    } 
    //public String getID(){ return ID; }
    public double getSentiScore(){ return sentiScore; }
    //public static HashMap<String, Integer> getCorpusFreqTable() {return corpusFreqTable; }
    
    private HashMap<String, ArrayDeque<Integer>> textFeaturizer(String t) {
        if (t == null) throw new NullPointerException();
        
        String text = t;
        text = clearURL(t);
        String[] arr = text.split("\\s+");
        wordCount = arr.length;
        HashMap<String, ArrayDeque<Integer>> map = new HashMap<String, ArrayDeque<Integer>>(); 
        
        //removes stopwords and counts each word's occurences in the tweet
        for (int i = 0; i < arr.length; i++) {
            //remove all non-alphanumeric characters
            arr[i] = clearPunc(arr[i]);
            if (!stops.containsKey(arr[i]) && !map.containsKey(arr[i])) {
                map.put(arr[i], new ArrayDeque<Integer>(1));
                map.get(arr[i]).add(i + 1);
            }
            else if (map.containsKey(arr[i])) map.get(arr[i]).add(i + 1);      
        }
        
        return map;
    }
    
    
    private String clearPunc(String s){
        if(s == null) throw new NullPointerException();
        s = clearAmp.matcher(s).replaceAll("");
        return clearPunc.matcher(s).replaceAll("");
    }
    
    private String clearURL(String s) {
        if(s == null) throw new NullPointerException();
        
        int i = 0;
        Matcher m = clearURL.matcher(s);
        while(m.find()) {
            s = s.replaceAll(Pattern.quote(m.group(i)),"").trim();
            i++;
        }
        return s;
    }
    
    public static void main(String[] args) {
        Tweet tweet = new Tweet("Just a seagull pulling my girls :)... #Photo by John Wilhelm #Dream #Love #Hope #Health #Peace &amp; #Art https://t.co/DVo3ujr1Sq","Sun Apr 03 16:19:36 +0000 2016","eclipse1905","716661438434177024");
        System.err.println(tweet.text);
        //System.err.println(tweet.ID);
        System.out.println(tweet.features);
        for (String s : tweet.features.keySet()) {
            System.out.print(tweet.features.get(s).size() + " ");
        }
        
        System.out.println(tweet.sentiScore);
    }
}