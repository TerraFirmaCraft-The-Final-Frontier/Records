package com.ashindigo.customrecord;

import com.google.common.collect.Sets;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;

public class RecordResourcePack extends FolderResourcePack {

    private static final ArrayList<String> modelFiles = new ArrayList<>();
    private static final ArrayList<String> oggFiles = new ArrayList<>();
    private static final ArrayList<String> textureFiles = new ArrayList<>();

    public RecordResourcePack() {
        super(new File("sounds.json"));
        for (SoundEvent name : RecordJsonHandler.getSounds()) {
            modelFiles.add("assets/customrecord/models/item/" + name.getSoundName().getResourcePath() + ".json");
        }
        for (SoundEvent name : RecordJsonHandler.getSounds()) {
            oggFiles.add("assets/customrecord/sounds/music/" + name.getSoundName().getResourcePath() + ".ogg");
        }
        for (SoundEvent name : RecordJsonHandler.getSounds()) {
            textureFiles.add("assets/customrecord/textures/items/" + name.getSoundName().getResourcePath() + ".png");
        }
    }

    @Override
    @Nonnull
    protected InputStream getInputStreamByName(String name) throws IOException {
        if (name.equals("assets/customrecord/sounds.json")) {
            return new ByteArrayInputStream(CustomRecord.soundsJson.getBytes());
        } else if (name.equals("pack.mcmeta")) {
            return new ByteArrayInputStream(("{\n \"pack\": {\n   \"description\": \"Custom record's internal pack\",\n   \"pack_format\": 3\n}\n}").getBytes(StandardCharsets.UTF_8));
        } else if (name.equals("assets/customrecord/lang/en_us.lang")) {
            StringBuilder str = new StringBuilder();
            ArrayList<String> names = RecordJsonHandler.getNames();
            for (int i = 0; i < names.size(); i++) {
                String eName = names.get(i);
                str.append("item.customrecord.").append(RecordJsonHandler.getSounds().get(i).getSoundName().getResourcePath()).append(".name").append("=").append("Music Disc").append("\n");
                str.append("item.record.").append(RecordJsonHandler.getSounds().get(i).getSoundName().getResourcePath()).append(".desc").append("=").append(eName).append("\n");
            }
            return new ByteArrayInputStream(str.toString().getBytes());
        } else if (modelFiles.contains(name)) {
            return new ByteArrayInputStream(RecordJsonHandler.genModelJson(name.replace("assets/customrecord/models/item/", "").replace(".json", "")).getBytes());
        } else if (oggFiles.contains(name)) {
            return new FileInputStream(new File(Loader.instance().getConfigDir() + File.separator + CustomRecord.MODID + File.separator + name.replace("assets/customrecord/sounds/music/", "").replace(".json", ".ogg")));
        } else if (textureFiles.contains(name)) {
            return new FileInputStream(new File(Loader.instance().getConfigDir() + File.separator + CustomRecord.MODID + File.separator + name.replace("assets/customrecord/textures/items/", "").replace(".json", ".png")));
        }
        return super.getInputStreamByName(name);
    }

    @Override
    protected boolean hasResourceName(String name) {
        return name.equals("assets/customrecord/sounds.json") || name.equals("assets/customrecord/lang/en_us.lang") || modelFiles.contains(name) || oggFiles.contains(name) || textureFiles.contains(name);
    }

    @Override
    @Nonnull
    public String getPackName() {
        return "CustomRecordInternalResourcePack";
    }

    @Override
    @Nonnull
    public Set<String> getResourceDomains() {
        Set<String> set = Sets.newHashSet();
        set.add("customrecord");
        return set;
    }
}
