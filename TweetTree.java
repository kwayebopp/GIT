import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by kevin on 5/1/2016.
 */
public class TweetTree
{
    private static Leaf treeRoot = null;
    
    //  private static int size = 50;
    public TweetTree(){}
    
    public TweetTree(Leaf root)
    {
        treeRoot = TreeUtils.readAndAddTweets(root, 0);
    }
    
    public Leaf getRoot()
    {
        return treeRoot;
    }
}