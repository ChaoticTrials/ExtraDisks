package de.melanx.extradisks.items.storageblocks;

import com.refinedmods.refinedstorage.RSBlocks;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.api.storage.disk.StorageDiskSyncData;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.item.blockitem.BaseBlockItem;
import com.refinedmods.refinedstorage.render.Styles;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlock;
import de.melanx.extradisks.blocks.item.ExtraItemStorageNetworkNode;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import de.melanx.extradisks.items.item.ExtraStoragePartItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ExtraItemStorageBlockItem extends BaseBlockItem {
    private final ExtraItemStorageType type;

    public ExtraItemStorageBlockItem(ExtraItemStorageBlock block, Properties builder) {
        super(block, builder);
        this.type = block.getType();
    }

    @OnlyIn(Dist.CLIENT)

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (this.isValid(stack)) {
            UUID id = this.getId(stack);
            API.instance().getStorageDiskSync().sendRequest(id);
            StorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);

            if (data != null) {
                if (data.getCapacity() == -1) {
                    tooltip.add(new TranslationTextComponent("misc.refinedstorage.storage.stored", API.instance().getQuantityFormatter().format(data.getStored())).setStyle(Styles.GRAY));
                } else {
                    tooltip.add(new TranslationTextComponent("misc.refinedstorage.storage.stored_capacity", API.instance().getQuantityFormatter().format(data.getStored()), API.instance().getQuantityFormatter().format(data.getCapacity())).setStyle(Styles.GRAY));
                }
            }

            if (flag.isAdvanced()) {
                tooltip.add(new StringTextComponent(id.toString()).setStyle(Styles.GRAY));
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote && player.isCrouching()) {
            UUID diskId = null;
            IStorageDisk disk = null;

            if (this.isValid(stack)) {
                diskId = this.getId(stack);
                disk = API.instance().getStorageDiskManager((ServerWorld) world).get(diskId);
            }

            if (disk == null || disk.getStored() == 0) {
                ItemStack part = new ItemStack(ExtraStoragePartItem.getByType(this.type));

                if (!player.inventory.addItemStackToInventory(part.copy())) {
                    InventoryHelper.spawnItemStack(world, player.getPosX(), player.getPosY(), player.getPosZ(), part);
                }

                if (disk != null) {
                    API.instance().getStorageDiskManager((ServerWorld) world).remove(diskId);
                    API.instance().getStorageDiskManager((ServerWorld) world).markForSaving();
                }

                return new ActionResult<>(ActionResultType.SUCCESS, new ItemStack(Registration.ADVANCED_MACHINE_CASING.get()));
            }
        }
        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    private UUID getId(ItemStack disk) {
        return disk.getTag().getUniqueId(ExtraItemStorageNetworkNode.NBT_ID);
    }

    private boolean isValid(ItemStack disk) {
        return disk.hasTag() && disk.getTag().hasUniqueId(ExtraItemStorageNetworkNode.NBT_ID);
    }
}
