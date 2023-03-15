package objects;

public class Category {



        /*
                =================================================================
                *                                                               *
                *           The following class has the Category object         *
                *                                                               *
                =================================================================

     */





    private int categoryId;
    private String categoryName;
    private String categoryPhotoUrl;


    public Category(int categoryId, String categoryName, String categoryPhotoUrl) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPhotoUrl = categoryPhotoUrl;
    }

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPhotoUrl() {
        return categoryPhotoUrl;
    }

    public void setCategoryPhotoUrl(String categoryPhotoUrl) {
        this.categoryPhotoUrl = categoryPhotoUrl;
    }

}
