package jp.atr.dni.api.nsn.data;

/**
 * This class is just an example. Please make this an interface or abstract class to handle multiple value types (byte, csv, etc...).
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/10
 */
public class NSNEvent {

   private double timestamp;

   private Object value;

   /**
    * @return the timestamp
    */
   public double getTimestamp() {
      return timestamp;
   }

   /**
    * @param timestamp the timestamp to set
    */
   public void setTimestamp(double timestamp) {
      this.timestamp = timestamp;
   }

   /**
    * @return the value
    */
   public Object getValue() {
      return value;
   }

   /**
    * @param value the value to set
    */
   public void setValue(Object value) {
      this.value = value;
   }
}
