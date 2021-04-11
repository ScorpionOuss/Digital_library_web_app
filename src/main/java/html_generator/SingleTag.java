package html_generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public abstract class SingleTag extends Tag{
	
	protected SingleTag() {
    	super();
    }
    
    protected SingleTag(HashMap<String, String> attributes){
        super(attributes);
    }
	
    public String getTag(){
        return "<" + getName() + " " + printAttributes() + "/>";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getTag());
    }
    
}