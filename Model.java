import java.util.ArrayList;

public class Model{
    
    private Card head;

    public Model(){
        head = null;
    }

    public void addCard(String info){
        try{
            Card card = new Card(info);
            
            if(head == null){
                // System.out.println("head == null");
                head = card;
            }
            else {
                // System.out.println("head is not null, inserting...");
                head.insert(card);
            }
        } catch (Exception e){
            //some format exception, not going to print it as card prints its own message before throwing
        }
    }

    public void exportAll(String folderpath){
        //every 9 cards, create a file and align them correctly.
        CardPainter.paintChain(head);
        CardPainter.exportPainted(folderpath);
    }

    public void clear(){
        if(head != null){
            head.deleteChildren();
            head = null;
        }
    }
}