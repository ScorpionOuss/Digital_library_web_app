package html_generator;
import java.io.FileWriter; 
import java.io.IOException;

class Html extends DoubleTag{

    public Html(){
        super();
        this.name = "html";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getOpenTag());
        writer.write('\n');
        getBody().printInFile(writer);
        writer.write('\n');
        writer.write(getCloseTag());
    }

}