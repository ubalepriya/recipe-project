package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.service.RecipeService;
import org.h2.index.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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

    @Test
    void getIndex() {
        assertEquals(indexController.getIndex(model), "index");
        verify(recipeService,times(1)).getAllRecipes();
    }
}