package guru.springframework.recipeproject.repositories;

import guru.springframework.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

//Loads the Sring Context
@SpringBootTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void findByUom() {
        Optional<UnitOfMeasure> unitOfMeasure   = unitOfMeasureRepository.findByUom("TABLESPOON");
        System.out.println(unitOfMeasureRepository.findByUom("TABLESPOON").get().getUom());
        assertEquals("TABLESPOON",unitOfMeasure.get().getUom());
    }

    @Test
    void findByCup() {
        Optional<UnitOfMeasure> unitOfMeasure   = unitOfMeasureRepository.findByUom("CUP");
        System.out.println(unitOfMeasureRepository.findByUom("CUP").get().getUom());
        assertEquals("CUP",unitOfMeasure.get().getUom());
    }
}