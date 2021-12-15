package de.melanx.extradisks.items.item;

import com.refinedmods.refinedstorage.api.storage.StorageType;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDiskProvider;
import com.refinedmods.refinedstorage.api.storage.disk.StorageDiskSyncData;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.render.Styles;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ExtraStorageDiskItem extends Item implements IStorageDiskProvider {
    private static final String NBT_ID = "Id";
    private final ExtraItemStorageType type;

    public ExtraStorageDiskItem(ExtraItemStorageType type) {
        super(new Properties().tab(ExtraDisks.ModCategory).stacksTo(1));
        this.type = type;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
        if (!level.isClientSide && !stack.hasTag()) {
            UUID id = UUID.randomUUID();
            API.instance().getStorageDiskManager((ServerLevel) level).set(id, API.instance().createDefaultItemDisk((ServerLevel) level, this.getCapacity(stack), (Player) entity));
            API.instance().getStorageDiskManager((ServerLevel) level).markForSaving();
            this.setId(stack, id);
        }
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (this.isValid(stack)) {
            UUID id = this.getId(stack);
            API.instance().getStorageDiskSync().sendRequest(id);
            StorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);
            if (data != null) {
                if (data.getCapacity() == -1) {
                    tooltip.add((new TranslatableComponent("misc.refinedstorage.storage.stored", API.instance().getQuantityFormatter().format(data.getStored()))).withStyle(Styles.GRAY));
                } else {
                    tooltip.add((new TranslatableComponent("misc.refinedstorage.storage.stored_capacity", API.instance().getQuantityFormatter().format(data.getStored()), API.instance().getQuantityFormatter().format(data.getCapacity()))).withStyle(Styles.GRAY));
                }
            }

            if (flag.isAdvanced()) {
                tooltip.add((new TextComponent(id.toString())).withStyle(Styles.GRAY));
            }
        }

    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack diskStack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isCrouching()) {
            IStorageDisk disk = API.instance().getStorageDiskManager((ServerLevel) level).getByStack(diskStack);
            if (disk != null && disk.getStored() == 0) {
                ItemStack storagePart = new ItemStack(ExtraStoragePartItem.getByType(this.type), diskStack.getCount());
                if (!player.getInventory().add(storagePart.copy())) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), storagePart);
                }

                API.instance().getStorageDiskManager((ServerLevel) level).remove(this.getId(diskStack));
                API.instance().getStorageDiskManager((ServerLevel) level).markForSaving();
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, new ItemStack(Registration.ADVANCED_STORAGE_HOUSING.get()));
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, diskStack);
    }

    @Override
    public int getEntityLifespan(ItemStack stack, Level level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public UUID getId(ItemStack disk) {
        return disk.getOrCreateTag().getUUID(NBT_ID);
    }

    @Override
    public void setId(ItemStack disk, UUID id) {
        disk.setTag(new CompoundTag());
        disk.getOrCreateTag().putUUID(NBT_ID, id);
    }

    @Override
    public boolean isValid(ItemStack disk) {
        return disk.getOrCreateTag().hasUUID(NBT_ID);
    }

    @Override
    public int getCapacity(ItemStack disk) {
        return this.type.getCapacity();
    }

    @Override
    public StorageType getType() {
        return StorageType.ITEM;
    }

}
