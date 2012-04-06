/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/08
 */
package jp.atr.dni.api.nsn.channels;

import jp.atr.dni.api.nsn.data.NSNInfoFileData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/18
 */
public class NSNInfoFileChannel implements NSNChannel<NSNInfoFileData> {

   private int id;

   private NSNInfoFileData data;

   public NSNInfoFileChannel(int id, String xml) {
      this.id = id;
      this.data = new NSNInfoFileData(xml);
   }

   @Override
   public int getId() {
      return id;
   }

   @Override
   public NSNChannelType getType() {
      return NSNChannelType.INFO_FILE;
   }

   @Override
   public String getLabel() {
      return NSNChannelType.INFO_FILE.toString();
   }

   @Override
   public void setLabel(String label) {

   }

   /**
    * @param id the id to set
    */
   public void setId(int id) {
      this.id = id;
   }

   /**
    * @param data the data to set
    */
   public void setData(NSNInfoFileData data) {
      this.data = data;
   }

   @Override
   public long getItemCount() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void setItemCount(long itemCount) {
      // TODO Auto-generated method stub

   }

   @Override
   public String getURI() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public NSNInfoFileData getData() {
      return data;
   }
}
