package cn.shadow.vacation_diary.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class VacationGroup extends ItemGroup {
    public VacationGroup() {
        super("vacation_group");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.dimension.get());
    }
}
