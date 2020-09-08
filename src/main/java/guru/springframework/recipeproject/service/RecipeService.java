package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getAllRecipes()
    {
        Set<Recipe> recipes =   new HashSet<Recipe>() ;
        recipeRepository.findAll().forEach(recipe -> {
            recipes.add(recipe);
        });
        return recipes;
    }
}
