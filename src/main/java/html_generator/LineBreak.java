package html_generator;
public class LineBreak extends ShortTag{
    public LineBreak(){
        super();
        this.name = "br";
    }

    public LineBreak(HashMap<String, String> attributes){
        super(attributes);
        this.name = "br";
    }
}
