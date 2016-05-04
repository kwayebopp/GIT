public class TFIDF{
    
    public static int tfRaw(String s, Tweet t){
        if (t.getFeats().containsKey(s.toLowerCase()))
           return t.getFeats().get(s).size();
        else return 0;
    }
    
    public static double idfRaw(String s, TweetTree tree){
        Nodes node = tree.getRoot().getNodeFromKey(s);
        if (node != null) return (double) node.getTotal() / node.getTweet().size();
        else return 0;
    }
    
    public static double calc(String s, Tweet t, TweetTree tree){
        return Math.log(tfRaw(s, t)) * Math.log(idfRaw(s, tree));
    }
}