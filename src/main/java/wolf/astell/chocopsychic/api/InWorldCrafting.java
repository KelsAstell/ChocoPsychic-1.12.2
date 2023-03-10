/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.chocopsychic.api;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolf.astell.chocopsychic.init.ModItem;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class InWorldCrafting {
    @SubscribeEvent
    public static void playerLeftClickedBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide() == Side.CLIENT){return;}
        ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if (isBlock("minecraft:enchanting_table", event.getWorld().getBlockState(event.getPos()).getBlock())){
            if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("minecraft", "golden_apple"))) {
                infuseRecipe(event,Blocks.GOLD_BLOCK, Items.GOLDEN_APPLE, 0,4,1.0, Items.GOLDEN_APPLE, 1);
            }
            if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("minecraft", "enchanted_book"))) {
                enchantRecipe(event,Blocks.BOOKSHELF, 8);
            }
            if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("minecraft", "egg"))) {
                infuseRecipe(event,Blocks.OBSIDIAN, Items.EGG, 0,64,1.0, Item.getItemFromBlock(Blocks.DRAGON_EGG), 0);
            }
        }
        if (isBlock("minecraft:crafting_table", event.getWorld().getBlockState(event.getPos()).getBlock())){
            if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("minecraft", "end_crystal"))) {
                uncraftRecipe(event,Blocks.CRAFTING_TABLE, Items.END_CRYSTAL, 0,8,0, Items.NETHER_STAR, Item.getItemFromBlock(Blocks.GLASS_PANE));
            }
        }
    }

    private static boolean shouldBreak(double chance) {
        double ran = Math.random();
        return ran < chance;
    }
    @SuppressWarnings("ConstantConditions")
    private static boolean isBlock(String unlocalizedPath, Block block) {
        ResourceLocation loc = block.getRegistryName();
        String fullPath = loc.getResourceDomain() + ":" + loc.getResourcePath();
        return fullPath.equals(unlocalizedPath);
    }
    private static void infuseRecipe(PlayerInteractEvent.LeftClickBlock event, Block blocks, Item ingredient, int meta, int catalyst, double chance, Item output, int meta1) {
        BlockPos pos = event.getPos();
        List<EntityItem> Items = event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-2, -2, -2), pos.add(2, 2, 2)));
        for(EntityItem item:Items){
            if (item.getItem().getItem().equals(ModItem.catalyst)){
                if (item.getItem().getCount() < catalyst){
                    event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.chocopsychic.insufficient_catalyst"));
                    event.getEntityPlayer().sendMessage(new TextComponentString(String.valueOf(catalyst-item.getItem().getCount())));
                    return;
                }
                else{
                    item.getItem().shrink(catalyst);
                    for (EntityItem item1:Items) {
                        if (item1.getItem().getItem().equals(ingredient) && item1.getItem().getItemDamage() == meta) {
                            int amount = item1.getItem().getCount();
                            if (checkBlocks(event,pos,blocks)){
                                item1.setDead();
                                consumeBlocks(event,pos,blocks,chance);
                                Vec3d vector = item1.getPositionVector();
                                EntityItem i = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5, vector.z, new ItemStack(output, amount, meta1));
                                i.setDefaultPickupDelay();
                                i.setGlowing(true);
                                i.setNoDespawn();
                                event.getWorld().spawnEntity(i);
                                event.getWorld().playSound(null,pos,SoundEvents.ENTITY_PLAYER_LEVELUP,SoundCategory.PLAYERS,1.0F,1.0F);
                                return;
                            }
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.chocopsychic.insufficient_blocks"));
                        }
                    }
                }
            }
        }
    }
    private static void enchantRecipe(PlayerInteractEvent.LeftClickBlock event, Block blocks, int required) {
        BlockPos pos = event.getPos();
        List<EntityItem> Items = event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-2, -2, -2), pos.add(2, 2, 2)));
        for(EntityItem item:Items){
            if (item.getItem().getItem().equals(ModItem.catalyst)){
                if (item.getItem().getCount() < required){
                    event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.chocopsychic.insufficient_catalyst"));
                    event.getEntityPlayer().sendMessage(new TextComponentString(String.valueOf(required-item.getItem().getCount())));
                    return;
                }
                else{
                    item.getItem().shrink(required);
                    for(EntityItem entity : Items) {
                        ItemStack item1 = entity.getItem();
                        if(item1.getItem() == net.minecraft.init.Items.ENCHANTED_BOOK && checkBlocks(event,pos,blocks)) {
                            NBTTagList enchants = ItemEnchantedBook.getEnchantments(item1);
                            if(enchants.tagCount() > 0) {
                                NBTTagCompound enchant = enchants.getCompoundTagAt(0);
                                short enchantLvl = enchant.getShort("lvl");
                                if (enchantLvl<10){
                                    enchant.setShort("lvl", (short) (enchantLvl+1));
                                    entity.setGlowing(true);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private static void uncraftRecipe(PlayerInteractEvent.LeftClickBlock event, Block blocks, Item ingredient, int meta, int catalyst, double chance, Item output1, Item output2) {
        BlockPos pos = event.getPos();
        List<EntityItem> Items = event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-2, -2, -2), pos.add(2, 2, 2)));
        for(EntityItem item:Items){
            if (item.getItem().getItem().equals(ModItem.catalyst)){
                if (item.getItem().getCount() < catalyst){
                    event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.chocopsychic.insufficient_catalyst"));
                    event.getEntityPlayer().sendMessage(new TextComponentString(String.valueOf(catalyst-item.getItem().getCount())));
                    return;
                }
                else{
                    item.getItem().shrink(catalyst);
                    for (EntityItem item1:Items) {
                        if (item1.getItem().getItem().equals(ingredient) && item1.getItem().getItemDamage() == meta) {
                            int amount = item1.getItem().getCount();
                            if (checkBlocks(event,pos,blocks)){
                                item1.setDead();
                                consumeBlocks(event,pos,blocks,chance);
                                Vec3d vector = item1.getPositionVector();
                                EntityItem i = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5, vector.z, new ItemStack(output1, amount, 0));
                                i.setDefaultPickupDelay();
                                i.setGlowing(true);
                                i.setNoDespawn();
                                event.getWorld().spawnEntity(i);
                                EntityItem i2 = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5, vector.z, new ItemStack(output2, amount, 0));
                                i2.setDefaultPickupDelay();
                                i2.setGlowing(true);
                                i2.setNoDespawn();
                                event.getWorld().spawnEntity(i2);
                                event.getWorld().playSound(null,pos,SoundEvents.ENTITY_PLAYER_LEVELUP,SoundCategory.PLAYERS,1.0F,1.0F);
                                return;
                            }
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.chocopsychic.insufficient_blocks"));
                        }
                    }
                }
            }
        }
    }
    private static boolean checkBlocks(PlayerInteractEvent.LeftClickBlock event, BlockPos pos, Block blocks){
        int count=0;
        while (count<8){
            for (int i=-1;i<2;i++){
                for (int j=-1;j<2;j++){
                    if (!(i==0 && j==0)){
                        if (event.getWorld().getBlockState(new BlockPos(pos.getX()+i,pos.getY(), pos.getZ()+j)).getBlock()==blocks){
                            count++;
                        }else{
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static void consumeBlocks(PlayerInteractEvent.LeftClickBlock event, BlockPos pos, Block blocks, double chance){
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                if (!(i==0 && j==0)){
                    event.getWorld().playEvent(2001, new BlockPos(pos.getX()+i,pos.getY(), pos.getZ()+j), Block.getStateId(blocks.getDefaultState()));
                    if (shouldBreak(chance)){
                        event.getWorld().setBlockState(new BlockPos(pos.getX()+i,pos.getY(), pos.getZ()+j), Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }
}