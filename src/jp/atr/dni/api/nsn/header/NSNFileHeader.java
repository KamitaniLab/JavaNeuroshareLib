package jp.atr.dni.api.nsn.header;

/**
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/08
 */
public class NSNFileHeader {

   private String magicCode;

   /** Human readable file type descriptor */
   private String fileType;

   /**
    * Number of entities in the data file. This number is used to enumerate all the entities in the
    * data file from 0 to (entityCount-1) and to identify each entity in function calls (entityID);
    */
   private long entityCount;

   /**
    * Minimum timestamp resolution in seconds.
    */
   private double timeStampRes;

   /**
    * Time span covered by the data file in seconds;
    */
   private double timeSpan;

   /**
    * Information about the application that created the file.
    */
   private String appName;

   /**
    * Year.
    */
   private long year;

   /**
    * Month (1-12, where 1 = January).
    */
   private long month;

   /**
    * Day of the week (0-6, where 0 = Sunday).
    */
   private long dayOfWeek;

   /**
    * Day of the month (1-31).
    */
   private long dayOfMonth;

   /**
    * Hour since midnight (0-23).
    */
   private long hourOfDay;

   /**
    * Minute after the hour (0-59).
    */
   private long minOfDay;

   /**
    * Seconds after the minute (0-59).
    */
   private long secOfDay;

   /**
    * Milliseconds after the second (0-1000). TODO: this is what the spec says, but is it really
    * 0-999 or is a second in neuroshare really 1001ms??? The spec has other typos throughout, so
    * maybe this is one of them?
    */
   private long milliSecOfDay;

   /**
    * Extended comments.
    */
   private String comments;

   /**
    * Default constructor
    */
   public NSNFileHeader() {
      super();
   }

   /**
    * @param fileType
    * @param entityCount
    * @param timeStampRes
    * @param timeSpan
    * @param appName
    * @param year
    * @param month
    * @param dayOfWeek
    * @param dayOfMonth
    * @param hourOfDay
    * @param minofDay
    * @param secOfDay
    * @param milliSecOfDay
    * @param comments
    */
   public NSNFileHeader(String fileType, long entityCount, double timeStampRes, double timeSpan,
         String appName, long year, long month, long dayOfWeek, long dayOfMonth, long hourOfDay,
         long minofDay, long secOfDay, long milliSecOfDay, String comments) {
      super();

      if (fileType == null) {
         fileType = "";
      }

      if (appName == null) {
         appName = "";
      }

      if (comments == null) {
         comments = "";
      }

      this.fileType = fileType.trim();
      this.entityCount = entityCount;
      this.timeStampRes = timeStampRes;
      this.timeSpan = timeSpan;
      this.appName = appName.trim();
      this.year = year;
      this.month = month;
      this.dayOfWeek = dayOfWeek;
      this.dayOfMonth = dayOfMonth;
      this.hourOfDay = hourOfDay;
      this.minOfDay = minofDay;
      this.secOfDay = secOfDay;
      this.milliSecOfDay = milliSecOfDay;
      this.comments = comments.trim();
   }

   /**
    * @return the magicCode
    */
   public String getMagicCode() {
      return magicCode;
   }

   /**
    * @param magicCode the magicCode to set
    */
   public void setMagicCode(String magicCode) {
      this.magicCode = magicCode;
   }

   /**
    * @return the fileType
    */
   public String getFileType() {
      return fileType;
   }

   /**
    * @param fileType the fileType to set
    */
   public void setFileType(String fileType) {
      this.fileType = fileType;
   }

   /**
    * @return the entityCount
    */
   public long getEntityCount() {
      return entityCount;
   }

   /**
    * @param entityCount the entityCount to set
    */
   public void setEntityCount(long entityCount) {
      this.entityCount = entityCount;
   }

   /**
    * @return the timeStampeRes
    */
   public double getTimeStampRes() {
      return timeStampRes;
   }

   /**
    * @param timeStampRes the timeStampeRes to set
    */
   public void setTimeStampRes(double timeStampRes) {
      this.timeStampRes = timeStampRes;
   }

   /**
    * @return the timeSpan
    */
   public double getTimeSpan() {
      return timeSpan;
   }

   /**
    * @param timeSpan the timeSpan to set
    */
   public void setTimeSpan(double timeSpan) {
      this.timeSpan = timeSpan;
   }

   /**
    * @return the appName
    */
   public String getAppName() {
      return appName;
   }

   /**
    * @param appName the appName to set
    */
   public void setAppName(String appName) {
      this.appName = appName;
   }

   /**
    * @return the year
    */
   public long getYear() {
      return year;
   }

   /**
    * @param year the year to set
    */
   public void setYear(long year) {
      this.year = year;
   }

   /**
    * @return the month
    */
   public long getMonth() {
      return month;
   }

   /**
    * @param month the month to set
    */
   public void setMonth(long month) {
      this.month = month;
   }

   /**
    * @return the dayOfWeek
    */
   public long getDayOfWeek() {
      return dayOfWeek;
   }

   /**
    * @param dayOfWeek the dayOfWeek to set
    */
   public void setDayOfWeek(long dayOfWeek) {
      this.dayOfWeek = dayOfWeek;
   }

   /**
    * @return the dayOfMonth
    */
   public long getDayOfMonth() {
      return dayOfMonth;
   }

   /**
    * @param dayOfMonth the dayOfMonth to set
    */
   public void setDayOfMonth(long dayOfMonth) {
      this.dayOfMonth = dayOfMonth;
   }

   /**
    * @return the hourOfDay
    */
   public long getHourOfDay() {
      return hourOfDay;
   }

   /**
    * @param hourOfDay the hourOfDay to set
    */
   public void setHourOfDay(long hourOfDay) {
      this.hourOfDay = hourOfDay;
   }

   /**
    * @return the minofDay
    */
   public long getMinOfDay() {
      return minOfDay;
   }

   /**
    * @param minOfDay the minOfDay to set
    */
   public void setMinOfDay(long minOfDay) {
      this.minOfDay = minOfDay;
   }

   /**
    * @return the secOfDay
    */
   public long getSecOfDay() {
      return secOfDay;
   }

   /**
    * @param secOfDay the secOfDay to set
    */
   public void setSecOfDay(long secOfDay) {
      this.secOfDay = secOfDay;
   }

   /**
    * @return the milliSecOfDay
    */
   public long getMilliSecOfDay() {
      return milliSecOfDay;
   }

   /**
    * @param milliSecOfDay the milliSecOfDay to set
    */
   public void setMilliSecOfDay(long milliSecOfDay) {
      this.milliSecOfDay = milliSecOfDay;
   }

   /**
    * @return the comments
    */
   public String getComments() {
      return comments;
   }

   /**
    * @param comments the comments to set
    */
   public void setComments(String comments) {
      this.comments = comments;
   }
}
