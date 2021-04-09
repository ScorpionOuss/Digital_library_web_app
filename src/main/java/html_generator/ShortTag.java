package html_generator;
import java.io.FileWriter; 
import java.io.IOException;


public abstract class ShortTag extends Tag{
    public String getTag(){
        return "<" + getName() + ">";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getTag());
    }
    
}