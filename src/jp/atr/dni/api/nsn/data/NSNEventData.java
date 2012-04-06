package jp.atr.dni.api.nsn.data;

import jp.atr.dni.api.APIData;
import jp.atr.dni.api.APIList;
import jp.atr.dni.api.nsn.header.NSNEventHeader;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public final class NSNEventData implements APIData {

   private APIList<NSNEvent> events;

   public NSNEventData(NSNEventHeader nsnEntity) {
      // Create Data List.
      events = new APIList<NSNEvent>(new NSNEventDataProvider(0, nsnEntity));
   }

   /**
    * @return the events
    */
   public APIList<NSNEvent> getEvents() {
      return events;
   }

   /**
    * @param events the events to set
    */
   public void setEvents(APIList<NSNEvent> events) {
      this.events = events;
   }
}
