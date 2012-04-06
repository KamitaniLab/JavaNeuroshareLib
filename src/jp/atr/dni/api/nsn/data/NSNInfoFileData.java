/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/15
 */
package jp.atr.dni.api.nsn.data;

import jp.atr.dni.api.APIData;

/**
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/15
 */
public class NSNInfoFileData implements APIData {

   /**
    * 
    */
   public NSNInfoFileData() {
      super();
   }

   /**
    * @param xml
    */
   public NSNInfoFileData(String xml) {
      super();
      this.xml = xml;
   }

   private String xml;

   /**
    * @return the xml
    */
   public String getXml() {
      return xml;
   }

   /**
    * @param xml the xml to set
    */
   public void setXml(String xml) {
      this.xml = xml;
   }

}
