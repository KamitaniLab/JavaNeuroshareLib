package jp.atr.dni.api.nsn.data;

import jp.atr.dni.api.APIData;
import jp.atr.dni.api.APIList;
import jp.atr.dni.api.nsn.header.NSNNeuralHeader;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public final class NSNNeuralSpikeData implements APIData {

   private APIList<Double> timeStamps;

   public NSNNeuralSpikeData(NSNNeuralHeader nsnEntity) {
      // Create Data List.
      timeStamps = new APIList<Double>(new NSNNeuralSpikeDataProvider(0, nsnEntity));
   }

   /**
    * @return the timeStamps
    */
   public APIList<Double> getTimeStamps() {
      return timeStamps;
   }

   /**
    * @param timeStamps the timeStamps to set
    */
   public void setTimeStamps(APIList<Double> timeStamps) {
      this.timeStamps = timeStamps;
   }
}
