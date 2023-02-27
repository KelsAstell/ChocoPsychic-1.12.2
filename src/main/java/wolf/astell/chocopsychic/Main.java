package wolf.astell.chocopsychic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolf.astell.chocopsychic.init.ItemList;
import wolf.astell.chocopsychic.init.register.CraftRegister;
import wolf.astell.chocopsychic.init.register.ItemRegister;



@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "choco";
    public static final String VERSION = "1.0.0";
    public static Main INSTANCE;
    public static CreativeTabs ChocoPsychic = new CreativeTabs("ChocoPsychic") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemList.catalyst);
        }
    };

    public Main() {
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ItemList.init();
        MinecraftForge.EVENT_BUS.register(new ItemRegister());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CraftRegister.init();
    }
}
