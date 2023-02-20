package wolf.astell.choco.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;

@Mod.EventBusSubscriber(modid = Main.MODID)

public class Eternity extends Enchantment {
    public Eternity() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("eternity");
        this.setName("eternity");
    }
    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public boolean canApply(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @SubscribeEvent
    public void setEternal(ItemExpireEvent e) {
        ItemStack stack = e.getEntityItem().getItem();
        if (EnchantmentHelper.getEnchantments(stack).containsKey(this)) {
            e.setExtraLife(Integer.MAX_VALUE);
            double d0 = (float)e.getEntity().posX + e.getEntity().getEntityWorld().rand.nextFloat();
            double d1 = e.getEntity().posY - 0.05D;
            double d2 = (float)e.getEntity().posZ + e.getEntity().getEntityWorld().rand.nextFloat();
            e.getEntity().getEntityWorld().spawnParticle(EnumParticleTypes.TOTEM,d0,d1,d2,0,0,0,1);
            e.setCanceled(true);
        }
    }
}