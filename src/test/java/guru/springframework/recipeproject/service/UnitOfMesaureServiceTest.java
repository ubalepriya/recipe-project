package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.domain.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMesaureServiceTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @InjectMocks
    UnitOfMesaureService unitOfMesaureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUom() {
        UnitOfMeasure unitOfMeasure1    =   new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        UnitOfMeasure unitOfMeasure2    =   new UnitOfMeasure();
        unitOfMeasure2.setId(2L);

        Set<UnitOfMeasure> unitOfMeasureSet =   new HashSet<>();
        unitOfMeasureSet.add(unitOfMeasure1);
        unitOfMeasureSet.add(unitOfMeasure2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);
        Set<UnitOfMeasure>  foundUnitOfMesaure  = unitOfMesaureService.getAllUom();

        assertNotNull(foundUnitOfMesaure);
        assertEquals(2,foundUnitOfMesaure.size());
        verify(unitOfMeasureRepository,times(1)).findAll();
    }
}