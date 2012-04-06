package jp.atr.dni.api.nsn.data;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import jp.atr.dni.api.APIDataProvider;
import jp.atr.dni.api.nsn.header.NSNSegmentHeader;
import jp.atr.dni.api.nsn.utils.NSNConstants;
import jp.atr.dni.api.utils.ReaderUtils;

/**
 * The package-level access on this class is intentional.
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/22
 */
final class NSNSegmentDataProvider implements APIDataProvider {

   private String filePath;

   private long byteOffset;

   private int dataCount;

   private int segmentNum;

   private NSNSegmentHeader entity;

   public NSNSegmentDataProvider(int segmentNum, long byteOffset, NSNSegmentHeader nsnEntity) {
      this.entity = nsnEntity;

      this.segmentNum = segmentNum;
      this.filePath = nsnEntity.getFilePath();
      this.byteOffset = nsnEntity.getDataPosition();
      this.dataCount = -1;
   }

   @Override
   public synchronized int size() {
      return dataCount > 0 ? dataCount : getDataCount();
   }

   @Override
   public synchronized List<Double> getData(int from, int to) {
      ArrayList<Double> values = new ArrayList<Double>();
      long dataCountRow = 0;

      long itemCount = entity.getItemCount();

      try {
         RandomAccessFile file = new RandomAccessFile(filePath, "r");
         file.seek(byteOffset);

         for (int count = 0; count <= segmentNum; count++) {
            // dataCountRow
            dataCountRow = ReaderUtils.readUnsignedInt(file);
            file.skipBytes(12);

            // values
            for (int i = 0; i < dataCountRow; i++) {
               double d = ReaderUtils.readDouble(file);
               if (count == segmentNum && i >= from && i <= to) {
                  values.add(d);
               }
            }
         }
         file.close();
      } catch (Exception err) {
         err.printStackTrace();
         return null;
      }
      return values;
   }

   private int getDataCount() {

      dataCount = 0;
      int rowCount = 0;
      long rowSampleCount = 0;

      try {
         RandomAccessFile file = new RandomAccessFile(filePath, "r");
         file.seek(byteOffset);

         while (rowCount <= segmentNum) {

            // dataCount
            rowSampleCount = ReaderUtils.readUnsignedInt(file);
            dataCount += rowSampleCount;

            // skip : timestamp
            ReaderUtils.readDouble(file);

            // skip : unitID
            ReaderUtils.readUnsignedInt(file);

            // skip : values
            file.skipBytes(((Long) (NSNConstants.DOUBLE_BYTE_SIZE * rowSampleCount)).intValue());

            rowCount++;
         }
         dataCount = (int) rowSampleCount;
         file.close();
      } catch (Exception err) {
         err.printStackTrace();
         return -1;
      }
      return dataCount;
   }
   //
   // /**
   // *
   // * @param fileFullPath
   // * @param entityNFO
   // * @param segNFO
   // * @return
   // * @throws IOException
   // */
   // public SegmentData getSegmentData(String fileFullPath, long dataPosition, long sourceCount)
   // throws IOException {
   // RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
   // file.seek(dataPosition);
   //
   // ArrayList<Long> sampleCounts = new ArrayList<Long>();
   // ArrayList<Double> timeStamps = new ArrayList<Double>();
   // ArrayList<Long> unitIDS = new ArrayList<Long>();
   // ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();
   //
   // for (int x = 0; x < sourceCount; x++) {
   // // NOTE: sample count is not defined in the spec because the spec documentation is wrong!
   // long sampleCount = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("sampleCount: " + sampleCount);
   // sampleCounts.add(sampleCount);
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // timeStamps.add(timeStamp);
   //
   // long unitID = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("unitID: " + unitID);
   // unitIDS.add(unitID);
   //
   // vals.add(x, new ArrayList<Double>());
   //
   // // for (int y = 0; y < segNFO.getMaxSampleCount(); y++) {
   // for (int y = 0; y < sampleCount; y++) {
   // vals.get(x).add(ReaderUtils.readDouble(file));
   // }
   // // LOGGER.debug("vals: " + vals.get(x));
   // }
   // file.close();
   //
   // return new SegmentData(sampleCounts, timeStamps, unitIDS, vals);
   // }
   //
   // private SegmentData getSegmentData(EntityInfo entityNFO, SegmentInfo segNFO, RandomAccessFile
   // file)
   // throws IOException {
   //
   // ArrayList<Long> sampleCounts = new ArrayList<Long>();
   // ArrayList<Double> timeStamps = new ArrayList<Double>();
   // ArrayList<Long> unitIDS = new ArrayList<Long>();
   // ArrayList<ArrayList<Double>> vals = new ArrayList<ArrayList<Double>>();
   //
   // for (int x = 0; x < segNFO.getSourceCount(); x++) {
   // // NOTE: sample count is not defined in the spec because the spec documentation is wrong!
   // long sampleCount = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("sampleCount: " + sampleCount);
   // sampleCounts.add(sampleCount);
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // timeStamps.add(timeStamp);
   //
   // long unitID = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("unitID: " + unitID);
   // unitIDS.add(unitID);
   //
   // vals.add(x, new ArrayList<Double>());
   //
   // // for (int y = 0; y < segNFO.getMaxSampleCount(); y++) {
   // for (int y = 0; y < sampleCount; y++) {
   // vals.get(x).add(ReaderUtils.readDouble(file));
   // }
   // // LOGGER.debug("vals: " + vals.get(x));
   // }
   // file.close();
   //
   // return new SegmentData(sampleCounts, timeStamps, unitIDS, vals);
   // }
}
