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

    @Override
    public void onInitialize() {
        try {
            ArrayList<ItemCustomRecord> list = RecordJsonParser.parse();
            for (ItemCustomRecord item : list) {
                Registry.register(Registry.ITEM, item.getID(), item);
                AutoJsonApi.addEntry(item.getID(), new AutoConfig(AutoConfig.AutoConfigTextureMode.EXTERNAL, AutoConfig.AutoConfigType.ITEM).setLangName(new TranslatableText("item.minecraft.music_disc_13").asString()));
                AutoJsonApi.addSoundEntry(item.getSound());
            }
            System.out.println(AutoJsonApi.getMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
