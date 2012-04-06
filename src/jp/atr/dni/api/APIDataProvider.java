package jp.atr.dni.api;

import java.util.List;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 * @param <T>
 */
public interface APIDataProvider<T> {

   /**
    * 
    * @return the size of the data set
    */
   public int size();

   /**
    * 
    * @param from - index to start reading data from
    * @param to - index to reading data to
    * @return the data from the <code>from</code> index to the <code>to</code> 
    * index, exclusive. If <code>to</code> is greater than the size, only data 
    * up to the end of the data set will be returned, without any exception 
    * being thrown
    */
   public List<T> getData(int from, int to);
}
