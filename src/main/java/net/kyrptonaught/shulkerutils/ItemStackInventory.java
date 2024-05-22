package net.kyrptonaught.shulkerutils;


import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;


public class ItemStackInventory extends SimpleInventory {
    protected final ItemStack itemStack;
    protected final int SIZE;

    public ItemStackInventory(ItemStack stack, int SIZE) {
        super(getStacks(stack, SIZE).toArray(new ItemStack[SIZE]));
        itemStack = stack;
        this.SIZE = SIZE;
    }

    public static DefaultedList<ItemStack> getStacks(ItemStack usedStack, int SIZE) {
        //  usedStack.getComponents().get(DataComponentTypes.CONTAINER).stream().forEach((System.out::println));
        //   NbtComponent compoundTag = usedStack.getComponents().get(DataComponentTypes.BLOCK_ENTITY_DATA);
        DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);
        //    if (compoundTag != null && compoundTag.contains("Items")) {
        //       Inventories.readNbt(compoundTag.copyNbt(), itemStacks,null);
        //    }
        //System.out.println(SIZE);
        usedStack.getComponents().get(DataComponentTypes.CONTAINER).copyTo(itemStacks);
        return itemStacks;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        try {
            DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);
            for (int i = 0; i < size(); i++) {
                itemStacks.set(i, getStack(i));
            }
            itemStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(itemStacks));
            //  if (shouldDeleteNBT(blockEntityTag)) {
            //        itemStack.remove(DataComponentTypes.BLOCK_ENTITY_DATA);
//            itemStack.removeSubNbt("BlockEntityTag");}
        } catch (Exception ignored) {
        }
    }

    public boolean shouldDeleteNBT(NbtCompound blockEntityTag) {
        if (!blockEntityTag.contains("Items"))
            return blockEntityTag.getKeys().isEmpty();
        return isEmpty();
    }

    @Override
    public void onClose(PlayerEntity playerEntity_1) {
        if (itemStack.getCount() > 1) {
            int count = itemStack.getCount();
            itemStack.setCount(1);
            playerEntity_1.giveItemStack(new ItemStack(itemStack.getItem(), count - 1));
        }
        markDirty();
        // itemStack.removeSubTag(QuickShulkerMod.MOD_ID);
    }
}