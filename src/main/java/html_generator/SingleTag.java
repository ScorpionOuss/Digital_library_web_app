package html_generator;
public abstract class SingleTag extends Tag{
    public String getTag(){
        return "<" + getName() + " " + printAttributes() + "/>";
    }

    public void printInFile(FileWriter writer) throws IOException {
        writer.write(getTag());
    }
    
}