package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public abstract class Tag {
    protected String name;
    private HashMap<String, String> attributes;


    protected Tag(){
        this.name = "GenericTag";
        this.attributes = new HashMap<String, String>();
    }
    protected Tag(HashMap<String, String> attributes){
        this.name = "GenericTag";
        this.attributes = attributes;
    }
    
    public String getName(){
        return name;
    }
    
    public int getId(){
        return id;
    }
    public HashMap<String, String> getAttributes(){
        return attributes;
    }
    protected String printAttributes(){
        String chain = "";
        for (String s : attributes.keySet()){
            chain += s + "=" + "\"" + attributes.get(s) + "\" ";
        } 
        return chain;
    }

    public abstract void printInFile(FileWriter writer) throws IOException;
}