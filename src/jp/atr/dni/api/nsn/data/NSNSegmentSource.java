package jp.atr.dni.api.nsn.data;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public final class NSNSegmentSource {

   private double resolution;

   private double minVal;

   private double maxVal;

   private double subSampleShift;

   private double locationX;

   private double locationY;

   private double locationZ;

   private double locationUser;

   private double highFreqCorner;

   private int highFreqOrder;

   private String highFilterType;

   private double lowFreqCorner;

   private int lowFreqOrder;

   private String lowFilterType;

   private String probeInfo;

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
    * @return the subSampleShift
    */
   public double getSubSampleShift() {
      return subSampleShift;
   }

   /**
    * @param subSampleShift the subSampleShift to set
    */
   public void setSubSampleShift(double subSampleShift) {
      this.subSampleShift = subSampleShift;
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
   public int getHighFreqOrder() {
      return highFreqOrder;
   }

   /**
    * @param highFreqOrder the highFreqOrder to set
    */
   public void setHighFreqOrder(int highFreqOrder) {
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
   public int getLowFreqOrder() {
      return lowFreqOrder;
   }

   /**
    * @param lowFreqOrder the lowFreqOrder to set
    */
   public void setLowFreqOrder(int lowFreqOrder) {
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
