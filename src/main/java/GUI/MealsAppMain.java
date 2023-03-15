package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;
import REST.Response;
import events.Interaction;
import events.MsgToUser;

public class MealsAppMain {
    private JButton exitButton;
    private JButton StatisticsButton;
    private JButton infoButton;
    private JButton searchButton;
    private JButton searchByCategoryButton;
    public JPanel MealsAppPanel;
    public JLabel image;
    private JLabel secondimage;
    private JTextField whatAreYouInTextField;
    private JButton favoriteMealsButton;
    private JLabel imagecenter;
    private boolean isAnotherResultsByNameFrameVisible = false;
    private boolean isAnotherCategoryFrameVisible=false;
    private boolean isAnotherFavoritesFrameVisible=false;
    private boolean isAnotherStatisticsFrameVisible=false;



    public MealsAppMain() {

        Interaction interaction = Interaction.getInteractionInstance();

        searchButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            String searchText= whatAreYouInTextField.getText();

            // Check for search validation
            if (searchText.equals("What are you in the mood for?") || searchText.equals("") || searchText.equals("Please enter something to search for"))
            {
                whatAreYouInTextField.setText("Please enter something to search for");
            } else if (isAnotherResultsByNameFrameVisible)
                MsgToUser.infoBox("Close the search window first","WARNING");
            else
            {
                //Send to BackEnd through Interaction search data and receive.
                List<String> resultsFromSearchByName = interaction.searchMealByName(searchText);

                // List to Vector conversion to insert data to jlist
                Vector<String> nameOfMeals = new Vector<>(resultsFromSearchByName);

                //Activation of the Results JFrame
                JFrame resbyname = new JFrame("Results By Name");
                ResultsByName res = new ResultsByName();
                res.resultsbynamelist.setListData(nameOfMeals);
                resbyname.setContentPane(res.resultsbyname);
                resbyname.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                resbyname.pack();
                resbyname.setLocationRelativeTo(null);
                isAnotherResultsByNameFrameVisible=true;
                resbyname.setVisible(true);
                //Mouse Listener for closing the Results panel
                res.closeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Response.getResponseInstance().setResponseNull();
                        isAnotherResultsByNameFrameVisible=false;
                        resbyname.setVisible(false);
                    }
                });

            }
        }
    });

    searchByCategoryButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!isAnotherCategoryFrameVisible){
                //Get the List of Categories from Backend and convert the list to Vector for the JList.
                List<String> resultsFromSearchByCategory=interaction.mealCategoriesNames();
                Vector<String> categories=new Vector<String>(resultsFromSearchByCategory);
                //Activation of the Results JFrame
                JFrame searchbycategory=new JFrame("Search by Category");
                SearchbyCategory sbc=new SearchbyCategory();
                sbc.categorylist.setListData(categories);
                searchbycategory.setContentPane(sbc.searchbycatpanel);
                searchbycategory.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                searchbycategory.pack();
                searchbycategory.setLocationRelativeTo(null);
                isAnotherCategoryFrameVisible=true;
                searchbycategory.setVisible(true);
                sbc.closeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Response.getResponseInstance().setResponseNull();
                        isAnotherCategoryFrameVisible=false;
                        searchbycategory.setVisible(false);
                    }
                });

            }
            else MsgToUser.infoBox("Close the search window first","WARNING");


        }
    });
        StatisticsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(!isAnotherStatisticsFrameVisible){
                    //Model to create the Table Data
                    DefaultTableModel defaultTableModel= new DefaultTableModel();
                    defaultTableModel.addColumn("Name");
                    defaultTableModel.addColumn("Hits");
                    LinkedHashMap<String,Integer> statistics = interaction.mostViewedMeals();
                    String[] row1={"Name","Hits"};
                    defaultTableModel.addRow(row1);
                    statistics.forEach((key,value) ->{
                        String[] temp = new String[2];
                        temp[0] = key;
                        temp[1] = String.valueOf(value);
                        defaultTableModel.addRow(temp);
                    });

                    //Activation of Statistics JFrame
                    JFrame stat=new JFrame("Statistics");
                    Statistics s=new Statistics();
                    s.table1.setModel(defaultTableModel);
                    stat.setContentPane(s.statistics);
                    stat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    stat.pack();
                    stat.setLocationRelativeTo(null);
                    isAnotherStatisticsFrameVisible=true;
                    stat.setVisible(true);
                    s.closeButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            isAnotherStatisticsFrameVisible=false;
                            stat.setVisible(false);
                        }
                    });
                }
                else MsgToUser.infoBox("Close the search window first","WARNING");

            }
        });


        infoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Information JFrame of the Creators
                interaction.bounceStart(whatAreYouInTextField.getText());
                JFrame information=new JFrame("Information");
                infoFrame inf=new infoFrame();
                information.setContentPane(inf.info);
                information.setDefaultCloseOperation(information.DO_NOTHING_ON_CLOSE);
                information.pack();
                information.setLocationRelativeTo(null);
                information.setVisible(true);

                inf.closeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        information.setVisible(false);
                    }
                });
            }
        });

        favoriteMealsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(!isAnotherFavoritesFrameVisible){
                    JFrame favorites=new JFrame("Favorites");
                    Favorites favoriteWindow= new Favorites();
                    //Request to BackEnd for the favorite Meals and if the List is empty the following message appears.
                    List<String> returnMealName= interaction.selectAllReturnMealName();
                    if (returnMealName.isEmpty()){
                        returnMealName.add("You don't have meals in your favorites yet");
                    }
                    //Conversion from list to Vector for the JList
                    Vector<String> vector= new Vector<String>(returnMealName);
                    //Fill the List and Activate the JFrame
                    favoriteWindow.favoritesList.setListData(vector);
                    favorites.setContentPane(favoriteWindow.favoritesPanel);
                    favorites.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    favorites.pack();
                    isAnotherFavoritesFrameVisible=true;
                    favorites.setLocationRelativeTo(null);
                    favorites.setVisible(true);
                    favoriteWindow.closeButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            isAnotherFavoritesFrameVisible=false;
                            favorites.setVisible(false);
                        }
                    });

                }
                else MsgToUser.infoBox("Close the search window first","WARNING");
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Jframe to exit the program
                JFrame exit=new JFrame("Exit Window");
                exitWindow ex=new exitWindow();
                exit.setContentPane(ex.exitWindowPanel);
                exit.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                exit.pack();
                exit.setLocationRelativeTo(null);
                exit.setVisible(true);
                ex.cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        exit.setVisible(false);
                    }
                });
            }
        });
        whatAreYouInTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                whatAreYouInTextField.setText("");
            }
        });

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        image=new JLabel(new ImageIcon("src/media/image.jpg"));
        secondimage=new JLabel(new ImageIcon("src/media/image2.jpeg"));
        imagecenter=new JLabel(new ImageIcon("src/media/image3.jpg"));
    }
}
