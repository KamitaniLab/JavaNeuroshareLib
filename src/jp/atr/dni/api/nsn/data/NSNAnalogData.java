package jp.atr.dni.api.nsn.data;

import java.io.RandomAccessFile;
import java.util.ArrayList;

import jp.atr.dni.api.APIData;
import jp.atr.dni.api.APIList;
import jp.atr.dni.api.nsn.header.NSNAnalogHeader;
import jp.atr.dni.api.nsn.utils.NSNConstants;
import jp.atr.dni.api.utils.ReaderUtils;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public final class NSNAnalogData implements APIData {

   private NSNAnalogHeader nsnEntity;

   private ArrayList<Double> timeStamps;

   private ArrayList<APIList<Double>> values;

   /**
    * Constructor. 
    * 
    * @param nsnEntity - the neuroshare entity underlying this channel's data
    */
   public NSNAnalogData(NSNAnalogHeader nsnEntity) {
      this.nsnEntity = nsnEntity;
      initializeData();
   }

   /**
    * This method initializes the data for the AnalogChannel. It creates a new APIList and data provider for each segment of data.
    */
   private void initializeData() {
      timeStamps = new ArrayList<Double>();
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

            double timeStamp = ReaderUtils.readDouble(file);
            timeStamps.add(timeStamp);

            long dataCount = ReaderUtils.readUnsignedInt(file);

            APIList<Double> vals = new APIList<Double>(new NSNAnalogDataProvider(segmentNum, nsnEntity));
            values.add(vals);

            // Skip all the data for now. Read it in through the data provider only as-needed.
            file.skipBytes(((Long) (NSNConstants.DOUBLE_BYTE_SIZE * dataCount)).intValue());
            count += dataCount;
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
