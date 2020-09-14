package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @Mock
    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @InjectMocks
    ImageService imageService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveRecipeImage() throws Exception{
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imageFile","testing.txt","text/pain",("Hi There").getBytes());

        Recipe recipe   =   new Recipe();
        recipe.setId(1L);

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        imageService.saveRecipeImage(1L, mockMultipartFile);
        ArgumentCaptor<Recipe> argumentCaptor   =   ArgumentCaptor.forClass(Recipe.class);

        verify(recipeRepository,times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe  =   argumentCaptor.getValue();
        assertEquals(mockMultipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}