package jp.atr.dni.api.nsn.data;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jp.atr.dni.api.APIDataProvider;
import jp.atr.dni.api.nsn.enums.NSNEventType;
import jp.atr.dni.api.nsn.header.NSNEventHeader;
import jp.atr.dni.api.utils.ReaderUtils;

/**
 * The package-level access on this class is intentional.
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Computational Neuroscience Labs, Department of Neuroinformatics
 * @version 2011/07/28 
 */
final class NSNEventDataProvider implements APIDataProvider<NSNEvent> {

   private String filePath;

   private long byteOffset;

   private long dataCount;

   private NSNEventHeader entity;

   public NSNEventDataProvider(int i, NSNEventHeader nsnEntity) {
      entity = nsnEntity;

      this.filePath = nsnEntity.getFilePath();
      this.byteOffset = nsnEntity.getDataPosition();
   }

   @Override
   public int size() {
      // Return Header value.(EntityInfo.ItemCount)
      return ((Long) (entity.getItemCount())).intValue();
   }

   @Override
   public List<NSNEvent> getData(int from, int to) {

      ArrayList<NSNEvent> arrData = new ArrayList<NSNEvent>();
      int ndx = 0;
      int itemCount = ((Long) (entity.getItemCount())).intValue();

      // Check Input args.
      if (from < 0 || to < 0 || from > itemCount - 1) {
         return null;
      }

      try {
         RandomAccessFile file = new RandomAccessFile(filePath, "r");
         file.seek(byteOffset);

         while (ndx <= to) {
            // NSNEvent has TimeStamp and Value.
            NSNEvent record = new NSNEvent();

            double timeStamp = ReaderUtils.readDouble(file);

            // Set TimeStamp
            record.setTimestamp(timeStamp);

            dataCount = ReaderUtils.readUnsignedInt(file);

            if (entity.getEventType() == NSNEventType.TEXT) {
               // We are dealing with text
               String dataStr = "";

               for (int i = 0; i < dataCount; i++) {
                  dataStr += (char) file.readByte();
               }

               // Set Value
               record.setValue(dataStr);

            } else if (entity.getEventType() == NSNEventType.CSV) {
               // We are dealing with CSV. What do we do here?! TODO:XXX:FIXME:
               // XXX: this is not defined in the FILE format specification
            } else if (entity.getEventType() == NSNEventType.BYTE) {
               // We are dealing with 1-byte values
               // NOTE: We use the wordevent data for 1 and 2 byte values, because both are stored
               // as ints.
               int binData = file.readUnsignedByte();

               // Set Value
               record.setValue(binData);

            } else if (entity.getEventType() == NSNEventType.WORD) {
               // We are dealing with 2-byte values
               int binData = file.readUnsignedShort();

               // Set Value
               record.setValue(binData);
            } else if (entity.getEventType() == NSNEventType.DWORD) {
               // We are dealing with 4-byte values
               long binData = ReaderUtils.readUnsignedInt(file);

               // Set Value
               record.setValue(binData);

            } else {
               // We can't handle it, so just quit.
               JOptionPane.showMessageDialog(null, "Code : File Read Error\n"
                     + "Todo : Check your file format.");
               return null;

            }

            // Get value if from <= ndx <= to
            if (ndx >= from) {
               arrData.add(record);
            }

            ndx++;
         }
         file.close();
      } catch (Exception err) {
         err.printStackTrace();
         return null;
      }

      return arrData;
   }

   // /**
   // *
   // * @param fileFullPath
   // * @param entityNFO
   // * @param eventNFO
   // * @return
   // * @throws IOException
   // */
   // public ArrayList<EventData> getEventData(String fileFullPath, EntityInfo entityNFO, EventInfo
   // eventNFO)
   // throws IOException {
   // RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
   // file.seek(entityNFO.getDataPosition());
   //
   // ArrayList<EventData> data = new ArrayList<EventData>();
   //
   // for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // long byteSize = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("byteSize: " + byteSize);
   //
   // if (eventNFO.getEventType() == EventType.EVENT_TEXT) {
   // // We are dealing with text
   // String dataStr = "";
   //
   // for (int i = 0; i < byteSize; i++) {
   // dataStr += (char) file.readByte();
   // }
   // // LOGGER.debug("Data String: " + dataStr);
   // data.add(new TextEventData(timeStamp, byteSize, dataStr));
   //
   // } else if (eventNFO.getEventType() == EventType.EVENT_CSV) {
   // // We are dealing with CSV. What do we do here?! TODO:XXX:FIXME:
   // // XXX: this is not defined in the FILE format specification
   // } else if (eventNFO.getEventType() == EventType.EVENT_BYTE) {
   // // We are dealing with 1-byte values
   // // NOTE: We use the wordevent data for 1 and 2 byte values, because both are stored as
   // // ints.
   // int binData = file.readUnsignedByte();
   // // LOGGER.debug("binData: " + binData);
   // data.add(new WordEventData(timeStamp, byteSize, binData));
   //
   // } else if (eventNFO.getEventType() == EventType.EVENT_WORD) {
   // // We are dealing with 2-byte values
   // int binData = file.readUnsignedShort();
   // // LOGGER.debug("binData: " + binData);
   // data.add(new WordEventData(timeStamp, byteSize, binData));
   //
   // } else if (eventNFO.getEventType() == EventType.EVENT_DWORD) {
   // // We are dealing with 4-byte values
   // long binData = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("binData: " + binData);
   // data.add(new DWordEventData(timeStamp, byteSize, binData));
   //
   // } else {
   // // We can't handle it, so just quit.
   // // LOGGER.error("An unexpected EVENT type was encountered, so we have to quit.");
   // System.exit(1);
   // }
   // }
   // file.close();
   //
   // return data;
   // }

   // /**
   // *
   // * @param fileFullPath
   // * @param entityNFO
   // * @param eventNFO
   // * @return
   // * @throws IOException
   // */
   // public ArrayList<TextEventData> getTextEventData(String fileFullPath, long dataPosition, long
   // itemCount)
   // throws IOException {
   // RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
   // file.seek(dataPosition);
   //
   // ArrayList<TextEventData> data = new ArrayList<TextEventData>();
   //
   // for (int dataItemNum = 0; dataItemNum < itemCount; dataItemNum++) {
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // long byteSize = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("byteSize: " + byteSize);
   //
   // String dataStr = "";
   //
   // for (int i = 0; i < byteSize; i++) {
   // dataStr += (char) file.readByte();
   // }
   // // LOGGER.debug("Data String: " + dataStr);
   // data.add(new TextEventData(timeStamp, byteSize, dataStr));
   //
   // }
   // file.close();
   //
   // return data;
   // }
   //
   // /**
   // *
   // * @param fileFullPath
   // * @param entityNFO
   // * @param eventNFO
   // * @return
   // * @throws IOException
   // */
   // public ArrayList<ByteEventData> getByteEventData(String fileFullPath, long dataPosition, long
   // itemCount)
   // throws IOException {
   // RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
   // file.seek(dataPosition);
   //
   // ArrayList<ByteEventData> data = new ArrayList<ByteEventData>();
   //
   // for (int dataItemNum = 0; dataItemNum < itemCount; dataItemNum++) {
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // long byteSize = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("byteSize: " + byteSize);
   //
   // int binData = file.readUnsignedByte();
   // // LOGGER.debug("binData: " + binData);
   // data.add(new ByteEventData(timeStamp, byteSize, ((Integer) binData).byteValue()));
   //
   // }
   // file.close();
   //
   // return data;
   // }
   //
   // /**
   // *
   // * @param fileFullPath
   // * @param dataPosition
   // * @param itemCount
   // * @return
   // * @throws IOException
   // */
   // public ArrayList<WordEventData> getWordEventData(String fileFullPath, long dataPosition, long
   // itemCount)
   // throws IOException {
   // RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
   // file.seek(dataPosition);
   //
   // ArrayList<WordEventData> data = new ArrayList<WordEventData>();
   //
   // for (int dataItemNum = 0; dataItemNum < itemCount; dataItemNum++) {
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // long byteSize = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("byteSize: " + byteSize);
   //
   // // We are dealing with 2-byte values
   // int binData = file.readUnsignedShort();
   // // LOGGER.debug("binData: " + binData);
   // data.add(new WordEventData(timeStamp, byteSize, binData));
   //
   // }
   // file.close();
   //
   // return data;
   // }

   // /**
   // *
   // * @param fileFullPath
   // * @param dataPosition
   // * @param itemCount
   // * @return
   // * @throws IOException
   // */
   // public ArrayList<DWordEventData> getDWordEventData(String fileFullPath, long dataPosition,
   // long itemCount)
   // throws IOException {
   // RandomAccessFile file = new RandomAccessFile(fileFullPath, "r");
   // file.seek(dataPosition);
   //
   // ArrayList<DWordEventData> data = new ArrayList<DWordEventData>();
   //
   // for (int dataItemNum = 0; dataItemNum < itemCount; dataItemNum++) {
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // long byteSize = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("byteSize: " + byteSize);
   //
   // // We are dealing with 4-byte values
   // long binData = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("binData: " + binData);
   // data.add(new DWordEventData(timeStamp, byteSize, binData));
   // }
   // file.close();
   //
   // return data;
   // }
   //
   // private ArrayList<EventData> getEventData(EntityInfo entityNFO, EventInfo eventNFO, Tag elem,
   // RandomAccessFile file) throws IOException {
   // ArrayList<EventData> data = new ArrayList<EventData>();
   //
   // for (int dataItemNum = 0; dataItemNum < entityNFO.getItemCount(); dataItemNum++) {
   //
   // double timeStamp = ReaderUtils.readDouble(file);
   // // LOGGER.debug("timeStamp: " + timeStamp);
   // long byteSize = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("byteSize: " + byteSize);
   //
   // if (eventNFO.getEventType() == EventType.EVENT_TEXT) {
   // // We are dealing with text
   // String dataStr = "";
   //
   // for (int i = 0; i < byteSize; i++) {
   // dataStr += (char) file.readByte();
   // }
   // // LOGGER.debug("Data String: " + dataStr);
   // data.add(new TextEventData(timeStamp, byteSize, dataStr));
   //
   // } else if (eventNFO.getEventType() == EventType.EVENT_CSV) {
   // // We are dealing with CSV. What do we do here?! TODO:XXX:FIXME:
   // // XXX: this is not defined in the FILE format specification
   // } else if (eventNFO.getEventType() == EventType.EVENT_BYTE) {
   // // We are dealing with 1-byte values
   // // NOTE: We use the wordevent data for 1 and 2 byte values, because both are stored as
   // // ints.
   // int binData = file.readUnsignedByte();
   // // LOGGER.debug("binData: " + binData);
   // data.add(new WordEventData(timeStamp, byteSize, binData));
   //
   // } else if (eventNFO.getEventType() == EventType.EVENT_WORD) {
   // // We are dealing with 2-byte values
   // int binData = file.readUnsignedShort();
   // // LOGGER.debug("binData: " + binData);
   // data.add(new WordEventData(timeStamp, byteSize, binData));
   //
   // } else if (eventNFO.getEventType() == EventType.EVENT_DWORD) {
   // // We are dealing with 4-byte values
   // long binData = ReaderUtils.readUnsignedInt(file);
   // // LOGGER.debug("binData: " + binData);
   // data.add(new DWordEventData(timeStamp, byteSize, binData));
   //
   // } else {
   // // We can't handle it, so just quit.
   // // LOGGER.error("An unexpected EVENT type was encountered, so we have to quit.");
   // System.exit(1);
   // }
   // }
   // file.close();
   //
   // return data;
   // }
}
