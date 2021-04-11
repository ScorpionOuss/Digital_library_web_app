package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;


public abstract class ShortTag extends Tag{
    
	protected ShortTag() {
    	super();
    }
    
    protected ShortTag(HashMap<String, String> attributes){
        super(attributes);
    }
	
	public String getTag(){
        return "<" + getName() + ">";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getTag());
    }
    
}