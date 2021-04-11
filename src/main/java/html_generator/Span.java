package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public class Span extends DoubleTag{
    public Span(){
        super();
        this.name = "span";
    }

    public Span(HashMap<String, String> attributes){
        super(attributes);
        this.name = "span";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        getBody().printInFile(writer);
        writer.write(getCloseTag());
    }
}
