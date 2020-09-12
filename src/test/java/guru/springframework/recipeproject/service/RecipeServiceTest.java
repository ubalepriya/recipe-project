package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class RecipeServiceTest {

    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach called");
        MockitoAnnotations.initMocks(this);
        recipeService   =   new RecipeService(recipeRepository);
    }

    @Test
    void getAllRecipes() throws Exception{
        System.out.println("getAllRecipes Test method called");
        Recipe recipe   =   new Recipe();
        recipe.setId(1L);
        Set<Recipe>  recipes    =   new HashSet<>();
        recipes.add(recipe);

        //When is used becuase,
        //when we'll run recipeService.getAllRecipes(), internally recipeRepository.findAll() is called.
        //We are telling Mockito that, when recipeRepository.findAll() is called, return 'recipes' as a set of Recipe objects.
        when(recipeRepository.findAll()).thenReturn(recipes);
        assertEquals(recipeService.getAllRecipes().size(), 1);
        //Since in recipeService.getAllRecipes(), recipeRepository.findAll() is called only once, it should get verified.
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById() throws Exception{
        Long recipeId   =   1l;
        Recipe recipe   =   new Recipe();
        recipe.setId(recipeId);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        Recipe recipe2   =   recipeService.getRecipeById(recipeId);
        assertNotNull(recipe2);

    }


}