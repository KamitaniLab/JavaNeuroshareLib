package jp.atr.dni.api.nsn.header;

import jp.atr.dni.api.nsn.enums.NSNChannelType;

/**
 *
 * @author Keiji Harada
 * </br>ATR Intl. Computational Neuroscience Labs, Decoding Group
 * 
 * @version 2011/04/22
 */
public class NSNAnalogHeader extends NSNChannelHeader {

   private double sampleRate;

   private double minVal;

   private double maxVal;

   private String units;

   private double resolution;

   private double locationX;

   private double locationY;

   private double locationZ;

   private double locationUser;

   private double highFreqCorner;

   private long highFreqOrder;

   private String highFilterType;

   private double lowFreqCorner;

   private long lowFreqOrder;

   private String lowFilterType;

   private String probeInfo;

   /**
    * @param tag
    * @param entityInfo
    */
   public NSNAnalogHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         long dataPosition, String filePath) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);
   }

   /**
    * @param tag
    * @param entityInfo
    * @param sampleRate
    * @param minVal
    * @param maxVal
    * @param units
    * @param resolution
    * @param locationX
    * @param locationY
    * @param locationZ
    * @param locationUser
    * @param highFreqCorner
    * @param highFreqOrder
    * @param highFilterType
    * @param lowFreqCorner
    * @param lowFreqOrder
    * @param lowFiltertype
    * @param probeInfo
    */
   public NSNAnalogHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         long dataPosition, String filePath, double sampleRate, double minVal, double maxVal, String units,
         double resolution, double locationX, double locationY, double locationZ, double locationUser,
         double highFreqCorner, long highFreqOrder, String highFilterType, double lowFreqCorner,
         long lowFreqOrder, String lowFiltertype, String probeInfo) {
      super(elemLength, type, label, itemCount, dataPosition, filePath);

      if (units == null) {
         units = "";
      }

      if (highFilterType == null) {
         highFilterType = "";
      }

      if (lowFiltertype == null) {
         lowFiltertype = "";
      }

      if (probeInfo == null) {
         probeInfo = "";
      }

      this.sampleRate = sampleRate;
      this.minVal = minVal;
      this.maxVal = maxVal;
      this.units = units.trim();
      this.resolution = resolution;
      this.locationX = locationX;
      this.locationY = locationY;
      this.locationZ = locationZ;
      this.locationUser = locationUser;
      this.highFreqCorner = highFreqCorner;
      this.highFreqOrder = highFreqOrder;
      this.highFilterType = highFilterType.trim();
      this.lowFreqCorner = lowFreqCorner;
      this.lowFreqOrder = lowFreqOrder;
      this.lowFilterType = lowFiltertype.trim();
      this.probeInfo = probeInfo.trim();
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
    * @return the minVal
    */
   public double getMinVal() {
      return minVal;
   }

   /**
    * @param minVal the minVal to set
    */
   public void setMinVal(double minVal) {
      this.minVal = minVal;
   }

   /**
    * @return the maxVal
    */
   public double getMaxVal() {
      return maxVal;
   }

   /**
    * @param maxVal the maxVal to set
    */
   public void setMaxVal(double maxVal) {
      this.maxVal = maxVal;
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

   /**
    * @return the resolution
    */
   public double getResolution() {
      return resolution;
   }

   /**
    * @param resolution the resolution to set
    */
   public void setResolution(double resolution) {
      this.resolution = resolution;
   }

   /**
    * @return the locationX
    */
   public double getLocationX() {
      return locationX;
   }

   /**
    * @param locationX the locationX to set
    */
   public void setLocationX(double locationX) {
      this.locationX = locationX;
   }

   /**
    * @return the locationY
    */
   public double getLocationY() {
      return locationY;
   }

   /**
    * @param locationY the locationY to set
    */
   public void setLocationY(double locationY) {
      this.locationY = locationY;
   }

   /**
    * @return the locationZ
    */
   public double getLocationZ() {
      return locationZ;
   }

   /**
    * @param locationZ the locationZ to set
    */
   public void setLocationZ(double locationZ) {
      this.locationZ = locationZ;
   }

   /**
    * @return the locationUser
    */
   public double getLocationUser() {
      return locationUser;
   }

   /**
    * @param locationUser the locationUser to set
    */
   public void setLocationUser(double locationUser) {
      this.locationUser = locationUser;
   }

   /**
    * @return the highFreqCorner
    */
   public double getHighFreqCorner() {
      return highFreqCorner;
   }

   /**
    * @param highFreqCorner the highFreqCorner to set
    */
   public void setHighFreqCorner(double highFreqCorner) {
      this.highFreqCorner = highFreqCorner;
   }

   /**
    * @return the highFreqOrder
    */
   public long getHighFreqOrder() {
      return highFreqOrder;
   }

   /**
    * @param highFreqOrder the highFreqOrder to set
    */
   public void setHighFreqOrder(long highFreqOrder) {
      this.highFreqOrder = highFreqOrder;
   }

   /**
    * @return the highFilterType
    */
   public String getHighFilterType() {
      return highFilterType;
   }

   /**
    * @param highFilterType the highFilterType to set
    */
   public void setHighFilterType(String highFilterType) {
      this.highFilterType = highFilterType;
   }

   /**
    * @return the lowFreqCorner
    */
   public double getLowFreqCorner() {
      return lowFreqCorner;
   }

   /**
    * @param lowFreqCorner the lowFreqCorner to set
    */
   public void setLowFreqCorner(double lowFreqCorner) {
      this.lowFreqCorner = lowFreqCorner;
   }

   /**
    * @return the lowFreqOrder
    */
   public long getLowFreqOrder() {
      return lowFreqOrder;
   }

   /**
    * @param lowFreqOrder the lowFreqOrder to set
    */
   public void setLowFreqOrder(long lowFreqOrder) {
      this.lowFreqOrder = lowFreqOrder;
   }

   /**
    * @return the lowFilterType
    */
   public String getLowFilterType() {
      return lowFilterType;
   }

   /**
    * @param lowFilterType the lowFilterType to set
    */
   public void setLowFilterType(String lowFilterType) {
      this.lowFilterType = lowFilterType;
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
