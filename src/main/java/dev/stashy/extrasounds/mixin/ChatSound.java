package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.Mixers;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatSound
{
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V")
    private void messageSound(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci)
    {
        if (MinecraftClient.getInstance().player == null || refresh)
            return;
        String msg = message.getString();
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        if (msg.contains("@" + p.getName().getString()) || msg.contains("@" + p.getDisplayName().getString()))
        {
            if (ExtraSounds.config.enableChatMentionSounds)
            {
                ExtraSounds.playSound(Sounds.CHAT_MENTION, Mixers.CHAT);
            }
        }

        else if (ExtraSounds.config.enableChatMessageSounds)
        {
            ExtraSounds.playSound(Sounds.CHAT, Mixers.CHAT);
        }

    }
}
