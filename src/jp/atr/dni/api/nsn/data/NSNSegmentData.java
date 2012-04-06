package jp.atr.dni.api.nsn.data;

import java.io.RandomAccessFile;
import java.util.ArrayList;

import jp.atr.dni.api.APIData;
import jp.atr.dni.api.APIList;
import jp.atr.dni.api.nsn.header.NSNSegmentHeader;
import jp.atr.dni.api.nsn.utils.NSNConstants;
import jp.atr.dni.api.utils.ReaderUtils;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/22
 */
public final class NSNSegmentData implements APIData {

   private NSNSegmentHeader nsnEntity;

   private ArrayList<Double> timeStamps;

   private ArrayList<Integer> unitIDs;

   private ArrayList<APIList<Double>> values;

   public NSNSegmentData(NSNSegmentHeader nsnEntity) {
      this.nsnEntity = nsnEntity;
      initializeData();
   }

   private void initializeData() {
      timeStamps = new ArrayList<Double>();
      unitIDs = new ArrayList<Integer>();
      values = new ArrayList<APIList<Double>>();

      int count = 0;

      String filePath = nsnEntity.getFilePath();
      long byteOffset = nsnEntity.getDataPosition();
      long itemCount = nsnEntity.getItemCount();

      try {
         RandomAccessFile file = new RandomAccessFile(filePath, "r");
         file.seek(byteOffset);

         int segmentNum = 0;
         while (count < itemCount) {

            long segmentFilePointer = file.getFilePointer();

            long dataCount = ReaderUtils.readUnsignedInt(file);

            double timeStamp = ReaderUtils.readDouble(file);
            timeStamps.add(timeStamp);

            long unitID = ReaderUtils.readUnsignedInt(file);
            unitIDs.add(((Long) unitID).intValue());

            APIList<Double> vals = new APIList<Double>(new NSNSegmentDataProvider(segmentNum,
                  segmentFilePointer, nsnEntity));
            values.add(vals);

            // Skip all the data for now. Read it in through the data provider only as-needed.
            file.skipBytes(((Long) (NSNConstants.DOUBLE_BYTE_SIZE * dataCount)).intValue());
            count++;
            segmentNum++;
         }
         file.close();
      } catch (Exception err) {
         err.printStackTrace();
      }

   }

   /**
    * @return the timeStamps
    */
   public ArrayList<Double> getTimeStamps() {
      return timeStamps;
   }

   /**
    * @param timeStamps the timeStamps to set
    */
   public void setTimeStamps(ArrayList<Double> timeStamps) {
      this.timeStamps = timeStamps;
   }

   /**
    * @return the unitIDs
    */
   public ArrayList<Integer> getUnitIDs() {
      return unitIDs;
   }

   /**
    * @param unitIDs the unitIDs to set
    */
   public void setUnitIDs(ArrayList<Integer> unitIDs) {
      this.unitIDs = unitIDs;
   }

   /**
    * @return the values
    */
   public ArrayList<APIList<Double>> getValues() {
      return values;
   }

   /**
    * @param values the values to set
    */
   public void setValues(ArrayList<APIList<Double>> values) {
      this.values = values;
   }
}
