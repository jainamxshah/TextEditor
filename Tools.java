import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Tools {
    Scanner sc = new Scanner(System.in);

    //For Viewing the Content of File
    public void view(ArrayList<String> line){
        for (String st : line) {
            System.out.println(st);
        }  
    }

    //For finding specific word
    public void find(ArrayList<String> line) throws Exception {
        String searchWord;
        while(true) {
            try {
                System.out.print("Enter word to Find : ");
                searchWord = sc.next();
                break;        
            } catch (Exception e) {
                e.printStackTrace();
                sc.nextLine();
            }
        }
        for (String st : line) {
            st=find(st, searchWord);
            System.out.println(st);
        }
    }

    //Helper Method of Find to highlighting word
    public String find(String line,String search){
        String[] str = line.split(" ");
        for(int i=0; i< str.length; i++) {
            if(str[i].equals(search)) {
                str[i] = "\u001B[31m" + str[i] + "\u001B[0m";
            }
        }
        String str1 = "";
        for(int i=0;i<str.length; i++){
            str1 = str1+" "+str[i];
        }
        return str1;
    }

    //For Finding Words Frequencies
    public void wordsFrequency(ArrayList<String> line){
        HashMap<String, Integer> wordFrequencyMap = new HashMap<>();
        HashSet<String> set=new HashSet<>();
        String mostRepeatedWord = "";
        int maxFrequency = 0;
        int count=0;
        for (String string : line) {
            String[] str=string.split(" ");
            for (String string2 : str) {
                String lowercaseWord = string2.toLowerCase();
                if(!lowercaseWord.equals(".")){
                    wordFrequencyMap.put(lowercaseWord, wordFrequencyMap.getOrDefault(lowercaseWord, 0) + 1);
                    if (wordFrequencyMap.get(lowercaseWord) > maxFrequency) {
                        mostRepeatedWord = lowercaseWord;
                        maxFrequency = wordFrequencyMap.get(lowercaseWord);
                    }
                    count++;
                    set.add(string2);
                }
            }
        }
        System.out.println("Total Words: "+count);
        System.out.println("Unique Words: "+set.size());
        System.out.println("Most common word: "+mostRepeatedWord);
    }

    //For Replacing Specific word
    public ArrayList<String> replace(ArrayList<String> line) throws Exception {
        String searchWord, replaceWord;
        while(true) {
            try {
                System.out.print("Enter word to Find : ");
                searchWord = sc.next();
                System.out.print("Enter word to Replace with : ");
                replaceWord = sc.next();
                break;        
            } catch (Exception e) {
                e.printStackTrace();
                sc.nextLine();
            }
        }
        int index = -1;
        System.out.println();
        for (String word: line) {
            index++;
            String str = replaceWord(word,searchWord,replaceWord);
            line.set(index,str);
            System.out.println(str);
        }
        return line;
    }

    //Helper Method of Replace to replace the word 
    public String replaceWord(String line,String search, String replace) {
        String[] str = line.split(" ");
        for(int i=0; i< str.length; i++) {
            if(str[i].equals(search)) {
                str[i] = replace;
            }
        }
        String str1 = "";
        for(int i=0;i<str.length; i++){
            str1 = str1+" "+str[i];
        }
        return str1;
    }

    //Method for spellCheck
    public HashSet<String> spellCheck() throws IOException{
        HashSet<String> dict=new HashSet<>();
        BufferedReader bfr=new BufferedReader(new FileReader("Dictionary.txt"));
        String str="";
        while((str=bfr.readLine())!=null){
            dict.add(str.toLowerCase());
        }
        bfr.close();
        return dict;
    }

    //Mthod for converting Content to Upper or Lower case
    public ArrayList<String> convertCase(ArrayList<String> line, int val){
        if(val==1){
            int index=-1;
            for(String st: line){
                index++;
                line.set(index, st.toUpperCase());
            }
        }
        else{
            int index=-1;
            for(String st: line){
                index++;
                line.set(index, st.toLowerCase());
            }
        }
        return line;
    }

    //Method for checking consecutive duplicates words
    public void duplicateWords(ArrayList<String> line){
        for (String lines : line) {
            String[] words = lines.split(" ");
            StringBuilder result = new StringBuilder();
    
            for (int i = 0; i < words.length; i++) {
                String currentWord = words[i];
                result.append(currentWord);
    
                // Check if there are more words and the next word is a duplicate
                while (i < words.length - 1 && currentWord.equals(words[i + 1])) {
                    result.append(" \u001B[31m" + words[i + 1] + "\u001B[0m"); // Highlight duplicate word
                    i++; // Move to the next word
                }
    
                // Add a space if there are more words
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
            System.out.println(result.toString());
        }
        System.out.println();
        while(true) {
            String decision;
            while(true) {
                try {
                    System.out.print("Do You Want to Delete Duplicate Words? (Yes or No): ");
                    decision = sc.next();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    sc.nextLine();
                }
            }
            if(decision.equalsIgnoreCase("yes")) {
                int index = 0;
                for(String rmWord: line) {
                    String[] word = rmWord.split(" ");
                    StringBuilder result1 = new StringBuilder();
                    for(int i=0; i<word.length; i++) {
                        String currWord = word[i];
                        result1.append(currWord);
                        while(i<word.length-1 && currWord.equals(word[i+1])) {
                            result1.append("");
                            i++;
                        }
                        if(i<word.length-1) {
                            result1.append(" ");
                        }
                    }
                    String s = result1.toString(); 
                    System.out.println(s);
                    line.set(index, s);
                    index++;
                }
                break;
            } else if(decision.equalsIgnoreCase("No")) {
                break;
            } else {
                System.out.println("Type Yes or No!!!");
            }
        }
    }

    //For Sorting Lines in Lexicographical Order
    public ArrayList<String> sortLines(ArrayList<String> lines){
        PriorityQueue <String> p=new PriorityQueue<>();
        for (String string : lines) {
            p.add(string);
        }
        ArrayList<String> lines1=new ArrayList<>();
        while(!p.isEmpty()){
            lines1.add(p.poll());
        }
        return lines1;
    }
}