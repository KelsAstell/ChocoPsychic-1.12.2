package wolf.astell.chocopsychic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolf.astell.chocopsychic.init.ItemList;
import wolf.astell.chocopsychic.init.register.*;
import wolf.astell.chocopsychic.init.register.compact.AvaritiaRegister;
import wolf.astell.chocopsychic.init.register.compact.CoFHRegister;
import wolf.astell.chocopsychic.network.PacketHandler;
import wolf.astell.chocopsychic.recipes.compact.AvaritiaCompact;
import wolf.astell.chocopsychic.recipes.compact.CoFHCompact;



@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "choco";
    public static final String VERSION = "1.0.12";
    public static Main INSTANCE;
    public static CreativeTabs ChocoPsychic = new CreativeTabs("ChocoPsychic") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemList.foodChocolate);
        }
    };

    public Main() {
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ItemList.init();
        if (Loader.isModLoaded("redstoneflux") && Loader.isModLoaded(("cofhcore"))){
            CoFHRegister.init();
            CoFHCompact.init();
        }
        if (Loader.isModLoaded("avaritia")){
            AvaritiaRegister.init();
            AvaritiaCompact.init();
        }
        PacketHandler.init();
        AIORegister.init();
        MinecraftForge.EVENT_BUS.register(new ItemRegister());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDictRegister.init();
        CraftRegister.init();
        BrewRegister.init();
        AdvancementRegister.init();
        MinecraftForge.EVENT_BUS.register(new LootRegister());
    }
}
