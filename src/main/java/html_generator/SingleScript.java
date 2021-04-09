package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.HashMap;

public class SingleScript extends SingleTag{
    public SingleScript(){
        super();
        this.name = "script";
    }

    public SingleScript(HashMap<String, String> attributes){
        super(attributes);
        this.name = "script";
    }
}
