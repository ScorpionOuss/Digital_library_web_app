package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
public class TagMember extends Member{
    private Tag tag;
    public TagMember(Tag tag){
        this.tag = tag;
    }
    public Tag getTag(){
        return tag;
    }
    public void printInFile(FileWriter writer) throws IOException{
        tag.printInFile(writer);
        if (getNext() != null){
            getNext().printInFile(writer);
        }
    }
}
