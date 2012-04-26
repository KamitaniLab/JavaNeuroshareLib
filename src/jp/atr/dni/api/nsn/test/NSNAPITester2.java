/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/24
 */
package jp.atr.dni.api.nsn.test;

import java.io.IOException;

import jp.atr.dni.api.nsn.NSNFile;
import jp.atr.dni.api.nsn.channels.NSNAnalogChannel;
import jp.atr.dni.api.nsn.channels.NSNChannel;
import jp.atr.dni.api.nsn.channels.NSNInfoFileChannel;
import jp.atr.dni.api.nsn.data.NSNAnalogData;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.header.NSNFileHeader;
import jp.atr.dni.api.nsn.reader.NSNReader;
import jp.atr.dni.api.nsn.writer.NSNWriter;
import jp.atr.dni.api.utils.InfoFileXMLParser;

/**
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/24
 */
public class NSNAPITester2 {
   public static void main(String[] args) throws IOException {

      NSNReader reader = new NSNReader();
      NSNFile file1 = reader.readNSNFileAllData("rat_ecog_plain.nsn");
      for (int i = 0; i < file1.getFileHeader().getEntityCount(); i++) {
         NSNChannel channel = file1.getChannel(i);
         System.out.println(channel.getLabel());
         if (channel.getType() == NSNChannelType.ANALOG) {
            NSNAnalogChannel aChannel = (NSNAnalogChannel) channel;
            NSNAnalogData aData = aChannel.getData();

            System.out.println(aData.getValues().get(0).get(0));
         } else if (channel.getType() == NSNChannelType.INFO_FILE) {
            NSNInfoFileChannel iChannel = (NSNInfoFileChannel) channel;
            System.out.println(iChannel.getData().getXml());
            InfoFileXMLParser parser = new InfoFileXMLParser();
         }
      }

      // add channel
      System.out.println("b4: " + file1.getFileHeader().getEntityCount());
      NSNInfoFileChannel iChannel = new NSNInfoFileChannel(
            (int) (file1.getFileHeader().getEntityCount() + 1),
            "<?xml version=\"1.0\" ?><chInfo><ch num=\"1\"><neuroshareType>2</neuroshareType><chName>Fpz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"2\"><neuroshareType>2</neuroshareType><chName>Fz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"3\"><neuroshareType>2</neuroshareType><chName>Cz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"4\"><neuroshareType>2</neuroshareType><chName>Pz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"5\"><neuroshareType>2</neuroshareType><chName>Oz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch></chInfo>");
      file1.addChannel(iChannel);
      System.out.println("after: " + file1.getFileHeader().getEntityCount());

      // write the file
      NSNWriter nsnWriter = new NSNWriter();
      nsnWriter.writeNSNFile("myFile", file1);
      System.out.println("writing...");

      NSNFile file2 = reader.readNSNFileAllData("myFile");

      NSNFileHeader header = file2.getFileHeader();
      System.out.println(header.getMilliSecOfDay());
      System.out.println(header.getComments());
      System.out.println("f2 channels: " + file2.getFileHeader().getEntityCount());
   }
}
