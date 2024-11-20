package com.dre.brewery.integration.item;

import com.dre.brewery.BreweryPlugin;
import com.dre.brewery.integration.Hook;
import com.dre.brewery.recipe.PluginItem;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;

public class MMOItemsPluginItem extends PluginItem {

// When implementing this, put Brewery as softdepend in your plugin.yml!
// We're calling this as server start:
// PluginItem.registerForConfig("mmoitems", MMOItemsPluginItem::new);

	@Override
	public boolean matches(ItemStack item) {
		if (!Hook.MMOITEMS.isEnabled()) return false;

		try {
			NBTItem nbtItem = NBTItem.get(item);
			return nbtItem.hasType() && nbtItem.getString("MMOITEMS_ITEM_ID").equalsIgnoreCase(getItemId());
		} catch (Throwable e) {
			e.printStackTrace();
			BreweryPlugin.getInstance().errorLog("Could not check MMOItems for Item ID");
			Hook.MMOITEMS.setEnabled(false);
			return false;
		}
	}
}
