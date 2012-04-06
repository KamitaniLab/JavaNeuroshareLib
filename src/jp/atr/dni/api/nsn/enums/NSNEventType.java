/**
 * 
 */
package jp.atr.dni.api.nsn.enums;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Conputational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public enum NSNEventType {

   /**
    *
    */
   TEXT,
   /**
    * 
    */
   CSV,
   /**
    *
    */
   BYTE,
   /**
    * 
    */
   WORD,
   /**
    *
    */
   DWORD;

   /**
    *
    * @param identifier
    * @return
    */
   public static NSNEventType fromInt(int identifier) {
      if (identifier == 0) {
         return TEXT;
      } else if (identifier == 1) {
         return CSV;
      } else if (identifier == 2) {
         return BYTE;
      } else if (identifier == 3) {
         return WORD;
      } else if (identifier == 4) {
         return DWORD;
      } else {
         return null;
      }
   }

   public int toInt() {
      if (this == TEXT) {
         return 0;
      } else if (this == CSV) {
         return 1;
      } else if (this == BYTE) {
         return 2;
      } else if (this == WORD) {
         return 3;
      } else if (this == DWORD) {
         return 4;
      } else {
         return -1;
      }
   }
}
