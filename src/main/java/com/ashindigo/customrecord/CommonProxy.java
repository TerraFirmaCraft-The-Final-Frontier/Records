package com.ashindigo.customrecord;

import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class CommonProxy {

    File recordsJson;

    public void performReflection() {
        //NO-OP
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
