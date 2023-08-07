package com.uyurucar.liste;

import java.util.Comparator;

public class Item {
    private int priority;
    private Price price;
    private String explanation;

    public Item(int priority, Price price, String explanation) {
        this.priority = priority;
        this.price = price;
        this.explanation = explanation;

    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public static Comparator<Item> itemPriority = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            int pri1 = o1.getPriority();
            int pri2 = o2.getPriority();
            return pri1-pri2;
        }
    };

    public static Comparator<Item> itemPrice = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            Price price1 = o1.getPrice();
            Price price2 = o2.getPrice();
            return price1.compareTo(price2);
        }
    };
}
