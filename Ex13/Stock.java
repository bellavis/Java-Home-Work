
/**
 * Represents information about fooditems
 *
 * @author Bell Avisar
 * @version 1.3
 * date 26/12/2019
 */
public class Stock
{

    //instance variables
    private FoodItem[] _stock;
    private int _noOfItems;

    private final int NO_ITEMS = 0;
    private final int MAX_ITEMS_IN_STOCK = 100; 
    
    /**
     * Stock constructor create a FoodItem array with up to 100 FoodItems.
     */
    public Stock(){
        //creates an object of fooditem arrays with max of 100 items
        _stock= new FoodItem[MAX_ITEMS_IN_STOCK];
        _noOfItems= NO_ITEMS;
    }

    /**
     * gets the number of FoodItems in the Stock array
     * @return number of FoodItems in the Stock array
     */
    public int getNumOfItems(){// how many food items are in the array
        return _noOfItems;
    }

    /**
     * add a food item to the Stock array
     * if stock is full, return false, or add quantity only if item with the same 
     * <br> name and catalogue number already excist in the array 
     * @param newItem Food item to add to _stock array
     * @return true if item added successfully, false if not
     */
    public boolean addItem(FoodItem newItem)
    {
        if(_noOfItems == MAX_ITEMS_IN_STOCK){ //stock is full
            if(addQuantity(newItem))//if it's the same item- add the quantity of newitem
                return true;
            else
                return false;// no room for another food item
        }
        if(_noOfItems == NO_ITEMS){// array is empty
            _stock[0]= new FoodItem(newItem);
            _noOfItems++; 
            return true;
        }
        if(_noOfItems > NO_ITEMS && _noOfItems<MAX_ITEMS_IN_STOCK){
            if(addQuantity(newItem))//if it's the same item- add the quantity of newitem
                return true;
            for(int i = 0; i < _noOfItems; i++){
                if(_stock[i].getCatalogueNumber() == newItem.getCatalogueNumber() &&
                _stock[i].getName().equals(newItem.getName()))
                {
                    if(InsertSort(newItem ,i))//sort to be before the exicting item
                        return true;
                }
            }
            for(int i = 0; i < _noOfItems; i++){
                if(InsertSort(newItem ,-1)) // new item - has to be sorted by catalouge number

                    return true;
            }   
        }
        return false;
    }

    /**
     * add quantity to a food item to the Stock array
     * @param newItem Food item to add to the Stock array
     * @return true if item added successfully, false if not
     */
    private boolean addQuantity(FoodItem newItem){
        for(int i = 0; i < _noOfItems; i++){
            //food item exist, adding quantity
            if (_stock[i].getCatalogueNumber() == newItem.getCatalogueNumber() &&
            _stock[i].getName().equals(newItem.getName()) &&
            _stock[i].getExpiryDate().equals(newItem.getExpiryDate()) &&
            _stock[i].getProductionDate().equals(newItem.getProductionDate()))
            {
                int newQuantity= ((_stock[i].getQuantity()) + (newItem.getQuantity()));
                _stock[i].setQuantity(newQuantity);
                removeEmptyItems();
                return true;
            }
        } 
        return false;
    }

    //sort by catalogue number or place food item before existing item with same catalogue number but different dates
    // returns true if sort success 
    private boolean InsertSort(FoodItem newItem, int index){
        int i = index;
        int j=_noOfItems;
        {
            if(index==-1){ //sort by catalouge number
                while(j>0 && _stock[j-1].getCatalogueNumber() > newItem.getCatalogueNumber()) {
                    _stock[j] = _stock[j-1];
                    j--;
                } 
                _stock[j]=newItem;
                _noOfItems++;
                return true;
            }
            if(index==i){// place fooditem before existing item with same catalogue number but different dates
                while(j>0 && j>index) {
                    _stock[j] = _stock[j-1];
                    j--;
                } 
                _stock[index]=newItem;
                _noOfItems++;
                return true;
            }
        }
        return false;
    }

    /**
     * makes a list of food items with less than a given amount in quantity
     * @param amount amount quantity to check
     * @return list of food items with less than a given amount in quantity
     */
    public String order(int amount){
        int itemCount=0;
        String orderList="";
        int count=0;
        int quantity=0;
        for(int i=0;i<_noOfItems;){        
            for(int k=i; k<_noOfItems;k++){ //runs until item accurs on the list again
                if(_stock[i].getCatalogueNumber() == _stock[k].getCatalogueNumber()){
                    count ++;
                    quantity+=_stock[k].getQuantity();
                }
            }
            for(int j=i;j<i+count;j++){
                itemCount=+_stock[j].getQuantity();
            }
            if(amount>quantity){ //if items quantity is smaller
                orderList+=_stock[i].getName()+", ";
            }
            i+= count;
            itemCount=0;
            count=0;
            quantity=0;
        }
        if (orderList.length()==0)
            return "";
        else 
            return orderList.substring(0,orderList.length()-2);
    }

    /**
     * checks how many items can move to a fridge with a given temperature
     * @param temp temperature of the fridge we want to move items to 
     * @return amount of items can stay at the given temperature
     */
    public int howMany(int temp){
        if (_noOfItems==MAX_ITEMS_IN_STOCK)
            return NO_ITEMS;
        int itemsToFridge=0;
        for(int i=0;i<_noOfItems;i++){
            //find FoodItems with 'temp' at their temperature range 
            //add their quantity value to sum of other FoodItems pieces fit for that temperature range of 'temp' value
            if(_stock[i].getMaxTemperature()>=temp && _stock[i].getMinTemperature()<=temp)
                itemsToFridge+=(_stock[i].getQuantity());
        }
        return itemsToFridge;
    }

    /**
     * removes out of date FoodItems at a given date (FoodItems with expiry date before given date d)
     * @param d date to check which item is out of date at that specific date 
     */
    public void removeAfterDate (Date d){
        for (int i=0; i<_noOfItems; i++){
            if (!(_stock[i].isFresh(d))){//food item is not fresh at given date
                for (int j=i; j<_noOfItems-1; j++){//move the rest of the food items one spot back
                    _stock[j]=_stock[j+1];                   
                }
                _stock[_noOfItems-1]= null;
                _noOfItems--;
                i--;
            }//end of if
        }//end of external for
    }

    /**
     * checks which FoodItem in the array is the most expensive (has the largest value at their '_price' attribute)
     * @return the first FoodItem in the array with the lasrgest value of price
     */
    public FoodItem mostExpensive(){
        if(_noOfItems == NO_ITEMS) //if array is empty
            return null; 
        FoodItem mostExpensiveItem= _stock[_noOfItems-1];
        for (int i=_noOfItems-1; i>0; i--)
            if (mostExpensiveItem.isCheaper(_stock[i]) || mostExpensiveItem.getPrice()==_stock[i].getPrice())
                mostExpensiveItem= _stock[i];
        return new FoodItem(mostExpensiveItem);
    }

    /**
     * checks how many pieces of FoodItems are in the array (sum of all the FoodItems quantities in the array) 
     * @return number of pieces in total 
     */
    public int howManyPieces(){
        int noOfItems = 0;
        for (int i = 0; i < _noOfItems; i++) 
            noOfItems += _stock[i].getQuantity();       
        return noOfItems;
    }

    /**
     * a string that represents the FoodItems in the array
     * @return a string that represents the FoodItems in the array
     */
    public String toString(){
        String itemsInStock="";
        int i;
        for(i=0;i<_noOfItems;i++){   
            itemsInStock+=_stock[i]+"\n";
        }
        return itemsInStock;
    }

    //remove Fooditem at a given index of the array
    private void removeItemFromIndex(int index){  
        for (int i=index; i<_noOfItems-1; i++){
            //move the rest of the food items one spot back
            _stock[i]=_stock[i+1];                   
        }
        _stock[_noOfItems-1]= null;
        _noOfItems--;    
    }

    /**
     * gets an array(list) with names of FoodItems to be removed from their quantity (if their names are on this array)
     * <br>*remove as much from the quantity as the number of times the name appear on the array
     * <br>*remove item if quantity is empty (0 in value) after the update
     * @param itemList list of items represents the sold items
     */
    public void updateStock(String[] itemList){//get array of string which represents the sold items
        for (int i=0;i<_noOfItems;i++){
            for (int j=0;j<itemList.length;j++){               
                if(itemList[j].equals(_stock[i].getName())){
                    if(_stock[i].getQuantity()>0){
                        _stock[i].setQuantity(_stock[i].getQuantity()-1);
                        itemList[j]=itemList[j]+" - updated"; 
                    }//end of internal if
                }//end of if
            }//end of internal for
        }//end of external for
        removeEmptyItems();// delete items if there is 0 in their quantity
    }

    private void removeEmptyItems(){
        int i=0;
        for (; i<_noOfItems; i++){
            if (_stock[i].getQuantity()==0){
                for (int j=i; j<_noOfItems; j++){//move the rest of the food items one spot back
                    _stock[j]=_stock[j+1];                   
                }
                _stock[_noOfItems-1]= null;
                _noOfItems--;
                i--;
            }//end of if
        }//end of external for
    }

    /**
     * check if there is a temperature all the FoodItems can be stored at
     * @return temprature all the FoodItems can be store at<br>or 2,147,483,647  (Integer.MAX_VALUE) 
     * <br>or if there is no such a temperature or there are no items in the array
     */
    public int getTempOfStock(){
        if(_noOfItems == NO_ITEMS) //if array is empty
            return (Integer.MAX_VALUE);
        int i=0;
        int minValue = _stock[i].getMinTemperature();
        int maxValue = _stock[i].getMaxTemperature();
        for(;i < _noOfItems;i++){
            if(_stock[i].getMinTemperature() > minValue){
                minValue = _stock[i].getMinTemperature();
            }
            if(_stock[i].getMaxTemperature() < maxValue){
                maxValue = _stock[i].getMaxTemperature();
            }
        }
        if(maxValue>=minValue){
            return minValue;
        }
        return (Integer.MAX_VALUE);    
    }
}// SHAY: Correct

