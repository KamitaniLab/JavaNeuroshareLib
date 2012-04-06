/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/10
 */
package jp.atr.dni.api.nsn.test;

import java.util.List;

import jp.atr.dni.api.nsn.NSNFile;
import jp.atr.dni.api.nsn.channels.NSNAnalogChannel;
import jp.atr.dni.api.nsn.channels.NSNChannel;
import jp.atr.dni.api.nsn.channels.NSNInfoFileChannel;
import jp.atr.dni.api.nsn.channels.NSNSegmentChannel;
import jp.atr.dni.api.nsn.data.NSNAnalogData;
import jp.atr.dni.api.nsn.data.NSNChannelInfo;
import jp.atr.dni.api.nsn.data.NSNSegmentData;
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
 * @version 2011/11/23
 */
public class NSNAPITester {

   /**
    * @param args
    * @throws Exception 
    */
   public static void main(String[] args) throws Exception {
      NSNReader reader = new NSNReader();
      NSNFile file1 = reader.readNSNFileAllData("Neuroshare_SampleData_002.nsn");
      System.out.println(file1.getFileHeader().getEntityCount());
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
            List<NSNChannelInfo> chInfos = parser.parseXML(iChannel.getData().getXml());
            System.out.println(chInfos);
         }
      }

      // write the file
      NSNWriter nsnWriter = new NSNWriter();
      nsnWriter.writeNSNFile("myFile", file1);
      System.out.println("writing...");

      NSNFile file2 = reader.readNSNFileAllData("myFile");

      NSNFileHeader header = file2.getFileHeader();
      System.out.println(header.getMilliSecOfDay());
      System.out.println(header.getComments());
      // System.out.println(file1.getChannels().size() + "\t"+file2.getChannels().size());
      // System.exit(9);

      for (int i = 0; i < file2.getFileHeader().getEntityCount(); i++) {
         // if (i>=5501 && i < 10000){
         // continue;
         // }

         NSNChannel channel = file2.getChannel(i);
         System.out.println("i: " + i + " size: " + file2.getFileHeader().getEntityCount() + "\t"
               + channel.getType());
         System.out.println(channel.getLabel());
         if (channel.getType() == NSNChannelType.ANALOG) {
            NSNAnalogChannel aChannel = (NSNAnalogChannel) channel;
            NSNAnalogData aData = aChannel.getData();

            // NSNAnalogData aData1 = ((NSNAnalogChannel) file1.getChannels().get(i)).getData();
            //
            // for (int d = 0; d < aData.getValues().size(); d++) {
            // for (int k = 0; k < aData.getValues().get(d).size(); k++) {
            // if (!aData.getValues().get(d).get(k).equals(aData1.getValues().get(d).get(k))) {
            // NSNAnalogChannel aC1 = (NSNAnalogChannel) file1.getChannels().get(i);
            // System.out.println("i: " + i + "\td: " + d + "\tk: " + k);
            // System.out.println("no match: " + aData.getValues().get(d).get(k) + "\t"
            // + aData1.getValues().get(d).get(k));
            // System.out.println("no match: " + Double.toHexString(aData.getValues().get(d).get(k))
            // + "\t" + Double.toHexString(aData1.getValues().get(d).get(k)));
            // System.out.println(aChannel.getLabel() + "\t" +
            // file1.getChannels().get(i).getLabel());
            // System.out.println(aChannel.getByteLength() + "\t" + aC1.getByteLength());
            // System.out.println(aData.getValues().get(d).size() + "\t"
            // + aData1.getValues().get(d).size());
            // System.out.println(aData.getValues().get(d).get(k + 1) + "\t"
            // + aData1.getValues().get(d).get(k + 1));
            // System.out.println(aData.getValues().get(d).get(k + 2) + "\t"
            // + aData1.getValues().get(d).get(k + 2));
            //
            // NSNAnalogChannel paC1 = (NSNAnalogChannel) file1.getChannels().get(i );
            // NSNAnalogChannel paC2 = (NSNAnalogChannel) file2.getChannels().get(i );
            // System.out.println(paC2.getData().getTimeStamps().size() + "\t"
            // + paC1.getData().getTimeStamps().size());
            // System.out.println(paC2.getData().getValues().get(0).size() + "\t"
            // + paC1.getData().getValues().get(0).size());
            //
            // NSNAnalogData pacD1 = paC1.getData();
            // NSNAnalogData pacD2 = paC2.getData();
            //
            // for (int x = 0; x < pacD1.getValues().get(0).size(); x++) {
            // // if (!pacD1.getValues().get(0).get(x).equals(pacD2.getValues().get(0).get(x))) {
            // System.out.println(pacD2.getValues().get(0).get(x) + "\t" +
            // pacD1.getValues().get(0).get(x));
            // // System.out.println("nono match");
            // // }
            // }
            //
            // System.exit(9);
            // }
            // }
            // }

            System.out.println(aData.getValues().get(0).get(0));
         } else if (channel.getType() == NSNChannelType.INFO_FILE) {
            NSNInfoFileChannel iChannel = (NSNInfoFileChannel) channel;
            System.out.println(iChannel.getData().getXml());
            InfoFileXMLParser parser = new InfoFileXMLParser();
            List<NSNChannelInfo> chInfos = parser.parseXML(iChannel.getData().getXml());
            System.out.println(chInfos);
         } else if (channel.getType() == NSNChannelType.SEGMENT) {
            NSNSegmentChannel sChannel1 = (NSNSegmentChannel) file1.getChannel(i);
            NSNSegmentChannel sChannel2 = (NSNSegmentChannel) file2.getChannel(i);

            NSNSegmentData sData1 = sChannel1.getData();
            NSNSegmentData sData2 = sChannel2.getData();

            for (int d = 0; d < sData2.getValues().size(); d++) {
               for (int k = 0; k < sData2.getValues().get(d).size(); k++) {
                  if (!sData2.getValues().get(d).get(k).equals(sData1.getValues().get(d).get(k))) {
                     System.out.println("i: " + i + "\td: " + d + "\tk: " + k);
                     System.out.println("no match: " + sData2.getValues().get(d).get(k) + "\t"
                           + sData1.getValues().get(d).get(k));
                     System.out.println("no match: " + Double.toHexString(sData2.getValues().get(d).get(k))
                           + "\t" + Double.toHexString(sData1.getValues().get(d).get(k)));
                     System.out.println(sChannel2.getLabel() + "\t" + sChannel1.getLabel());
                     // System.out.println(aChannel.getByteLength() + "\t" +
                     // sChannel1.getByteLength());
                     System.out.println(sData2.getValues().get(d).size() + "\t"
                           + sData1.getValues().get(d).size());
                     System.out.println(sData2.getValues().get(d).get(k + 1) + "\t"
                           + sData1.getValues().get(d).get(k + 1));
                     System.out.println(sData2.getValues().get(d).get(k + 2) + "\t"
                           + sData1.getValues().get(d).get(k + 2));

                     NSNAnalogChannel paC1 = (NSNAnalogChannel) file1.getChannel(i);
                     NSNAnalogChannel paC2 = (NSNAnalogChannel) file2.getChannel(i);
                     System.out.println(paC2.getData().getTimeStamps().size() + "\t"
                           + paC1.getData().getTimeStamps().size());
                     System.out.println(paC2.getData().getValues().get(0).size() + "\t"
                           + paC1.getData().getValues().get(0).size());

                     NSNAnalogData pacD1 = paC1.getData();
                     NSNAnalogData pacD2 = paC2.getData();

                     for (int x = 0; x < pacD1.getValues().get(0).size(); x++) {
                        // if
                        // (!pacD1.getValues().get(0).get(x).equals(pacD2.getValues().get(0).get(x)))
                        // {
                        System.out.println(pacD2.getValues().get(0).get(x) + "\t"
                              + pacD1.getValues().get(0).get(x));
                        // System.out.println("nono match");
                        // }
                     }

                     System.exit(9);
                  }
               }
            }
         }
      }
   }
}
