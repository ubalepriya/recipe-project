package guru.springframework.recipeproject.controller;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));


        verify(recipeService,times(1)).getRecipeById(recipeId);
    }

    @Test
    void createNewRecipe() throws Exception
    {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"));
    }

    @Test
    public void saveRecipe() throws Exception
    {
        Recipe recipe   =   new Recipe();
        recipe.setId(2L);

        when(recipeService.saveRecipe(any())).thenReturn(recipe);
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description","My Recipe")
        ).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/show/2"))
        ;
    }

    @Test
    public void updateRecipe() throws Exception
    {
        Long recipeId   =   1l;
        Recipe recipe   =   new Recipe();
        recipe.setId(recipeId);
        when(recipeService.getRecipeById(any())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"))
        ;
    }

    @Test
    public void deleteRecipe() throws  Exception
    {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
        verify(recipeService,times(1)).deleteRecipeById(any());
    }


}