package lnatit.mcardsth.datagen;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class AdvancementConsumer implements Consumer<Consumer<Advancement>>
{
    @Override
    public void accept(Consumer<Advancement> consumer)
    {
        Advancement root =
                Advancement.Builder
                        .builder()
                        .withDisplay(ItemReg.BLANK.get(),
                                     new TranslationTextComponent("advancements." + MOD_ID + ".root.title"),
                                     new TranslationTextComponent("advancements." + MOD_ID + ".root.description"),
                                     new ResourceLocation("textures/icons.png"),
                                     FrameType.TASK, true, true, false)
                        .withCriterion("impossible", new ImpossibleTrigger.Instance())
                        .register(consumer, MOD_ID + ":root");

        Advancement adv[] = new Advancement[56];
        int i = 0;
        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
        {
            Item item = itemObj.get();
            if (item instanceof AbstractCard)
            {
                String name = item.getRegistryName().getPath();
                adv[i++] =
                        Advancement.Builder
                                .builder()
                                .withDisplay(item,
                                             new TranslationTextComponent("advancements." + MOD_ID + '.' + name + ".title"),
                                             new TranslationTextComponent("advancements." + MOD_ID + '.' + name + ".description"),
                                             null,
                                             FrameType.TASK, true, true, false)
//                                .withParentId(new ResourceLocation(MOD_ID, ""))
                                .withCriterion("impossible", new ImpossibleTrigger.Instance())
                                .register(consumer, MOD_ID + ':' + name);
            }
        }
    }
}