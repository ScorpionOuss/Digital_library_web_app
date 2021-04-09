package html_generator;
import java.io.FileWriter; 
import java.io.IOException;

class Section extends DoubleTag{

    public Section(){
        super();
        this.name = "section";
    }
    public Section(HashMap<String, String> attributes){
        super(attributes);
        this.name = "section";
    }
    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        writer.write('\n');
        getBody().printInFile(writer);
        writer.write('\n');
        writer.write(getCloseTag());
    }

}