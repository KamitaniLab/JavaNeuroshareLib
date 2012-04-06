/**
 * 
 */
package jp.atr.dni.api.nsn.header;

import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 *
 * @author kharada
 * @version 2011/01/13
 */
public abstract class NSNChannelHeader {
   private long elemLength;

   private long itemCount;

   private long dataPosition;

   private NSNChannelType type;

   private String label;

   private String filePath;

   /**
    * @param elemLength
    * @param type
    * @param entityLabel
    * @param itemCount
    * @param dataPosition
    * @param filePath
    */
   public NSNChannelHeader(long elemLength, NSNChannelType type, String label, long itemCount, long dataPosition,
         String filePath) {
      super();
      this.elemLength = elemLength;
      this.type = type;
      this.label = label;
      this.itemCount = itemCount;
      this.dataPosition = dataPosition;
      this.filePath = filePath;
   }

   /**
    * @return the elemLength
    */
   public long getElemLength() {
      return elemLength;
   }

   /**
    * @param elemLength the elemLength to set
    */
   public void setElemLength(long elemLength) {
      this.elemLength = elemLength;
   }

   /**
    * @return the type
    */
   public NSNChannelType getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(NSNChannelType type) {
      this.type = type;
   }

   /**
    * @return the label
    */
   public String getLabel() {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label) {
      this.label = label;
   }

   /**
    * @return the itemCount
    */
   public long getItemCount() {
      return itemCount;
   }

   /**
    * @param itemCount the itemCount to set
    */
   public void setItemCount(long itemCount) {
      this.itemCount = itemCount;
   }

   /**
    * @return the dataPosition
    */
   public long getDataPosition() {
      return dataPosition;
   }

   /**
    * @param dataPosition the dataPosition to set
    */
   public void setDataPosition(long dataPosition) {
      this.dataPosition = dataPosition;
   }

   /**
    * @return the filePath
    */
   public String getFilePath() {
      return filePath;
   }

   /**
    * @param filePath the filePath to set
    */
   public void setFilePath(String filePath) {
      this.filePath = filePath;
   }
}