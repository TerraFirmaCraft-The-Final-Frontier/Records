package com.ashindigo.customrecord;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class RecordJsonHandler {

    private static final ArrayList<ItemCustomRecord> records = new ArrayList<>();
    private static final ArrayList<SoundEvent> sounds = new ArrayList<>();
    private static final ArrayList<String> name = new ArrayList<>();
    private static final HashMap<ItemCustomRecord, String> map = new HashMap<>();

    static void handleConfig(File file) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(file));
            JsonObject element = gson.fromJson(reader, JsonObject.class);
            int i = 0;
            if (element != null) {
                for (Map.Entry<String, JsonElement> object : element.entrySet()) {
                    name.add(object.getValue().getAsJsonObject().get("name").getAsString());
                    SoundEvent event = new SoundEvent(new ResourceLocation(CustomRecord.MODID, object.getValue().getAsJsonObject().get("filename").getAsString())).setRegistryName(CustomRecord.MODID, object.getValue().getAsJsonObject().get("filename").getAsString());
                    Item item = Item.getByNameOrId(object.getValue().getAsJsonObject().get("item").getAsString());
                    if (item == null) {
                        throw new NullPointerException(object.getValue().getAsJsonObject().get("item").getAsString() + " is not a valid item!");
                    }
                    ItemStack stack = new ItemStack(Objects.requireNonNull(item));
                    stack.setItemDamage(object.getValue().getAsJsonObject().get("meta").getAsInt());
                    records.add(new ItemCustomRecord(object.getValue().getAsJsonObject().get("filename").getAsString(), event, stack));
                    sounds.add(event);
                    map.put(records.get(i++), object.getValue().getAsJsonObject().get("name").getAsString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static String setupSoundsJson() {
        Gson gson = new Gson();
        JsonObject finishedFile = new JsonObject();
        for (SoundEvent sound : sounds) {
            JsonObject soundInfo = new JsonObject();
            soundInfo.addProperty("category", "records");
            JsonObject trackInfo = new JsonObject();
            trackInfo.addProperty("name", CustomRecord.MODID + ":music/" + Objects.requireNonNull(sound.getRegistryName()).getResourcePath());
            trackInfo.addProperty("stream", true);
            JsonArray array = new JsonArray();
            array.add(trackInfo);
            soundInfo.add("sounds", array);
            finishedFile.add(sound.getRegistryName().getResourcePath(), soundInfo);
        }
        return gson.toJson(finishedFile);
    }

    public static ArrayList<SoundEvent> getSounds() {
        return sounds;
    }

    static ArrayList<String> getNames() {
        return name;
    }

    public static ArrayList<ItemCustomRecord> getRecords() {
        return records;
    }

    public static HashMap<ItemCustomRecord, String> getMap() {
        return map;
    }

    static String genModelJson(String name) {
        JsonObject finishedFile = new JsonObject();
        finishedFile.addProperty("forge_marker", 1);
        finishedFile.addProperty("parent", "item/generated");
        JsonObject texture = new JsonObject();
        texture.addProperty("layer0", "customrecord:items/" + name);
        finishedFile.add("textures", texture);
        return finishedFile.toString();
    }
}
