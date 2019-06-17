package com.ashindigo.customrecord;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

class ItemCustomRecord extends ItemRecord {

    private final ItemStack item;

    ItemCustomRecord(String name, SoundEvent sound, ItemStack item) {
        super(name, sound);
        this.item = item;
        this.setRegistryName(CustomRecord.MODID, name);
        this.setUnlocalizedName(CustomRecord.MODID + "." + name);
        this.setCreativeTab(CreativeTabs.MISC);
        ForgeRegistries.ITEMS.register(this);

    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal("item.record.name").trim();
    }

    public ItemStack getItem() {
        return item;
    }
}
