package com.appoule.monkeat;

public class Menu {
    private Integer id;
    private String name;
    private String description;
    private Float price;

    public Menu(Integer id, String name, String description, Float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId(){
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price.toString();
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
