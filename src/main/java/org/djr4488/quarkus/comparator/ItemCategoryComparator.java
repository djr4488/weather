package org.djr4488.quarkus.comparator;

import org.djr4488.quarkus.model.rss.Item;

import java.util.Comparator;

public class ItemCategoryComparator {
    public static Comparator<Item> itemComparator() {
        return Comparator.comparing(Item::getCategory);
    }
}
