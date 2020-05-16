import javax.persistence.*;

@Entity
@Table(name="Dish")
@DiscriminatorValue(value= "+")
public class DishDiscount extends Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;



    public DishDiscount(){


    }


}
