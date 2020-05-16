import javax.persistence.*;

@Entity
@Table(name="Dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nameDish;

    @Column(name="price",nullable = false)
   private Double price;

    @Column(nullable = false)
   private Integer weight;

    private String discount;

    public Dish(String nameDish, Double price, Integer weight, String discount) {
        this.nameDish = nameDish;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public Dish(){

    }
    public int getId() {
        return id;
    }

    public String getNameDish() {
        return nameDish;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getWeight() {
        return weight;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", nameDish='" + nameDish + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", discount='" + discount + '\'' +
                '}';
    }
}
