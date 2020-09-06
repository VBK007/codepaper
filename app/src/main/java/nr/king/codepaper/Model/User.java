package nr.king.codepaper.Model;

public class User {

    private String email,image,password;

    public User(){

    }


    public User(String email, String image, String password) {
        this.email = email;
        this.image = image;
        this.password = password;
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
}
