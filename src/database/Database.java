package database;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<T> {
    private List<T> items;
    private final String filename;

    protected Database(String filename) {
        this.filename = filename;
        loadItems();
    }

    // Abstract methods that subclasses must implement
    protected abstract List<T> loadFromFile();
    protected abstract String getItemName(T item);
    protected abstract String getItemId(T item);
    protected abstract void saveToFile(List<T> items);
    
    private void loadItems() {
        this.items = loadFromFile();
    }

    public List<T> getItems() {// get all items array//
        return new ArrayList<>(items); // Return a copy to preserve encapsulation
    }

    public void addItem(T item) { //add an item into file//
        items.add(item);
        saveToFile(items);
    }

    public T getItemById(String id) {
    for (T item : items) {
        if (getItemId(item).equalsIgnoreCase(id)) {
            return item;
        }
    }
    return null;
}
    public T getItemByName(String name) {//get an item by only name//
        for (T item : items) {
            if (getItemName(item).equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public boolean itemExistsById(String id) {
        return getItemById(id) != null;
    }

    public boolean itemExistsByName(String name) {// to check if item exits using name//
        return getItemByName(name) != null;
    }
}
