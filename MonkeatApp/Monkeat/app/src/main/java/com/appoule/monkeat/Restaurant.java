package com.appoule.monkeat;

import java.util.List;

public class Restaurant {
    private Integer id;
    private String name;
    private String description;
    private Float grade;
    private String localization;
    private String phone_number;
    private String website;
    private String hours;
    private List<Menu> menuList;

    public Restaurant(Integer id, String name, String description, Float grade, String localization, String phone_number, String website, String hours, List<Menu> menuList)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.grade = grade;
        this.localization = localization;
        this.phone_number = phone_number;
        this.website = website;
        this.hours = hours;
        this.menuList = menuList;
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

    public String getGrade() {
        return grade.toString();
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
