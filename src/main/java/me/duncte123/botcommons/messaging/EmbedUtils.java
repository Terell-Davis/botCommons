/*
 *    Copyright 2018 Duncan "duncte123" Sterken
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package me.duncte123.botcommons.messaging;

import gnu.trove.map.TLongIntMap;
import gnu.trove.map.hash.TLongIntHashMap;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Supplier;

public class EmbedUtils {

    static final TLongIntMap customColors = new TLongIntHashMap();

    private static Supplier<EmbedBuilder> embedBuilderSupplier = EmbedBuilder::new;

    public static void setEmbedBuilder(Supplier<EmbedBuilder> embedBuilderSupplier) {
        EmbedUtils.embedBuilderSupplier = embedBuilderSupplier;
    }

    public static void addColor(long key, @NotNull Color value) {
        customColors.put(key, value.getRGB());
    }

    public static void addColor(long key, int value) {
        customColors.put(key, value);
    }

    public static void removeColor(long key) {
        customColors.remove(key);
    }

    /**
     * The default way to send a embedded message to the channel with a field in it
     *
     * @param title
     *         The title of the field
     * @param message
     *         The message to display
     *
     * @return The {@link MessageEmbed} to send to the channel
     */
    public static MessageEmbed embedField(String title, String message) {
        return defaultEmbed().addField(title, message, false).build();
    }

    /**
     * The default way to display a nice embedded message
     *
     * @param message
     *         The message to display
     *
     * @return The {@link MessageEmbed} to send to the channel
     */
    public static MessageEmbed embedMessage(String message) {
        return defaultEmbed().setDescription(message).build();
    }

    /**
     * The default way to display a nice embedded message
     *
     * @param message
     *         The message to display
     * @param title
     *         The title for the embed
     *
     * @return The {@link MessageEmbed} to send to the channel
     */
    public static MessageEmbed embedMessageWithTitle(String title, String message) {
        return defaultEmbed().setTitle(title).setDescription(message).build();
    }

    /**
     * The default way to send a embedded image to the channel
     *
     * @param imageURL
     *         The url from the image
     *
     * @return The {@link MessageEmbed} to send to the channel
     */
    public static MessageEmbed embedImage(String imageURL) {
        return defaultEmbed().setImage(imageURL).build();
    }

    public static MessageEmbed embedImageWithTitle(String title, String url, String image) {
        return defaultEmbed().setTitle(title, url).setImage(image).build();
    }

    /**
     * The default embed layout that all of the embeds are based off
     *
     * @return The way that that the {@link EmbedBuilder embed} will look like
     */
    public static EmbedBuilder defaultEmbed() {
        return embedBuilderSupplier.get();
    }

    public static EmbedBuilder defaultEmbed(long colorKey) {

        EmbedBuilder builder = embedBuilderSupplier.get();

        if (customColors.containsKey(colorKey)) {
            builder.setColor(customColors.get(colorKey));
        }

        return builder;
    }

    /**
     * This will convert our embeds for if the bot is not able to send embeds
     *
     * @param embed
     *         the {@link MessageEmbed} that we are trying to send
     *
     * @return the converted embed
     */
    static String embedToMessage(MessageEmbed embed) {
        StringBuilder msg = new StringBuilder();

        if (embed.getAuthor() != null) {
            msg.append("***").append(embed.getAuthor().getName()).append("***\n\n");
        }
        if (embed.getDescription() != null) {
            msg.append("_").append(embed.getDescription()
                // Reformat
                .replaceAll("\\[(.+)]\\((.+)\\)", "$1 (Link: $2)")
            ).append("_\n\n");
        }
        for (MessageEmbed.Field f : embed.getFields()) {
            msg.append("__").append(f.getName()).append("__\n").append(
                f.getValue()
                    // Reformat
                    .replaceAll("\\[(.+)]\\((.+)\\)", "$1 (Link: $2)")
            ).append("\n\n");
        }
        if (embed.getImage() != null) {
            msg.append(embed.getImage().getUrl()).append("\n");
        }
        if (embed.getFooter() != null) {
            msg.append(embed.getFooter().getText());
        }
        if (embed.getTimestamp() != null) {
            msg.append(" | ").append(embed.getTimestamp());
        }

        return msg.toString();
    }

    /*public static Queue<Message> embedToCodeBlock(MessageEmbed embed) {
        return new MessageBuilder().appendCodeBlock(embedToMessage(embed), "java").buildAll(SplitPolicy.NEWLINE);
    }*/
}