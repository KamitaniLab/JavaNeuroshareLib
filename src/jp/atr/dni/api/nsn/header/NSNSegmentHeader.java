/**
 * 
 */
package jp.atr.dni.api.nsn.header;

import java.util.ArrayList;

import jp.atr.dni.api.nsn.data.NSNSegmentData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 *
 * @author kharada
 * @version 2011/01/13
 */
public class NSNSegmentHeader extends NSNChannelHeader {

   private long sourceCount;

   private long minSampleCount;

   private long maxSampleCount;

   private double sampleRate;

   private String units;

   private ArrayList<NSNSegmentSourceHeader> segSourceInfos;

   /**
    * @param tag
    * @param entityInfo
    */
   public NSNSegmentHeader(long elemLength, NSNChannelType type, String label, long itemCount, long dataPosition,
         String filePath) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);
   }

   /**
    * @param tag
    * @param entityInfo
    * @param sourceCount
    * @param minSampleCount
    * @param maxSampleCount
    * @param sampleRate
    * @param units
    */
   public NSNSegmentHeader(long elemLength, NSNChannelType type, String label, long itemCount, long dataPosition,
         String filePath, long sourceCount, long minSampleCount, long maxSampleCount, double sampleRate,
         String units) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);

      if (units == null) {
         units = "";
      }

      this.sourceCount = sourceCount;
      this.minSampleCount = minSampleCount;
      this.maxSampleCount = maxSampleCount;
      this.sampleRate = sampleRate;
      this.units = units.trim();
   }

   /**
    * @return the sourceCount
    */
   public long getSourceCount() {
      return sourceCount;
   }

   /**
    * @param sourceCount the sourceCount to set
    */
   public void setSourceCount(long sourceCount) {
      this.sourceCount = sourceCount;
   }

   /**
    * @return the minSampleCount
    */
   public long getMinSampleCount() {
      return minSampleCount;
   }

   /**
    * @param minSampleCount the minSampleCount to set
    */
   public void setMinSampleCount(long minSampleCount) {
      this.minSampleCount = minSampleCount;
   }

   /**
    * @return the maxSampleCount
    */
   public long getMaxSampleCount() {
      return maxSampleCount;
   }

   /**
    * @param maxSampleCount the maxSampleCount to set
    */
   public void setMaxSampleCount(long maxSampleCount) {
      this.maxSampleCount = maxSampleCount;
   }

   /**
    * @return the sampleRate
    */
   public double getSampleRate() {
      return sampleRate;
   }

   /**
    * @param sampleRate the sampleRate to set
    */
   public void setSampleRate(double sampleRate) {
      this.sampleRate = sampleRate;
   }

   /**
    * @return the units
    */
   public String getUnits() {
      return units;
   }

   /**
    * @param units the units to set
    */
   public void setUnits(String units) {
      this.units = units;
   }

}
