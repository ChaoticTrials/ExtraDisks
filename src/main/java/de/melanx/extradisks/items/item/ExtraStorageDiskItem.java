package de.melanx.extradisks.items.item;

import com.raoulvdberge.refinedstorage.api.storage.StorageType;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDisk;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDiskProvider;
import com.raoulvdberge.refinedstorage.api.storage.disk.StorageDiskSyncData;
import com.raoulvdberge.refinedstorage.apiimpl.API;
import com.raoulvdberge.refinedstorage.render.Styles;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ExtraStorageDiskItem extends Item implements IStorageDiskProvider {
    private static final String NBT_ID = "Id";
    private final ExtraItemStorageType type;

    public ExtraStorageDiskItem(ExtraItemStorageType type) {
        super((new Properties()).group(ExtraDisks.ModGroup).maxStackSize(1));
        this.type = type;
        this.setRegistryName(ExtraDisks.MODID, type.getName() + "_storage_disk");
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isRemote && !stack.hasTag()) {
            UUID id = UUID.randomUUID();
            API.instance().getStorageDiskManager((ServerWorld) world).set(id, API.instance().createDefaultItemDisk((ServerWorld) world, this.getCapacity(stack)));
            API.instance().getStorageDiskManager((ServerWorld) world).markForSaving();
            this.setId(stack, id);
        }

    }

    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (this.isValid(stack)) {
            UUID id = this.getId(stack);
            API.instance().getStorageDiskSync().sendRequest(id);
            StorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);
            if (data != null) {
                if (data.getCapacity() == -1) {
                    tooltip.add((new TranslationTextComponent("misc.refinedstorage.storage.stored", new Object[]{API.instance().getQuantityFormatter().format(data.getStored())})).setStyle(Styles.GRAY));
                } else {
                    tooltip.add((new TranslationTextComponent("misc.refinedstorage.storage.stored_capacity", new Object[]{API.instance().getQuantityFormatter().format(data.getStored()), API.instance().getQuantityFormatter().format(data.getCapacity())})).setStyle(Styles.GRAY));
                }
            }

            if (flag.isAdvanced()) {
                tooltip.add((new StringTextComponent(id.toString())).setStyle(Styles.GRAY));
            }
        }

    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack diskStack = player.getHeldItem(hand);
        if (!world.isRemote && player.isSneaking()) {
            IStorageDisk disk = API.instance().getStorageDiskManager((ServerWorld) world).getByStack(diskStack);
            if (disk != null && disk.getStored() == 0) {
                ItemStack storagePart = new ItemStack(ExtraStoragePartItem.getByType(this.type), diskStack.getCount());
                if (!player.inventory.addItemStackToInventory(storagePart.copy())) {
                    InventoryHelper.spawnItemStack(world, (double) player.getPosition().getX(), (double) player.getPosition().getY(), (double) player.getPosition().getZ(), storagePart);
                }

                API.instance().getStorageDiskManager((ServerWorld) world).remove(this.getId(diskStack));
                API.instance().getStorageDiskManager((ServerWorld) world).markForSaving();
                return new ActionResult(ActionResultType.SUCCESS, new ItemStack(ExtraItems.ADVANCED_STORAGE_HOUSING));
            }
        }

        return new ActionResult(ActionResultType.PASS, diskStack);
    }

    public int getEntityLifespan(ItemStack stack, World world) {
        return Integer.MAX_VALUE;
    }

    public UUID getId(ItemStack disk) {
        return disk.getTag().getUniqueId(NBT_ID);
    }

    public void setId(ItemStack disk, UUID id) {
        disk.setTag(new CompoundNBT());
        disk.getTag().putUniqueId(NBT_ID, id);
    }

    public boolean isValid(ItemStack disk) {
        return disk.hasTag() && disk.getTag().hasUniqueId(NBT_ID);
    }

    public int getCapacity(ItemStack disk) {
        return this.type.getCapacity();
    }

    public StorageType getType() {
        return StorageType.ITEM;
    }

}
