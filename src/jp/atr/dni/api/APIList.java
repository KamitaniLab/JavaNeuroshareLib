package jp.atr.dni.api;

import java.util.HashMap;
import java.util.List;

/**
 * TODO: make iterable!
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 * @param <E>
 */
public final class APIList<E> {

   /** Number of elements in this list. */
   private int size;

   private int internalSize;

   /** Counts the number of times data is not in the cache and must be read in, 
    * similiar to page faults in memory.*/
   private int dataFaults;

   /** Data provider for this APIList. */
   private APIDataProvider<E> dataProvider;

   /** The starting index for data that is currently in the cache.*/
   private int startIndex = -1;

   /** The ending index for data that is currently in the cache. */
   private int endIndex = -1;

   /** The maximum size of the data cache that is stored in memory. */
   private int cacheSize = 10000;

   /** The cache of data that is loaded into memory. */
   private List<E> cache;

   /** Map of data that has been changed, from index to datum. */
   private HashMap<Integer, E> changes;

   // private victimCache
   /** 
    * Constructor. 
    * 
    * @param dataProvider - data provider for this APIList
    */
   public APIList(APIDataProvider<E> dataProvider) {
      this.dataProvider = dataProvider;
      this.size = dataProvider.size();
      this.internalSize = dataProvider.size();
      changes = new HashMap<Integer, E>();
   }

   /**
    * @return the data at index ndx
    */
   public synchronized E get(int ndx) {
      if (changes.containsKey(ndx)) {
         return changes.get(ndx);
      } else if (ndx >= 0 && ndx < this.size) {
         if (ndx >= startIndex && ndx <= endIndex && cache != null) {
            return cache.get(ndx - startIndex);
         } else {
            // DataFault
            dataFaults++;
            if (cacheSize < 50000) {
               // Grow the cache size by 500 for every faults, to a max of 50,000
               cacheSize += 500;
            }
            startIndex = ndx;
            endIndex = startIndex + cacheSize;
            if (endIndex >= internalSize) {
               endIndex = internalSize - 1;
            }
            cache = dataProvider.getData(startIndex, endIndex);

            return cache.get(ndx - startIndex);
         }
      }

      return null;
   }

   /**
    * Changes the data.
    * 
    * @param ndx - index of datum to change
    * @param e - datum to change
    */
   public synchronized void set(int ndx, E e) {
      changes.put(ndx, e);
      // TODO: offload changes to the disk once this gets too big
   }

   // TODO:
   // /**
   // *
   // *
   // * @param e
   // */
   public synchronized void add(E e) {
      changes.put(size++, e);
      // TODO: offload changes to the disk once this gets too big
   }

   // public synchronized boolean remove(E e) {
   // //First search for the object, then remove it
   //
   // }
   // /**
   // *
   // * @param ndx
   // * @return
   // */
   // public synchronized void remove(int ndx) {
   // // data.remove(ndx);
   // //TODO:!
   // }

   /**
    * 
    * @return 
    */
   public synchronized int size() {
      return size;
   }
}
