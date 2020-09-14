package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showRecipeById(@PathVariable Long id, Model model)
    {
        Recipe recipe   =   recipeService.getRecipeById(id);
        model.addAttribute("recipe",recipe);

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String createNewRecipe(Model model)
    {
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeForm";
    }

    @PostMapping("recipe")
    public String saveorUpdateRecipe(@ModelAttribute Recipe recipe)
    {
        Recipe savedRecipe  =   recipeService.saveRecipe(recipe);
        return "redirect:/recipe/"+savedRecipe.getId()+"/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model)
    {
        Recipe recipe   = recipeService.getRecipeById(id);
        model.addAttribute("recipe",recipe);
        return "recipe/recipeForm";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id)
    {
        recipeService.deleteRecipeById(id);
        return "redirect:/index";
    }


}
