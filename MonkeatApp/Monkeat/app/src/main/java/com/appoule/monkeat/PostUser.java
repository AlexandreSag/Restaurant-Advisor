package com.appoule.monkeat;

public class PostUser {
    private String login;
    private String password;
    private String email;
    private String name;
    private String firstname;
    private Integer age;

    public PostUser(String login, String password, String email, String name, String firstname, Integer age)
    {
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.firstname = firstname;
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public Integer getAge() {
        return age;
    }

}
