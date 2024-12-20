package com.dre.brewery.commands.subcommands;

import com.dre.brewery.BIngredients;
import com.dre.brewery.Brew;
import com.dre.brewery.BreweryPlugin;
import com.dre.brewery.commands.SubCommand;
import com.dre.brewery.configuration.files.Lang;
import com.dre.brewery.recipe.BRecipe;
import com.dre.brewery.recipe.Ingredient;
import com.dre.brewery.recipe.RecipeItem;
import com.dre.brewery.utility.Logging;
import com.dre.brewery.utility.MinecraftVersion;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DebugInfoCommand implements SubCommand {


    @Override
    public void execute(BreweryPlugin breweryPlugin, Lang lang, CommandSender sender, String label, String[] args) {
        debugInfo(sender, args.length > 1 ? args[1] : null);
    }

    @Override
    public List<String> tabComplete(BreweryPlugin breweryPlugin, CommandSender sender, String label, String[] args) {
        return null;
    }

    @Override
    public String permission() {
        return "brewery.cmd.debuginfo";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }


    public void debugInfo(CommandSender sender, String recipeName) {
        if (BreweryPlugin.getMCVersion().isOrEarlier(MinecraftVersion.V1_9) || !sender.isOp()) return;
        Player player = (Player) sender;
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand != null) {
            Brew brew = Brew.get(hand);
            if (brew == null) return;
            Logging.log(brew.toString());
            BIngredients ingredients = brew.getIngredients();
            if (recipeName == null) {
                Logging.log("&lIngredients:");
                for (Ingredient ing : ingredients.getIngredientList()) {
                    Logging.log(ing.toString());
                }
                Logging.log("&lTesting Recipes");
                for (BRecipe recipe : BRecipe.getAllRecipes()) {
                    int ingQ = ingredients.getIngredientQuality(recipe);
                    int cookQ = ingredients.getCookingQuality(recipe, false);
                    int cookDistQ = ingredients.getCookingQuality(recipe, true);
                    int ageQ = ingredients.getAgeQuality(recipe, brew.getAgeTime());
                    Logging.log(recipe.getRecipeName() + ": ingQlty: " + ingQ + ", cookQlty:" + cookQ + ", cook+DistQlty: " + cookDistQ + ", ageQlty: " + ageQ);
                }
                BRecipe distill = ingredients.getBestRecipe(brew.getWood(), brew.getAgeTime(), true);
                BRecipe nonDistill = ingredients.getBestRecipe(brew.getWood(), brew.getAgeTime(), false);
                Logging.log("&lWould prefer Recipe: " + (nonDistill == null ? "none" : nonDistill.getRecipeName()) + " and Distill-Recipe: " + (distill == null ? "none" : distill.getRecipeName()));
            } else {
                BRecipe recipe = BRecipe.getMatching(recipeName);
                if (recipe == null) {
                    Logging.msg(player, "Could not find Recipe " + recipeName);
                    return;
                }
                Logging.log("&lIngredients in Recipe " + recipe.getRecipeName() + ":");
                for (RecipeItem ri : recipe.getIngredients()) {
                    Logging.log(ri.toString());
                }
                Logging.log("&lIngredients in Brew:");
                for (Ingredient ingredient : ingredients.getIngredientList()) {
                    int amountInRecipe = recipe.amountOf(ingredient);
                    Logging.log(ingredient.toString() + ": " + amountInRecipe + " of this are in the Recipe");
                }
                int ingQ = ingredients.getIngredientQuality(recipe);
                int cookQ = ingredients.getCookingQuality(recipe, false);
                int cookDistQ = ingredients.getCookingQuality(recipe, true);
                int ageQ = ingredients.getAgeQuality(recipe, brew.getAgeTime());
                Logging.log("ingQlty: " + ingQ + ", cookQlty:" + cookQ + ", cook+DistQlty: " + cookDistQ  + ", ageQlty: " + ageQ);
            }

            Logging.msg(player, "Debug Info for item written into Log");
        }
    }
}
