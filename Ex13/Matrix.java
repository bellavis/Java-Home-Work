
/**
 * Class Matrix represents a two-dimensional array- matrix of numbers
 * @author Bell Avisar
 * @version 0.5
 * date 28/12/2019
 */
public class Matrix {

    private int[][] _matrix;
    final int DEFAULT_VALUE=0;
    /**
     * Consturcts a size1 by size2 Matrix of zeros.
     * @param size1 represents the number of rows
     * @param size2 represents the number of columns
     */ 
    public Matrix(int size1,int size2) {
        _matrix = new int[size1][size2];
        for(int i =0 ; i < _matrix.length;i++){
            for(int j = 0 ; j < _matrix[i].length; j++){
                _matrix[i][j] = DEFAULT_VALUE;
            }
        }
    }

    /**
     * Consturcts a Matrix from a two-dimentional array
     * <>br> the dimentions as well as the value of the Matrix will be the same
     * <br> as the dimentions and values of the two-dimentional array.
     * @param matrix represents a two-dimentional array
     */ 
    public Matrix (int[][] array){
        int size1=array.length;
        int size2=array[0].length;
        _matrix = new int[size1][size2];
        for(int i =0 ; i < size1 ; i++){
            for(int j = 0 ; j < size2 ; j++){
                _matrix[i][j] = array[i][j];
            }
        }
    }

    /**
     * represents a string of the Matrix in this format:
     * <br> [0][0]+tab+[0][1]...
     * <br>(new line)[1][0]+tab+[1][1]... 
     * @return Matrix String
     */ 
    public String toString(){
        String matrix="";
        for(int i =0 ; i < _matrix.length;i++){
            for(int j = 0 ; j < _matrix[i].length; j++){
                if(j != _matrix[i].length-1)
                    matrix+=_matrix[i][j]+ "\t";
                else
                    matrix+=_matrix[i][j];
            }
            if(i != _matrix.length-1)
                matrix+="\n";
        }
        return matrix;
    }

    /**
     * Makes a negative image of a matrix
     * @return Matrix String of the new negative matrix
     */ 
    public Matrix makeNegative(){
        int[][] newArray = new int[_matrix.length][_matrix[0].length];

        for(int i =0 ; i < _matrix.length;i++){
            for(int j = 0 ; j < _matrix[i].length; j++){
                newArray[i][j] = _matrix[i][j];
                newArray[i][j] = Math.abs(255-newArray[i][j]);
            }
        }
        return new Matrix(newArray);
    }
    //checks if an index exist
    private boolean isValid(int i,int j){
        if(i >= _matrix.length || j>=_matrix[0].length || i<0 || j<0){
                //neighbor in this index does not exist
                return false;
        }else{
                //neighbor in this index does exist
                return true;
        }
    }
    // sum of all neighbors values + value in "home" index
    private int sumNeighborsVal(int i, int j){
        int sum=_matrix[i][j];
        // check 1 row above
        if(isValid(i-1,j-1)){
            sum+=_matrix[i-1][j-1];
        }
        if(isValid(i-1,j)){
            sum+=_matrix[i-1][j];
        }
        if(isValid(i-1,j+1)){
            sum+=_matrix[i-1][j+1];
        }
        //check same row
        if(isValid(i,j-1)){
            sum+=_matrix[i][j-1];
        }
        if(isValid(i,j+1)){
            sum+=_matrix[i][j+1];
        }
        //check one row below
        if(isValid(i+1,j-1)){
            sum+=_matrix[i+1][j-1];
        }
        if(isValid(i+1,j)){
            sum+=_matrix[i+1][j];
        }
        if(isValid(i+1,j+1)){
            sum+=_matrix[i+1][j+1];
        }
        return sum;
    }

    /**
     * Makes a smoother image of a copy of a matrix
     * @return Matrix String of the copied new smoother matrix
     */ 
    public Matrix imageFilterAverage() {
        int[][] newArray = new int[_matrix.length][_matrix[0].length];
        int sum=0;
        for(int i =0 ; i < _matrix.length;i++){
            for(int j = 0 ; j < _matrix[i].length; j++){
                //3 neighbors 
                if(i==0 && j==0 || j==_matrix[i].length-1 && i==0){
                    sum=sumNeighborsVal(i,j);
                    newArray[i][j]=sum/4;
                }
                if(i==_matrix.length-1 && j==0 || i==_matrix.length-1 && j==_matrix[i].length-1){
                    sum=sumNeighborsVal(i,j);
                    newArray[i][j]=sum/4;
                }
                //5 neighbors
                if(i==_matrix.length-1 && j<_matrix[i].length-1 && j>0 
                ||i==0 && j<_matrix[i].length-1 && j>0
                ||j==_matrix[i].length-1 && i<_matrix.length-1 && i>0
                ||j==0 && i<_matrix.length-1 && i>0)
                {
                    sum=sumNeighborsVal(i,j);
                    newArray[i][j]=sum/6;  
                }
                //8 neighbors
                if(i>0 && i<_matrix.length-1 
                && j>0 && j<_matrix[i].length-1)
                {
                    sum=sumNeighborsVal(i,j);
                    newArray[i][j]=sum/9;  
                }
                sum=0;
            }
        }
        return new Matrix(newArray);
    }


    /**
     * rotates a copy of a matrix 90 degrees clockwise
     * @return the copied clockwise rotated matrix
     */
    public Matrix rotateClockwise(){
        int[][] newArray = new int[_matrix[0].length][_matrix.length];
        //runs from the last index of existing matrix and from the first index of the new matrix
        //x0, x1 = index of existing matrix, y0, y1 = index of new matrix
        for (int y0=_matrix.length-1 , x1=0 ; y0>=0 && x1<newArray[0].length ; x1++, y0--){
            //runs from the first index of existing matrix and first index of the new matrix
            for(int x0=0, y1=0 ; x0<_matrix[0].length && y1<newArray.length ; x0++, y1++){
                newArray[y1][x1] = _matrix[y0][x0];
            }
        }
        return new Matrix(newArray);
    }

    /**
     * rotates a copy of a matrix 90 degrees counter clockwise
     * @return the copied counter clockwise rotated matrix
     */
    public Matrix rotateCounterClockwise(){ 
        int[][] newArray = new int[_matrix[0].length][_matrix.length];
        //runs from the first index of existing matrix and from the last index of the new matrix
        //x0, x1 = index of existing matrix, y0, y1 = index of new matrix
        for (int x0=0, y1=newArray.length-1 ; y1>=0 && x0<newArray.length ; x0++ ,y1--){
            //runs from the first index of existing matrix and first index of the new matrix
            for(int x1=0, y0=0 ; y0<newArray[0].length ; y0++, x1++){
                newArray[y1][x1] = _matrix[y0][x0];
            }
        }
        return new Matrix(newArray);
    }
    
    
}
