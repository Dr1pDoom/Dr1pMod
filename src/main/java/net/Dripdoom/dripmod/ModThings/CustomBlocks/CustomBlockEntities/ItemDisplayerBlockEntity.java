package net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities;

import net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries.ModBlockEntities;

import net.Dripdoom.dripmod.ModThings.CustomBlocks.ItemDisplayerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayerBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(1){

        public void hello(){System.out.println("Nigga !");}
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            hello();
        }
    };

    public void clearContents(){
        for(int i = 0; i <= inventory.getSlots(); i++){
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    public ItemDisplayerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ItemDisplayerEntity.get(), pPos, pBlockState);
    }

    public ItemStack drops() {
        for (int i = 0; i <= inventory.getSlots(); i++) {
            return inventory.getStackInSlot(i);
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", inventory.serializeNBT(pRegistries));
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

}
