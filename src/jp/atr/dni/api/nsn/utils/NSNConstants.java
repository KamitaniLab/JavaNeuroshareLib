package jp.atr.dni.api.nsn.utils;

/**
 *
 * @author Keiji Harada [*1]</br>[*1] ATR Intl. Computational Neuroscience Labs, Decoding Group
 * @version 2011/04/22
 */
public class NSNConstants {

   /**
    *
    */
   public static final int CHAR16_LENGTH = 16;

   /**
    *
    */
   public static final int CHAR32_LENGTH = 32;

   /**
    *
    */
   public static final int CHAR64_LENGTH = 64;

   /**
    *
    */
   public static final int CHAR128_LENGTH = 128;

   /**
    *
    */
   public static final int DOUBLE_BYTE_SIZE = 8;

   /**
    *
    */
   public static final int CHAR256_LENGTH = 256;

   /**
    *
    */
   public static final int NS_FILEINFO_LENGTH = 404;

   /**
    *
    */
   public static final int NS_TAGELEMENT_LENGTH = 8;

   /**
    *
    */
   public static final int NS_ENTITYINFO_LENGTH = 40;

   /**
    *
    */
   public static final int NS_EVENTINFO_LENGTH = 140;

   /**
    *
    */
   public static final int NS_ANALOGHEADER_LENGTH = 264;

   /**
    *
    */
   public static final int NS_SEGMENTINFO_LENGTH = 52;

   /**
    *
    */
   public static final int NS_SEGSOURCEINFO_LENGTH = 248;

   /**
    *
    */
   public static final int NS_NEURALINFO_LENGTH = 136;

   public static final int NS_FILEHEADER_LENGTH = 420;

   /**
    *
    */
   public static final String BLANK_CHAR16 = "                ";

   /**
    *
    */
   public static final String BLANK_CHAR32 = "                                ";

   /**
    *
    */
   public static final String BLANK_CHAR64 = "                                                                ";

   /**
    *
    */
   public static final String BLANK_CHAR128 = "                                                                                                                                ";

   /**
    *
    */
   public static final String BLANK_CHAR256 = "                                                                                                                                                                                                                                                                ";

   /**
    *
    */
   public static final String FN_HEADER = "_01_";

   /**
    *
    */
   public static final String FILE = "FILE";

   /**
    *
    */
   public static final String EVENT = "EVENT";

   /**
    *
    */
   public static final String ANALOG = "ANALOG";

   /**
    *
    */
   public static final String SEGMENT = "SEGMENT";

   /**
    *
    */
   public static final String NEURAL = "NEURAL";

   /**
    *
    */
   public static final String MAGICCODE = "NSN ver00000010";

   /**
    *
    */
   public static final String TEMPDIRNAME = ".brainliner_tmp";

   /**
    *
    */
   public static final String USERHOMEDIRPATH = System.getProperty("user.home");
}
