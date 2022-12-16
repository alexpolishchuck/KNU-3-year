package db;

public class vegetable{
    public vegetable(String category,String name){
        this.category = category;
        this.name = name;
    }

    public vegetable(){}

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String category;
    private String name;
}