package wolf.astell.chocopsychic.init.register;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import wolf.astell.chocopsychic.Main;
import wolf.astell.chocopsychic.init.ModItem;


@Mod.EventBusSubscriber(modid = Main.MODID)
public class ItemRegister {
    @SubscribeEvent
    public void register(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item object : ModItem.ITEM_LIST)
        {
            registry.register(object);
        }
    }
}