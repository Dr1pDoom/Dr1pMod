package net.Dripdoom.dripmod.ModThings.CustomBlocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries.ModBlockEntities;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.ItemDisplayerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayerBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);
    private static MapCodec<ItemDisplayerBlock> CODEC = simpleCodec(ItemDisplayerBlock::new);

    public ItemDisplayerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static VoxelShape getSHAPE() {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemDisplayerBlockEntity(pPos, pState);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()){
            if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity){
                popResource(pLevel, pPos, itemDisplayerBlockEntity.drops());
                pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState,
                                              Level pLevel, BlockPos pPos, Player pPlayer,
                                              InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity){
            if(itemDisplayerBlockEntity.inventory.getStackInSlot(0).isEmpty() && !pStack.isEmpty()){
                itemDisplayerBlockEntity.inventory.insertItem(0, pStack.copy(), false);
                pStack.shrink(1);
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }
            if(!itemDisplayerBlockEntity.inventory.getStackInSlot(0).isEmpty() && pPlayer.getItemInHand(pHand).isEmpty() && pPlayer.isCrouching()){

                pPlayer.setItemInHand(pHand, itemDisplayerBlockEntity.inventory.extractItem(0, 1, false));
                itemDisplayerBlockEntity.inventory.getStackInSlot(0).setCount(0);
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
