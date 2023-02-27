package wolf.astell.chocopsychic.init;

import net.minecraft.item.Item;
import wolf.astell.chocopsychic.items.Catalyst;

import java.util.ArrayList;
import java.util.List;

public class ModItem {
    public static final List<Item> ITEM_LIST = new ArrayList<>();
    public static Item catalyst;


    public static void init() {
        catalyst = new Catalyst("catalyst");
    }
}
