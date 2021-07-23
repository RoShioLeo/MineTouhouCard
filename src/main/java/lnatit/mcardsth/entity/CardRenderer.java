package lnatit.mcardsth.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class CardRenderer extends EntityRenderer<CardEntity>
{
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private final Random random = new Random();

    public CardRenderer(EntityRendererManager renderManagerIn, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
    {
        super(renderManagerIn);
        this.itemRenderer = itemRendererIn;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    public void render(CardEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.push();
        ItemStack itemstack = entityIn.getCard();
        int i = itemstack.isEmpty() ? 187 : Item.getIdFromItem(itemstack.getItem()) + itemstack.getDamage();
        this.random.setSeed(i);
        IBakedModel ibakedmodel = this.itemRenderer
                .getItemModelWithOverrides(itemstack, entityIn.world, null);
        boolean flag = ibakedmodel.isGui3d();
        float f1 = MathHelper.sin(((float) entityIn.getAge() + partialTicks) / 10.0F + entityIn.hoverStart) * 0.1F + 0.1F;
        float f2 = shouldBob() ? ibakedmodel
                .getItemCameraTransforms()
                .getTransform(ItemCameraTransforms.TransformType.GROUND)
                .scale
                .getY() : 0;
        matrixStackIn.translate(0.0D, f1 + 0.25F * f2, 0.0D);
        float f3 = entityIn.getItemHover(partialTicks);
        matrixStackIn.rotate(Vector3f.YP.rotation(f3));
        if (!flag)
            matrixStackIn.translate(0, 0, 0);

        matrixStackIn.push();

        this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        matrixStackIn.pop();
        if (!flag)
            matrixStackIn.translate(0.0, 0.0, 0.09375F);

        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override

    public ResourceLocation getEntityTexture(@Nonnull CardEntity entity)
    {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }

    /**
     * @return If items should have a bob effect
     */
    public boolean shouldBob()
    {
        return true;
    }

    @Override
    public boolean shouldRender(@Nonnull CardEntity livingEntityIn, @Nonnull ClippingHelper camera, double camX, double camY, double camZ)
    {
        return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }
}