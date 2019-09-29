package com.ashindigo.customrecord;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.Objects;

@Mod.EventBusSubscriber
@Mod(modid = CustomRecord.MODID, name = CustomRecord.NAME, version = CustomRecord.VERSION)
public class CustomRecord {

    static final String MODID = "customrecord";
    static final String NAME = "CustomRecord";
    static final String VERSION = "1.0";
    static String soundsJson;

    @Mod.Instance
    private static CustomRecord INSTANCE;

    @SidedProxy(clientSide = "com.ashindigo.customrecord.ClientProxy", serverSide = "com.ashindigo.customrecord.CommonProxy")
    private static CommonProxy proxy;

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (ItemCustomRecord item : RecordJsonHandler.getRecipeMap().keySet()) {
            item.setItem(new ItemStack(Objects.requireNonNull(Item.getByNameOrId(RecordJsonHandler.getRecipeMap().get(item).getKey())), 1, RecordJsonHandler.getRecipeMap().get(item).getValue()));
        }
        for (ItemCustomRecord record : RecordJsonHandler.getRecords()) {
            NonNullList<Ingredient> list = NonNullList.create();
            list.add(0, Ingredient.EMPTY);
            list.add(1, Ingredient.fromStacks(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata())));
            list.add(2, Ingredient.EMPTY);
            list.add(3, Ingredient.fromStacks(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata())));
            ItemStack item = record.getItem();
            list.add(4, Ingredient.fromStacks(item)); // Custom item
            list.add(5, Ingredient.fromStacks(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata())));
            list.add(6, Ingredient.EMPTY);
            list.add(7, Ingredient.fromStacks(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata())));
            list.add(8, Ingredient.EMPTY);
            event.getRegistry().register(new ShapedRecipes("", 3, 3, list, new ItemStack(record)).setRegistryName(Objects.requireNonNull(record.getRegistryName())));
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (ItemCustomRecord record : RecordJsonHandler.getRecords()) {
            ModelLoader.setCustomModelResourceLocation(record, 0, new ModelResourceLocation(Objects.requireNonNull(record.getRegistryName()), "inventory"));
        }
    }

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        File recordsJson = proxy.setupFiles();
        RecordJsonHandler.handleConfig(recordsJson);
        soundsJson = RecordJsonHandler.setupSoundsJson();
        proxy.performReflection();
    }



    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent sounds : RecordJsonHandler.getSounds()) {
            event.getRegistry().register(sounds);
        }
    }
}
