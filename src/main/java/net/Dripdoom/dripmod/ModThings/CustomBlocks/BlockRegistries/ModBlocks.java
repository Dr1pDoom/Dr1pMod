package net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries;

import net.Dripdoom.dripmod.DripMod;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.AGoofyBlock;
import net.Dripdoom.dripmod.ModThings.CustomItems.ItemRegistries.ModItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DripMod.MOD_ID);

    public static final RegistryObject<AGoofyBlock> Alexandrite_Block = registerGoofyBLock("alexandrite_block",
            () -> new AGoofyBlock(BlockBehaviour.
                    Properties.of()
                    .strength(4f, 1000f)
                    ));

    public static final RegistryObject<Block> Raw_Alexandrite_Block = registerBLock("raw_alexandrite_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f, 600f)
                    ));

    private static <T extends Block> RegistryObject<T> registerBLock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <K extends AGoofyBlock> RegistryObject<K> registerGoofyBLock(String name, Supplier<K> block){
        RegistryObject<K> toReturn = BLOCKS.register(name, block);
        registerGoofyBlockItem(name, toReturn);
        return toReturn;
    }

    private static <K extends AGoofyBlock> void registerGoofyBlockItem(String name, RegistryObject<K> block){
        ModItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }



    public static void register(IEventBus eventbus){
        BLOCKS.register(eventbus);
    }
}
