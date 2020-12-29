
/**
 * This class represents a Date object.
 *
 * @author Bell Avisar
 * @version 07/12/2019
 */

public class Date
{
    //Declaration - object attributes
    private int _day;
    private int _month;
    private int _year;
    //months of the year
    final int JAN = 1;
    final int FEB = 2;
    final int MRC = 3;
    final int APR = 4;
    final int MAY = 5;
    final int JUN = 6;
    final int JUL = 7;
    final int AUG = 8;
    final int SEP = 9;
    final int OCT = 10;
    final int NOV = 11;
    final int DEC = 12;
    //possible maximum days in a certain month
    final int SHORT_MONTH = 30;
    final int LONG_MONTH = 31;
    final int LEAPY_FEB_DAYS = 29;
    final int REG_FEB_DAYS = 28;
    //default date
    final int DEFAULT_MONTH = JAN;
    final int DEFAULT_DAY = 1;
    final int DEFAULT_YEAR = 2000;
    //first day of a month
    final int FIRST_DAY = 1;
    //Min and max years
    final int MIN_YEAR = 1000;
    final int MAX_YEAR = 9999;

    //constructors:
    /**
     * creates a new Date object if the date is valid, 
     * otherwise creates the date 1/1/2000
     * @param _day the day in the month(1-31)
     * @param _month the month in the year
     * @param _year the year (in 4 digits)
     */

    public Date(int day, int month, int year) {
        _day = day;
        _month = month;
        _year = year;
        this.validDate(day,month,year);
    }// SHAY: Correct

    /**
     * Copy Constructor
     * @param other the date to be copied
     */
    public Date(Date other){
        _day = other._day;
        _month = other._month;
        _year = other._year;
    }

    private void validDate(int day,int month,int year) {
        if (validYear(year)){
            _year = year;
            if (validMonth (month)){
                _month = month;
                if (validDay(day)){
                    _day = day;
                }
                else{
                    this.toDefaultDate();   
                }     
            }
            else{
                this.toDefaultDate();   
            }
        } 
        else{
            this.toDefaultDate();
        }
    }

    public void toDefaultDate (){
        _year= DEFAULT_YEAR;
        _month = JAN;
        _day = DEFAULT_DAY;
    }

    /** 
     * gets the year
     * @return the year
     */
    public int getYear(){
        return _year;
    }

    /** 
     * gets the month
     * @return the month
     */
    public int getMonth(){
        return _month;
    }

    /**
     * gets the day
     * @return the day
     */
    public int getDay(){
        return _day;
    }

    // check if year is a leap year
    private boolean leapYear (int year){
        if ((year%4 == 0 && !(((year%100)==0)) || (year%400) == 0)) 
            return true;
        return false;
    }

    // check is day is valid (February has 28-29 days depends on leap year, other months have 30-31 days)
    private boolean validDay (int day){
        if (day<1)
            return false;
        if(_month == JAN || _month == MRC || _month == MAY || _month == JUL || _month == AUG || _month == OCT || _month == DEC){
            if (day<=LONG_MONTH)
                return true;
            return false;
        }
        if(_month == APR || _month == JUN || _month == SEP || _month == NOV){
            if (day<=SHORT_MONTH)
                return true;
            return false;
        }
        if (_month == (FEB)){
            if(leapYear(_year)) {
                if(day<=LEAPY_FEB_DAYS)
                    return true;
                return false;
            }
            else {
                if(day<=REG_FEB_DAYS)
                    return true;
                return false;
            }
        }
        return false;
    }

    // check is month is valid
    private boolean validMonth (int month){
        if (month>=JAN && month<=DEC)
            return true;
        return false;
    }

    // check is year is valid
    private boolean validYear (int year){
        if (year>= MIN_YEAR && year<=MAX_YEAR)
            return true;
        return false;
    }

    /** 
     * sets the day (only if date remains valid)
     * @param dayToSet the day value to be set 
     */
    public void setDay(int dayToSet){
        if (validDay (dayToSet) && validMonth(_month) && validYear(_year)){
            _day = dayToSet;
        }
    }   

    /** 
     * sets the month (only if date remains valid)
     * @param monthToSet the month value to be set 
     */
    public void setMonth( int monthToSet){
        if (validDay (_day) && validMonth(monthToSet) && validYear(_year)){
            _month = monthToSet;
        }
    }

    /** 
     * sets the year (only if date remains valid)
     * @param yearToSet the year value to be set 
     */
    public void setYear( int yearToSet){
        if (validDay (_day) && validMonth(_month) && validYear(yearToSet)){
            _year = yearToSet;
        }
    }

    /**
     * check if 2 dates are the same
     * @param other the date to compare this date to
     * @return true if the dates are the same 
     */

    public boolean equals (Date other){
        if((_day==other._day)&&(_month==other._month)&&(_year==other._year))
            return true;
        return false;
    }

    /**
     * Check if this date is before other date
     * @param other date to compare this date to
     * @return rue if this date is before other date
     */
    public boolean before (Date other){
        if (this._year < other._year)
            return true;
        if  (this._year > other._year)
            return false;
        //same year  

        if(this._month<other._month)
            return true;
        if(this._month>other._month)
            return false;   
        //same month   

        if(this._day<other._day)
            return true;
        if(this._day>other._day)
            return false;
        //same date
        return false; //same date
    }

    /**
     * Date is after other date
     * @param other the date to compare this date to
     * @return true if this date is after other date 
     */
    public boolean after (Date other){
        return other.before(this);
    }
    //given calculation for - days since the beginning of gregorian calendar 
    private int calculateDate () {
        int year= _year;
        int day= _day;
        int month= _month;

        if (month < 3) {
            year--;
            month = month + 12;
        }
        return 365 * year + year/4 - year/100 + year/400 + ((month+1) * 306)/10 + (day - 62);
    } 

    /**
     * Calculates the difference in days between two dates
     * @param other the date to calculate the difference between
     * @return the number of days between the dates
     */
    public int difference (Date other){
        int difference = Math.abs(this.calculateDate() - other.calculateDate());
        return difference;
    }   

    /**
     * calculate the date of tomorrow
     * @return the date of tomorrow
     */
    public Date tomorrow (){
        int year= _year;
        int day= _day;
        int month= _month;

        if(day== REG_FEB_DAYS || day ==LEAPY_FEB_DAYS || day==SHORT_MONTH || day==LONG_MONTH){ //end of months days
            //day of long months ( have 31 days) is 30
            if((month == JAN || month == MRC || month == MAY || month == JUL || month == AUG || month == OCT || month == DEC)&& day==SHORT_MONTH){             
                day++;
            }
            else{ //day = 30 for short month and 31 for long months
                if(month == DEC && day==LONG_MONTH){ //end of a year
                    day=FIRST_DAY;
                    month=JAN;
                    year++;
                }
                else{
                    month++;
                    day=FIRST_DAY;

                }
            }
            if(month == FEB){
                if(leapYear(year) && day == LEAPY_FEB_DAYS){ //end of february during leap year
                    month++;
                    day=FIRST_DAY;
                }
                if(leapYear(year) && day == REG_FEB_DAYS){ //february during leap year on day 28 
                    day++;
                }
                if(!leapYear(year)){ //february not during leap year on day 28 (end of the month)
                    month++;
                    day=FIRST_DAY;
                }
            }
        }
        else{// it's not an end of a month
            day++;
        }
        Date tomorrow = new Date(day, month, year);
        return tomorrow;
    }

    /**
     * Calculate the day of the week that this date occurs on 
     * 0-Saturday 1-Sunday 2-Monday etc.
     * @return the day of the week that this date occurs on
     */
    public String dayInWeek (){
        int year= _year;
        int day= _day;
        int month= _month;

        if(month==FEB){
            month = 14;
            year--;
        }
        if(month==JAN){
            month = 13;
            year--;
        }
        int Y = (year%100);
        int C = (year/100);     
        int dayInWeek = (day + (26*(month+1))/10 + Y + Y/4 + C/4 - 2*C); // calculates the day of the week 
        dayInWeek=(dayInWeek%7);
        dayInWeek= Math.floorMod(dayInWeek,7); //outputs the numbers 0-6, 0= Saturday, 1=Sunday, etc...
        switch(dayInWeek){
            case 0:
            return "Saturday";
            case 1:
            return "Sunday";
            case 2:
            return "Monday";
            case 3:
            return "Tuesday";
            case 4:
            return "Wednesday";
            case 5:
            return "Thuesday";
            case 6:
            return "Friday";
        }
        return "Somthing is wrong";
    }

    /**
     * Returns a String that represents this date
     * @return String that represents this date in the following format: 
     * day/month/year for example: 02/03/1998
     */
    public String toString (){
        String s="";
        if(this._day<10)
            s+= "0"+this._day+"/";
        else
            s+=this._day + "/";
        if(this._month<10)
            s+= "0"+this._month+"/";
        else
            s+=this._month+"/";
        s+= this._year;
        return s;
    }// SHAY: Correct
}
