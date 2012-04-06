/**
 * 
 */
package jp.atr.dni.api.nsn.header;

import java.util.ArrayList;

import jp.atr.dni.api.nsn.data.NSNEventData;
import jp.atr.dni.api.nsn.enums.NSNEventType;
import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 *
 * @author kharada
 * @version 2011/01/13
 */
public class NSNEventHeader extends NSNChannelHeader {

   /**
    * A type code describing the type of event data associated with each indexed entry. The
    * following information types are allowed: EVENT_TEXT 0 (text string), EVENT_CSV 1 (comma
    * separated values), EVENT_BYTE 2 (8-bit binary values), EVENT_WORD 3 (16-bit binary
    * values), and EVENT_DWORD 4 (32-bit binary values).
    */
   private NSNEventType eventType;

   /**
    * Minimum number of bytes that can be returned for an Event.
    */
   private long minDataLength;

   /**
    * Maximum number of bytes that can be returned for an Event.
    */
   private long maxDataLength;

   /**
    * Provides descriptions of the data fields for CSV Event Entities.
    */
   private String csvDesc;

   private ArrayList<NSNEventData> data;

   /**
    * @param tag
    * @param entityInfo
    */
   public NSNEventHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         long dataPosition, String filePath) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);
   }

   /**
    * @param tag
    * @param entityInfo
    * @param eventType
    * @param minDataLength
    * @param maxDataLength
    * @param csvDesc
    */
   public NSNEventHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         long dataPosition, String filePath, NSNEventType eventType, long minDataLength, long maxDataLength,
         String csvDesc) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);

      if (csvDesc == null) {
         csvDesc = "";
      }

      this.eventType = eventType;
      this.minDataLength = minDataLength;
      this.maxDataLength = maxDataLength;
      this.csvDesc = csvDesc.trim();
   }

   /**
    * @return the eventType
    */
   public NSNEventType getEventType() {
      return eventType;
   }

   /**
    * @param eventType the eventType to set
    */
   public void setEventType(NSNEventType eventType) {
      this.eventType = eventType;
   }

   /**
    * @return the minDataLength
    */
   public long getMinDataLength() {
      return minDataLength;
   }

   /**
    * @param minDataLength the minDataLength to set
    */
   public void setMinDataLength(long minDataLength) {
      this.minDataLength = minDataLength;
   }

   /**
    * @return the maxDataLength
    */
   public long getMaxDataLength() {
      return maxDataLength;
   }

   /**
    * @param maxDataLength the maxDataLength to set
    */
   public void setMaxDataLength(long maxDataLength) {
      this.maxDataLength = maxDataLength;
   }

   /**
    * @return the csvDesc
    */
   public String getCsvDesc() {
      return csvDesc;
   }

   /**
    * @param csvDesc the csvDesc to set
    */
   public void setCsvDesc(String csvDesc) {
      this.csvDesc = csvDesc;
   }

   /**
    * @return the data
    */
   public ArrayList<NSNEventData> getData() {
      return data;
   }

   /**
    * @param data the data to set
    */
   public void setData(ArrayList<NSNEventData> data) {
      this.data = data;
   }

   /**
   * @return the label of entity type.
   */
   public String getEventTypeLabel() {
      String rtnVal = "";
      switch (this.eventType) {
      case TEXT:
         rtnVal = "Text";
         break;
      case CSV:
         rtnVal = "Csv";
         break;
      case BYTE:
         rtnVal = "Byte";
         break;
      case WORD:
         rtnVal = "Word";
         break;
      case DWORD:
         rtnVal = "DWord";
         break;
      default:
         rtnVal = "Unknown";
         break;
      }
      return rtnVal;
   }

}
