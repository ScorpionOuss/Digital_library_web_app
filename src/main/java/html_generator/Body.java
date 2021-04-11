package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public class Body extends DoubleTag{
    public Body(){
        super();
        this.name = "body";
    }

    public Body(HashMap<String, String> attributes){
        super(attributes);
        this.name = "body";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write('\n');
        writer.write(getOpenTag());
        writer.write('\n');
        getBody().printInFile(writer);
        writer.write('\n');
        writer.write(getCloseTag());
        writer.write('\n');
    }
}
