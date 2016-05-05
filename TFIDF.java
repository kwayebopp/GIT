public class TFIDF{
    
    public static int tfRaw(String s, Tweet t){
        if (t.getFeats().containsKey(s.toLowerCase()))
        {


            return t.getFeats().get(s).size();
        }

        else return 0;
    }
    
    public static double idfRaw(String s, TweetTree tree){
        Nodes node = tree.getRoot().getNodeFromKey(s);


        // Trying + 1 in order to prevent negative infinity
        if (node != null) return (double) (tree.getRoot().getCount() / node.getTweet().size());
        else return 0;
    }
    
    public static double calc(String s, Tweet t, TweetTree tree){

        int tfRaw = (tfRaw(s, t));

        if (tfRaw == 0)
            return 0;

        return (1+ Math.log(tfRaw)) * Math.log(idfRaw(s, tree));
    }
}