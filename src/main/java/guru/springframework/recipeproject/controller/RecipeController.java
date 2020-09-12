package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showRecipeById(@PathVariable Long id, Model model)
    {
        System.out.println("Controller called :: id :: "+id);
        Recipe recipe   =   recipeService.getRecipeById(id);
        model.addAttribute("recipe",recipe);
        System.out.println("Size :: "+recipe.getIngredients().size());

        return "recipe/show";
    }
}
