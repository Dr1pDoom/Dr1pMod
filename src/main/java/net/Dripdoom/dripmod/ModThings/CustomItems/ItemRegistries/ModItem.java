package net.Dripdoom.dripmod.ModThings.CustomItems.ItemRegistries;

import net.Dripdoom.dripmod.DripMod;
import net.Dripdoom.dripmod.ModThings.CustomItems.AGoofyStuff;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DripMod.MOD_ID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DripMod.MOD_ID);

    public static final RegistryObject<Item> Alexandrite = ITEMS.register("alexandrite",
            () -> new Item(
                    new Item.Properties()
                            .rarity(Rarity.RARE)
                            .stacksTo(64)
                            ));

    public static final RegistryObject<Item> Raw_Alexandrite = ITEMS.register("raw_alexandrite", () -> new Item(
            new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(64)));

    public static final RegistryObject<Item> Chisel_Item = ITEMS.register("chisel",
            () -> new AGoofyStuff(5.0f, 0.2f,
                    new Item.Properties()
                            .durability(200)
                            .rarity(Rarity.EPIC)
                            .fireResistant()
                            ));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }
}


