package jp.atr.dni.api.nsn.enums;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/17
 */
public enum NSNChannelTypeCode {
   UNDEFINED, ECOG, EEG, FMRI, MEG, MICROELECTRODE, NIRS, OPTICALIMAGING, PET, BRAIN_OTHER, MOVEMENT, STIMULUS, TASK, BEHAVIOR_OTHER, COMMENT;

   public static NSNChannelTypeCode fromInt(int val) {
      if (val == 110) {
         return ECOG;
      } else if (val == 120) {
         return EEG;
      } else if (val == 130) {
         return FMRI;
      } else if (val == 140) {
         return MEG;
      } else if (val == 150) {
         return MICROELECTRODE;
      } else if (val == 160) {
         return NIRS;
      } else if (val == 170) {
         return OPTICALIMAGING;
      } else if (val == 180) {
         return PET;
      } else if (val == 300) {
         return BRAIN_OTHER;
      } else if (val == 410) {
         return MOVEMENT;
      } else if (val == 510) {
         return STIMULUS;
      } else if (val == 610) {
         return TASK;
      } else if (val == 700) {
         return BEHAVIOR_OTHER;
      } else if (val == 900) {
         return COMMENT;
      } else {
         return UNDEFINED;
      }
   }

   public static NSNChannelTypeCode fromString(String val) {
      if (val.equalsIgnoreCase("ECOG")) {
         return ECOG;
      } else if (val.equalsIgnoreCase("EEG")) {
         return EEG;
      } else if (val.equalsIgnoreCase("FMRI")) {
         return FMRI;
      } else if (val.equalsIgnoreCase("MEG")) {
         return MEG;
      } else if (val.equalsIgnoreCase("MICROELECTRODE")) {
         return MICROELECTRODE;
      } else if (val.equalsIgnoreCase("NIRS")) {
         return NIRS;
      } else if (val.equalsIgnoreCase("OPTICALIMAGING")) {
         return OPTICALIMAGING;
      } else if (val.equalsIgnoreCase("PET")) {
         return PET;
      } else if (val.equalsIgnoreCase("BRAIN_OTHER")) {
         return BRAIN_OTHER;
      } else if (val.equalsIgnoreCase("MOVEMENT")) {
         return MOVEMENT;
      } else if (val.equalsIgnoreCase("STIMULUS")) {
         return STIMULUS;
      } else if (val.equalsIgnoreCase("TASK")) {
         return TASK;
      } else if (val.equalsIgnoreCase("BEHAVIOR_OTHER")) {
         return BEHAVIOR_OTHER;
      } else if (val.equalsIgnoreCase("COMMENT")) {
         return COMMENT;
      } else {
         return UNDEFINED;
      }
   }

   public int toInt() {
      if (this == ECOG) {
         return 110;
      } else if (this == EEG) {
         return 120;
      } else if (this == FMRI) {
         return 130;
      } else if (this == MEG) {
         return 140;
      } else if (this == MICROELECTRODE) {
         return 150;
      } else if (this == NIRS) {
         return 160;
      } else if (this == OPTICALIMAGING) {
         return 170;
      } else if (this == PET) {
         return 180;
      } else if (this == BRAIN_OTHER) {
         return 300;
      } else if (this == MOVEMENT) {
         return 410;
      } else if (this == STIMULUS) {
         return 510;
      } else if (this == TASK) {
         return 610;
      } else if (this == BEHAVIOR_OTHER) {
         return 700;
      } else if (this == COMMENT) {
         return 900;
      } else {
         return 000;
      }
   }

   public String toString() {
      if (this == ECOG) {
         return "ECoG";
      } else if (this == EEG) {
         return "EEG";
      } else if (this == FMRI) {
         return "fMRI";
      } else if (this == MEG) {
         return "MEG";
      } else if (this == MICROELECTRODE) {
         return "microelctrode";
      } else if (this == NIRS) {
         return "NIRS";
      } else if (this == OPTICALIMAGING) {
         return "optical imaging";
      } else if (this == PET) {
         return "PET";
      } else if (this == BRAIN_OTHER) {
         return "Brain: Other";
      } else if (this == MOVEMENT) {
         return "movement";
      } else if (this == STIMULUS) {
         return "stimulus";
      } else if (this == TASK) {
         return "task";
      } else if (this == BEHAVIOR_OTHER) {
         return "Behavior: Other";
      } else if (this == COMMENT) {
         return "comment";
      } else {
         return "undefined";
      }
   }
}