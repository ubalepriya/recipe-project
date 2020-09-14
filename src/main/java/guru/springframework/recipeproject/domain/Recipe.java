package guru.springframework.recipeproject.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    @Lob
    private Byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients =   new HashSet<Ingredient>();
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories  =   new HashSet<Category>();

    public Recipe() {
    }

    public Recipe addIngredient(Ingredient ingredient)
    {
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
        return this;
    }

    public void removeIngredient(Ingredient ingredient)
    {
        ingredients.remove(ingredient);
    }

    public Set<Category> getCategory() {
        return categories;
    }

    public void setCategory(Set<Category> categories) {
        this.categories = categories;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Recipe;
    }


}
