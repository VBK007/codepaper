package nr.king.codepaper.Model;

public class List {


    public String image;
    public String name;
    public String menuId;
    public String postid;
    public String publisherid;
    public String imageUrl;
    public boolean isSale;
public String userName;
public String buyiedby;
public String money;

    public List(){

    }

    public List(String image, String name, String menuId, String postid, String publisherid, String imageUrl, boolean isSale, String userName, String buyiedby, String money) {
        this.image = image;
        this.name = name;
        this.menuId = menuId;
        this.postid = postid;
        this.publisherid = publisherid;
        this.imageUrl = imageUrl;
        this.isSale = isSale;
        this.userName = userName;
        this.buyiedby = buyiedby;
        this.money = money;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(String publisherid) {
        this.publisherid = publisherid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuyiedby() {
        return buyiedby;
    }

    public void setBuyiedby(String buyiedby) {
        this.buyiedby = buyiedby;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
