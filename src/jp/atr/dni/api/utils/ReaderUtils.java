package jp.atr.dni.api.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/14
 */
public class ReaderUtils {

   /**
    * 
    * @param size - number of bytes to read
    * @param file
    * @return
    * @throws IOException
    */
   public static byte[] readBytes(int size, RandomAccessFile file) throws IOException {
      byte[] bytes = new byte[size];
      // file.read(bytes);
      file.readFully(bytes, 0, size);
      return bytes;
   }

   /**
    *
    * @param file
    * @return
    * @throws IOException
    */
   public static short readShort(RandomAccessFile file) throws IOException {
      short myInt = 0;
      int tempInt;
      byte[] uInt = new byte[2];
      file.readFully(uInt, 0, 2);
      for (int i = 1; i >= 0; i--) {
         tempInt = (int) uInt[i] & 0xFF;
         myInt <<= 8;
         myInt |= tempInt & 0xFFL;
      }
      return myInt;
   }

   /**
    *
    * @param file
    * @return
    * @throws IOException
    */
   public static long readUnsignedInt(RandomAccessFile file) throws IOException {
      long myLong = 0;
      int myInt;
      byte[] uInt = new byte[4];

      file.readFully(uInt, 0, 4);
      for (int i = 3; i >= 0; i--) {
         myInt = (int) uInt[i] & 0xFF;
         myLong <<= 8;
         myLong |= (long) myInt & 0xffffffffL;
      }
      return myLong;
   }

   /**
    *
    * @param file
    * @return
    * @throws IOException
    */
   public static int readInt(RandomAccessFile file) throws IOException {
      int temp = 0;
      int myInt;
      byte[] inttemp = new byte[4];

      file.readFully(inttemp, 0, 4);
      for (int i = 3; i >= 0; i--) {
         myInt = inttemp[i] & 0xFF;
         temp <<= 8;
         temp |= myInt & 0xffffffffL;
      }
      return temp;
   }

   /**
    *
    * @param file
    * @return
    * @throws IOException
    */
   public static double readDouble(RandomAccessFile file) throws IOException {
      long myLong = 0;
      int myInt;
      byte[] uInt = new byte[8];

      file.readFully(uInt, 0, 8);
      // double d = EndianUtils.readSwappedDouble(uInt,0);

      for (int i = 7; i >= 0; i--) {
         myInt = (int) uInt[i] & 0xFF;
         myLong <<= 8;
         myLong |= (long) myInt;
      }

      double d = Double.longBitsToDouble(myLong);
      if (d == Double.NaN) {
         d = 0;
      }

      return d;
   }
}
