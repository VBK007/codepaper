package nr.king.codepaper.Model;

public class Category {
   private String Image;
   private String Name;
    private String MenuId;

    public Category() {
    }


    public Category(String image, String name, String menuId) {
        Image = image;
        Name = name;
        MenuId = menuId;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
