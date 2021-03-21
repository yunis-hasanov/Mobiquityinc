package com.mobiquity.packer.utils;

import com.mobiquity.packer.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Common {

    public static List<Item> findPack(int weightLimit, List<Item> items) {

        items.sort((x1, x2) -> x1.getWeight().compareTo(x2.getWeight()));
        Double[] wt = items.stream().map(Item::getWeight).toArray(Double[]::new);
        Integer[] val = items.stream().map(Item::getValue).toArray(Integer[]::new);
        Boolean checked[] = new Boolean[items.size()];
        Arrays.fill(checked, Boolean.FALSE);

        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (checked[i]) {
                itemList.add(items.get(i));
            }
        }

        itemList.sort((x1, x2) -> x1.getIndex().compareTo(x2.getIndex()));

        return itemList;

    }
}
