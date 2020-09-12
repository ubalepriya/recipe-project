package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void showRecipeById() throws Exception {

        Long recipeId   =   1l;
        Recipe recipe   =   new Recipe();
        recipe.setId(recipeId);

        when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);
        ArgumentCaptor<Recipe> argumentCaptor   =   ArgumentCaptor.forClass(Recipe.class);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));


        verify(recipeService,times(1)).getRecipeById(recipeId);
        verify(model(), times(1)).attribute(eq("recipe"), argumentCaptor.capture());
        assertNotNull(argumentCaptor.getValue());
        assertNotNull(argumentCaptor.getValue().getId());
        System.out.println(argumentCaptor.getValue().getId());
    }
}