package jp.atr.dni.api.nsn.channels;

import java.util.ArrayList;
import java.util.List;

import jp.atr.dni.api.nsn.data.NSNSegmentData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.header.NSNSegmentHeader;
import jp.atr.dni.api.nsn.header.NSNSegmentSourceHeader;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/20
 */
public final class NSNSegmentChannel implements NSNChannel<NSNSegmentData> {

   private int id;

   private NSNSegmentHeader header;

   private NSNSegmentData data;

   private List<NSNSegmentSourceHeader> segmentSrcHeaders = new ArrayList<NSNSegmentSourceHeader>();

   public NSNSegmentChannel(int id, NSNSegmentHeader nsnHeader) {
      this.id = id;
      this.header = nsnHeader;
      this.data = null;

      // if (nsnHeader.getSourceCount() > 0) {
      // for (int i = 0; i < segmentSources.size(); i++) {
      // segmentSources.add(segmentSources.get(i));
      // }
      // }
   }

   private void initialize() {
      this.data = new NSNSegmentData(header);
   }

   @Override
   public int getId() {
      return id;
   }

   @Override
   public NSNChannelType getType() {
      return NSNChannelType.SEGMENT;
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

   public void setURI(String uri) {
      header.setFilePath(uri);
   }

   @Override
   public NSNSegmentData getData() {
      if (this.data == null) {
         initialize();
      }
      return data;
   }

   /**
    * @return the sourceCount
    */
   public long getSourceCount() {
      return header.getSourceCount();
   }

   /**
    * @param sourceCount the sourceCount to set
    */
   public void setSourceCount(long sourceCount) {
      header.setSourceCount(sourceCount);
   }

   /**
    * @return the minSampleCount
    */
   public long getMinSampleCount() {
      return header.getMinSampleCount();
   }

   /**
    * @param minSampleCount the minSampleCount to set
    */
   public void setMinSampleCount(long minSampleCount) {
      header.setMinSampleCount(minSampleCount);
   }

   /**
    * @return the maxSampleCount
    */
   public long getMaxSampleCount() {
      return header.getMaxSampleCount();
   }

   /**
    * @param maxSampleCount the maxSampleCount to set
    */
   public void setMaxSampleCount(long maxSampleCount) {
      header.setMaxSampleCount(maxSampleCount);
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
   public void setSamplingRate(double samplingRate) {
      header.setSampleRate(samplingRate);
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

   public synchronized NSNSegmentSourceHeader getSegmentSourceHeader(int ndx) {
      if (ndx >= 0 && ndx < segmentSrcHeaders.size()) {
         return segmentSrcHeaders.get(ndx);
      } else {
         return null;
      }
   }

   public synchronized void setSegmentSourceHeader(int ndx, NSNSegmentSourceHeader segSrcHeader) {
      if (ndx >= 0 && ndx < segmentSrcHeaders.size()) {
         segmentSrcHeaders.set(ndx, segSrcHeader);
      }
   }

   /**
    * @param segmentSources the segmentSources to set
    */
   public void setSegmentSources(List<NSNSegmentSourceHeader> segmentSources) {
      this.segmentSrcHeaders = segmentSources;
   }

   public synchronized void removeSegmentSource(int ndx) {
      if (ndx >= 0 && ndx < segmentSrcHeaders.size()) {
         segmentSrcHeaders.remove(ndx);
      }
   }

   public synchronized int numSegSources() {
      return segmentSrcHeaders.size();
   }

   /**
    * @return the header
    */
   public NSNSegmentHeader getHeader() {
      return header;
   }

   /**
    * @param header the header to set
    */
   public void setHeader(NSNSegmentHeader header) {
      this.header = header;
   }

   @Override
   public String toString() {
      return getLabel();
   }
}
