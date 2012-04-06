/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/15
 */
package jp.atr.dni.api.nsn.data;

import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.enums.NSNChannelTypeCode;

/**
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/17
 */
public class NSNChannelInfo {
   private Integer channelNumber;

   private String entityLabel;

   private NSNChannelType entityType;

   private String channelType;

   private NSNChannelTypeCode channelTypeCode;

   private String comment;

   /**
    * @return the channelNumber
    */
   public Integer getChannelNumber() {
      return channelNumber;
   }

   /**
    * @param channelNumber the channelNumber to set
    */
   public void setChannelNumber(Integer channelNumber) {
      this.channelNumber = channelNumber;
   }

   /**
    * @return the entityLabel
    */
   public String getEntityLabel() {
      return entityLabel;
   }

   /**
    * @param entityLabel the entityLabel to set
    */
   public void setEntityLabel(String entityLabel) {
      this.entityLabel = entityLabel;
   }

   /**
    * @return the entityType
    */
   public NSNChannelType getEntityType() {
      return entityType;
   }

   /**
    * @param entityType the entityType to set
    */
   public void setEntityType(NSNChannelType entityType) {
      this.entityType = entityType;
   }

   /**
    * @return the channelType
    */
   public String getChannelType() {
      return channelType;
   }

   /**
    * @param channelType the channelType to set
    */
   public void setChannelType(String channelType) {
      this.channelType = channelType;
   }

   /**
    * @return the channelTypeCode
    */
   public NSNChannelTypeCode getChannelTypeCode() {
      return channelTypeCode;
   }

   /**
    * @param channelTypeCode the channelTypeCode to set
    */
   public void setChannelTypeCode(NSNChannelTypeCode channelTypeCode) {
      this.channelTypeCode = channelTypeCode;
   }

   /**
    * @return the comment
    */
   public String getComment() {
      return comment;
   }

   /**
    * @param comment the comment to set
    */
   public void setComment(String comment) {
      this.comment = comment;
   }

   /**
    * This method creates an xml representation of an object of this class, which can then be parsed by the InfoFileXMLParser.
    * 
    * @return - xml representation of this NSNChannelInfo object
    */
   public String toXML() {
      String xmlRepresentation = "<ch num=\"" + channelNumber + "\"><entityLabel>" + entityLabel
            + "</entityLabel><entityType>" + entityType + "</entityType>";

      if (channelTypeCode != null) {
         xmlRepresentation += "<chTypeCode>" + channelTypeCode.toInt() + "</chTypeCode>";
      }

      if (channelType != null) {
         xmlRepresentation += "<chType>" + channelType + "</chType>";
         if (channelTypeCode == null) {
            xmlRepresentation += "<chTypeCode>" + NSNChannelTypeCode.fromString(channelType).toInt()
                  + "</chTypeCode>";
         }
      }
      if (comment != null) {
         xmlRepresentation += "<comment>" + comment + "</comment>";
      }

      xmlRepresentation += "</ch>";

      return xmlRepresentation;
   }

   @Override
   public String toString() {
      // return "channelNumber: " + channelNumber + "\nentityLabel: " + entityLabel +
      // "\nentityType: "
      // + entityType + "\nchannelType: " + channelType + "\nchannelTypeCode: " + channelTypeCode
      // + "\ncomment: " + comment;
      return toXML();
   }
}
