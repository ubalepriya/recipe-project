package guru.springframework.recipeproject.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Ingredient {
    private String description;
    private BigDecimal amount;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Recipe recipe;
    @OneToOne
    private UnitOfMeasure unitOfMeasure;

    public Ingredient() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Ingredient;
    }

}
