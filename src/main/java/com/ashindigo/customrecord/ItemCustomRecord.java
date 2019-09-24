package com.ashindigo.customrecord;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemCustomRecord extends MusicDiscItem {

    private final ItemStack stack;
    private final String name;
    private final Identifier id;

    public ItemCustomRecord(SoundEvent event, ItemStack stack, String name) {
        super(15, event, new Settings().group(ItemGroup.MISC).maxCount(1)); // Sorry redstoners
        this.stack = stack;
        this.name = name;
        this.id = new Identifier(CustomRecordMod.MODID, event.getId().getPath());
    }

    public ItemStack getStack() {
        return stack;
    }

    @Environment(EnvType.CLIENT)
    public Text getDescription() {
        return new LiteralText(getIName());
    }

    public String getIName() {
        return name;
    }

    public Identifier getID() {
        return id;
    }

}
