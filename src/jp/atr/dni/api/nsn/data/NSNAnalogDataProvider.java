package jp.atr.dni.api.nsn.data;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import jp.atr.dni.api.APIDataProvider;
import jp.atr.dni.api.nsn.header.NSNAnalogHeader;
import jp.atr.dni.api.nsn.utils.NSNConstants;
import jp.atr.dni.api.utils.ReaderUtils;

/**
 * The package-level access on this class is intentional.
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/23
 */
final class NSNAnalogDataProvider implements APIDataProvider {

   private int segmentNum;

   private String filePath;

   private long byteOffset;

   private int dataCount;

   private NSNAnalogHeader entity;

   public NSNAnalogDataProvider(int segmentNum, NSNAnalogHeader nsnHeader) {
      entity = nsnHeader;

      this.filePath = nsnHeader.getFilePath();
      this.byteOffset = nsnHeader.getDataPosition();
      this.dataCount = -1;
      this.segmentNum = segmentNum;
   }

   @Override
   public synchronized int size() {
      return dataCount > 0 ? dataCount : getDataCount();
   }

   @Override
   public synchronized List<Double> getData(int from, int to) {

      ArrayList<Double> data = new ArrayList<Double>();
      int count = 0;
      int iter = 0;
      long itemCount = entity.getItemCount();

      try {
         RandomAccessFile file = new RandomAccessFile(filePath, "r");
         file.seek(byteOffset);

         while (count < itemCount) {
            file.skipBytes(8);// skip timestamp
            dataCount = ((Long) ReaderUtils.readUnsignedInt(file)).intValue();

            if (iter == segmentNum) {
               if (from > dataCount) {
                  from = (int) (dataCount - 1);
               }

               if (to > dataCount) {
                  to = (int) (dataCount - 1);
               }

               // skip to the "from" index.
               file.skipBytes(NSNConstants.DOUBLE_BYTE_SIZE * from);

               for (int valNDX = from; valNDX <= to; valNDX++) {
                  data.add(ReaderUtils.readDouble(file));
                  count++;
               }
               break;
            } else {
               // skip this data
               file.skipBytes(NSNConstants.DOUBLE_BYTE_SIZE * dataCount);
               count += dataCount;
            }

            iter++;
         }
         file.close();
      } catch (Exception err) {
         err.printStackTrace();
         return null;
      }
      return data;
   }

   private synchronized int getDataCount() {
      try {
         int count = 0;
         int iter = 0;
         long itemCount = entity.getItemCount();
         RandomAccessFile file = new RandomAccessFile(filePath, "r");

         file.seek(byteOffset);

         while (count < itemCount) {
            ReaderUtils.readDouble(file);
            dataCount = ((Long) ReaderUtils.readUnsignedInt(file)).intValue();

            if (iter == segmentNum) {
               break;
            }
            // skip this data
            file.skipBytes(NSNConstants.DOUBLE_BYTE_SIZE * dataCount);
            count += dataCount;
            iter++;
         }

         file.close();
      } catch (Exception err) {
         err.printStackTrace();
         return -1;
      }
      return dataCount;
   }
}
