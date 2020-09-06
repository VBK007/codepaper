package nr.king.codepaper.Model;

public class Demo {

    public String image;
    public String steps;


    public Demo() {
    }


    public Demo(String image, String steps) {
        this.image = image;
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
