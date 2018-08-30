import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Driver{
    public static void main(String[] args){
        Model model = new Model();
        
        String input = "";
        BufferedReader console;
        console = new BufferedReader(new InputStreamReader(System.in));

        try{
            while(!input.equals("exit")){
                input = console.readLine();
                if(input.equals("export")){
                    model.exportAll("/");//for this version, the files are exported to the same folder the program is running in.
                }
                else if(input.equals("help")){
                    printHelp();
                }
                else if(input.equals("clear")){
                    model.clear();
                }
                else if(!input.equals("exit")){
                    // System.out.println("adding card: ");
                    model.addCard(input);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void printHelp(){
        System.out.println("\nMass Prototyper v1.0\n");
        System.out.println("To add a card to the 'export' list, simply type the " 
        + "header of the card, followed by a ':', followed by the content of the card.");
        System.out.println("\nspecial commands:\n");
        System.out.println("exit - exits program and discards all working data.");
        System.out.println("export - creates the card images based on the cards that have been specified so far.");
        System.out.println("clear - clears the card cache. This will erase all memory of cards added previously.");
        System.out.println("help - displays this message");
        System.out.println("");
    }
}