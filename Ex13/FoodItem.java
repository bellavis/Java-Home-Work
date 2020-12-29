
/**
 * The class represents a Food Item obejct with 8 attributes:
 * name, catalogue number, quantity, production & expiry dates, 
 * min/max storage temperatures and price.
 *
 * @author  Bell Avisar
 * @version 13/12/2019
 */
public class FoodItem
{
    //decleration
    private String _name;
    private long _catalogueNumber; //has to be positive not a zero
    private int _quantity; //has to be positive or zero
    private Date _productionDate;
    private Date _expiryDate;
    private int _minTemperature;
    private int _maxTemperature;
    private int _price; //has to be positive
    //defaults
    final String DEFAULT_NAME= "item";
    final int DEFAULT_PRICE = 1;
    final int DEFAULT_QUANTITY = 1;
    final long DEFAULT_CATALOUGE_NUM = 9999;

    /**
     *creates a new FoodItem object
     *@param name name of food item
     *@param catalogueNumber catalogue number of food item
     *@param quantity quantity of food item
     *@param productionDate production date
     *@param expiryDate expiry date
     *@param minTemperature minimum storage temperature
     *@param maxTemperature maximum storage temperature
     *@param price unit price
     */
    public FoodItem (String name,long catalogueNumber, int quantity, Date productionDate,
    Date expiryDate,int minTemperature,int maxTemperature,int price){
        if(validName (name))
            _name=name;
        else
            _name=DEFAULT_NAME;

        if(validCatalogueNumber(catalogueNumber))
            _catalogueNumber= catalogueNumber; 
        else 
            _catalogueNumber= DEFAULT_CATALOUGE_NUM;

        if (quantity>=0)
            _quantity= quantity;
        else
            _quantity=DEFAULT_QUANTITY;

        _productionDate= new Date (productionDate);
// SHAY: Correct
        if(validExpiryDate(expiryDate))
            _expiryDate= new Date (expiryDate);
        else
            _expiryDate= productionDate.tomorrow();

        if (maxTemperature>minTemperature){
            _maxTemperature=maxTemperature;
            _minTemperature=minTemperature;
        }
        else{
            int temp= maxTemperature;
            _maxTemperature= minTemperature;
            _minTemperature = temp;
        }

        if (validPrice(price))   
            _price= price;
        else
            _price= DEFAULT_PRICE;
    }

    /**
     * copy constructor
     * @param other the food item to be copied
     */
    public FoodItem (FoodItem other){
        _name=other._name;
        _catalogueNumber= other._catalogueNumber; 
        _quantity= other._quantity;
        _productionDate= new Date (other._productionDate);
        _expiryDate= new Date (other._expiryDate);
        _minTemperature=other._minTemperature;
        _maxTemperature=other._maxTemperature;
        _price= other._price; 

    }
    //get
    /**
     * gets the name
     * @return the name
     */

    public String getName(){
        String name=_name;
        return name;
    }

    /**
     * gets the catalogue number
     * @return the catalogue number
     */

    public long getCatalogueNumber(){
        long catalogueNumber= _catalogueNumber;
        return catalogueNumber;
    }

    /**
     * gets the quantity
     * @return the quantity
     */
    public int getQuantity(){
        return _quantity;
    }

    /**
     * gets the production date
     * @return the production date
     */
    public Date getProductionDate(){
        return new Date (_productionDate);
    }

    /**
     * gets the expiry date
     * @return the expiry date
     */
    public Date getExpiryDate(){
        return new Date (_expiryDate);
    }

    /**
     * gets the minimum storage temperature
     * @return the minimum storage temperature
     */
    public int getMinTemperature(){
        return _minTemperature;
    }

    /**
     * gets the maximum storage temperature
     * @return the maximum storage temperature
     */
    public int getMaxTemperature(){
        return _maxTemperature;
    }

    /**
     * gets the gets the unit price
     * @return the unit price
     */
    public int getPrice(){
        return _price;
    }

    //private valid boolean methods
    private boolean validName(String name){
        if(name.equals("") || name.equals(" ")) //name string is empty
            return false;
        return true;
    }

    private boolean validCatalogueNumber(long c){
        if(c>=1000 && c<=9999)//catalouge num = 4 digits
            return true;
        return false;
    }

    private boolean validExpiryDate(Date d){ 
        //expiry date is equal or after expiry date
        if (d.after(_productionDate) || d.equals(_productionDate))
            return true;
        return false;
    }

    private boolean validProductionDate(Date d){ 
        //production date is equal or before expiry date
        if (d.before(_expiryDate) || d.equals(_expiryDate))
            return true;
        return false;
    }

    private boolean validPrice(int price){
        if(price>0) //positive price
          return true;
        return false;
    }

    //set
    /**
     * set the quantity (only if not negative)
     * @param n the quantity value to be set
     */
    public void setQuantity(int n){
        if (n>= 0) //not a negative quantity
            _quantity=n;
    }

    /**
     * set the production date (only if not after expiry date ) 
     * @param d production date value to be set
     */
    public void setProductionDate(Date d){ 
        if (validProductionDate(d))
            _productionDate= new Date (d);
    }

    /**
     * set the expiry date (only if not before production date)
     * @param d expiry date value to be set
     */
    public void setExpiryDate(Date d){ 
        if (validExpiryDate(d))
            _expiryDate= new Date (d);
    }

    /**
     * set the price (only if positive)
     * @param priceToSet expiry date value to be set
     */
    public void setPrice(int n){
        if (validPrice(n))
            _price=n;
    }

    /**
     * check if 2 food items are the same (excluding the quantity values)
     * @param other the food item to compare this food item to
     * @return true if the food items are the same
     */
    public boolean equals (FoodItem other){
        if 
        ((_catalogueNumber)== (other._catalogueNumber)&&
        _productionDate.equals(other._productionDate)&&
        _expiryDate.equals(other._expiryDate)&&
        (_minTemperature)==(other._minTemperature)&&
        (_maxTemperature)==(other._maxTemperature)&&
        (_price)== (other._price)&&
        _name.equals(other._name))
            return true;
        return false;
    }

    /**
     * check if this food item is fresh on the date d
     * @param d date to check
     * @return true if this food item is fresh on the date d
     */
    public boolean isFresh (Date d){
        if (d.before(_expiryDate) && d.after(_productionDate) || 
        d.before(_expiryDate) && d.equals(_productionDate)|| 
        d.equals(_expiryDate) && d.after(_productionDate) || 
        d.equals(_expiryDate) && d.equals(_productionDate))
            return true;
        return false;
    }

    /**
     * check if this food item is older than other food item
     * @param other food item to compare this food item to
     * @return true if this food item is older than other date
     */
    public boolean olderFoodItem (FoodItem other){
        if (this._productionDate.before(other._productionDate))
            return true;
        return false;     
    }

    /**
     * returns the number of units of products that can be purchased 
     * for a given amount
     * @param amount amount to purchase
     * @return the number of units can be purchased
     */
    public int howManyItems (int amount){
        int possibleAmount=(amount/_price);
        int q = _quantity;
        if(possibleAmount <= _quantity)
            return possibleAmount;
        return q;
    }    

    /**
     * check if this food item is cheaper than other food item
     * @param other food item to compare this food item to
     * @return true if this food item is cheaper than other date
     */
    public boolean isCheaper (FoodItem other){
        if (this._price<other._price)
            return true;
        return false;
    }

    /**
     * returns a String that represents this food item
     * @return String that represents this food item in the following format:
     * FoodItem: milk CatalogueNumber: 1234 ProductionDate: 14/12/2019 ExpiryDate: 21/12/2019 Quantity: 3
     */
    public String toString (){
        String f="FoodItem: "+_name +"\tCatalogueNumber: "+_catalogueNumber+ "\tProductionDate: "
            +_productionDate+ "\tExpiryDate: "+_expiryDate +"\tQuantity: "+_quantity;

        return f;
    }
}
// SHAY: Correct