package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.block.NetworkNodeBlock;
import com.refinedmods.refinedstorage.container.factory.PositionalTileContainerProvider;
import com.refinedmods.refinedstorage.util.BlockUtils;
import com.refinedmods.refinedstorage.util.NetworkUtils;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExtraFluidStorageBlock extends NetworkNodeBlock {
    private final ExtraFluidStorageType type;

    public ExtraFluidStorageBlock(ExtraFluidStorageType type) {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
        this.type = type;
    }

    public ExtraFluidStorageType getType() {
        return this.type;
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        if (!world.isRemote) {
            ExtraFluidStorageNetworkNode storage = ((ExtraFluidStorageBlockTile) world.getTileEntity(pos)).getNode();
            if (stack.hasTag() && stack.getTag().hasUniqueId(ExtraFluidStorageNetworkNode.NBT_ID)) {
                storage.setStorageId(stack.getTag().getUniqueId(ExtraFluidStorageNetworkNode.NBT_ID));
            }
            storage.loadStorage(placer instanceof PlayerEntity ? (PlayerEntity) placer : null);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ExtraFluidStorageBlockTile(this.type);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (!world.isRemote) {
            return NetworkUtils.attemptModify(world, pos, player, () -> NetworkHooks.openGui((ServerPlayerEntity) player,
                    new PositionalTileContainerProvider<ExtraFluidStorageBlockTile>(((ExtraFluidStorageBlockTile) world.getTileEntity(pos)).getNode().getTitle(),
                            (tile, windowId, inventory, p) -> new ExtraFluidStorageBlockContainer(windowId, player, tile), pos), pos));
        }
        return ActionResultType.SUCCESS;
    }
}
