import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class CardPainter{
    
    private static int exportCount = 0;

    private static final int imageWidth = Card.width * 3 + (2 * Card.borders);
    private static final int imageHeight = Card.height * 3 + (2 * Card.borders);

    private static ArrayList<BufferedImage> images;

    public static void paintChain(Card head){
        //create a new arraylist of images for this batch of card images
        images = new ArrayList<BufferedImage>();
        int imageIndex = 0;
        //create and add the first image to the image list:
        images.add(new BufferedImage(
            Card.width * 3 + Card.borders,
            Card.height * 3 + Card.borders,
            BufferedImage.TYPE_INT_RGB
        ));
        Graphics g = images.get(images.size() - 1).createGraphics();

        for(Card card = head; card != null; card = card.next()){
            if(card.index() % 9 == 0 && card.index() != 0){
                //we need to move to the next image:
                imageIndex++;
                images.add(new BufferedImage(
                    Card.width * 3 + Card.borders,
                    Card.height * 3 + Card.borders,
                    BufferedImage.TYPE_INT_RGB
                ));

                g = images.get(images.size() - 1).createGraphics();//create new graphics context for this new image, then
                card.draw(g);//draw the card onto this graphics context.
            }
            else{
                card.draw(g);//the cards are oriented when inserted into the card chain.
            }
        }
    }

    public static void exportPainted(String folderpath){
        for(int i = 0; i < images.size(); i ++){
            String filename = "file" + String.valueOf(++exportCount).toString() + ".png";
            try{
                ImageIO.write(images.get(i), "png", new File(filename));
            } catch(IOException e){
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
        } 
    }
    
}