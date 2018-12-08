import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a linked list, to make organizing the cards easier when painting them to a canvas and exporting them.
 * (the index can be set internally on an insert)
 */
public class Card {

    public static final int width = 250;
    public static final int height = 350;
    public static final int borders = 2;
    private static final int marginWidth = 30; //for text margins

    private String header;
    private String content;

    private int x;
    private int y;
    private int index;

    private Card next;

    public Card(String header, String content){
        this.header = header;
        this.content = content;
        index = 0;
        x = 0;
        y = 0;
        next = null;
    }

    public Card(String info) throws Exception{
        header = "";
        content = "";
        x = 0;
        y = 0;
        index = 0;
        if(info.matches(".+: .+")){
            int i = 0;
            //basically, this loop looks for the ": " pattern and the stops
            header+= info.charAt(0);
            for(i = 1; info.charAt(i - 1) != ':'; i++){
                header += info.charAt(i);
            }
            i++;//skip over space after
            for(; i < info.length(); i++){
                content += info.charAt(i);
            }
        }
        else {
            System.out.println("Card info \"" + info + "\" does not match formatting standards.");
            throw new Exception(info);
        }
    }

    public void insert(Card card){
        card.index++;

        if(next == null){
            next = card;

            //then we determine the formatting of the card based on its index in the LinkedList:
            card.x = this.x + Card.width + Card.borders;
            card.y = y;//set the y to match the last card in the chain. 
        
            //if a new row is starting, edit the y values and x values appropiately:
            if(card.index % 3 == 0){
                card.x = 0;
                card.y = this.y + Card.height + Card.borders;
            }
            
            //if a new image is starting, then reset y:
            if(card.index % 9 == 0){
                card.y = 0;//the last if clause deals with the x coord
            }
        }
        else {
            next.insert(card);
        }
    }

    public int index(){
        return index;
    }

    public Card next(){
        return next;
    }

    public void draw(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(this.getBackgroundColor());
        g.fillRect(x + 10, y + 10, width - 20, height - 20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
       // g.drawString(header, x + marginWidth, y + marginWidth + 10);//the 10 is a hardcode to compensate for the misaligning of the margins height wise compared to width wise.
        drawText(g, header, 10);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        drawText(g, content, 75);
    }

    private Color getBackgroundColor(){
        Color color = new Color(255, 255, 255);//default color
        ArrayList<Color> colorList = new ArrayList<Color>();
        Scanner scan = new Scanner(header);
        scan.useDelimiter("[\\s:]");
        while(scan.hasNext()){
            String word = scan.next();
            switch(word){
                case "{D}": colorList.add(new Color(224, 123, 157)); break;
                case "{L}": colorList.add(new Color(167, 234, 159)); break;
                case "{B}": colorList.add(new Color(237, 221, 158)); break;
                case "{M}": colorList.add(new Color(158, 158, 237)); break;
                case "{S}": colorList.add(new Color(223, 159, 234)); break;
                case "{T}": colorList.add(new Color(158, 237, 201)); break;
            }
        }
        scan.close();

        //average all the colors in the colorList, and set color equal to average.
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        System.out.println("Color list size = " + colorList.size());
        for(Color c: colorList){
            redSum += c.getRed();
            blueSum += c.getBlue();
            greenSum += c.getGreen();
        }

        if (colorList.size() > 0){
            color = new Color(
                redSum/colorList.size(), 
                blueSum/colorList.size(), 
                greenSum/colorList.size()
                );
        }

        return color;
    }

    private void drawText(Graphics g, String text, int yOffset){
        
        //create an arraylist to store lines in, so we can draw them line by line
        ArrayList<String> lines = new ArrayList<String>();
        String[] words = text.split("\\s");
        int lineNum = 0;

        lines.add("");

        //create each line, making sure no line is longer than the card width - the black border width of the card.
        for (int i = 0; i < words.length; i++) {
            //find the length of the ongoing line value + the next word length:
            int nextWidth = g.getFontMetrics().stringWidth(lines.get(lineNum) + " " + words[i]);
            if(nextWidth > Card.width - Card.marginWidth*2){
                lineNum++;
                lines.add(words[i] + " ");
            }
            else {
                lines.set(lineNum, lines.get(lineNum) + words[i] + " ");
            }
        }

        //draw each line:
        int lineHeight =  g.getFontMetrics().getHeight();
        for(int line = 0; line < lines.size(); line++){
            g.drawString(lines.get(line), x + marginWidth, y + marginWidth + (line*lineHeight) + yOffset);//marginWidth accounts for the graphical borders (that are diferent from the card border value)
        }
    }

    public void deleteChildren(){
        if(next != null){
            next.deleteChildren();
        }

        next = null;
    }
}