package jp.atr.dni.api.nsn.reader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import jp.atr.dni.api.APIDataProvider;
import jp.atr.dni.api.nsn.channels.NSNAnalogChannel;
import jp.atr.dni.api.nsn.channels.NSNChannel;
import jp.atr.dni.api.nsn.channels.NSNEventChannel;
import jp.atr.dni.api.nsn.channels.NSNInfoFileChannel;
import jp.atr.dni.api.nsn.channels.NSNNeuralSpikeChannel;
import jp.atr.dni.api.nsn.channels.NSNSegmentChannel;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.enums.NSNEventType;
import jp.atr.dni.api.nsn.header.NSNAnalogHeader;
import jp.atr.dni.api.nsn.header.NSNEventHeader;
import jp.atr.dni.api.nsn.header.NSNNeuralHeader;
import jp.atr.dni.api.nsn.header.NSNSegmentHeader;
import jp.atr.dni.api.nsn.header.NSNSegmentSourceHeader;
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
final class NSNChannelProvider implements APIDataProvider {

   private int channelCount;

   private String filePath;

   long channelsStartOffset;

   public NSNChannelProvider(long channelCount, long channelsStartOffset, String filePath) {
      this.channelCount = (int) channelCount;
      this.filePath = filePath;
      this.channelsStartOffset = channelsStartOffset;
   }

   @Override
   public synchronized int size() {
      return channelCount;
   }

   @Override
   public synchronized List<NSNChannel> getData(int from, int to) {
      // System.out.println("channelCount: " + channelCount);

      ArrayList<NSNChannel> channels = new ArrayList<NSNChannel>();

      if (to >= channelCount) {
         to = channelCount;
      }
      RandomAccessFile file = null;
      try {
         file = new RandomAccessFile(filePath, "r");
         file.seek(channelsStartOffset);

         // Now read in the entities
         for (int chanNum = 0; chanNum <= to; chanNum++) {
            if (chanNum > to) {
               break;
            }

//            System.out.println("pos: " + file.getFilePointer());
            // First read in the element tag
            Long elemType = ReaderUtils.readUnsignedInt(file);
            long elemLength = ReaderUtils.readUnsignedInt(file);
            NSNChannelType chanType = NSNChannelType.fromInt(elemType.intValue());

            String label = null;
            long entityType;
            long itemCount = -1;
            if (chanType != NSNChannelType.INFO_FILE) {

               // Read in the entity information
               label = "";
               for (int i = 0; i < 32; i++) {
                  label += (char) file.readByte();
               }

               entityType = ReaderUtils.readUnsignedInt(file);
               itemCount = ReaderUtils.readUnsignedInt(file);
            }

            // Now that we have the tag, we should do different things, based on the type
            if (chanType == NSNChannelType.ANALOG) {
               // Read in header
               NSNAnalogHeader analogHeader = readAnalogHeader(elemLength, chanType, label, itemCount,
                     filePath, file);
               NSNAnalogChannel analogChannel = new NSNAnalogChannel(chanNum, analogHeader);
               if (chanNum >= from) {
                  channels.add(analogChannel);
               }

               // Save position info here.
               analogHeader.setDataPosition(file.getFilePointer());

               // Go through and get all the data. The type of data depends on the eventNFO's
               // EVENT type.
               // skip the data since we are not reading it in here
               int skipBytes = (int) elemLength
                     - (NSNConstants.NS_ENTITYINFO_LENGTH + NSNConstants.NS_ANALOGHEADER_LENGTH);
               file.skipBytes(skipBytes);

            } else if (chanType == NSNChannelType.NEURAL_SPIKE) {
               // Read in header
               NSNNeuralHeader neuralHeader = readNeuralHeader(elemLength, chanType, label, itemCount,
                     filePath, file);
               NSNNeuralSpikeChannel neuralChannel = new NSNNeuralSpikeChannel(chanNum, neuralHeader);
               if (chanNum >= from) {
                  channels.add(neuralChannel);
               }

               // Save position info here.
               neuralHeader.setDataPosition(file.getFilePointer());

               // Skip Bytes
               int skipBytes = (int) elemLength
                     - (NSNConstants.NS_ENTITYINFO_LENGTH + NSNConstants.NS_NEURALINFO_LENGTH);
               file.skipBytes(skipBytes);

            } else if (chanType == NSNChannelType.EVENT) {
               // Read in header
               NSNEventHeader eventHeader = readEventHeader(elemLength, chanType, label, itemCount, filePath,
                     file);
               NSNEventChannel eventChannel = new NSNEventChannel(chanNum, eventHeader);
               if (chanNum >= from) {
                  channels.add(eventChannel);
               }

               // Save position info here.
               eventHeader.setDataPosition(file.getFilePointer());

               // Skip Bytes
               int skipBytes = (int) elemLength
                     - (NSNConstants.NS_ENTITYINFO_LENGTH + NSNConstants.NS_EVENTINFO_LENGTH);
               file.skipBytes(skipBytes);

            } else if (chanType == NSNChannelType.SEGMENT) {
               // Read in header
               NSNSegmentHeader segmentHeader = readSegmentHeader(elemLength, chanType, label, itemCount,
                     filePath, file);
               NSNSegmentChannel segmentChannel = new NSNSegmentChannel(chanNum, segmentHeader);
               if (chanNum >= from) {
                  channels.add(segmentChannel);
               }

               ArrayList<NSNSegmentSourceHeader> segSourceHeaders = new ArrayList<NSNSegmentSourceHeader>();
               // Get the SEGMENT source headers
               for (int srcNDX = 0; srcNDX < segmentHeader.getSourceCount(); srcNDX++) {
                  segSourceHeaders.add(readSegmentSourceHeader(file));
               }

               segmentChannel.setSegmentSources(segSourceHeaders);

               // Save position info here.
               segmentHeader.setDataPosition(file.getFilePointer());

               // Skip Bytes
               int skipBytes = (int) elemLength
                     - (NSNConstants.NS_ENTITYINFO_LENGTH + NSNConstants.NS_SEGMENTINFO_LENGTH + (int) segmentHeader
                           .getSourceCount() * NSNConstants.NS_SEGSOURCEINFO_LENGTH);
               if (skipBytes > 0) {
                  file.skipBytes(skipBytes);
               }

            } else if (chanType == NSNChannelType.INFO_FILE) {
               int numBytes = ((Long) elemLength).intValue();// - NSNConstants.NS_ENTITYINFO_LENGTH;
               byte[] bytes = ReaderUtils.readBytes(numBytes, file);

               // Inflater decompressor = new Inflater(true);

               ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
               GZIPInputStream gis = new GZIPInputStream(bis);
               // zis.read()
               // ZipEntry ze = zis.getNextEntry();
               // System.out.println(ze.toString());

               Reader decoder = new InputStreamReader(gis, "UTF-8");

               BufferedReader buffered = new BufferedReader(decoder);
               String xml = "";
               String x = null;
               while ((x = buffered.readLine()) != null) {
                  xml += x;
               }
               // System.out.println(buffered.readLine());

               // ByteArrayOutputStream bos = new ByteArrayOutputStream(numBytes);
               // ArrayList<Byte> byteList = new ArrayList<Byte>();
               // ZipEntry entry;
               // zis.
               // while ((entry = zis.getNextEntry()) != null) {
               // System.out.println("Unzipping: " + entry.getName());
               //
               // int size;
               // byte[] buffer = new byte[2048];
               //
               // FileOutputStream fos = new FileOutputStream(entry.getName());
               // BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
               //
               // while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
               // bos.write(buffer, 0, size);
               // }
               // bos.flush();
               // bos.close();
               // }
               // decompressor.setInput(zis);// XXX:putting this here is a hack
               //
               // while (!decompressor.finished()) {
               // int decompressedCount = decompressor.inflate(buf);
               // System.out.println("decompressedCount: " + decompressedCount);
               // for (int i = 0; i < decompressedCount; i++) {
               // byteList.add(buf[i]);
               // }
               // // bos.write(buf, 0, decompressedCount);
               // }
               // bos.close();
               // byte[] decompressedData = bos.toByteArray();
               // byte[] decompressedData = new byte[byteList.size()];
               // for (int i = 0; i < decompressedData.length; i++) {
               // decompressedData[i] = byteList.get(i);
               // }
               // System.out.println(xml);

               NSNInfoFileChannel infoFileChannel = new NSNInfoFileChannel(chanNum, xml);
               if (chanNum >= from) {
                  channels.add(infoFileChannel);
               }

            } else {
               // We can't handle this, so just quit.
               // NOTE: we would just be able to skip this tag and move on, but since the
               // Neuroshare .nsn files are binary and not XML-based, we cannot just skip a tag and
               // move on. This is one good reason not to use binary FILE formats like this.
               // LOGGER.error("An element tag has an unknown datatype, so we have to quit.");
               return null;
            }
         }

      } catch (Throwable err) {
         err.printStackTrace();
         return null;
      } finally {
         try {
            file.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return channels;
   }

   private NSNAnalogHeader readAnalogHeader(long elemLength, NSNChannelType type, String label,
         long itemCount, String filePath, RandomAccessFile file) throws IOException {

      double sampleRate;
      double minVal;
      double maxVal;
      double resolution;
      double locationX;
      double locationY;
      double locationZ;
      double locationuser;
      double highFreqCorner;
      long highFreqOrder;
      double lowFreqCorner;
      long lowFreqOrder;

      String units = "";
      String highFilterType = "";
      String lowFiltertype = "";
      String probeInfo = "";

      sampleRate = ReaderUtils.readDouble(file);
      minVal = ReaderUtils.readDouble(file);
      maxVal = ReaderUtils.readDouble(file);

      for (int i = 0; i < 16; i++) {
         units += (char) file.readByte();
      }

      resolution = ReaderUtils.readDouble(file);

      locationX = ReaderUtils.readDouble(file);
      locationY = ReaderUtils.readDouble(file);
      locationZ = ReaderUtils.readDouble(file);

      locationuser = ReaderUtils.readDouble(file);

      highFreqCorner = ReaderUtils.readDouble(file);
      highFreqOrder = ReaderUtils.readUnsignedInt(file);

      for (int i = 0; i < 16; i++) {
         highFilterType += (char) file.readByte();
      }

      lowFreqCorner = ReaderUtils.readDouble(file);
      lowFreqOrder = ReaderUtils.readUnsignedInt(file);

      for (int i = 0; i < 16; i++) {
         lowFiltertype += (char) file.readByte();
      }

      for (int i = 0; i < 128; i++) {
         probeInfo += (char) file.readByte();
      }

      return new NSNAnalogHeader(elemLength, type, label, itemCount, -1, filePath, sampleRate, minVal, maxVal,
            units, resolution, locationX, locationY, locationZ, locationuser, highFreqCorner, highFreqOrder,
            highFilterType, lowFreqCorner, lowFreqOrder, lowFiltertype, probeInfo);
   }

   private NSNNeuralHeader readNeuralHeader(long elemLength, NSNChannelType type, String label,
         long itemCount, String filePath, RandomAccessFile file) throws IOException {
      long sourceEntityID;
      long sourceUnitID;
      String probeInfo = "";

      sourceEntityID = ReaderUtils.readUnsignedInt(file);
      sourceUnitID = ReaderUtils.readUnsignedInt(file);

      for (int i = 0; i < 128; i++) {
         probeInfo += (char) file.readByte();
      }

      return new NSNNeuralHeader(elemLength, type, label, itemCount, -1, filePath, sourceEntityID,
            sourceUnitID, probeInfo);
   }

   private NSNEventHeader readEventHeader(long elemLength, NSNChannelType type, String label, long itemCount,
         String filePath, RandomAccessFile file) throws IOException {

      // Now process the event header
      Long eventType = ReaderUtils.readUnsignedInt(file);
      NSNEventType et = NSNEventType.fromInt(eventType.intValue());
      long minDataLength = ReaderUtils.readUnsignedInt(file);
      long maxDataLength = ReaderUtils.readUnsignedInt(file);
      String csvDesc = "";

      for (int i = 0; i < 128; i++) {
         csvDesc += (char) file.readByte();
      }

      return new NSNEventHeader(elemLength, type, label, itemCount, -1, filePath, et, minDataLength,
            maxDataLength, csvDesc);
   }

   private NSNSegmentHeader readSegmentHeader(long elemLength, NSNChannelType type, String label,
         long itemCount, String filePath, RandomAccessFile file) throws IOException {
      long sourceCount;
      long minSampleCount;
      long maxSampleCount;
      double sampleRate;
      String units = "";

      sourceCount = ReaderUtils.readUnsignedInt(file);
      minSampleCount = ReaderUtils.readUnsignedInt(file);
      maxSampleCount = ReaderUtils.readUnsignedInt(file);
      sampleRate = ReaderUtils.readDouble(file);

      for (int i = 0; i < 32; i++) {
         units += (char) file.readByte();
      }

      return new NSNSegmentHeader(elemLength, type, label, itemCount, -1, filePath, sourceCount,
            minSampleCount, maxSampleCount, sampleRate, units);
   }

   private NSNSegmentSourceHeader readSegmentSourceHeader(RandomAccessFile file) throws IOException {

      double minVal;
      double maxVal;
      double resolution;
      double subSampleShift;
      double locationX;
      double locationY;
      double locationZ;
      double locationUser;
      double highFreqCorner;
      long highFreqOrder;
      String highFilterType = "";
      double lowFreqCorner;
      long lowFreqOrder;
      String lowFilterType = "";
      String probeInfo = "";

      minVal = ReaderUtils.readDouble(file);
      maxVal = ReaderUtils.readDouble(file);

      resolution = ReaderUtils.readDouble(file);
      subSampleShift = ReaderUtils.readDouble(file);

      locationX = ReaderUtils.readDouble(file);
      locationY = ReaderUtils.readDouble(file);
      locationZ = ReaderUtils.readDouble(file);

      locationUser = ReaderUtils.readDouble(file);
      highFreqCorner = ReaderUtils.readDouble(file);
      highFreqOrder = ReaderUtils.readUnsignedInt(file);

      for (int i = 0; i < 16; i++) {
         highFilterType += (char) file.readByte();
      }

      lowFreqCorner = ReaderUtils.readDouble(file);
      lowFreqOrder = ReaderUtils.readUnsignedInt(file);

      for (int i = 0; i < 16; i++) {
         lowFilterType += (char) file.readByte();
      }

      for (int i = 0; i < 128; i++) {
         probeInfo += (char) file.readByte();
      }

      return new NSNSegmentSourceHeader(minVal, maxVal, resolution, subSampleShift, locationX, locationY,
            locationZ, locationUser, highFreqCorner, highFreqOrder, highFilterType, lowFreqCorner,
            lowFreqOrder, lowFilterType, probeInfo);
   }
}
