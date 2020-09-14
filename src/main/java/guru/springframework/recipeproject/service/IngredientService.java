package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class IngredientService {

    private final RecipeRepository recipeRepository;

    public IngredientService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Ingredient findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId )
    {
        Ingredient ingredient           =   null;
        Recipe  recipe                  =   null;
        Optional<Recipe> optional       =   recipeRepository.findById(recipeId);
        if(optional.isPresent())
            recipe  =   optional.get();
        if(recipe != null)
        {
            ingredient  =   recipe.getIngredients().stream().filter(ingredient1 -> ingredient1.getId() == ingredientId).findFirst().orElse(null);
            if(ingredient != null)
                System.out.println("Ingredient found :: ID :: "+ingredient.getId());
        }
        return ingredient;
    }

    @Transactional
    public Ingredient saveOrUpdateIngredient(Ingredient ingredient)
    {
        Optional<Recipe> optionalRecipe   =   recipeRepository.findById(ingredient.getRecipe().getId());
        Recipe recipe   =   null;
        if(optionalRecipe.isPresent())
        {
            recipe       =   optionalRecipe.get();

            Ingredient ingredientFound  =   recipe.getIngredients()
                    .stream()
                    .filter(ingredient1 -> ingredient1.getId() == ingredient.getId())
                    .findFirst()
                    .orElse(null);

            if(ingredientFound == null)
            {
                //New Ingredient
                recipe.addIngredient(ingredient);
            }
            else {

                ingredientFound.setDescription(ingredient.getDescription());
                ingredientFound.setUnitOfMeasure(ingredient.getUnitOfMeasure());
                ingredientFound.setAmount(ingredient.getAmount());
            }
        }
        else
        {
            throw new RuntimeException("Recipe not found");
        }

        Recipe recipe2 = recipeRepository.save(recipe);

        //If the ingredient already extisted, you can match it with ID
        Ingredient finalIngredient  =   recipe2.getIngredients()
                .stream()
                .filter(ingredient1 -> ingredient1.getId() == ingredient.getId())
                .findFirst()
                .orElse(null);

        //But if the ingredient is new, you do not have ID to match it with, hence you match using descripion, aount and UOM descirption
        if(finalIngredient == null)
        {
            finalIngredient  =   recipe2.getIngredients()
                    .stream()
                    .filter(ingredient1 -> ingredient1.getDescription().equals(ingredient.getDescription()))
                    .filter(ingredient1 -> ingredient1.getAmount().equals(ingredient.getAmount()))
                    .filter(ingredient1 -> ingredient1.getUnitOfMeasure().getId().equals(ingredient.getUnitOfMeasure().getId()))
                    .findFirst()
                    .orElse(null);
        }
        return  finalIngredient;
    }

    @Transactional
    public void deleteIngredientByRecipeIdIngredientId(Long recipeId, Long ingredientId){
        Optional<Recipe> optionalRecipe   =   recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            Recipe recipe   =   optionalRecipe.get();
            Optional<Ingredient> optionalIngredient  =   recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst()
                    ;
            if(optionalIngredient.isPresent())
            {
                Ingredient ingredientFound  =   optionalIngredient.get();
                recipe.removeIngredient(ingredientFound);
                ingredientFound.setRecipe(null);
                recipeRepository.save(recipe);
            }
        }
    }

}
