package net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries;

import net.Dripdoom.dripmod.DripMod;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.ItemDisplayerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DripMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ItemDisplayerBlockEntity>> ItemDisplayerEntity =
            BLOCK_ENTITIES.register("item_displayer_block_entity",
                    () -> BlockEntityType.Builder.of(ItemDisplayerBlockEntity::new, ModBlocks.ItemDisplayerBlock.get()).build(null));

    public static void register(IEventBus eventbus){
        BLOCK_ENTITIES.register(eventbus);
    }
}
