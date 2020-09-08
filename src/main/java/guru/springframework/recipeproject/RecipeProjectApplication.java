package guru.springframework.recipeproject;

import guru.springframework.recipeproject.domain.*;
import guru.springframework.recipeproject.repositories.CategoryRepository;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.math.BigDecimal;

@SpringBootApplication
public class RecipeProjectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx  =    SpringApplication.run(RecipeProjectApplication.class, args);

        UnitOfMeasureRepository unitOfMeasureRepository =   (UnitOfMeasureRepository) ctx.getBean("unitOfMeasureRepository");
        CategoryRepository  categoryRepository          =   (CategoryRepository)    ctx.getBean("categoryRepository") ;
        RecipeRepository    recipeRepository            =   (RecipeRepository)  ctx.getBean("recipeRepository") ;

        System.out.println(unitOfMeasureRepository.findByUom("CUP").get().getId());
        System.out.println(categoryRepository.findByDescription("ITALIAN").get().getId());

        Recipe  recipe  =   loadGuacamoleRecipe(unitOfMeasureRepository, categoryRepository);
        recipeRepository.save(recipe);

        recipe  =   loadChickenRecipe(unitOfMeasureRepository, categoryRepository);
        recipeRepository.save(recipe);
    }

    private static Recipe loadGuacamoleRecipe(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository  categoryRepository)
    {
        Recipe recipe   =   new Recipe();
        recipe.setDescription("The Perfect Guacamole");
        Ingredient ingredient           =   null;
        UnitOfMeasure   unitOfMeasure   =   null;
        recipe.setDifficulty(Difficulty.EASY);
        Notes guacamoleNote             =   new Notes();
        guacamoleNote.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        recipe.setNotes(guacamoleNote);

        /*
        2 ripe avocados
        1/4 teaspoon of salt, more to taste
        1 tablespoon fresh lime juice or lemon juice
        2 tablespoons to 1/4 cup of minced red onion or thinly sliced green onion
        1-2 serrano chiles, stems and seeds removed, minced
        2 tablespoons cilantro (leaves and tender stems), finely chopped
        A dash of freshly grated black pepper
        1/2 ripe tomato, seeds and pulp removed, chopped
        Red radishes or jicama, to garnish
        Tortilla chips, to serve
         */
        ingredient                      =   new Ingredient();
        ingredient.setAmount(new BigDecimal("2.0"));
        ingredient.setDescription("ripe avacados");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TEASPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("0.25"));
        ingredient.setDescription("salt");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("1.0"));
        ingredient.setDescription("fresh lime juice");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("2.0"));
        ingredient.setDescription("minced red onion");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        ingredient.setAmount(new BigDecimal("1"));
        ingredient.setDescription("chiles, stems and seeds removed, minced");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("2.0"));
        ingredient.setDescription("cilantro (leaves and tender stems), finely chopped");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        ingredient.setAmount(new BigDecimal("0.5"));
        ingredient.setDescription("ripe tomato, seeds and pulp removed, chopped");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        recipe.getCategory().add(categoryRepository.findByDescription("ITALIAN").get());
        //categoryRepository.findByDescription("MEXICAN").get().getRecipe().add(recipe);

        recipe.setCookTime(10);
        recipe.setPrepTime(10);
        recipe.setServings(4);
        recipe.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");


        return recipe;

    }

    private static Recipe loadChickenRecipe(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository  categoryRepository)
    {
        Recipe recipe   =   new Recipe();
        recipe.setDescription("Spicy Grilled Chicken Tacos");
        Ingredient ingredient           =   null;
        UnitOfMeasure   unitOfMeasure   =   null;
        recipe.setDifficulty(Difficulty.HARD);
        Notes tacoNote             =   new Notes();
        tacoNote.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        recipe.setNotes(tacoNote);
        /*
        2 tablespoons ancho chili powder
        1 teaspoon dried oregano
        1 teaspoon dried cumin
        1 teaspoon sugar
        1/2 teaspoon salt
        1 clove garlic, finely chopped
        1 tablespoon finely grated orange zest
        3 tablespoons fresh-squeezed orange juice
        2 tablespoons olive oil
        4 to 6 skinless, boneless chicken thighs (1 1/4 pounds)
         */

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("2.0"));
        ingredient.setDescription("ancho chili powder");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TEASPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("1"));
        ingredient.setDescription("dried oregano");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TEASPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("1"));
        ingredient.setDescription("sugar");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TEASPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("0.5"));
        ingredient.setDescription("salt");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        ingredient.setAmount(new BigDecimal("1"));
        ingredient.setDescription("clove garlic, finely chopped");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("1.0"));
        ingredient.setDescription("dried cumin");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("1"));
        ingredient.setDescription("finely grated orange zest");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("3.0"));
        ingredient.setDescription("fresh-squeezed orange juice");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("TABLESPOON").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("2"));
        ingredient.setDescription("olive oil");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);

        ingredient                      =   new Ingredient();
        unitOfMeasure                   =   unitOfMeasureRepository.findByUom("KG").get();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setAmount(new BigDecimal("0.5"));
        ingredient.setDescription("boneless chicken thighs");
        recipe.getIngredients().add(ingredient);
        ingredient.setRecipe(recipe);


        recipe.getCategory().add(categoryRepository.findByDescription("MEXICAN").get());
        //categoryRepository.findByDescription("MEXICAN").get().getRecipe().add(recipe);

        recipe.setCookTime(15);
        recipe.setPrepTime(20);
        recipe.setServings(5);
        recipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");


        return recipe;

    }

}
