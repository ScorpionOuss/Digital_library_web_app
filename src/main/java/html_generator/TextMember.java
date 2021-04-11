package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
public class TextMember extends Member{
    String text;
    public TextMember(String text){
        this.text = text;
    }
    public String getText(){
        return text;
    }

    public void printInFile(FileWriter writer) throws IOException{
        for (char c : text.toCharArray()){
            writer.write(c);
        }
        if (getNext() != null){
            getNext().printInFile(writer);
        }
    }
}
