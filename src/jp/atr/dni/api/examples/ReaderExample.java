/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2012/04/06
 */
package jp.atr.dni.api.examples;

import java.util.List;

import jp.atr.dni.api.nsn.NSNFile;
import jp.atr.dni.api.nsn.channels.NSNAnalogChannel;
import jp.atr.dni.api.nsn.channels.NSNChannel;
import jp.atr.dni.api.nsn.channels.NSNInfoFileChannel;
import jp.atr.dni.api.nsn.data.NSNAnalogData;
import jp.atr.dni.api.nsn.data.NSNChannelInfo;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.header.NSNFileHeader;
import jp.atr.dni.api.nsn.reader.NSNReader;
import jp.atr.dni.api.utils.InfoFileXMLParser;

/**
 * This is an example class showing how to read the contents of a neuroshare file using this library.
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2012/04/06
 */
public class ReaderExample {

   /**
    * @param args
    * @throws Exception 
    */
   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         System.out.println("Usage: filePath");
         System.exit(-1337);
      }
      String filePath = args[0];

      NSNReader reader = new NSNReader();
      NSNFile nsnFile = reader.readNSNFileAllData(filePath);

      // Get header information about this file
      NSNFileHeader header = nsnFile.getFileHeader();

      // Get the number of neuroshare entities (data channels)
      // this is a long because neuroshare used an unsigned int to store this
      long numEntities = header.getEntityCount();

      System.out.println(numEntities + " data channels");

      for (int i = 0; i < numEntities; i++) {
         NSNChannel channel = nsnFile.getChannel(i);

         // Print out the channel's name
         System.out.println("channel: " + channel.getLabel());

         if (channel.getType() == NSNChannelType.ANALOG) {
            // This is a neuroshare analog entity, meaning it has timeseries data
            NSNAnalogChannel aChannel = (NSNAnalogChannel) channel;
            NSNAnalogData aData = aChannel.getData();

            // Since neuroshare analog entities can have gaps between segments in the timeseries
            // data, let's print out the first value from the first segment
            System.out.println(aData.getValues().get(0).get(0));
         } else if (channel.getType() == NSNChannelType.INFO_FILE) {
            // This is an info_file entity. The team working on http://brainliner.jp at ATR defines
            // this entity in the documentation here:
            // http://www.cns.atr.jp/dni/en/downloads/tools-for-brain-behavior-data-sharing/
            NSNInfoFileChannel iChannel = (NSNInfoFileChannel) channel;

            // As defined by ATR's Department of Neuroinformatics, the info_file entity contains XML
            // data which can then by parsed
            System.out.println(iChannel.getData().getXml());
            InfoFileXMLParser parser = new InfoFileXMLParser();
            List<NSNChannelInfo> chInfos = parser.parseXML(iChannel.getData().getXml());
            System.out.println(chInfos);
         }
      }
   }
}