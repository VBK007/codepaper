package nr.king.codepaper.Model;

public class USer {

    private String email;
    private String userName;
    private String imageUrl;
    private String userid;
    private String uppid;
    private String phone;
    private String password;
private String gpayname;
    public USer() {
    }

    public USer(String email, String userName, String imageUrl, String userid, String uppid, String phone, String password, String gpayname) {
        this.email = email;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.userid = userid;
        this.uppid = uppid;
        this.phone = phone;
        this.password = password;
        this.gpayname = gpayname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUppid() {
        return uppid;
    }

    public void setUppid(String uppid) {
        this.uppid = uppid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGpayname() {
        return gpayname;
    }

    public void setGpayname(String gpayname) {
        this.gpayname = gpayname;
    }
}
