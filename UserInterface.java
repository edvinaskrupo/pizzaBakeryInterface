import lt.vu.mif.ggrazevicius.pizza.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * GUI for a pizza bakery.
 * @author Edvinas Krupovnickas
 */
public class UserInterface {

    public static void main(String[] args) {

        //initializing the frame
        JFrame f = new JFrame();

        //creating components
        JLabel pizzaTypeLabel = new JLabel("Choose your pizza type:");
        JLabel pizzaSizeLabel = new JLabel("Choose your pizza size:");
        JLabel ingredientLabel = new JLabel("Choose additional ingredients:");
        JLabel incomeLabel = new JLabel("Total income so far: 0");
        JButton bakeButton = new JButton("Bake a pizza");
        JButton returnButton = new JButton("Return a pizza");
        JButton addIngredient = new JButton("Add");

        //creating drop-down boxes
        String[] pizzaTypes = {"Capricciosa", "Margherita", "Pepperoni"};
        String[] pizzaSizes = {"Large", "Small"};
        String[] ingredients = {"Cheese", "Ham", "Mushrooms", "Olives", "Oregano", "Pepperoni", "Tomato sauce"};
        JComboBox pizzaType = new JComboBox(pizzaTypes);
        JComboBox pizzaSize = new JComboBox(pizzaSizes);
        JComboBox ingredient = new JComboBox(ingredients);

        //component positions and sizes
        pizzaType.setBounds(25, 100, 200, 40);
        pizzaSize.setBounds(300, 100, 200,40);
        bakeButton.setBounds(50,400,700, 100);
        returnButton.setBounds(50, 550, 700, 100);
        addIngredient.setBounds(575, 150, 200, 40);
        ingredient.setBounds(575, 100, 200, 40);
        pizzaTypeLabel.setBounds(25, 25, 150, 100);
        pizzaSizeLabel.setBounds(300, 25, 150, 100);
        ingredientLabel.setBounds(575, 25, 180, 100);
        incomeLabel.setBounds(325, 700, 200, 100);

        //adding components to the frame
        f.add(bakeButton);
        f.add(returnButton);
        f.add(pizzaType);
        f.add(pizzaSize);
        f.add(addIngredient);
        f.add(ingredient);
        f.add(incomeLabel);
        f.add(pizzaTypeLabel);
        f.add(pizzaSizeLabel);
        f.add(ingredientLabel);

        //frame options
        f.setSize(800,800);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        //selecting a pizza type
        final PizzaType[] selectedPizzaType = new PizzaType[1];
        selectedPizzaType[0] = PizzaType.CAPRICCIOSA;
        pizzaType.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedPizzaType[0] = PizzaType.valueOf(((String) pizzaType.getSelectedItem()).toUpperCase());
            }
        });

        //selecting a pizza size
        final PizzaSize[] selectedPizzaSize = new PizzaSize[1];
        selectedPizzaSize[0] = PizzaSize.LARGE;
        pizzaSize.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedPizzaSize[0] = PizzaSize.valueOf(((String) pizzaSize.getSelectedItem()).toUpperCase());
            }
        });

        //selecting (hovering over) an ingredient
        final Ingredient[] selectedIngredient = new Ingredient[1];
        ingredient.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if ("Tomato sauce".equals((String) ingredient.getSelectedItem())) {
                    selectedIngredient[0] = Ingredient.TOMATO_SAUCE;
                } else {
                    selectedIngredient[0] = Ingredient.valueOf(((String) ingredient.getSelectedItem()).toUpperCase());
                }
            }
        });

        //confirming the ingredient and adding it to the ingredient list
        ArrayList<Ingredient> list=new ArrayList<Ingredient>();
        addIngredient.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                list.add(selectedIngredient[0]);
            }
        });

        //bakes a pizza
        PizzaBakeryFactory factory = new PizzaBakeryFactory();
        PizzaBakery bakery = factory.createPizzaBakery();
        final Pizza[] lastPizza = new Pizza[1];
        bakeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                lastPizza[0] = bakery.bake(selectedPizzaType[0], selectedPizzaSize[0], list);
                list.clear();
                incomeLabel.setText("Total income so far: " + bakery.getOperationalIncome());
            }
        });

        //returns the last pizza
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    bakery.returnAPizza(lastPizza[0]);
                } catch (PizzaNotFromThisBakeryException pizzaNotFromThisBakeryException) {
                    pizzaNotFromThisBakeryException.printStackTrace();
                }
                incomeLabel.setText("Total income so far: " + bakery.getOperationalIncome());
            }
        });
    }
}
