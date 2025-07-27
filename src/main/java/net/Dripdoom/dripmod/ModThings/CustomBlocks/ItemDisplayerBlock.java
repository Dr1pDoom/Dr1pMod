package net.Dripdoom.dripmod.ModThings.CustomBlocks;

import com.mojang.serialization.MapCodec;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.ItemDisplayerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
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
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ItemDisplayerBlockEntity(pPos, pState);
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    protected void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()){
            if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity){
                for(int j = 0; j < itemDisplayerBlockEntity.drops().size(); j++){
                    ItemStack items = itemDisplayerBlockEntity.drops().get(j);
                    popResource(pLevel, pPos, items);
                    pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
                }
                itemDisplayerBlockEntity.clearContents();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    @NotNull
    protected ItemInteractionResult useItemOn(@NotNull ItemStack pStack, @NotNull BlockState pState,
                                                       Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer,
                                                       @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity){
            for (int slot = 0; slot < itemDisplayerBlockEntity.inventory.getSlots(); slot++){
                if(itemDisplayerBlockEntity.inventory.getStackInSlot(slot).isEmpty() && !pStack.isEmpty()){
                    itemDisplayerBlockEntity.inventory.insertItem(slot, pStack.copy(), false);
                    pStack.shrink(1);
                    pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                    break;
                }
            }
            if(!itemDisplayerBlockEntity.inventory.getStackInSlot(0).isEmpty() && pPlayer.getItemInHand(pHand).isEmpty()){
                for(int o = 0; o < itemDisplayerBlockEntity.inventory.getSlots(); o++){
                    if(!itemDisplayerBlockEntity.inventory.getStackInSlot(o).isEmpty()){
                        pPlayer.setItemInHand(pHand, itemDisplayerBlockEntity.inventory.extractItem(0, 1, false));
                        pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                    }
                }
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
