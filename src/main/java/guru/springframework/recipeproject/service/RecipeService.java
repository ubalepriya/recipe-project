package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getAllRecipes()
    {
        log.debug("getAllRecipes started Debug");
        log.info("getAllRecipes Info");
        Set<Recipe> recipes =   new HashSet<>() ;
        recipeRepository.findAll().forEach(recipe -> {
            recipes.add(recipe);
        });
        return recipes;
    }

    public Recipe getRecipeById(Long id)
    {
        Recipe recipe   =   null;
        Optional<Recipe> optional =  recipeRepository.findById(id);
        if(optional.isPresent())
        {
            recipe  =  optional.get();
        }
        else
        {
            throw new RuntimeException("Recipe not present");
        }
        return recipe;
    }
}
