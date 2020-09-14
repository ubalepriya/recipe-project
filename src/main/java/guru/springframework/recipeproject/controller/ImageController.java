package guru.springframework.recipeproject.controller;

import guru.springframework.recipeproject.domain.Recipe;
import guru.springframework.recipeproject.service.ImageService;
import guru.springframework.recipeproject.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }


    @GetMapping("/recipe/{recipeId}/image")
    public String changeRecipeImageGet(@PathVariable String recipeId, Model model)
    {
        model.addAttribute("recipe",recipeService.getRecipeById(Long.valueOf(recipeId))) ;
        return "/recipe/imageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String changeRecipeImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) throws Exception
    {
        imageService.saveRecipeImage(Long.valueOf(recipeId),file);
        return "redirect:/recipe/"+recipeId+"/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeImage")
    public void renderRecipeImage(@PathVariable String recipeId, HttpServletResponse response) throws Exception {
        Recipe recipe = recipeService.getRecipeById(Long.valueOf(recipeId));
        if (recipe != null) {
            if(recipe.getImage() != null)
            {
                byte[] byteArray = new byte[recipe.getImage().length];

                int i = 0;
                for (Byte b : recipe.getImage()) {
                    byteArray[i++] = b;
                }

                response.setContentType("image/jpeg");
                InputStream inputStream = new ByteArrayInputStream(byteArray);
                IOUtils.copy(inputStream, response.getOutputStream());
            }
        }

    }
}
