package html_generator;
import java.io.FileWriter; 
import java.io.IOException;
public abstract class Member {
    private Member next = null;
    public void setNext(Member next){
        this.next = next;
    }
    public Member getNext(){
        return next;
    }
    public void addMember(Member member){
        /* adds the given member at the end of the current member chain */
        Member current = this;
        while (current.getNext() != null){
            current = current.getNext();
        }
        current.setNext(member);
    }

    public abstract void printInFile(FileWriter writer) throws IOException;
}
