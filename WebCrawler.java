/*
    Bryan Dingman
    Lab 3 
    Web crawl through urls and grab the most common words
    LOGIC:
        Check if file exists, remove
        Ask user for url
        Ask user for time to crawl
            Error and default if invalid
        Compile lists
        Start Crawl (Method)
            Go to URL
                Error if invalid
            Grab new urls
            Grab new words
            Return them back to main
        Save new URLs
        Remove previous URL
        Add words to master
        REPEAT CRAWL for user set time
        After timeout, or ran out of urls
        Count the words and remove the extra words
        Sort (Method)
            Bubble sort counts based on the count 
        Create the file
        Print results to the file
*/
package lab3;
import java.util.Scanner;
import java.net.URL;
import java.io.IOException;
import java.util.InputMismatchException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;


public class Dingman_lab3 
{
    public static void main(String[] args) 
    {
        /*
            Create our amazing arraylists. I love these things. 
        */
        ArrayList<String> spentURLs = new ArrayList<>();
        ArrayList<String> pendingURLs = new ArrayList<>();
        ArrayList<String> ignore = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        
        /* 
            Add our words to the array list
        */
        ignore.add("a");
        ignore.add("of");
        ignore.add("the");
        ignore.add("in");
        ignore.add("on");
        ignore.add("for");
        ignore.add("from");
        ignore.add("with");
        ignore.add("and");
        ignore.add("or");
        ignore.add("you");
        ignore.add("i");
        ignore.add("your");
        ignore.add("my");
        ignore.add("<p>");
        ignore.add("to");
        ignore.add("is");
        ignore.add("are");
        ignore.add("as");
        ignore.add("it");
        ignore.add("at");
        ignore.add("we");
        ignore.add("an");
        ignore.add("its");
        
        // Create our file before we even ask the user for input. 
        File file = new File("output.txt");
        if (file.exists()) 
        {
            // Attempt to delete the file
            file.delete();
            
            // If we didn't succeed, unfortunately, close the application
            if (file.exists()) 
            {
                System.out.println("Could not delete output.txt. Please manually delete this file and try again");
                System.exit(0);
            }
        }

        // Create our scanner
        Scanner input = new Scanner(System.in);
        
        // Ask for the URL
        System.out.println("Please input a URL: ");
        String userURL = input.nextLine();
        
        // Ask the user for the seconds to run the crawl
        double timeOut;
        try 
        {
            System.out.println("Please enter how long we should crawl (in seconds): ");
            timeOut = input.nextDouble();
        }
        // Catch and default in case they don't input the correct value
        catch (InputMismatchException ex)
        {
            System.out.println("Input was invalid, defaulting to 10 seconds\n\n");
            timeOut = 10;
        }
        
        // Create our timer by figuring out the millis
        double currentMili = System.currentTimeMillis();
        timeOut = (timeOut * 1000) + currentMili;
        
        // Add our first URL
        pendingURLs.add(userURL);

        // Run our timer!
        System.out.println("Crawling. Please Wait...");
        while (!pendingURLs.isEmpty() && (System.currentTimeMillis() < timeOut))
        {
            try 
            {
                // Remember our current URL
                String currentURL = pendingURLs.get(0);

                // Check to see if we've used this before
                if (!spentURLs.contains(currentURL))
                {
                    // Traverse the URL and gather more URLs
                    // Returned == [LIST_OF_URLS,LIST_OF_WORDS]
                    ArrayList<ArrayList<String>> returned = traverse(currentURL,ignore);

                    // Remove our traversed url
                    pendingURLs.remove(currentURL);

                    // Add the traversed URL into our array
                    spentURLs.add(currentURL);

                    // Loop through all of our URLs that we got from traversing
                    for (String i: returned.get(0))
                    {
                        // Make sure that URL isn't in either of our lists
                        if (!pendingURLs.contains(i) && !spentURLs.contains(i) && !i.equals(""))
                        {
                            // Add it!
                            pendingURLs.add(i);
                        }
                    }
                    
                    // Loop through all the new words and add them 
                    for (String a: returned.get(1))
                    {
                        if (!a.equals("") && !a.equals(" "))
                        {
                           words.add(a); 
                        }
                    }
                }
            }
            // Force our loop to exit if something goes wrong
            catch (IndexOutOfBoundsException ex)
            {
                pendingURLs.clear();
                System.out.println("We ran out of URLs");
            }
        }

        // Make sure our words actually have something there.. Just in case
        if (!words.isEmpty())
        {
            System.out.println("Crawling finished. Compiling words");
            
            /*
                Create MOAR arraylists
            */
            ArrayList<String> finalWords = new ArrayList<>();
            ArrayList<Integer> finalCount = new ArrayList<>();
            ArrayList<String> usedWords = new ArrayList<>();
            
            // Loop through our words. I don't quite understand the netbeans warning...
            for (String x: words)
            {
                // Remember the currentWord
                String currentWord = x.toLowerCase();

                // Check to see if we've seen this word before
                if (!usedWords.contains(currentWord))
                {
                    // Add it so we know
                    usedWords.add(currentWord);

                    // Find how many occurances 
                    Integer count = Collections.frequency(words, currentWord);

                    // Add it to our final words arraylist
                    finalWords.add(currentWord);
                    
                    // Add the count to our list 
                    finalCount.add(count);
                }          
            }
            
            System.out.println("Sorting...");
            
            // Sort our arrays
            sort(finalWords,finalCount);
            
            // Create a file
            try 
            {
                java.io.PrintWriter fileOutput = new java.io.PrintWriter(file);

                // Write formatted output to the file
                for (int i = finalWords.size() - 1; i >= 0 ; i--)
                {
                    fileOutput.println(finalWords.get(i) + " " + finalCount.get(i));
                }

                // Close the file
                fileOutput.close();
                
                System.out.println("Output.txt has been created!");
            }
            // Not sure why file not found, but catch it anyway?
            catch (FileNotFoundException ex)
            {
                System.out.println("Could not write to directory!");
            }
        }
        else
        {
            // Error just in case we couldn't find any words... For some reason
            System.out.println("Something went wrong!\nWe were unable to gather anything from the crawl.\nPlease try another URL");
            System.exit(0);
        }
    }
   
    /*
        Travese Method. 
        This method will search through a URL and pull any other URLs.
        Also will compile a list of words pulled from the URLs
    
        INPUT:
            URL - STRING
            ignoreList - ArrayList<String>
    
        OUTPUT:
            List of: - ArrayList<ArrayList<String>>
                URLS - ArrayList<String>
                New Words - ArrayList<String>
    */
    public static ArrayList<ArrayList<String>> traverse (String URL,ArrayList<String> ignoreList)
    {
        //System.out.println("Traverse was called with URL: " + URL);
        // Create an arraylist to remember all of the new urls
        ArrayList<String> newURLs = new ArrayList<>();
        ArrayList<String> newWords = new ArrayList<>();
        ArrayList<ArrayList<String>> returned = new ArrayList<>();
        
        try 
        {
            // Create the URL object
            URL url = new URL(URL); 
            // Try to pull the webpage
            Scanner input = new Scanner(url.openStream());
            int start;
            // Loop until we have ran out of information
            while (input.hasNext()) 
            {
                // Get our current line
                String line = input.nextLine();
                
                // Find the first mention of http://
                start = line.indexOf("http://", 0);
                while (start > 0) 
                {
                    // Find the ending of the url
                    int end = line.indexOf("\"", start);
                    // Loop until we don't have any more URLs on our line
                    if (end > 0) 
                    {
                        // If we have soemthing good, add it so we can send it back.
                        newURLs.add(line.substring(start, end)); 
                        
                        // Find the next url 
                        start = line.indexOf("http://", end);
                    }
                    else
                    {
                        start = -1;
                    }
                }
                
                // Find our words 
                start = line.indexOf("<p>", 0);
                while (start > 0) 
                {
                    // Find the ending of the url
                    int end = line.indexOf("<", start + 1);
                    // Loop until we don't have any more URLs on our line
                    if (end > 0) 
                    {
                        // If we have soemthing good, separate it so we can send it back 
                        String[] list = (line.substring(start,end)).split(" ");
                        for (String i: list)
                        {
                            // Make it easier to compare
                            i = i.trim().toLowerCase();
                            
                            // Remove <p> if the string contains it
                            if (i.contains("<p>"))
                            {
                                i = i.replace("<p>", "");
                            }
                            // Remove any periods
                            if (i.contains("."))
                            {
                                i = i.replace(".", "");
                            }

                            // Remove any commas
                            if (i.contains(","))
                            {
                                i = i.replace(",", "");
                            }
                                
                            // Make sure we aren't trying to ignore it. 
                            if (!ignoreList.contains(i))
                            {
                                // Make sure the text doesn't have any symbols
                                if (i.matches("[A-Za-z]*"))
                                {
                                    newWords.add(i);
                                }
                            }
                        }
                        // Find the next paragraph tag 
                        start = line.indexOf("<p>", end);
                    }
                    else
                    {
                        start = -1;
                    }
                }
            } 
        }
        // Catch if we have a broken URL
        catch (MalformedURLException ex) 
        {
            System.out.println("Invalid URL: " + ex);
        }
        // Catch if we have a IO exception
        catch (IOException ex) 
        {
            System.out.println(ex);
        }
        // Catch anything else 
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        
        // Add our array lists to our array lists
        returned.add(newURLs);
        returned.add(newWords);
        
        // Return the arraylist
        return returned;
    }
    
    /*
        Sort our word and count array using a bubble sort. 
        INPUT:
            list of words - Arraylist<String>
            list of counts to match words - ArrayList<String>
        OUTPUT:
            None
    */
    public static void sort (ArrayList<String> word, ArrayList<Integer> count)
    {
        // Get the size 
        int length = count.size();
        int y;
        
        // Inital loop 
        for (int i = 0; i < length; i++)
        {
            // Start from the beginning
            for (int x = 0; x < length - 1; x++) 
            {
                // Get the number next to the first
                y = x + 1;
                int first = count.get(x);
                int second = count.get(y);
                
                // If it's greater than, swap them
                if (first > second) 
                {
                    // Call the swap method
                    swap(x,y,word,count);
                }
            }
        }
    }
    
    /*
        Swap function for switching places in an array. This will switch both our words and our counts based on index

        INPUT:
            index of first count - INT
            index of second count - INT
            list of words - ArrayList<String>
            list of counts to match words - ArrayList<String>

        OUTPUT:
            NONE
    */
    public static void swap(int i, int x, ArrayList<String> word, ArrayList<Integer> count)
    {
        // Make our temp variables
        Integer temp;
        String tempWord;
        
        // Swap our count array
        temp = count.get(i);
        count.set(i, count.get(x));
        count.set(x,temp);
        
        // Swap word array
        tempWord = word.get(i);
        word.set(i, word.get(x));
        word.set(x,tempWord);
    }
}

