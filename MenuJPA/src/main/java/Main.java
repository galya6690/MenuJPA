
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAMenu");
        EntityManager em = emf.createEntityManager();
        Scanner sc=new Scanner(System.in);
        try {
            em.getTransaction().begin();
            try {
                Dish dish1 = new Dish("dish1", 97.0, 120, "+");
                Dish dish2 = new Dish("dish2", 78.9, 500, "-");
                Dish dish3 = new Dish("dish3", 200.8, 340, "+");
                Dish dish4 = new Dish("dish4", 370.0, 400, "-");
                Dish dish5 = new Dish("dish5", 478.0, 900, "+");
                Dish dish6 = new Dish("dish6", 112.0, 200, "-");
                Dish[] menu = {dish1, dish2, dish3, dish4, dish5, dish6};
                for (Dish dish : menu) {
                    em.persist(dish);
                }
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
                return;
            }
                while (true) {
                    System.out.println("1: add new dish");
                    System.out.println("2: sort");
                    System.out.println("3: checkDiscount");
                    System.out.println("4: weightLimit1kg");
                    System.out.println("5: Sorting by price");
                    System.out.print("-> ");
                 ;
                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDishInMenu(em);
                            break;
                        case "2":
                            sort(em);
                            break;
                        case "3":
                            checkDiscount(em);
                            break;
                        case "4":
                            weightLimit1kg(em);
                            break;
                        case "5":
                            selectByPrice(em);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }}
        private static void weightLimit1kg (EntityManager em){
            Integer alreadyOrdered = 0;
            boolean check = true;
            while (check) {
                System.out.println(" select a dish from the menu and enter:");
                Scanner sc = new Scanner(System.in);
                String dishs = sc.nextLine();
                Dish d = null;
                try {
                    Query query = em.createQuery("SELECT c FROM Dish c WHERE c.nameDish = :dishname", Dish.class);
                    query.setParameter("dishname", dishs);
                    d = (Dish) query.getSingleResult();

                    if (((alreadyOrdered) + d.getWeight()) <= 1000) {
                        alreadyOrdered+=d.getWeight();
                        System.out.println();
                        System.out.println("you ordered a meal ' "+d.getNameDish()+"' . The total weight of your order "+alreadyOrdered+ " gram");

                    } else if ((1000 - alreadyOrdered) < d.getWeight()) {
                        System.out.println("You cannot order this dish, your limit  " + (1000 - alreadyOrdered)+ "  gram");
                    } else if((alreadyOrdered) >1000) {
                        check = false;
                        System.out.println(" You have exceeded the 1000 gram limit");
                        break;
                    }
                    System.out.println();
                    System.out.println("If you do not want to order more dishes enter  stop, continue press enter ");
                    String i=sc.nextLine();
                    if(i.equals("stop")){
                        check = false;
                        break;
                    }

                } catch (NoResultException ex) {
                    System.out.println("Dish not found!");
                    return;
                } catch (NonUniqueResultException ex) {
                    System.out.println("Non unique result!");
                    return;
                }

            }

        }
    private static void selectByPrice (EntityManager em){
        Scanner sc=new Scanner(System.in);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery <Dish> cq = cb.createQuery(Dish.class);
        Root<Dish> root = cq.from(Dish.class);
        System.out.println("enter price from :");
        int from=sc.nextInt();
        System.out.println("enter price to :");
        int to=sc.nextInt();
        cq.select(root).where(cb.between(root.get("price"),from,to));
        System.out.println();
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    private static void checkDiscount (EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery <Dish> dishLikeCriteria = cb.createQuery(Dish.class);
        Root<Dish> ownerPassportRoot = dishLikeCriteria.from(Dish.class);
        dishLikeCriteria.select(ownerPassportRoot);
        dishLikeCriteria.where(cb.equal(ownerPassportRoot.get("discount"), "+"));
        System.out.println();
        System.out.println("Discount food : ");
        System.out.println();
        em.createQuery(dishLikeCriteria).getResultList().forEach(System.out::println);
    }

    private static void sort (EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery <Dish> dishLikeCriteria = cb.createQuery(Dish.class);
        Root<Dish> ownerPassportRoot = dishLikeCriteria.from(Dish.class);
        dishLikeCriteria.orderBy(cb.asc(ownerPassportRoot.get("price")));
        System.out.println();
        System.out.println("Sorting by price : ");
        System.out.println();
        em.createQuery(dishLikeCriteria).getResultList().forEach(System.out::println);
    }

    private static void addDishInMenu (EntityManager em) {
        Scanner sc= new Scanner(System.in);
        System.out.print("Is there a discount on this dish?(+ or -): ");
        String  discount = sc.nextLine();
        System.out.print("Enter  nameDish: ");
        String nameDish = sc.nextLine();
        System.out.print("Enter price: ");
        Double price = sc.nextDouble();
        System.out.print("Enter weight: ");
       Integer  weight = sc.nextInt();
        em.getTransaction().begin();
        try {
           Dish dish = new Dish(nameDish,price,weight,discount);
            em.merge(dish);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
}}
