package html_generator;
import java.io.FileWriter; 
import java.io.IOException;

public class Head extends DoubleTag{
    public Head(){
        super();
        this.name = "head";
    }
    public Head(HashMap<String, String> attributes){
        super(attributes);
        this.name = "head";
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
