package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    @Mock
    RecipeRepository recipeRepository;
    @InjectMocks
    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        Long recipeId       =   1L;
        Long ingredientId   =   2L;

        Recipe recipe   =   new Recipe();
        recipe.setId(recipeId);
        Ingredient ingredient   =   new Ingredient();
        ingredient.setId(ingredientId);
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(ingredient);

        recipe.setIngredients(ingredientSet);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Ingredient ingredientFound   =   ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

        assertNotNull(ingredientFound);
        assertEquals(2L, ingredientFound.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    public void saveOrUpdateIngredient()
    {
        Ingredient ingredient   =   new Ingredient();
        ingredient.setId(1L);

        Recipe recipe   =   new Recipe();
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional optionalRecipe =   Optional.of(recipe);

        when(recipeRepository.findById(any())).thenReturn(optionalRecipe);
        when(recipeRepository.save(any())).thenReturn(recipe);
    }

    @Test
    public void deleteIngredientByRecipeIdIngredientId()
    {
        Ingredient ingredient1   =   new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2   =   new Ingredient();
        ingredient2.setId(2L);

        Recipe recipe   =   new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        ingredientService.deleteIngredientByRecipeIdIngredientId(recipe.getId(),ingredient1.getId());

        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any());
        assertEquals(1,recipe.getIngredients().size());
    }

}