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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolf.astell.chocopsychic.init.ItemList;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class InWorldCrafting {
    @SubscribeEvent
    public static void playerLeftClickedBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide() == Side.CLIENT){return;}
        ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("minecraft", "golden_apple")) && isBlock("minecraft:enchant_table", event.getWorld().getBlockState(event.getPos()).getBlock())) {
            checkRecipe(event,Blocks.GOLD_BLOCK, Items.GOLDEN_APPLE, 0,4);
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
    private static void checkRecipe(PlayerInteractEvent.LeftClickBlock event, Block blocks, Item ingredient, int meta, int required) {
        BlockPos pos = event.getPos();
        List<EntityItem> Items = event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-2, -2, -2), pos.add(2, 2, 2)));
        for(EntityItem item:Items){
            if (item.getItem().getItem().equals(ingredient) && item.getItem().getItemDamage() == meta){
                item.setDead();
                if (item.getItem().getCount()<required){
                    return;
                }else{
                    item.getItem().shrink(required);
                    if (consumeBlocks(event,pos,blocks)){
                        Vec3d vector = item.getPositionVector();
                        EntityItem i = new EntityItem(event.getWorld(), vector.x, vector.y, vector.z, new ItemStack(ItemList.catalyst, 1));
                        i.setDefaultPickupDelay();
                        i.setGlowing(true);
                        i.setNoDespawn();
                        event.getWorld().spawnEntity(i);
                        event.getWorld().playEvent(2001, pos, Block.getStateId(Blocks.ANVIL.getDefaultState()));
                        event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                        return;
                    }
                }
            }
        }
        event.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
    private static boolean consumeBlocks (PlayerInteractEvent.LeftClickBlock event,BlockPos pos, Block blocks){
        int count=0;
        while (count<8){
            if(event.getWorld().getTotalWorldTime() % 10 == 0){
                if (count < 3){
                    for (int i=-1;i<2;i++){
                        if (event.getWorld().getBlockState(new BlockPos(pos.getX()+i,pos.getY(), pos.getZ()-1)).getBlock()==blocks){
                            event.getWorld().playEvent(2001, new BlockPos(pos.getX()+i,pos.getY(), pos.getZ()-1), Block.getStateId(blocks.getDefaultState()));
                            event.getWorld().setBlockState(new BlockPos(pos.getX()+i,pos.getY(), pos.getZ()-1), Blocks.AIR.getDefaultState());
                            count++;
                        }else{
                            return false;
                        }
                    }
                }else if (count==3){
                    if (event.getWorld().getBlockState(new BlockPos(pos.getX()+1,pos.getY(), pos.getZ())).getBlock()==blocks){
                        event.getWorld().playEvent(2001, new BlockPos(pos.getX()+1,pos.getY(), pos.getZ()), Block.getStateId(blocks.getDefaultState()));
                        event.getWorld().setBlockState(new BlockPos(pos.getX()+1,pos.getY(), pos.getZ()), Blocks.AIR.getDefaultState());
                        count++;
                    }else{
                        return false;
                    }
                }else if (count==7){
                    if (event.getWorld().getBlockState(new BlockPos(pos.getX()-1,pos.getY(), pos.getZ())).getBlock()==blocks){
                        event.getWorld().playEvent(2001, new BlockPos(pos.getX()-1,pos.getY(), pos.getZ()), Block.getStateId(blocks.getDefaultState()));
                        event.getWorld().setBlockState(new BlockPos(pos.getX()-1,pos.getY(), pos.getZ()), Blocks.AIR.getDefaultState());
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    for (int i=-1;i<2;i++){
                        if (event.getWorld().getBlockState(new BlockPos(pos.getX()-i,pos.getY(), pos.getZ()+1)).getBlock()==blocks){
                            event.getWorld().playEvent(2001, new BlockPos(pos.getX()-i,pos.getY(), pos.getZ()+1), Block.getStateId(blocks.getDefaultState()));
                            event.getWorld().setBlockState(new BlockPos(pos.getX()-i,pos.getY(), pos.getZ()+1), Blocks.AIR.getDefaultState());
                            count++;
                        }else{
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
}