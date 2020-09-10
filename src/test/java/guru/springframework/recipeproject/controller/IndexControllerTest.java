package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.RecipeService;
import org.h2.index.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        //Initialise recipeService and model
        MockitoAnnotations.initMocks(this);
        indexController =   new IndexController(recipeService);
    }

    //This is how you can perform a full flideged MVC test. You dont need a web server. DispatcherServlet is provided.
    @Test
    void mockMvc()  throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void getIndex() {
        Recipe recipe1   =   new Recipe();
        recipe1.setId(1L);
        Recipe recipe2   =   new Recipe();
        recipe2.setId(2L);
        Set<Recipe> recipeSet   =   new HashSet<>();
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);

        when(recipeService.getAllRecipes()).thenReturn(recipeSet);

        //Capture argument of Type Set<Recipe>
        ArgumentCaptor<Set<Recipe>> argumentCaptor  =   ArgumentCaptor.forClass(Set.class);

        assertEquals("index",indexController.getIndex(model));
        verify(recipeService,times(1)).getAllRecipes();
        //Verify that model.addAttribute("recipes") -> here we're checking whether attribute name is 'recipes'.
        verify(model, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        assertEquals(2,argumentCaptor.getValue().size());
    }
}