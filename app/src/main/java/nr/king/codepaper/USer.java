package nr.king.codepaper;

public class USer {

    private String email, image, password, UserName, Phone, Userid, ImageUrl,Uppid,Gpayname;

    public USer() {

    }

    public USer(String email, String image, String password, String userName, String phone, String userid, String imageUrl, String uppid, String gpayname) {
        this.email = email;
        this.image = image;
        this.password = password;
        UserName = userName;
        Phone = phone;
        Userid = userid;
        ImageUrl = imageUrl;
        Uppid = uppid;
        Gpayname = gpayname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUppid() {
        return Uppid;
    }

    public void setUppid(String uppid) {
        Uppid = uppid;
    }

    public String getGpayname() {
        return Gpayname;
    }

    public void setGpayname(String gpayname) {
        Gpayname = gpayname;
    }
}
