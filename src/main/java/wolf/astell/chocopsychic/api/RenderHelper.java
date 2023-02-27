package wolf.astell.chocopsychic.api;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolf.astell.chocopsychic.Main;
import wolf.astell.chocopsychic.init.ItemList;

import java.util.Objects;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid= Main.MODID)
public class RenderHelper {
    @SubscribeEvent
    public static void Render(ModelRegistryEvent event) {
        for (Item item : ItemList.ITEM_LIST) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
    }
}
