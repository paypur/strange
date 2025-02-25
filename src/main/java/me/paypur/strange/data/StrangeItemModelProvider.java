package me.paypur.strange.data;

import me.paypur.strange.Strange;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class StrangeItemModelProvider extends ItemModelProvider {
    public StrangeItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Strange.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture("strangifier", new ResourceLocation("item/generated"), "layer0",  new ResourceLocation(Strange.MOD_ID, "item/strangifier"));
    }
}
