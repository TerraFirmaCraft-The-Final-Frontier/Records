package com.ashindigo.customrecord;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @SuppressWarnings("WeakerAccess")
    File recordsJson;

    public void performReflection() {
        ArrayList<IResourcePack> actualList = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao");
        actualList.add(new RecordResourcePack());
        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), actualList, "field_110449_ao");
    }

    @SuppressWarnings("all")
    public File setupFiles() {
        recordsJson = new File(Loader.instance().getConfigDir() + File.separator + CustomRecord.MODID + File.separator + "records.json");
        if (!recordsJson.exists()) {
            try {
                recordsJson.getParentFile().mkdirs();
                recordsJson.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return recordsJson;
    }
}
