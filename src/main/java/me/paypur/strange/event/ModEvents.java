package me.paypur.strange.event;

import me.paypur.strange.data.StrangeItemTagsProvider;
import me.paypur.strange.data.StrangeRecipeProvider;
import me.paypur.strange.data.StrangeItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModEvents {

    @SubscribeEvent
    void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(new StrangeItemTagsProvider(generator, existingFileHelper));
        generator.addProvider(new StrangeRecipeProvider(generator));
        generator.addProvider(new StrangeItemModelProvider(generator, existingFileHelper));
    }

}
