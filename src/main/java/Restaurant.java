import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public static void main(String[] args) {

    }
    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime currentTime = getCurrentTime();

        // considering scenarios where a restaurant is open from night to morning, for ex: 8pm to 6am, then closing time will be lesser than opening time
        boolean isNightTimingsRestaurant = (this.openingTime.isAfter(this.closingTime)) ? true: false;
        boolean isOpen = isNightTimingsRestaurant ? isRestaurantOpenWithNightTimings() : (currentTime.isAfter(this.openingTime) && currentTime.isBefore(this.closingTime));
        return isOpen;
    }

    public boolean isRestaurantOpenWithNightTimings() {
        LocalTime currentTime = getCurrentTime();

        if(currentTime.isAfter(openingTime)) {
            return true;
        } else if(currentTime.isBefore(openingTime)) {
            if(currentTime.isAfter(closingTime)) {
                return false;
            } else if(currentTime.isBefore(closingTime)) {
                return true;
            }
        }
        return true;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public int getOrderCost(String ... itemNames) {
        int totalCost = 0;
        for(String itemName: itemNames) {
            for(Item item: menu) {
                if(item.getName().equals(itemName))
                    totalCost += item.getPrice();
            }
        }
        return totalCost;
    }

}
