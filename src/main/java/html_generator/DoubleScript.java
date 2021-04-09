package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public class DoubleScript extends DoubleTag{
    public DoubleScript(){
        super();
        this.name = "script";
    }

    public DoubleScript(HashMap<String, String> attributes){
        super(attributes);
        this.name = "script";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        getBody().printInFile(writer);
        writer.write(getCloseTag());
    }
}
