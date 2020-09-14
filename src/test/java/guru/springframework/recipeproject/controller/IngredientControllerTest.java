package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Ingredient;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipeproject.service.IngredientService;
import guru.springframework.recipeproject.service.RecipeService;
import guru.springframework.recipeproject.service.UnitOfMesaureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMesaureService unitOfMesaureService;
    @InjectMocks
    IngredientController ingredientController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void getIngredients() throws Exception {
        Recipe recipe   =   new Recipe();
        recipe.setId(1L);

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService,times(1)).getRecipeById(anyLong());
    }

    @Test
    public void getIngredientForARecipe()  throws Exception
    {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        when(ingredientService.findByRecipeIdAndIngredientId(any(),any())).thenReturn(ingredient);
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void updateIngredient() throws Exception
    {
        when(unitOfMesaureService.getAllUom()).thenReturn(new HashSet<>());
        when(ingredientService.findByRecipeIdAndIngredientId(any(),any())).thenReturn(new Ingredient());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void saveOrUpdateIngredient() throws Exception
    {
        Ingredient ingredient   =   new Ingredient();
        ingredient.setId(2L);
        Recipe recipe           =   new Recipe();
        recipe.setId(1L);
        ingredient.setRecipe(recipe);

        when(ingredientService.saveOrUpdateIngredient(any())).thenReturn(ingredient);
        mockMvc.perform(post("/recipe/1/ingredient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"))  ;
    }

    @Test
    public void addNewIngredient() throws Exception
    {
        when(unitOfMesaureService.getAllUom()).thenReturn(new HashSet<>());
        when(recipeService.getRecipeById(any())).thenReturn(new Recipe());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void deleteRecipe()  throws Exception
    {
        mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/1/ingredients"));
        verify(ingredientService,times(1)).deleteIngredientByRecipeIdIngredientId(anyLong(),anyLong());
    }

}