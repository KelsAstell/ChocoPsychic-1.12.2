package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;
import wolf.astell.choco.items.tools.PickaxeChocolate;

import java.util.List;

@EventBusSubscriber
public class BaubleChocolate extends Item implements IBauble
{
	public BaubleChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}
	public static final String TAG_CHOCO_POWER = "chocolatePower";
	int potionLevel = ModConfig.POTION_CONF.RESISTANCE_LEVEL - 1;

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void handleDamage(LivingAttackEvent e)
	{
		if(ModConfig.TRINKET_CONF.GODMODE && e.getEntityLiving() instanceof EntityPlayer)
		{
			IBaublesItemHandler h = BaublesApi.getBaublesHandler((EntityPlayer) e.getEntityLiving());
			for(int i : BaubleType.AMULET.getValidSlots())
			{
				ItemStack stack = h.getStackInSlot(i);
				if(!e.isCanceled() && !stack.isEmpty() && stack.getItem() instanceof BaubleChocolate)
				{
					int shield = NBTHelper.getInt(stack, TAG_CHOCO_POWER, 0);
					if (shield > 0){
						int dmg = shield - (int) Math.min(100, e.getAmount());
						if (dmg >= 0){
							e.setCanceled(true);
							NBTHelper.setInt(stack,TAG_CHOCO_POWER, dmg);
						}else{
							NBTHelper.setInt(stack,TAG_CHOCO_POWER, 0);
						}
					}
				}
			}
		}
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote){
			if (potionLevel >= 0) {
				player.removePotionEffect(MobEffects.RESISTANCE);
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, potionLevel, true, false));
			}
			NBTHelper.setInt(stack, TAG_CHOCO_POWER, Math.max(NBTHelper.getInt(stack, TAG_CHOCO_POWER, 0) + 1, 10000));
		}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		PotionEffect effect = player.getActivePotionEffect(MobEffects.RESISTANCE);
		if(effect != null && effect.getAmplifier() == potionLevel)
			player.removePotionEffect(MobEffects.RESISTANCE);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.bauble_chocolate.desc.0"));
		if(potionLevel >= 0){
			tooltip.add(I18n.format("item.bauble_chocolate.desc.2"));
		}
		if(ModConfig.TRINKET_CONF.GODMODE){
			tooltip.add(I18n.format("item.bauble_chocolate.desc.1"));
		}
		if (!ModConfig.TRINKET_CONF.GODMODE && potionLevel < 0){
			tooltip.add(I18n.format("message.choco.effect_off"));
		}
	}
}