/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/17
 */
package jp.atr.dni.api.nsn.writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import jp.atr.dni.api.APIList;
import jp.atr.dni.api.nsn.NSNFile;
import jp.atr.dni.api.nsn.channels.NSNAnalogChannel;
import jp.atr.dni.api.nsn.channels.NSNChannel;
import jp.atr.dni.api.nsn.channels.NSNEventChannel;
import jp.atr.dni.api.nsn.channels.NSNInfoFileChannel;
import jp.atr.dni.api.nsn.channels.NSNNeuralSpikeChannel;
import jp.atr.dni.api.nsn.channels.NSNSegmentChannel;
import jp.atr.dni.api.nsn.data.NSNAnalogData;
import jp.atr.dni.api.nsn.data.NSNEvent;
import jp.atr.dni.api.nsn.data.NSNEventData;
import jp.atr.dni.api.nsn.data.NSNNeuralSpikeData;
import jp.atr.dni.api.nsn.data.NSNSegmentData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.enums.NSNEventType;
import jp.atr.dni.api.nsn.header.NSNFileHeader;
import jp.atr.dni.api.nsn.header.NSNSegmentSourceHeader;
import jp.atr.dni.api.nsn.utils.NSNConstants;

/**
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/24
 */
public class NSNWriter {

   public void writeNSNFile(String outputFile, NSNFile file) throws IOException {
      FileOutputStream fos = new FileOutputStream(outputFile);
      writeHeader(fos, file.getFileHeader());

      // write each data channel
      int numChannels = (int) file.getFileHeader().getEntityCount();
      for (int ndx = 0; ndx < numChannels; ndx++) {
         writeChannel(fos, file.getChannel(ndx));
      }

      // close the file
      fos.close();
   }

   private void writeHeader(FileOutputStream fos, NSNFileHeader header) throws IOException {

      ByteArrayOutputStream bos = new ByteArrayOutputStream(NSNConstants.NS_FILEHEADER_LENGTH);
      DataOutputStream dos = new DataOutputStream(bos);

      // TODO:FIXME:XXX:i'm not convinced that the following will correctly write unsigned doubles.
      // ints should be okay, though
      bos.write(getBytesFromString(NSNConstants.CHAR16_LENGTH, header.getMagicCode()));
      bos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, header.getFileType()));
      dos.writeInt(Integer.reverseBytes((int) header.getEntityCount()));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(header.getTimeStampRes())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(header.getTimeSpan())));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR64_LENGTH, header.getAppName()));

      dos.writeInt(Integer.reverseBytes((int) header.getYear()));
      dos.writeInt(Integer.reverseBytes((int) header.getMonth()));
      dos.writeInt(Integer.reverseBytes((int) header.getDayOfWeek()));
      dos.writeInt(Integer.reverseBytes((int) header.getDayOfMonth()));
      dos.writeInt(Integer.reverseBytes((int) header.getHourOfDay()));
      dos.writeInt(Integer.reverseBytes((int) header.getMinOfDay()));
      dos.writeInt(Integer.reverseBytes((int) header.getSecOfDay()));
      dos.writeInt(Integer.reverseBytes((int) header.getMilliSecOfDay()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR256_LENGTH, header.getComments()));

      bos.writeTo(fos);

      dos.close();
      bos.close();
   }

   private void writeChannel(FileOutputStream fos, NSNChannel channel) throws IOException {
      if (channel.getType() == NSNChannelType.ANALOG) {
         writeNSNAnalogChannel(fos, (NSNAnalogChannel) channel);
      } else if (channel.getType() == NSNChannelType.NEURAL_SPIKE) {
         writeNSNNeuralSpikeChannel(fos, (NSNNeuralSpikeChannel) channel);
      } else if (channel.getType() == NSNChannelType.INFO_FILE) {
         writeNSNInfoFileChannel(fos, (NSNInfoFileChannel) channel);
      } else if (channel.getType() == NSNChannelType.SEGMENT) {
         writeNSNSegmentChannel(fos, (NSNSegmentChannel) channel);
      } else if (channel.getType() == NSNChannelType.EVENT) {
         writeNSNEventChannel(fos, (NSNEventChannel) channel);
      }
   }

   private void writeNSNAnalogChannel(FileOutputStream fos, NSNAnalogChannel channel) throws IOException {
      // Write channel header

      ByteArrayOutputStream bos = new ByteArrayOutputStream(NSNConstants.NS_ENTITYINFO_LENGTH
            + NSNConstants.NS_ANALOGHEADER_LENGTH);
      DataOutputStream dos = new DataOutputStream(bos);

      dos.writeInt(Integer.reverseBytes(channel.getHeader().getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getHeader().getElemLength()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, channel.getLabel()));

      dos.writeInt(Integer.reverseBytes((int) channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getItemCount()));

      // Start analog-specific header
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getSamplingRate())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getMinVal())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getMaxVal())));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR16_LENGTH, channel.getUnits()));

      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getResolution())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getLocationX())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getLocationY())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getLocationZ())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getLocationUser())));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getHighFreqCorner())));
      dos.writeInt(Integer.reverseBytes((int) channel.getHighFreqOrder()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR16_LENGTH, channel.getHighFilterType()));

      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getLowFreqCorner())));
      dos.writeInt(Integer.reverseBytes((int) channel.getLowFreqOrder()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR16_LENGTH, channel.getLowFilterType()));
      bos.write(getBytesFromString(NSNConstants.CHAR128_LENGTH, channel.getProbeInfo()));

      // Write header to file
      dos.close();
      bos.writeTo(fos);

      // Write channel's data, one segment at a time
      NSNAnalogData analogData = channel.getData();
      List<Double> timestamps = analogData.getTimeStamps();
      ArrayList<APIList<Double>> values = analogData.getValues();

      // bos = new ByteArrayOutputStream(8192);
      dos = new DataOutputStream(fos);

      int numSegs = timestamps.size();

      for (int ndx = 0; ndx < numSegs; ndx++) {

         Double timestamp = timestamps.get(ndx);
         APIList<Double> vals = values.get(ndx);
         int dataCount = vals.size();

         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(timestamp)));
         dos.writeInt(Integer.reverseBytes(dataCount));

         int dataNdx = 0;
         while (dataNdx < dataCount) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(vals.get(dataNdx))));
            // EndianUtils.writeSwappedDouble(dos, vals.get(dataNdx));
            // ByteBuffer buffer = ByteBuffer.allocate(8);
            // buffer.order(ByteOrder.LITTLE_ENDIAN);
            // buffer.putDouble(vals.get(dataNdx));
            // byte[] bytes = buffer.array();
            // dos.write(bytes);
            dataNdx++;
         }
      }
      dos.flush();
   }

   private void writeNSNNeuralSpikeChannel(FileOutputStream fos, NSNNeuralSpikeChannel channel)
         throws IOException {

      // Write channel header
      ByteArrayOutputStream bos = new ByteArrayOutputStream(NSNConstants.NS_ENTITYINFO_LENGTH
            + NSNConstants.NS_NEURALINFO_LENGTH);
      DataOutputStream dos = new DataOutputStream(bos);

      dos.writeInt(Integer.reverseBytes(channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getHeader().getElemLength()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, channel.getLabel()));

      dos.writeInt(Integer.reverseBytes((int) channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getItemCount()));

      // start neural spike-specific header
      dos.writeInt(Integer.reverseBytes((int) channel.getSourceEntityID()));
      dos.writeInt(Integer.reverseBytes((int) channel.getSourceUnitID()));
      dos.flush();
      bos.write(getBytesFromString(NSNConstants.CHAR128_LENGTH, channel.getProbeInfo()));

      // Write header to file
      dos.close();
      bos.close();
      bos.writeTo(fos);

      // Write channel's data, one segment at a time
      NSNNeuralSpikeData spikeData = channel.getData();
      APIList<Double> timestamps = spikeData.getTimeStamps();

      dos = new DataOutputStream(fos);

      int numTimestamps = timestamps.size();

      for (int ndx = 0; ndx < numTimestamps; ndx++) {
         Double timestamp = timestamps.get(ndx);
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(timestamp)));
      }
      dos.flush();
   }

   private void writeNSNInfoFileChannel(FileOutputStream fos, NSNInfoFileChannel channel) throws IOException {

      // Create the GZIP output stream
      ByteArrayOutputStream bos = new ByteArrayOutputStream(8192000);
      GZIPOutputStream out = new GZIPOutputStream(bos);

      // Open the input file
      ByteArrayInputStream in = new ByteArrayInputStream(channel.getData().getXml().getBytes());

      // Transfer bytes from the input file to the GZIP output stream
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
         out.write(buf, 0, len);
      }
      in.close();

      // Complete the GZIP file
      out.finish();
      out.close();

      // zip xml and calculate size
      // Deflater compresser = new Deflater(9, true);
      // compresser.setInput(channel.getData().getXml().getBytes());
      // compresser.finish();// TODO:change this to stream xml data to be compressed
      //
      // ByteArrayOutputStream bos = new ByteArrayOutputStream(8192000);
      //
      // byte[] buf = new byte[4096];
      // while (!compresser.finished()) {
      // compresser.needsInput();
      // int compressedCount = compresser.deflate(buf);
      // bos.write(buf, 0, compressedCount);
      // }
      // bos.flush();

      byte[] compressedXML = bos.toByteArray();

      int totalSize = compressedXML.length;
      bos = new ByteArrayOutputStream(totalSize);
      DataOutputStream dos = new DataOutputStream(bos);

      // Write channel header to file
      dos.writeInt(Integer.reverseBytes(channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes(totalSize));

      // Write XML to file
      dos.write(compressedXML);

      dos.flush();
      bos.writeTo(fos);
   }

   private void writeNSNSegmentChannel(FileOutputStream fos, NSNSegmentChannel channel) throws IOException {
      // Write channel header
      ByteArrayOutputStream bos = new ByteArrayOutputStream((int) (NSNConstants.NS_ENTITYINFO_LENGTH
            + NSNConstants.NS_SEGMENTINFO_LENGTH + NSNConstants.NS_SEGSOURCEINFO_LENGTH
            * channel.getSourceCount()));
      DataOutputStream dos = new DataOutputStream(bos);

      dos.writeInt(Integer.reverseBytes(channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getHeader().getElemLength()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, channel.getLabel()));

      dos.writeInt(Integer.reverseBytes((int) channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getItemCount()));

      // Write segment entity-specific header
      dos.writeInt(Integer.reverseBytes((int) channel.getSourceCount()));
      dos.writeInt(Integer.reverseBytes((int) channel.getMinSampleCount()));
      dos.writeInt(Integer.reverseBytes((int) channel.getMaxSampleCount()));
      dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(channel.getSamplingRate())));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, channel.getUnits()));

      // Write segment source-specific headers
      for (int segSrcNum = 0; segSrcNum < channel.getSourceCount(); segSrcNum++) {
         NSNSegmentSourceHeader currSegSrc = channel.getSegmentSourceHeader(segSrcNum);

         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getMinVal())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getMaxVal())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getResolution())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getSubSampleShift())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getLocationX())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getLocationY())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getLocationZ())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getLocationUser())));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getHighFreqCorner())));
         dos.writeInt(Integer.reverseBytes((int) currSegSrc.getHighFreqOrder()));
         dos.flush();

         bos.write(getBytesFromString(NSNConstants.CHAR16_LENGTH, currSegSrc.getHighFilterType()));

         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currSegSrc.getLowFreqCorner())));
         dos.writeInt(Integer.reverseBytes((int) currSegSrc.getLowFreqOrder()));
         dos.flush();

         bos.write(getBytesFromString(NSNConstants.CHAR16_LENGTH, currSegSrc.getLowFilterType()));
         bos.write(getBytesFromString(NSNConstants.CHAR128_LENGTH, currSegSrc.getProbeInfo()));
      }

      // Write all header info to file
      dos.close();
      bos.writeTo(fos);

      // Write segment data
      dos = new DataOutputStream(fos);

      NSNSegmentData segData = channel.getData();

      for (int segSrcNum = 0; segSrcNum < channel.getItemCount(); segSrcNum++) {
         APIList<Double> vals = segData.getValues().get(segSrcNum);
         // if (channel.getLabel().startsWith("elec1")) {
         // dos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, "takemiya"));
         // System.out.println(channel.getItemCount());
         // System.out.println(channel.getSourceCount());
         // System.out.println(vals.size());
         // // System.exit(0);
         // }
         dos.writeInt(Integer.reverseBytes(vals.size()));
         dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(segData.getTimeStamps().get(segSrcNum))));
         dos.writeInt(Integer.reverseBytes((int) segData.getUnitIDs().get(segSrcNum)));

         for (int dataNum = 0; dataNum < vals.size(); dataNum++) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(vals.get(dataNum))));
         }
      }

      dos.flush();
   }

   private void writeNSNEventChannel(FileOutputStream fos, NSNEventChannel channel) throws IOException {
      // Write channel header
      ByteArrayOutputStream bos = new ByteArrayOutputStream(NSNConstants.NS_ENTITYINFO_LENGTH
            + NSNConstants.NS_EVENTINFO_LENGTH);
      DataOutputStream dos = new DataOutputStream(bos);

      dos.writeInt(Integer.reverseBytes(channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getHeader().getElemLength()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR32_LENGTH, channel.getLabel()));

      dos.writeInt(Integer.reverseBytes((int) channel.getType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getItemCount()));

      // start event-specific header
      dos.writeInt(Integer.reverseBytes((int) channel.getEventType().toInt()));
      dos.writeInt(Integer.reverseBytes((int) channel.getMinDataLength()));
      dos.writeInt(Integer.reverseBytes((int) channel.getMaxDataLength()));
      dos.flush();

      bos.write(getBytesFromString(NSNConstants.CHAR128_LENGTH, channel.getCsvDesc()));

      // Write header to file
      dos.close();
      bos.writeTo(fos);

      NSNEventType eventType = channel.getEventType();

      // Write channel's data, one segment at a time
      NSNEventData eventData = channel.getData();
      APIList<NSNEvent> events = eventData.getEvents();

      int numEvents = events.size();
      dos = new DataOutputStream(fos);

      for (int eventNum = 0; eventNum < numEvents; eventNum++) {
         NSNEvent currEvent = events.get(eventNum);

         if (eventType == NSNEventType.TEXT) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currEvent.getTimestamp())));
            dos.writeInt(Integer.reverseBytes(((String) currEvent.getValue()).length()));
            dos.writeBytes((String) currEvent.getValue());
         } else if (eventType == NSNEventType.CSV) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currEvent.getTimestamp())));
            dos.writeInt(Integer.reverseBytes(((String) currEvent.getValue()).length()));
            dos.writeBytes((String) currEvent.getValue());
         } else if (eventType == NSNEventType.BYTE) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currEvent.getTimestamp())));
            dos.writeInt(Integer.reverseBytes(1));
            dos.write((Byte) currEvent.getValue());
         } else if (eventType == NSNEventType.WORD) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currEvent.getTimestamp())));
            dos.writeInt(Integer.reverseBytes(2));
            dos.writeShort(Short.reverseBytes((Short) currEvent.getValue()));
         } else if (eventType == NSNEventType.DWORD) {
            dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(currEvent.getTimestamp())));
            dos.writeInt(Integer.reverseBytes(4));
            dos.writeInt(Integer.reverseBytes(((Long) currEvent.getValue()).intValue()));
         }
      }

      dos.flush();
   }

   private byte[] getBytesFromString(int numBytes, String str) {
      byte[] bytes = new byte[numBytes];
      byte space = ' ';
      Arrays.fill(bytes, space);

      int len = str.length() < numBytes ? str.length() : numBytes;
      for (int i = 0; i < len; i++) {
         bytes[i] = (byte) str.charAt(i);
      }

      return bytes;
   }
}
