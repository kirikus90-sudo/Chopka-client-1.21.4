package dev.chopka.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public final class ChopkaScreen extends Screen {
    private TextFieldWidget nicknameField;
    private int tick;

    public ChopkaScreen() {
        super(Text.literal("Chopka Client"));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int top = Math.max(48, this.height / 2 - 100);

        nicknameField = new TextFieldWidget(textRenderer, cx - 100, top + 40, 200, 20,
                Text.literal("Display nickname"));
        nicknameField.setMaxLength(16);
        nicknameField.setText(ChopkaSettings.displayNickname);
        addDrawableChild(nicknameField);

        addDrawableChild(ButtonWidget.builder(Text.literal("Apply nickname"), button -> {
            String value = nicknameField.getText().trim();
            if (!value.isEmpty()) {
                ChopkaSettings.displayNickname = value;
            }
        }).dimensions(cx - 100, top + 66, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Neon glow: " + (ChopkaSettings.neonGlow ? "ON" : "OFF")), button -> {
            ChopkaSettings.neonGlow = !ChopkaSettings.neonGlow;
            button.setMessage(Text.literal("Neon glow: " + (ChopkaSettings.neonGlow ? "ON" : "OFF")));
        }).dimensions(cx - 100, top + 94, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Particles: " + (ChopkaSettings.animatedParticles ? "ON" : "OFF")), button -> {
            ChopkaSettings.animatedParticles = !ChopkaSettings.animatedParticles;
            button.setMessage(Text.literal("Particles: " + (ChopkaSettings.animatedParticles ? "ON" : "OFF")));
        }).dimensions(cx - 100, top + 122, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("FPS HUD: " + (ChopkaSettings.showFps ? "ON" : "OFF")), button -> {
            ChopkaSettings.showFps = !ChopkaSettings.showFps;
            button.setMessage(Text.literal("FPS HUD: " + (ChopkaSettings.showFps ? "ON" : "OFF")));
        }).dimensions(cx - 100, top + 150, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Coordinates: " + (ChopkaSettings.showCoordinates ? "ON" : "OFF")), button -> {
            ChopkaSettings.showCoordinates = !ChopkaSettings.showCoordinates;
            button.setMessage(Text.literal("Coordinates: " + (ChopkaSettings.showCoordinates ? "ON" : "OFF")));
        }).dimensions(cx - 100, top + 178, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> close())
                .dimensions(cx - 100, top + 206, 200, 20).build());
    }

    @Override
    public void tick() {
        tick++;
        if (nicknameField != null) nicknameField.tick();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        int cx = this.width / 2;
        int top = Math.max(48, this.height / 2 - 100);
        int panelLeft = cx - 125;
        int panelTop = top - 28;
        int panelRight = cx + 125;
        int panelBottom = top + 239;

        context.fill(panelLeft, panelTop, panelRight, panelBottom, 0xE8172026);
        context.fill(panelLeft, panelTop, panelRight, panelTop + 2, 0xFF22E6C3);
        if (ChopkaSettings.neonGlow) {
            context.fill(panelLeft - 2, panelTop + 2, panelLeft, panelBottom - 2, 0x9922E6C3);
            context.fill(panelRight, panelTop + 2, panelRight + 2, panelBottom - 2, 0x9922E6C3);
        }

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("CHOPKA CLIENT"), cx, panelTop + 8, 0xFF63FFE1);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("VISUAL SETTINGS  •  1.21.4"), cx, panelTop + 20, 0xFFB5C8CE);
        context.drawTextWithShadow(textRenderer, Text.literal("Display nickname (UI only)"), cx - 100, top + 28, 0xFFD6E5E8);

        if (ChopkaSettings.animatedParticles) {
            for (int i = 0; i < 10; i++) {
                int x = (i * 83 + tick * (i % 3 + 1)) % Math.max(1, this.width);
                int y = (i * 47 + tick / 2) % Math.max(1, this.height);
                context.fill(x, y, x + 2, y + 2, 0x6635F5D0);
            }
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
