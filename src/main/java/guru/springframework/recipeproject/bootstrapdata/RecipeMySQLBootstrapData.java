package guru.springframework.recipeproject.bootstrapdata;

import guru.springframework.recipeproject.domain.Category;
import guru.springframework.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.CategoryRepository;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 8/7/17.
 */
@Slf4j
@Component
@Profile({"dev", "prod"})
public class RecipeMySQLBootstrapData implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeMySQLBootstrapData(CategoryRepository categoryRepository,
                          UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (categoryRepository.count() == 0L){
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L){
            log.debug("Loading UOMs");
            loadUom();
        }
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("ITALIAN");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("MEXICAN");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("INDIAN");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("CHINESE");
        categoryRepository.save(cat4);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("TABLESPOON");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("TEASPOON");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("CUP");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("GRAMS");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("KG");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("EACH");
        unitOfMeasureRepository.save(uom6);
    }
}