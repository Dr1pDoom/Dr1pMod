package net.Dripdoom.dripmod.ModThings.CustomItems;

import net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AGoofyStuff extends Item
{
    float attackdamage;
    float attackspeed;

    private List<Block> Blockslist = new ArrayList<>();

    private void AddBlocks(Block... block){
        Blockslist.addAll(Arrays.asList(block));
    }

    public AGoofyStuff(float attackdamage, float attackspeed, Properties pProperties)
    {
        super(pProperties);
        this.attackdamage = attackdamage;
        this.attackspeed = attackspeed;
    }

    public static final Map<Block, Block> BLOCK_MAP =
            Map.of(
                    Blocks.STONE, Blocks.COBBLESTONE,
                    Blocks.ACACIA_WOOD, Blocks.ACACIA_LEAVES,
                    Blocks.COBBLESTONE, Blocks.STONE,
                    Blocks.BEDROCK, Blocks.OBSIDIAN,
                    Blocks.DIAMOND_BLOCK, Blocks.ACACIA_LEAVES,
                    Blocks.AMETHYST_BLOCK, Blocks.DIRT,
                    ModBlocks.Raw_Alexandrite_Block.get(), Blocks.RAW_IRON_BLOCK,
                    ModBlocks.Alexandrite_Block.get(), Blocks.WATER
            );


    private void spawnParticles(ParticleOptions particle, Level level, double X, double Y, double Z, double RandomAmplitude)
    {
        for(int jkl = 10; jkl <= 10; jkl++){
            level.addParticle(particle,
                        X, Y, Z,
                    (level.random.nextFloat() - RandomAmplitude) * 0.1,
                    (level.random.nextFloat() - RandomAmplitude) * 0.1,
                    (level.random.nextFloat() - RandomAmplitude) * 0.1);
        }
    }

    //Overrided Methods from the Item Class
    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();
        Block clickedblock = level.getBlockState(pContext.getClickedPos()).getBlock();
        BlockPos blockPos = pContext.getClickedPos();
        double PosX = pContext.getClickedPos().getX();
        double PosY = pContext.getClickedPos().getY();
        double PosZ = pContext.getClickedPos().getZ();

        if(level.isClientSide && BLOCK_MAP.containsKey(clickedblock)){
            spawnParticles(ParticleTypes.ANGRY_VILLAGER, level, PosX, PosY + 1, PosZ, 0.5);
        }

        if(!level.isClientSide && BLOCK_MAP.containsKey(clickedblock)) {
            level.setBlockAndUpdate(pContext.getClickedPos(), BLOCK_MAP.get(clickedblock).defaultBlockState());

            pContext.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer)pContext.getPlayer()),
                    item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

            level.playSound(null, blockPos, SoundEvents.MACE_SMASH_GROUND_HEAVY, SoundSource.BLOCKS);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity Target, LivingEntity pAttacker) {

        Level level = Target.level();
        BlockPos pos = Target.getOnPos();
        if(!(Target.level().isClientSide && pAttacker.level().isClientSide))
        {
            Target.addEffect(new MobEffectInstance(MobEffects.GLOWING,
                        200, 5));

            level.playSound(null, pos, SoundEvents.MACE_SMASH_GROUND_HEAVY, SoundSource.BLOCKS);

            Target.move(MoverType.PLAYER, new Vec3(0f, 10f, 0f));
            //Target.push(new Vec3(0f, 1f, 0f));
        }
        return super.hurtEnemy(pStack, Target, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack Stack, TooltipContext Context, List<Component> TooltipComponents, TooltipFlag TooltipFlag) {

        super.appendHoverText(Stack, Context, TooltipComponents, TooltipFlag);
    }
}
