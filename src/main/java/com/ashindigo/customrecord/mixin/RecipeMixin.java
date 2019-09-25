package com.ashindigo.customrecord.mixin;

import com.ashindigo.customrecord.CustomRecordMod;
import com.ashindigo.customrecord.ItemCustomRecord;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Dynamic code based recipes
 */
@Mixin(RecipeManager.class)
public abstract class RecipeMixin extends JsonDataLoader {

    @Shadow
    private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipeMap;

    public RecipeMixin(Gson gson_1, String string_1) {
        super(gson_1, string_1);
    }

    @Inject(method = "method_20705(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("RETURN"))
    private void addRecipe(CallbackInfo info) {
        ImmutableMap.Builder<RecipeType<?>, Map<Identifier, Recipe<?>>> iMapB = new ImmutableMap.Builder<>();
        Map<Identifier, Recipe<?>> map = new HashMap<>();
        for (ItemCustomRecord item : CustomRecordMod.list) {
            map.put(item.getID(), new ShapedRecipe(item.getID(), "", 3, 3,
                    DefaultedList.copyOf(Ingredient.EMPTY,
                            Ingredient.EMPTY, Ingredient.ofItems(Items.BLACK_TERRACOTTA), Ingredient.EMPTY,
                            Ingredient.ofItems(Items.BLACK_TERRACOTTA), Ingredient.ofItems(item.getStack().getItem()), Ingredient.ofItems(Items.BLACK_TERRACOTTA),
                            Ingredient.EMPTY, Ingredient.ofItems(Items.BLACK_TERRACOTTA), Ingredient.EMPTY),
                    new ItemStack(item)));
        }
        map.putAll(recipeMap.get(RecipeType.CRAFTING));
        iMapB.put(RecipeType.CRAFTING, map);
        recipeMap = iMapB.build();

    }

}
