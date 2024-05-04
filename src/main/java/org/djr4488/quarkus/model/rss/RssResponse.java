package org.djr4488.quarkus.model.rss;

import lombok.Data;
import org.djr4488.quarkus.comparator.ItemCategoryComparator;
import org.djr4488.quarkus.comparator.ItemPubDateComparator;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@XmlRootElement(name="rss")
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class RssResponse  implements Serializable {
    @XmlElement(name="channel")
    private Channel channel;

    public List<Item> itemSortByDateDesc() {
        Comparator<Item> comparator = ItemPubDateComparator.itemComparator();
        return channel.getItem()
         .stream()
         .sorted(comparator)
         .toList();
    }

    public List<Item> itemSortByCategory() {
        Comparator<Item> comparator = ItemCategoryComparator.itemComparator();
        return channel.getItem()
          .stream()
          .sorted(comparator)
          .toList();
    }

    public Item sotd() {
        for (Item it : channel.getItem()) {
            if (it.getCategory().equals("Saints")) {
                return it;
            }
        }
        return null;
    }
}
