package jp.atr.dni.api.nsn.enums;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public enum NSNChannelType {

   /** Times for neural events. */
   NEURAL_SPIKE,
   /** Times and associated values for analog data. */
   ANALOG,
   /** Times and event labels. */
   EVENT,
   /** Times and associated values with segment ID. */
   SEGMENT,
   /** General information stored in a free format. */
   INFO_FILE,
   /** Other/Unknown */
   UNKNOWN;

   @Override
   public String toString() {
      if (this == NEURAL_SPIKE) {
         return "neural spike";
      } else if (this == ANALOG) {
         return "analog";
      } else if (this == EVENT) {
         return "event";
      } else if (this == SEGMENT) {
         return "segment";
      } else if (this == INFO_FILE) {
         return "information";
      } else {
         return "unknown";
      }
   }

   public int toInt() {
      if (this == EVENT) {
         return 1;
      } else if (this == ANALOG) {
         return 2;
      } else if (this == SEGMENT) {
         return 3;
      } else if (this == NEURAL_SPIKE) {
         return 4;
      } else if (this == INFO_FILE) {
         return 5;
      } else {
         return 0;
      }
   }

   /**
    * 
    * @param nsnIdentifier
    * @return
    */
   public static NSNChannelType fromInt(int nsnIdentifier) {
      if (nsnIdentifier == 1) {
         return EVENT;
      } else if (nsnIdentifier == 2) {
         return ANALOG;
      } else if (nsnIdentifier == 3) {
         return SEGMENT;
      } else if (nsnIdentifier == 4) {
         return NEURAL_SPIKE;
      } else if (nsnIdentifier == 5) {
         return INFO_FILE;
      } else {
         return UNKNOWN;
      }
   }
}
