package com.cicidi.bigdota.domain.dota;

public class Hero {
    private String name;
    private int id;
    private String localized_name;

    public Hero() {
    }

    public Hero(String name, int id, String localized_name) {
        this.name = name;
        this.id = id;
        this.localized_name = localized_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalized_name() {
        return localized_name;
    }

    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }
}
