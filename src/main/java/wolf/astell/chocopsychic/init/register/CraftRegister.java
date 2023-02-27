/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.chocopsychic.init.register;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.chocopsychic.Main;
import wolf.astell.chocopsychic.api.Log;
import wolf.astell.chocopsychic.init.ModItem;

public class CraftRegister {
    public static void init(){
        GameRegistry.addShapedRecipe(new ResourceLocation("catalyst"), new ResourceLocation(Main.MODID), new ItemStack(ModItem.catalyst),
                " R ",
                "GCG",
                " R ",
                'R', Items.REDSTONE,
                'G', Items.GOLD_INGOT,
                'C', wolf.astell.choco.init.ItemList.foodChocolate);
        Log.i("Crafting Recipe Inject Succeed.");
    }
}
