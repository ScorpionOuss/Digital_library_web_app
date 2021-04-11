package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public class Anchor extends DoubleTag{
    public Anchor(){
        super();
        this.name = "a";
    }

    public Anchor(HashMap<String, String> attributes){
        super(attributes);
        this.name = "a";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        getBody().printInFile(writer);
        writer.write(getCloseTag());
    }
}
