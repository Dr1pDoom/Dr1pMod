package net.Dripdoom.dripmod.ModThings.CustomBlocks;

import net.Dripdoom.dripmod.ModThings.CustomItems.ItemRegistries.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.List;
import java.util.Objects;

public class AGoofyBlock extends Block {

    public AGoofyBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        ResourceKey<Level> dimension = pLevel.dimension();
        double PosX = pPos.getX();
        double PosY = pPos.getY();
        double AbsoluteValueModifier = PosX * PosY * 0.01;
        if(dimension == Level.NETHER){
            return 2 * (int)AbsoluteValueModifier;
        }
        return 15;
    }

    @Override
    protected void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        List<Item> ItemDropsList = List.of(
                Items.DIAMOND,
                ModItem.Alexandrite.get(),
                ModItem.Raw_Alexandrite.get()
        );

        RandomSource random = pLevel.getRandom();
        Item randomItem = ItemDropsList.get(random.nextInt(ItemDropsList.size()));
        int dropCount = random.nextInt(4);
        popResource(pLevel, pPos, new ItemStack(randomItem, dropCount));

        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        List<Item> ItemsList = List.of(
                Items.DIAMOND,
                ModItem.Alexandrite.get(),
                ModItem.Raw_Alexandrite.get()
        );
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(pLevel);
        if(!(pLevel.isClientSide) && pEntity instanceof ItemEntity item){
            if(ItemsList.contains(item.getItem().getItem()) && item.getOwner() != null && lightningBolt != null){
                Vec3 playerPosition = item.getOwner().position();
                Vec2 playerRotation = item.getOwner().getRotationVector();
                int ItemDroppedCount = item.getItem().getCount();

                item.remove(Entity.RemovalReason.DISCARDED); // keeping this over coal transformation

                for(int i = ItemDroppedCount; i <= ItemDroppedCount; i++){
                    pLevel.addFreshEntity(lightningBolt);
                    lightningBolt.moveTo(playerPosition, playerRotation.y, playerRotation.x);
                }
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}