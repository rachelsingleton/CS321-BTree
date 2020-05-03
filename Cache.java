import java.util.*;

/*
 * Controls and maintains a cache
 */
public class Cache<T> {
    
    // Instance Variables
    private int cacheSize;
    private LinkedList<T> myCache;
    private int actualSize;

    /*  
     * Constructor
     */
    public Cache(int size) {
        cacheSize = size;
        myCache = new LinkedList<T>();
        actualSize = 0;
    }   
    
    /*  
     * Searches for the given object in the cache.
     * If found, return the object we were searching for and move it to the top.
     * If not found, return null and add object to cache. 
     */
    public T getObject(T word) {
        if(myCache.contains(word)) {
            removeObject(word); 
            return word;
        } else {
            addObject(word); 
            return null;
        }   
    }   
    
    public boolean Contains(T word) {
    	if(myCache.contains(word)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /*  
     * Adds an object to the front of the cache.
     * Checks to see if the cache is filled before adding an object.
     */
    public void addObject(T cacheItem) {
        if(myCache.size() == cacheSize) {
            myCache.removeLast();
            myCache.addFirst(cacheItem);
        } else {
            myCache.addFirst(cacheItem);
            actualSize++;
        }
    }

    /*
     * If object exists, the item will be removed from the cache and added to front of cache. 
     */
    public void removeObject(T cacheItem) {
        if(myCache.contains(cacheItem)) {
            int index = myCache.indexOf(cacheItem);
            T temp= myCache.remove(index);
            myCache.addFirst(temp);
        }
    }

    /* 
     * Clears the cache
     */
    public void clearCache() {
        myCache.clear();
        actualSize = 0;
    }
}
