package guru.springframework.recipeproject.controller;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.ImageService;
import guru.springframework.recipeproject.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    ImageService imageService;
    @InjectMocks
    ImageController imageController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void changeRecipeImageGet() throws  Exception
    {
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/imageUploadForm"));
        verify(recipeService,times(1)).getRecipeById(any());
    }

    @Test
    void changeRecipeImagePost()  throws  Exception{
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imageFile","testing.txt", "text/plain",("Hi There").getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location","/recipe/1/show"));
        verify(imageService,times(1)).saveRecipeImage(anyLong(),any());
    }

    @Test
    public void renderRecipeImage() throws Exception
    {
        Recipe recipe   =   new Recipe();
        recipe.setId(1L);
        String s    =   "Hi There";

        Byte[] byteArr  =   new Byte[s.getBytes().length];
        int i=0;
        for(byte b : s.getBytes())
        {
            byteArr[i++] = b;
        }

        recipe.setImage(byteArr);

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        MockHttpServletResponse response =
        mockMvc.perform(get("/recipe/1/recipeImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] bytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, bytes.length);
    }
}