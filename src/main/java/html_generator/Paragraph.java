package html_generator;
import java.io.FileWriter; 
import java.io.IOException;

public class Paragraph extends DoubleTag{
    public Paragraph(){
        super();
        this.name = "p";
    }
    public Paragraph(HashMap<String, String> attributes){
        super(attributes);
        this.name = "p";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        getBody().printInFile(writer);
        writer.write(getCloseTag());
    }
}
