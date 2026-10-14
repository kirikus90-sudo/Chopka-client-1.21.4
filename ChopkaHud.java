package dev.chopka.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.text.Text;

public final class ChopkaHud {
    private ChopkaHud() {}

    @SuppressWarnings("deprecation")
    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            var client = net.minecraft.client.MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;

            int y = 8;
            if (ChopkaSettings.showFps) {
                drawContext.drawTextWithShadow(client.textRenderer,
                        Text.literal("CHOPKA  •  " + client.getCurrentFps() + " FPS"), 8, y, 0xFF63FFE1);
                y += 12;
            }
            if (ChopkaSettings.showCoordinates) {
                var p = client.player.getBlockPos();
                drawContext.drawTextWithShadow(client.textRenderer,
                        Text.literal("XYZ " + p.getX() + " / " + p.getY() + " / " + p.getZ()), 8, y, 0xFFD6E5E8);
            }
        });
    }
}
