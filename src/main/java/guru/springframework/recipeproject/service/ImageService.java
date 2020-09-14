package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageService {

    private final RecipeRepository recipeRepository;

    public ImageService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void saveRecipeImage(Long recipeId, MultipartFile file) throws Exception
    {
        Optional<Recipe> recipeOptional   =   recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent())
        {
            Recipe recipe       =   recipeOptional.get();
            Byte[] byteArray    =   new Byte[file.getBytes().length];
            int i=0;
            for(byte b: file.getBytes())
            {
                byteArray[i++]  =   b;
            }
            recipe.setImage(byteArray);
            recipeRepository.save(recipe);
        }
    }

}
