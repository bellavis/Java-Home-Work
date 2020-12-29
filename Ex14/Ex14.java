
/**
 * Assignment  14
 * Bell Avisar
 * @version 6 - 01/02/2020
 */
public class Ex14
{
    //Question 1.a
    /**
     * count how many sub strings (that start and end with char c and have one char c between) are inside an original given string<br>
     * Space complexity- O(1), time complexity-O(n)
     * @param s original string
     * @param c a char to start, end and appear one time in a sub string
     * @return the total count of the sub strings within the original string
     */
    public static int subStrC (String s, char c)
    {
        int count=0;
        for(int i=0; i < s.length(); i++)
        {
            if(s.charAt(i)==c)
                count++;
        }
        if (count<3)
            return 0;
        else 
            return (count-2);
    }// SHAY: Correct

    //Question 1.b
    /**
     * count how many sub strings (that start and end with char c and have k times char c between) ares inside an original given string<br>
     * Space complexity- O(1), time complexity-O(n)
     * @param s original string
     * @param c a char to start, end and appear maximum k times in a sub string
     * @param k the maximum number that char c could be in a sub string that starts and end with char c
     * @return the total count of the sub strings within the original string
     */
    public static int subStrMaxC (String s, char c,int k)
    {
        int count=0;
        int sum=0;  
        int temp=0;
        for(int i=0; i < s.length(); i++)
        {
            if(s.charAt(i)==c)
                count++;
        }
        for (int j=0;j<k+1;j++){
            temp=((count)-(j+1));
            if (temp>0)
                sum+= temp;
            temp=0;
        }
        return sum;// SHAY: Correct
    }

    //Question 2
    /**
     * change the 1's in a given array to the distance from the nearest 0 value in the array<br>
     * Space complexity- O(1) time complexity-O(n)
     * @param a one dimentional array - contains only the numbers 0 and 1
     */
    public static void zeroDistance (int [] a)
    {
        int zeroFlag=-1;
        for(int i=0; i < a.length; i++){//running from begginng of the array to change 1's close to 0 from the left
            if (a[i]==0){
                zeroFlag=i;}
            if (a[i]==1 && zeroFlag != -1 ){
                a[i]= Math.abs(zeroFlag-i);}  
        }
        zeroFlag=-1;
        for(int i=a.length-1; i >= 0; i--){//running from the end of the array to change 1's close to 0 from the right
            if (a[i]==0) //mark where is a 0
                zeroFlag=i;
            //if a zero has marked, change values that are not 0 to their distance from the marked 0 (zeroFlag) only if:
            //a[i] value is the same as the as a[i-1], or the distance of a[i] from the marked 0 is smaller or equal to a[i-1] value
            if (a[i]!=0 && zeroFlag != -1 && i>0)
                if (a[i-1]==a[i] || Math.abs(zeroFlag-i) <= a[i-1])
                    a[i]= Math.abs(zeroFlag-i);   
            if (a[i]!=0 && i==0 && a[i+1]>=a[i]) 
                a[i]= Math.abs(zeroFlag-i);
        }
    }// SHAY: Correct

    //Question 3
    /**
     * check if a string is the same as the original string but after a transformation that made it duplicate it's letters unknown number of times
     * @param s original string
     * @param t string to check
     * @return true if it's a string that has been through a transformation from the original string
     */
    public static boolean isTrans (String s, String t)
    {
        char flag;
        if (s.equals(""))//mark the first character of string s
            flag= ' ';
        else
            flag= s.charAt(0);

        return isTrans(s,t,flag);
    }

    private static boolean isTrans (String s, String t, char flag)
    {
        //------------base cases---------------// 

        if(s.equals(t)){ //both strings are equal
            return true;
        }
        if(s.length()==0){ //string s is empty
            if(t.length()==0) //string t is empty
                return true;
            if (t.charAt(0)==flag && t.substring(1).equals(""))//string t first character equals to flag and there are no more characters in t string
                return true;
            if(t.length()!=0)
                return false;
            else 
                return false;    
        }
        if (t.length()==0) //string t is empty but s is not 
            return false;

        //---------------recalls---------------//

        //check if strings starts with the same character and t second character isn't equal to flag (s previous first character)
        if (s.charAt(0) == t.charAt(0) && t.charAt(1)!= flag ){ 
            flag= s.charAt(0);
            return isTrans(s.substring(1),t.substring(1),flag); //recall method without both strings first character and the new flag
        }
        //check if string t first character equals to string s previous first character
        if (t.charAt(0)==flag){
            flag= s.charAt(0);
            return isTrans(s.substring(0),t.substring(1),flag);// recall method without string "t" first character and the new flag
        }// SHAY: Missing the backtrack here (-15)
        return false;
    }

    //Question 4
    /**
     * find the numbers of legal paths from the first spot of a 2D array <br>
     * to the last spot.<br>
     * Can move in two ways:<br>
     * 1. move along the columns with the UNITS of the number inside a specific spot and move down the rows with the TENS.<br>
     * 2. move along the columns with the TENS of the number inside a specific spot and move down the rows with the UNITS.
     * @param a two diemetional array
     * @return number of legal paths from the first spot to the last
     */
    public static int countPaths (int [][] mat)
    {
        int maxRow = mat.length-1;    
        int maxCol = mat[0].length-1;  
        int res=0;
        return countPaths(mat, 0, 0, maxRow, maxCol, res);
    }
    // calculate the spot in the matrices after moving in 2 ways possible and check if this spot excist in the given 2d array
    private static int countPaths (int [][] mat,int r, int c, int maxRow,int maxCol, int res){
        if (c>maxCol || r>maxRow || mat[r][c]==0) // exceed array limits
            return 0;
        if (c==maxCol && r==maxRow)// index is at the last spot of the array
            return 1;
        int units,tens,flag1,flag2,flag3,flag4;
        units = mat[r][c] % 10;
        tens = mat[r][c] / 10;
        int canMoveInTheCol= (maxCol)-(c);  
        int canMoveInTheRow= (maxRow)-(r);
        int r2=r;
        int c2=c;
        flag1 = flag2 = flag3 = flag4 = 0;

        //way 1: check if legal to add the tens to rows ----- and units to the column
        if (units <= canMoveInTheCol && maxCol != 0){ // there are enough spots to move along the column before moving rows down
            r += tens;
            c += units;
        }
        if ((units > canMoveInTheCol || tens > canMoveInTheRow) && maxCol != 0){ 
            flag1=1;
        }
        if (maxCol==0){
            if (units == 0 && tens<=canMoveInTheRow)
                r += tens;
            else
                flag3 = 1;
        }// SHAY: Correct

        //way 2 : check if legal to add the tens to the column and units to the rows
        if (tens <= canMoveInTheCol && units <= canMoveInTheRow && maxCol != 0){
            c2 += tens;
            r2 += units;
        }
        if ((tens > canMoveInTheCol || units > canMoveInTheRow) && maxCol != 0){  
            flag2 = 1;
        }
        if (maxCol==0){
            if (tens == 0 && units<=canMoveInTheRow)
                r2 += units;
            else
                flag4 = 1;
        }

        //------------------------------recall------------------------------//
        if (flag1==1 && flag2==1)           
            return 0 ;   
        if (flag1==0 && flag2==0){ //if after adding tens to rows and units to the column, and the opposite, and index is still legal
            if (units != tens && maxCol != 0) //if units equals to tens or the array has only one column, count only one path
                res+= countPaths(mat, r, c, maxRow, maxCol, res)+ countPaths(mat, r2, c2, maxRow, maxCol, res);
            if (maxCol == 0){
                if (units != tens){
                    if (flag3==0 && flag4==0)
                        res+= countPaths(mat, r, c, maxRow, maxCol, res)+countPaths(mat, r2, c2, maxRow, maxCol, res);
                    if (flag3==1 && flag4==0)
                        res+= countPaths(mat, r2, c2, maxRow, maxCol, res);
                    if (flag3==0 && flag4==1)
                        res+= countPaths(mat, r, c, maxRow, maxCol, res);
                    if (flag3==1 && flag4==1)
                        return 0;  
                }
                if (units == tens)
                    res+= countPaths(mat, r, c, maxRow, maxCol, res);
            }
            if (maxCol != 0 && units == tens) // calculate one option if units == tens
                res += countPaths(mat, r, c, maxRow, maxCol, res);
        }    
        //if after adding tens to columns and units to rows, index is still legal, 
        //but with the opposite it's not, continue with the legal way 
        if (flag1==1 && flag2==0)           
            res += countPaths(mat, r2, c2, maxRow, maxCol, res);    
        //if after adding tens to rows and units to column, index is still legal,
        //but with the opposite it's not, continue with the legal way 
        if (flag1==0 && flag2==1)           
            res += countPaths(mat, r, c, maxRow, maxCol, res);
        return res;
    }
}
