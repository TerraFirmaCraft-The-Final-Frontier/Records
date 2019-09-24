package com.ashindigo.customrecord;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class RecordJsonParser {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static ArrayList<ItemCustomRecord> parse() throws IOException {
        ArrayList<ItemCustomRecord> records = new ArrayList<>();
        File recordsJson = new File(FabricLoader.getInstance().getConfigDirectory() + "/customrecord/records.json");
        if (!recordsJson.exists()) {
            recordsJson.getParentFile().mkdirs();
            recordsJson.createNewFile();
        }
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(new File(FabricLoader.getInstance().getConfigDirectory() + File.separator + "customrecord" + File.separator + "records.json")));
        JsonObject element = gson.fromJson(reader, JsonObject.class);
        //int i = 0;
        if (element != null) {
            for (Map.Entry<String, JsonElement> object : element.entrySet()) {
                SoundEvent event = new SoundEvent(new Identifier(CustomRecordMod.MODID, object.getValue().getAsJsonObject().get("filename").getAsString()));
                Item item = Registry.ITEM.get(Identifier.tryParse(object.getValue().getAsJsonObject().get("item").getAsString()));
                ItemStack stack = new ItemStack(Objects.requireNonNull(item));
                records.add(new ItemCustomRecord(event, stack, object.getValue().getAsJsonObject().get("name").getAsString()));
                //map.put(records.get(i++), object.getValue().getAsJsonObject().get("name").getAsString());
            }
        }
        return records;
    }
}
