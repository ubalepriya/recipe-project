package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipeproject.service.IngredientService;
import guru.springframework.recipeproject.service.RecipeService;
import guru.springframework.recipeproject.service.UnitOfMesaureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IngredientController {

    final private RecipeService recipeService;
    final private IngredientService ingredientService;
    final private UnitOfMesaureService unitOfMesaureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMesaureService unitOfMesaureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMesaureService = unitOfMesaureService;
    }


    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredients(@PathVariable Long id, Model model)
    {
        model.addAttribute("recipe",recipeService.getRecipeById(id));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getIngredientForARecipe(@PathVariable String recipeId, @PathVariable String ingredientId, Model model)
    {
        Ingredient ingredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredient",ingredient);
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model)
    {
        Ingredient ingredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredient",ingredient);
        model.addAttribute("uomList", unitOfMesaureService.getAllUom());
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute Ingredient ingredient)
    {
        Ingredient savedIngredient  =   ingredientService.saveOrUpdateIngredient(ingredient);
        return "redirect:/recipe/"+savedIngredient.getRecipe().getId() +"/ingredient/"+ savedIngredient.getId() +"/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@PathVariable String recipeId, Model model)
    {
        Recipe recipe   =   recipeService.getRecipeById(Long.valueOf(recipeId));
        Ingredient ingredient   =   new Ingredient();
        ingredient.setRecipe(recipe);
        ingredient.setUnitOfMeasure(new UnitOfMeasure());
        model.addAttribute("ingredient",ingredient);
        model.addAttribute("uomList", unitOfMesaureService.getAllUom());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipe(@PathVariable String recipeId, @PathVariable String ingredientId, Model model)
    {
        ingredientService.deleteIngredientByRecipeIdIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId));
        model.addAttribute("recipe",recipeService.getRecipeById(Long.valueOf(recipeId)));
        return "redirect:/recipe/"+ Long.valueOf(recipeId)+"/ingredients";
    }


}
