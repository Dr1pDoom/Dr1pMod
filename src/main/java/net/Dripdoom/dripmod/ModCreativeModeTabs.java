package net.Dripdoom.dripmod;

import net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries.ModBlocks;
import net.Dripdoom.dripmod.ModThings.CustomItems.ItemRegistries.ModItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DripMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ALEXANDRITE_COOLITEMS_TAB = CREATIVE_MODE_TABS.register("alexandrite_coolitems_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItem.Chisel_Item.get()))
                    .title(Component.translatable("creativetab.dripmod.Chisel_Item"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModItem.Chisel_Item.get());



                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> ALEXANDRITE_ITEMS_TAB = CREATIVE_MODE_TABS.register("alexandrite_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItem.Alexandrite.get()))
                    .title(Component.translatable("creativetab.dripmod.alexandrite_items"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModItem.Raw_Alexandrite.get());
                        output.accept(ModItem.Alexandrite.get());
                        output.accept(ModBlocks.Raw_Alexandrite_Block.get());
                        output.accept(ModBlocks.Alexandrite_Block.get());


                    })
                    .build());

    public static void register(IEventBus eventbus){
        CREATIVE_MODE_TABS.register(eventbus);
    }
}