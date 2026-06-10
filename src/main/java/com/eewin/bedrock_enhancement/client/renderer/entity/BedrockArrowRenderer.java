package com.eewin.bedrock_enhancement.client.renderer.entity;

import com.eewin.bedrock_enhancement.entity.BedrockArrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;

public class BedrockArrowRenderer extends ArrowRenderer<BedrockArrowEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("bedrock_enhancement", "textures/entity/bedrock_arrow.png");

    public BedrockArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BedrockArrowEntity entity) {
        return TEXTURE;
    }
}
