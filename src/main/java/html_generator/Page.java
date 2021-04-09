package html_generator;
import beans.*;
import jdk.javadoc.internal.doclets.formats.html.markup.Script;

import java.io.File;
import java.io.FileWriter; 
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Page {
    /* Generates a simple html page with a paragraph and some choices */
    Paragraphe par;
    Utilisateur user;
    boolean insert = false;
    String prefixFile;
    String suffixFile;
    DoubleTag topTag;
    public Page(Paragraphe par, Utilisateur user){
        this.par = par;
        this.user = user;
    }
    public Page(Paragraphe par, Utilisateur user, String wrapFileName){
        this.par = par;
        this.user = user;
        insert = true;
        prefixFile = wrapFileName + "_prefix.wrap";
        suffixFile = wrapFileName + "_suffix.wrap";
    }

    public void generateTags(){
        DoubleTag currentTag;
        if (!insert){
            topTag = new Section();
            currentTag = topTag;
        } else {
            topTag = new Html();
            TagMember head = new TagMember(new Head());
            TagMember body = new TagMember(new Body());
            topTag.addToBody(head);
            topTag.addToBody(body);
            currentTag = body.getTag();
        }
        HashMap<String, String> choiceAttributes = new  HashMap<String, String>();
        choiceAttributes.put("src", "choicehandler.js");
        choiceAttributes.put("type", "text/javascript");
        SingleScript choiceHandler = new SingleScript();

        Heading title = new Heading(2);
        title.addToBody(new textMember(par.getStory()));

        Heading author = new Heading(3);
        author.addToBody(new textMember(par.getAuthor()));

        TagMember scriptMember = new TagMember(choiceHandler);
        TagMember titleMember = new TagMember(title);
        TagMember authorMember = new TagMember(author);
        
        currentTag.addToBody(scriptMember);
        currentTag.addToBody(titleMember);
        currentTag.addToBody(authorMember);

        Paragraph paragraph = new Paragraph();
        paragraph.addToBody(new textMember(par.getText()));

        TagMember paragraphMember = new TagMember(paragraph);
        currentTag.addToBody(paragraphMember);

        Span choice;
        HashMap<String, String> attributes;
        for (Choix c : par.getChoices()){
            if (true){ /* if choice is visible for the user */
                attributes = new HashMap<String, String>();
                attributes.put("onclick", "handleChoice("+ c.getIdChoice() + ")"); /* script choiceHandler.js must contain a handleChoice(int id) method */
                choice = new Span(attributes);
                choice.addToBody(new textMember(c.getText()));
                currentTag.addToBody(new TagMember(choice));
            }
        }


    }
    public void writeFile(String fileName){
        try {
            File file = new File(fileName);
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            if (insert){
                writer.write(readFile(prefixFile));
            }
            topTag.printInFile(writer);
            if (insert){
                writer.write(readFile(suffixFile));
            }
            writer.close();
          } catch (IOException e) {
            System.out.println("IO error occurred while generating file : ");
            e.printStackTrace();
          }
        
    }

    public String readFile(String fileName){
        String data = "";
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data += reader.nextLine() + "\n";
            }
            reader.close();
          } catch (FileNotFoundException e) {
            System.out.println("IO error occurred : wrap files not found.");
            e.printStackTrace();
          }
    }

}
