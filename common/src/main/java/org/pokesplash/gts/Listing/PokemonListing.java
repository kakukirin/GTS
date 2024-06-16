package org.pokesplash.gts.Listing;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.util.UUID;

/**
 * Class that holds a single listing.
 */
public class PokemonListing extends Listing<Pokemon> {
	// The Pokemon that is being listed.
	private final JsonObject pokemon;

	/**
	 * Constructor to create a new listing.
	 * @param sellerUuid The UUID of the person selling the Pokemon.
	 * @param sellerName The name of the seller.
	 * @param price The price the Pokemon is selling for.
	 * @param pokemon The Pokemon for sale.
	 */
	public PokemonListing(UUID sellerUuid, String sellerName, double price, Pokemon pokemon) {
		super(sellerUuid, sellerName, price, true);
		this.pokemon = pokemon.saveToJSON(new JsonObject());
	}

	@Override
	public Pokemon getListing() {
		return new Pokemon().loadFromJSON(pokemon);
	}

	public JsonObject getListingAsJsonObject() {
		return pokemon;
	}

	public MutableComponent getDisplayName() {
		Style blue = Style.EMPTY.withColor(TextColor.parseColor("blue"));
		Style dark_aqua = Style.EMPTY.withColor(TextColor.parseColor("dark_aqua"));
		Style red = Style.EMPTY.withColor(TextColor.parseColor("red"));
		Style yellow = Style.EMPTY.withColor(TextColor.parseColor("yellow"));
		Style white = Style.EMPTY.withColor(TextColor.parseColor("white"));
		Pokemon pokemon = this.getListing();
		boolean isShiny = pokemon.getShiny();
		MutableComponent displayName = pokemon.getDisplayName().setStyle(isShiny ? yellow : dark_aqua);
		if (isShiny) {
			displayName.append(Component.literal("★").setStyle(red));
		}
		displayName.append(" ").append(Component.translatable("cobblemon.ui.lv.number", pokemon.getLevel()).setStyle(white));
		switch (pokemon.getGender().toString()) {
			case "MALE":
				displayName.append(Component.literal(" ♂").setStyle(blue));
				break;
			case "FEMALE":
				displayName.append(Component.literal(" ♀").setStyle(red));
				break;
			default:
				break;
		}
		return displayName;
	}
}
