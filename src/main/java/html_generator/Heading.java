package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public class Heading extends DoubleTag{
    public Heading(){
        super();
        this.name = "h1";
    }

    public Heading(int strength){
        super();
        if (strength < 1){
            strength = 1;
        } else if (strength > 6) {
            strength = 6;
        }
        this.name = "h" + Integer.toString(strength);
    }
    public Heading(HashMap<String, String> attributes){
        super(attributes);
        this.name = "h1";
    }
    public Heading(int strength, HashMap<String, String> attributes){
        super(attributes);
        if (strength < 1){
            strength = 1;
        } else if (strength > 6) {
            strength = 6;
        }
        this.name = "h" + Integer.toString(strength);
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        getBody().printInFile(writer);
        writer.write(getCloseTag());
    }
}
