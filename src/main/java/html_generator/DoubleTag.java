package html_generator;

import java.util.HashMap;

public abstract class DoubleTag extends Tag{
    protected Member body = null;
    
    protected DoubleTag() {
    	super();
    }
    
    protected DoubleTag(HashMap<String, String> attributes){
        super(attributes);
    }
    
    public String getOpenTag(){
        return "<" + getName() + " " + printAttributes() + ">";
    }
    public String getCloseTag(){
        return "</" + getName() + ">";
    }
    public void addToBody(Member member){
        /* adds a member to this tag's body */
        if (body == null){
            body = member;
        } else {
            body.addMember(member);
        }
    }
    
    public Member getBody(){
        return body;
    }
    
}
