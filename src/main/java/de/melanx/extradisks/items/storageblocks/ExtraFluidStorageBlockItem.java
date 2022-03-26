package de.melanx.extradisks.items.storageblocks;

import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.api.storage.disk.StorageDiskSyncData;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.item.blockitem.BaseBlockItem;
import com.refinedmods.refinedstorage.render.Styles;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlock;
import de.melanx.extradisks.blocks.item.ExtraItemStorageNetworkNode;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStoragePartItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ExtraFluidStorageBlockItem extends BaseBlockItem {

    private final ExtraFluidStorageType type;

    public ExtraFluidStorageBlockItem(ExtraFluidStorageBlock block, Properties builder) {
        super(block, builder);
        this.type = block.getType();
    }

    @OnlyIn(Dist.CLIENT)

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (this.isValid(stack)) {
            UUID id = this.getId(stack);
            API.instance().getStorageDiskSync().sendRequest(id);
            StorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);

            if (data != null) {
                if (data.getCapacity() == -1) {
                    tooltip.add(new TranslatableComponent("misc.refinedstorage.storage.stored", API.instance().getQuantityFormatter().format(data.getStored())).setStyle(Styles.GRAY));
                } else {
                    tooltip.add(new TranslatableComponent("misc.refinedstorage.storage.stored_capacity", API.instance().getQuantityFormatter().format(data.getStored()), API.instance().getQuantityFormatter().format(data.getCapacity())).setStyle(Styles.GRAY));
                }
            }

            if (flag.isAdvanced()) {
                tooltip.add(new TextComponent(id.toString()).setStyle(Styles.GRAY));
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isCrouching()) {
            UUID diskId = null;
            //noinspection rawtypes
            IStorageDisk disk = null;

            if (this.isValid(stack)) {
                diskId = this.getId(stack);
                disk = API.instance().getStorageDiskManager((ServerLevel) level).get(diskId);
            }

            if (disk == null || disk.getStored() == 0) {
                ItemStack part = new ItemStack(ExtraFluidStoragePartItem.getByType(this.type));

                if (!player.getInventory().add(part.copy())) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), part);
                }

                if (disk != null) {
                    API.instance().getStorageDiskManager((ServerLevel) level).remove(diskId);
                    API.instance().getStorageDiskManager((ServerLevel) level).markForSaving();
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, new ItemStack(Registration.ADVANCED_MACHINE_CASING.get()));
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return Integer.MAX_VALUE;
    }

    private UUID getId(ItemStack disk) {
        return disk.getOrCreateTag().getUUID(ExtraItemStorageNetworkNode.NBT_ID);
    }

    private boolean isValid(ItemStack disk) {
        return disk.hasTag() && disk.getOrCreateTag().hasUUID(ExtraItemStorageNetworkNode.NBT_ID);
    }
}
