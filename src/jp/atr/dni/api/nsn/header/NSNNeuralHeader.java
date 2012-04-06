package jp.atr.dni.api.nsn.header;

import java.util.ArrayList;

import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 *
 * @author kharada
 * @version 2011/01/13
 */
public class NSNNeuralHeader extends NSNChannelHeader {

   private long sourceEntityID;

   private long sourceUnitID;

   private String probeInfo;

   /**
    * @param tag
    * @param entityInfo
    */
   public NSNNeuralHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         long dataPosition, String filePath) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);
   }

   /**
    * @param tag
    * @param entityInfo
    * @param sourceEntityID
    * @param sourceUnitID
    * @param probeInfo
    */
   public NSNNeuralHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         long dataPosition, String filePath, long sourceEntityID, long sourceUnitID, String probeInfo) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);

      if (probeInfo == null) {
         probeInfo = "";
      }

      this.sourceEntityID = sourceEntityID;
      this.sourceUnitID = sourceUnitID;
      this.probeInfo = probeInfo.trim();
   }

   /**
    * @return the sourceEntityID
    */
   public long getSourceEntityID() {
      return sourceEntityID;
   }

   /**
    * @param sourceEntityID the sourceEntityID to set
    */
   public void setSourceEntityID(long sourceEntityID) {
      this.sourceEntityID = sourceEntityID;
   }

   /**
    * @return the sourceUnitID
    */
   public long getSourceUnitID() {
      return sourceUnitID;
   }

   /**
    * @param sourceUnitID the sourceUnitID to set
    */
   public void setSourceUnitID(long sourceUnitID) {
      this.sourceUnitID = sourceUnitID;
   }

   /**
    * @return the probeInfo
    */
   public String getProbeInfo() {
      return probeInfo;
   }

   /**
    * @param probeInfo the probeInfo to set
    */
   public void setProbeInfo(String probeInfo) {
      this.probeInfo = probeInfo;
   }
}
