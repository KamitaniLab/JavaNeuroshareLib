package jp.atr.dni.api.nsn.channels;

import jp.atr.dni.api.nsn.data.NSNAnalogData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.header.NSNAnalogHeader;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public final class NSNAnalogChannel implements NSNChannel<NSNAnalogData> {

   private int id;

   private final NSNAnalogHeader header;

   private NSNAnalogData data;

   public NSNAnalogChannel(int id, NSNAnalogHeader nsnEntity) {
      this.id = id;
      this.header = nsnEntity;
      this.data = null;
   }

   private void initialize() {
      this.data = new NSNAnalogData(header);
   }

   @Override
   public NSNAnalogData getData() {
      if (this.data == null) {
         initialize();
      }
      return data;
   }

   /**
    * @return the type
    */
   public NSNChannelType getType() {
      return NSNChannelType.ANALOG;
   }

   /**
    * @return the byteLength
    */
   public long getByteLength() {
      return header.getElemLength();
   }

   /**
    * @return the byteLength
    */
   public long getByteLength(int hashcode) {
      return header.getElemLength();
   }

   /**
    * @param byteLength the byteLength to set
    */
   public void setByteLength(long byteLength) {
      header.setElemLength(byteLength);
   }

   /**
    * 
    * @return the entity's label
    */
   public String getLabel() {
      return header.getLabel();
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label) {
      header.setLabel(label);
   }

   /**
    * @return the itemCount
    */
   public long getItemCount() {
      return header.getItemCount();
   }

   /**
    * @param itemCount the itemCount to set
    */
   public void setItemCount(long itemCount) {
      header.setItemCount(itemCount);
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
    * @return the filePath
    */
   public String getURI() {
      return header.getFilePath();
   }

   /**
    * @param filePath the filePath to set
    */
   public void setURI(String filePath) {
   }

   /**
    * @return the sampleRate
    */
   public double getSamplingRate() {
      return header.getSampleRate();
   }

   /**
    * @param sampleRate the sampleRate to set
    */
   public void setSamplingRate(double sampleRate) {
      header.setSampleRate(sampleRate);
   }

   /**
    * @return the minVal
    */
   public double getMinVal() {
      return header.getMinVal();
   }

   /**
    * @param minVal the minVal to set
    */
   public void setMinVal(double minVal) {
      header.setMinVal(minVal);
   }

   /**
    * @return the maxVal
    */
   public double getMaxVal() {
      return header.getMaxVal();
   }

   /**
    * @param maxVal the maxVal to set
    */
   public void setMaxVal(double maxVal) {
      header.setMaxVal(maxVal);
   }

   /**
    * @return the units
    */
   public String getUnits() {
      return header.getUnits();
   }

   /**
    * @param units the units to set
    */
   public void setUnits(String units) {
      header.setUnits(units);
   }

   /**
    * @return the resolution
    */
   public double getResolution() {
      return header.getResolution();
   }

   /**
    * @param resolution the resolution to set
    */
   public void setResolution(double resolution) {
      header.setResolution(resolution);
   }

   /**
    * @return the locationX
    */
   public double getLocationX() {
      return header.getLocationX();
   }

   /**
    * @param locationX the locationX to set
    */
   public void setLocationX(double locationX) {
      header.setLocationX(locationX);
   }

   /**
    * @return the locationY
    */
   public double getLocationY() {
      return header.getLocationY();
   }

   /**
    * @param locationY the locationY to set
    */
   public void setLocationY(double locationY) {
      header.setLocationY(locationY);
   }

   /**
    * @return the locationZ
    */
   public double getLocationZ() {
      return header.getLocationZ();
   }

   /**
    * @param locationZ the locationZ to set
    */
   public void setLocationZ(double locationZ) {
      header.setLocationZ(locationZ);
   }

   /**
    * @return the locationUser
    */
   public double getLocationUser() {
      return header.getLocationUser();
   }

   /**
    * @param locationUser the locationUser to set
    */
   public void setLocationUser(double locationUser) {
      header.setLocationUser(locationUser);
   }

   /**
    * @return the highFreqCorner
    */
   public double getHighFreqCorner() {
      return header.getHighFreqCorner();
   }

   /**
    * @param highFreqCorner the highFreqCorner to set
    */
   public void setHighFreqCorner(double highFreqCorner) {
      header.setHighFreqCorner(highFreqCorner);
   }

   /**
    * @return the highFreqOrder
    */
   public long getHighFreqOrder() {
      return header.getHighFreqOrder();
   }

   /**
    * @param highFreqOrder the highFreqOrder to set
    */
   public void setHighFreqOrder(long highFreqOrder) {
      header.setHighFreqOrder(highFreqOrder);
   }

   /**
    * @return the highFilterType
    */
   public String getHighFilterType() {
      return header.getHighFilterType();
   }

   /**
    * @param highFilterType the highFilterType to set
    */
   public void setHighFilterType(String highFilterType) {
      header.setHighFilterType(highFilterType);
   }

   /**
    * @return the lowFreqCorner
    */
   public double getLowFreqCorner() {
      return header.getLowFreqCorner();
   }

   /**
    * @param lowFreqCorner the lowFreqCorner to set
    */
   public void setLowFreqCorner(double lowFreqCorner) {
      header.setLowFreqCorner(lowFreqCorner);
   }

   /**
    * @return the lowFreqOrder
    */
   public long getLowFreqOrder() {
      return header.getLowFreqOrder();
   }

   /**
    * @param lowFreqOrder the lowFreqOrder to set
    */
   public void setLowFreqOrder(long lowFreqOrder) {
      header.setLowFreqOrder(lowFreqOrder);
   }

   /**
    * @return the lowFilterType
    */
   public String getLowFilterType() {
      return header.getLowFilterType();
   }

   /**
    * @param lowFilterType the lowFilterType to set
    */
   public void setLowFilterType(String lowFilterType) {
      header.setLowFilterType(lowFilterType);
   }

   /**
    * @return the probeInfo
    */
   public String getProbeInfo() {
      return header.getProbeInfo();
   }

   /**
    * @param probeInfo the probeInfo to set
    */
   public void setProbeInfo(String probeInfo) {
      header.setProbeInfo(probeInfo);
   }

   /**
    * @return the header
    */
   public NSNAnalogHeader getHeader() {
      return header;
   }

   @Override
   public int getId() {
      return id;
   }

   @Override
   public String toString() {
      return getLabel();
   }
}
