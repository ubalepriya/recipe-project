package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMesaureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public UnitOfMesaureService(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    public Set<UnitOfMeasure> getAllUom()
    {
        Set<UnitOfMeasure> unitOfMeasureSet =   new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(uom ->{
            unitOfMeasureSet.add(uom);
        });
        return unitOfMeasureSet;
    }
}
