package com.ashindigo.customrecord;

import com.ashindigo.autojson.api.AutoConfig;
import com.ashindigo.autojson.api.AutoJsonApi;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.ArrayList;

public class CustomRecordMod implements ModInitializer {
    public static final String MODID = "customrecord";
    public static ArrayList<ItemCustomRecord> list;
    @Override
    public void onInitialize() {
        try {
            list = RecordJsonParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ItemCustomRecord item : list) {
            Registry.register(Registry.ITEM, item.getID(), item);
            AutoJsonApi.addEntry(item.getID(), new AutoConfig(AutoConfig.AutoConfigTextureMode.EXTERNAL, AutoConfig.AutoConfigType.ITEM).setLangName(new TranslatableText("item.minecraft.music_disc_13").asString()));
            AutoJsonApi.addSoundEntry(item.getID(), item.getEvent());
        }
    }
}