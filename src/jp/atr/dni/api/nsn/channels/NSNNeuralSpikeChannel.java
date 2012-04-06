package jp.atr.dni.api.nsn.channels;

import jp.atr.dni.api.nsn.data.NSNNeuralSpikeData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.header.NSNNeuralHeader;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/19
 */
public final class NSNNeuralSpikeChannel implements NSNChannel<NSNNeuralSpikeData> {

   private int id;

   private NSNNeuralHeader header;

   private NSNNeuralSpikeData data;

   public NSNNeuralSpikeChannel(int id, NSNNeuralHeader nsnEntity) {
      this.id = id;
      this.header = nsnEntity;
      this.data = null;
   }

   private void initialize() {
      this.data = new NSNNeuralSpikeData(header);
   }

   @Override
   public int getId() {
      return id;
   }

   @Override
   public NSNChannelType getType() {
      return NSNChannelType.NEURAL_SPIKE;
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
   public NSNNeuralSpikeData getData() {
      if (this.data == null) {
         initialize();
      }
      return data;
   }

   /**
    * @return the sourceEntityID
    */
   public long getSourceEntityID() {
      return header.getSourceEntityID();
   }

   /**
    * @param sourceEntityID the sourceEntityID to set
    */
   public void setSourceEntityID(long sourceEntityID) {
      header.setSourceEntityID(sourceEntityID);
   }

   /**
    * @return the sourceUnitID
    */
   public long getSourceUnitID() {
      return header.getSourceUnitID();
   }

   /**
    * @param sourceUnitID the sourceUnitID to set
    */
   public void setSourceUnitID(long sourceUnitID) {
      header.setSourceUnitID(sourceUnitID);
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
   public NSNNeuralHeader getHeader() {
      return header;
   }

   /**
    * @param header the header to set
    */
   public void setHeader(NSNNeuralHeader header) {
      this.header = header;
   }

   @Override
   public String toString() {
      return getLabel();
   }
}
