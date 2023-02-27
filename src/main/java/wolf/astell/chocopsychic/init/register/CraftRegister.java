/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.chocopsychic.init.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.chocopsychic.Main;
import wolf.astell.chocopsychic.api.Log;
import wolf.astell.chocopsychic.init.ItemList;

public class CraftRegister {
    public static void init(){
        DogeCraftRegister.init();
        GameRegistry.addShapedRecipe(new ResourceLocation("world_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(ItemList.worldChocolate),
                "AAA",
                "DBD",
                "CDC",
                'A', Blocks.GRASS,
                'B', Items.NETHER_STAR,
                'C', Blocks.END_STONE,
                'D', ItemList.foodGoldenChocolate);
        if (SpecialDays.getToday().equals("VALENTINES_DAY")){
            GameRegistry.addShapedRecipe(new ResourceLocation("love_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(ItemList.loveChocolate),
                    "AAA",
                    "ABA",
                    "ACA",
                    'A', Items.PAPER,
                    'C', Items.DIAMOND,
                    'B', ItemList.foodChocolate);
        }
        Log.i("Crafting Recipe Inject Succeed.");
    }
}
