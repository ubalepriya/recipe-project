package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService   recipeService   ;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndex(Model model)
    {
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "index";
    }
}
