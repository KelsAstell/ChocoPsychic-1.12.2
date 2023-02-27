package wolf.astell.chocopsychic.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.chocopsychic.Main;
import wolf.astell.chocopsychic.init.ModItem;

import java.util.List;

@EventBusSubscriber
public class Catalyst extends Item
{
	public Catalyst(String name)
	{
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ChocoPsychic);
		this.setContainerItem(this);

		ModItem.ITEM_LIST.add(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.ahoge.desc.0"));
		tooltip.add(I18n.format("item.ahoge.desc.1"));
	}
}