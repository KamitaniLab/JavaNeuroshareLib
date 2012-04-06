package jp.atr.dni.api.nsn.channels;

import jp.atr.dni.api.nsn.data.NSNEventData;
import jp.atr.dni.api.nsn.enums.NSNEventType;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.header.NSNEventHeader;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/19
 */
public final class NSNEventChannel implements NSNChannel<NSNEventData> {

   private int id;

   private NSNEventHeader header;

   private NSNEventData data;

   public NSNEventChannel(int id, NSNEventHeader header) {
      this.id = id;
      this.header = header;
      this.data = null;
   }

   private void initialize() {
      this.data = new NSNEventData(header);
   }

   @Override
   public int getId() {
      return id;
   }

   @Override
   public NSNChannelType getType() {
      return NSNChannelType.EVENT;
   }

   public void setType(NSNChannelType type) {
   }

   @Override
   public String getLabel() {
      return header.getLabel();
   }

   @Override
   public void setLabel(String label) {
      header.setLabel(label);
   }

   @Override
   public long getItemCount() {
      return header.getItemCount();
   }

   @Override
   public void setItemCount(long itemCount) {
      header.setItemCount(itemCount);
   }

   @Override
   public String getURI() {
      return header.getFilePath();
   }

   public void setURI(String filePath) {
   }

   @Override
   public NSNEventData getData() {
      if (this.data == null) {
         initialize();
      }
      return data;
   }

   /**
    * @return the eventType
    */
   public NSNEventType getEventType() {
      return header.getEventType();
   }

   /**
    * @param eventType the eventType to set
    */
   public void setEventType(NSNEventType eventType) {
      header.setEventType(eventType);
   }

   /**
    * @return the minDataLength
    */
   public long getMinDataLength() {
      return header.getMinDataLength();
   }

   /**
    * @param minDataLength the minDataLength to set
    */
   public void setMinDataLength(long minDataLength) {
      header.setMinDataLength(minDataLength);
   }

   /**
    * @return the maxDataLength
    */
   public long getMaxDataLength() {
      return header.getMaxDataLength();
   }

   /**
    * @param maxDataLength the maxDataLength to set
    */
   public void setMaxDataLength(long maxDataLength) {
      header.setMaxDataLength(maxDataLength);
   }

   /**
    * @return the csvDesc
    */
   public String getCsvDesc() {
      return header.getCsvDesc();
   }

   /**
    * @param csvDesc the csvDesc to set
    */
   public void setCsvDesc(String csvDesc) {
      header.setCsvDesc(csvDesc);
   }

   /**
    * @return the dataPosition
    */
   public long getDataPosition() {
      return header.getDataPosition();
   }

   /**
    * @param dataPosition the dataPosition to set
    */
   public void setDataPosition(long dataPosition) {
      header.setDataPosition(dataPosition);
   }

   /**
    * @return the header
    */
   public NSNEventHeader getHeader() {
      return header;
   }

   /**
    * @param header the header to set
    */
   public void setHeader(NSNEventHeader header) {
      this.header = header;
   }

   @Override
   public String toString() {
      return getLabel();
   }
}
