package me.paypur.strange.event;

import me.paypur.strange.data.RecipeDataProvider;
import me.paypur.strange.data.ItemTagsDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModEvents {

    @SubscribeEvent
    void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
//        if (event.includeServer()) {
            generator.addProvider(new RecipeDataProvider(generator));
            generator.addProvider(new ItemTagsDataProvider(generator, existingFileHelper));
//        }
    }

}
