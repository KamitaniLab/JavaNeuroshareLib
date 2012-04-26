/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2012/04/06
 */
package jp.atr.dni.api.examples;

import java.io.IOException;

import jp.atr.dni.api.nsn.NSNFile;
import jp.atr.dni.api.nsn.channels.NSNInfoFileChannel;
import jp.atr.dni.api.nsn.header.NSNFileHeader;
import jp.atr.dni.api.nsn.reader.NSNReader;
import jp.atr.dni.api.nsn.writer.NSNWriter;

/**
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2012/04/26
 */
public class WriterExample {

   /**
    * This is an example class illustrating how to write neuroshare files using our Java library.
    * 
    * @param args
    * @throws IOException 
    */
   public static void main(String[] args) throws IOException {
      if (args.length < 1) {
         System.out.println("Usage: filePath");
         System.exit(-1337);
      }
      String filePath = args[0];

      NSNReader reader = new NSNReader();

      // read the specified nsn file
      NSNFile file1 = reader.readNSNFileAllData(filePath);

      // add channel
      NSNInfoFileChannel iChannel = new NSNInfoFileChannel(
            (int) (file1.getFileHeader().getEntityCount() + 1),
            "<?xml version=\"1.0\" ?><chInfo><ch num=\"1\"><neuroshareType>2</neuroshareType><chName>Fpz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"2\"><neuroshareType>2</neuroshareType><chName>Fz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"3\"><neuroshareType>2</neuroshareType><chName>Cz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"4\"><neuroshareType>2</neuroshareType><chName>Pz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch><ch num=\"5\"><neuroshareType>2</neuroshareType><chName>Oz</chName><chTypeCode>120</chTypeCode><chType>EEG</chType></ch></chInfo>");
      file1.addChannel(iChannel);

      // edit the header's comments
      file1.getFileHeader().setComments("just added a new info channel with our xml data");

      // write the file
      NSNWriter nsnWriter = new NSNWriter();
      System.out.println("writing...");
      nsnWriter.writeNSNFile("myFile", file1);
      System.out.println("just wrote a new nsn file with some bogus xml added to it");

      // read in the file we just wrote
      NSNFile file2 = reader.readNSNFileAllData("myFile");

      NSNFileHeader header = file2.getFileHeader();
      System.out.println("[header.comments]: " + header.getComments());
   }
}
