package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.block.NetworkNodeBlock;
import com.refinedmods.refinedstorage.container.factory.BlockEntityMenuProvider;
import com.refinedmods.refinedstorage.util.BlockUtils;
import com.refinedmods.refinedstorage.util.NetworkUtils;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExtraItemStorageBlock extends NetworkNodeBlock {

    private final ExtraItemStorageType type;

    public ExtraItemStorageBlock(ExtraItemStorageType type) {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
        this.type = type;
    }

    public ExtraItemStorageType getType() {
        return this.type;
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        if (!level.isClientSide) {
            //noinspection ConstantConditions
            ExtraItemStorageNetworkNode storage = ((ExtraItemStorageBlockEntity) level.getBlockEntity(pos)).getNode();
            CompoundTag tag = stack.getOrCreateTag();

            if (tag.hasUUID(ExtraItemStorageNetworkNode.NBT_ID)) {
                storage.setStorageId(tag.getUUID(ExtraItemStorageNetworkNode.NBT_ID));
            }

            storage.loadStorage(placer instanceof Player ? (Player) placer : null);
        }

        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ExtraItemStorageBlockEntity(this.type, pos, state);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (!level.isClientSide) {
            //noinspection ConstantConditions
            return NetworkUtils.attemptModify(level, pos, player, () -> NetworkHooks.openGui((ServerPlayer) player,
                    new BlockEntityMenuProvider<ExtraItemStorageBlockEntity>(((ExtraItemStorageBlockEntity) level.getBlockEntity(pos)).getNode().getTitle(),
                            (tile, windowId, inventory, p) -> new ExtraItemStorageBlockContainerMenu(windowId, player, tile), pos), pos));
        }

        return InteractionResult.SUCCESS;
    }
}
