package jp.atr.dni.api.nsn;

import jp.atr.dni.api.APIList;
import jp.atr.dni.api.nsn.channels.NSNChannel;
import jp.atr.dni.api.nsn.header.NSNFileHeader;

/**
 * 
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public class NSNFile {

   private NSNFileHeader fileHeader;

   private APIList<NSNChannel> channels;

   /**
    *Default constructor.
    */
   public NSNFile() {
      super();
   }

   /**
    * 
    * @param magicCode
    * @param fileHeader
    * @param channels
    */
   public NSNFile(NSNFileHeader fileHeader, APIList<NSNChannel> channels) {
      super();
      this.fileHeader = fileHeader;
      this.channels = channels;
   }

   public void addChannel(NSNChannel channel) {
      channels.add(channel);
      fileHeader.setEntityCount(fileHeader.getEntityCount() + 1);
   }

   /**
    * @return the fileHeader
    */
   public NSNFileHeader getFileHeader() {
      return fileHeader;
   }

   /**
    * @param fileHeader the fileHeader to set
    */
   public void setFileHeader(NSNFileHeader fileHeader) {
      this.fileHeader = fileHeader;
   }

   /**
    * @return the channels
    */
   public NSNChannel getChannel(int ndx) {
      return channels.get(ndx);
   }

   /**
    * @return the channels
    */
   public APIList<NSNChannel> getChannels() {
      return channels;
   }

   /**
    * @param channels the channels to set
    */
   public void setChannels(APIList<NSNChannel> channels) {
      this.channels = channels;
   }

   // /**
   // * @param channels the channels to set
   // */
   // public void setChannels(APIList<NSNChannel> channels) {
   // this.channels = channels;
   // }
}