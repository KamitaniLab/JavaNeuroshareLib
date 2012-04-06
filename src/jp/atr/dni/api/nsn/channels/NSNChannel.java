package jp.atr.dni.api.nsn.channels;

import jp.atr.dni.api.APIData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 * @param <E>
 */
public interface NSNChannel<E> {

   public int getId();

   /**
    * @return the type
    */
   public NSNChannelType getType();

   /**
    * 
    * @return the label
    */
   public String getLabel();

   /**
    * @param label the label to set
    */
   public void setLabel(String label);

   /**
    * @return the itemCount
    */
   public long getItemCount();

   /**
    * @param itemCount the itemCount to set
    */
   public void setItemCount(long itemCount);

   public String getURI();

   public APIData getData();
}
