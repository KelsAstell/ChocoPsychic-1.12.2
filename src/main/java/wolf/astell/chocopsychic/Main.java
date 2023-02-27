package wolf.astell.chocopsychic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolf.astell.chocopsychic.init.ModItem;
import wolf.astell.chocopsychic.init.register.CraftRegister;
import wolf.astell.chocopsychic.init.register.ItemRegister;



@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "chocopsychic";
    public static final String VERSION = "1.0.0";
    public static Main INSTANCE;
    public static CreativeTabs ChocoPsychic = new CreativeTabs("ChocoPsychic") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItem.catalyst);
        }
    };

    public Main() {
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItem.init();
        MinecraftForge.EVENT_BUS.register(new ItemRegister());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CraftRegister.init();
    }
}
