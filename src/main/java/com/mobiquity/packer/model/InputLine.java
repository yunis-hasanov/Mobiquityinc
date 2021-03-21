package com.mobiquity.packer.model;

import java.util.List;

public class InputLine {

    private int weightLimit;
    private List<Item> items;
    public InputLine(int weightLimit, List<Item> items) {
        this.weightLimit = weightLimit;
        this.items = items;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
