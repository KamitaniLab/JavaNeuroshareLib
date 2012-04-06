package jp.atr.dni.api.nsn.data;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import jp.atr.dni.api.APIDataProvider;
import jp.atr.dni.api.nsn.header.NSNNeuralHeader;
import jp.atr.dni.api.nsn.utils.NSNConstants;
import jp.atr.dni.api.utils.ReaderUtils;

/**
 * The package-level access on this class is intentional.
 *
 * @author Keiji Harada</br>ATR Intl. Computational Neuroscience Labs, Decoding Group
 * @version 2011/07/28 
 */
final class NSNNeuralSpikeDataProvider implements APIDataProvider<Double> {

   private String filePath;

   private long byteOffset;

   private int itemCount;

   private NSNNeuralHeader entity;

   public NSNNeuralSpikeDataProvider(int i, NSNNeuralHeader nsnEntity) {
      entity = nsnEntity;

      this.filePath = nsnEntity.getFilePath();
      this.byteOffset = nsnEntity.getDataPosition();
      this.itemCount = ((Long) (entity.getItemCount())).intValue();
   }

   @Override
   public int size() {
      return itemCount;
   }

   @Override
   public List<Double> getData(int from, int to) {

      ArrayList<Double> data = new ArrayList<Double>();
      int ndx = 0;

      // Check Input args.
      if (from < 0 || to < 0 || from > itemCount - 1) {
         return null;
      }
      if (to > itemCount - 1) {
         to = itemCount - 1;
      }

      try {
         RandomAccessFile file = new RandomAccessFile(filePath, "r");
         // Seek to the proper place, relative to the start of the data
         file.seek(byteOffset + (from * NSNConstants.DOUBLE_BYTE_SIZE));

         ndx = from;
         while (ndx <= to) {
            // read TimeStamp
            double time = ReaderUtils.readDouble(file);
            data.add(time);
            ndx++;
         }
         file.close();
      } catch (Exception err) {
         err.printStackTrace();
         return null;
      }

      return data;
   }
}