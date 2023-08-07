package com.uyurucar.liste;

public enum Price {
    Low(1),Medium(2),High(3),VeryHigh(4);

    private int id;
    private Price(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    //int color = Color.Blue.ordinal(); //example of getting int value
    public static Price fromId(int id) {
        for (Price price : values()) {
            if (price.getId() == id) {
                return price;
            }
        }
        return null;
    }
}
