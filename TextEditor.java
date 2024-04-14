import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

class TextEditor {
    static String name,loc;
    static ArrayList <String> line;
    static Stack <ArrayList <String>> undo=new Stack<>();
    static Stack <ArrayList <String>> redo=new Stack<>();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean flag=true;
        while(flag){
            int ch;
            while(true) {
                try {
                    System.out.println("\n\u001B[35m╔═════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║\u001B[0m                            Welcome To TextEditor                            \u001B[35m║");
                    System.out.println("╟─────────────────────────────────────────────────────────────────────────────╢");
                    System.out.println("║                                                                             ║");;
                    System.out.println("║\u001B[0m  [1]NewFile              [2]OpenFile              [3]Exit TextEditor        \u001B[35m║");
                    System.out.println("║                                                                             ║");;
                    System.out.println("╚═════════════════════════════════════════════════════════════════════════════╝\u001B[0m\n");
                    System.out.print("Enter choice: ");
                    ch = sc.nextInt();  
                    break;  
                } catch (Exception e) {
                    System.out.println("\nEnter valid Choice!\n");
                    sc.nextLine();
                }
            }
            switch (ch) {
                case 1:
                    //For Creating New File
                    newFile();
                    break;
                case 2:
                //For Opening Existing File
                while(true) {
                    try {
                        System.out.print("Enter File Name : ");
                        name = sc.next();
                        System.out.print("Enter file path: ");
                        loc=sc.next();
                        openFile(loc);
                        break;                       
                    } catch (Exception e) {
                        System.out.println("\nNo such file exists!\n");
                        sc.nextLine();
                    }
                }
                break;
                case 3:
                    flag=false;
                break;
                default:
                    System.out.println("Enter Valid Number!");
                break;
            }
        }
            sc.close();
    }

    //Method for Creating New File
    public static void newFile() throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            String loc;
            while(true) {
                try {
                    System.out.print("Enter New File Name : ");
                    name = sc.next();
                    System.out.print("Enter file path: ");
                    loc=sc.next();
                    break;        
                } catch (Exception e) {
                    System.out.println("\nEnter valid Choice!\n");
                    sc.nextLine();
                }
            }
            File f1 = new File(name);
            
            //If file already exists with the same name as entered
            if(f1.exists()) {
                System.out.println("\n\u001B[31m**File Already Exists!!**\u001B[0m\n");
                while(true) {
                    int ch;
                    while(true) {
                        try {
                            System.out.println("\n\u001B[35m╔═════════════════════════════════════════════════════════════════════════════╗");
                            System.out.println("║\u001B[0m                            Welcome To TextEditor                            \u001B[35m║");
                            System.out.println("╟─────────────────────────────────────────────────────────────────────────────╢");
                            System.out.println("║                                                                             ║");;
                            System.out.println("║\u001B[0m  [1]OpenFile              [2]NewFile             [3]Exit TextEditor         \u001B[35m║");
                            System.out.println("║                                                                             ║");;
                            System.out.println("╚═════════════════════════════════════════════════════════════════════════════╝\u001B[0m\n");
                            System.out.print("Enter Choice : ");
                            ch = sc.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("\nEnter valid choice!\n");
                            sc.nextLine();
                        }
                    }
                    System.out.println();

                    //When user choose to open that existing file
                    if(ch == 1) {
                        openFile(loc);
                        return;
                    } else if(ch == 2) { //when user want to create new file with different name
                        newFile();
                        break;
                    } else {
                        System.out.println("Enter Valid Option...");
                    }
                }
            } 
            else {
                //if file is not existing then creating file
                f1.createNewFile();
                openFile(loc);
                System.out.println("File Created Successfully");
            }
            sc.close();
        }
    }
    
    //Method for opening specific file
    public static void openFile(String open) throws Exception {
        Scanner sc = new Scanner(System.in);
        //As this ArrayList will store lines of that specific file
        line=new ArrayList<>();
        File f2 = new File(open);
        BufferedReader br = new BufferedReader(new FileReader(f2));
        if(f2.exists()) {
            String s;
            System.out.println();
            while((s = br.readLine()) != null){
                line.add(s);
                System.out.println(s);
            }
            br.close();
            undo.push(line);
            boolean b = true;
            while(b) {
                int choice;
                while(true) {
                    try {
                        System.out.println("\n\u001B[35m╔══════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║\u001B[0m                                   "+name+"                                      ║");
                        System.out.println("\u001B[35m╟──────────────────────────────────────────────────────────────────────────────╢");
                        System.out.println("║                                                                              ║");;
                        System.out.println("║\u001B[0m    [1]AppendText       [2]View       [3]Tools       [4]Save       [5]Exit    \u001B[35m║");
                        System.out.println("║                                                                              ║");;
                        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝\u001B[0m\n");
                        System.out.print("Enter Choice : ");
                        choice = sc.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("\nEnter valid choice!\n");
                        sc.nextLine();
                    }                  
                }
                switch (choice) {
                    case 1:
                        //To Append Text 
                        System.out.print("Enter text to Append : ");
                        sc.nextLine();
                        String newText = sc.nextLine();
                        while(!redo.empty()){
                            undo.push(redo.pop());
                        }
                        undo.push(new ArrayList<>(line));
                        line.add(newText);
                        System.out.println("Text Appended Successfully...");
                    break;
                    case 2:
                        //To View the Content Of File
                        Tools tv = new Tools();
                        tv.view(line);
                    break;
                    case 3:
                        //For Using Different Tools
                        Tools t = new Tools();
                        boolean c = true;
                        while(c) {
                            int ch;
                            while(true) {
                                try {
                                    System.out.println("\n\u001B[35m╔══════════════════════════════════════════════════════════════════════════════╗");
                                    System.out.println("║\u001B[0m                                    Tools                                     \u001B[35m║");
                                    System.out.println("╟──────────────────────────────────────────────────────────────────────────────╢");
                                    System.out.println("║\u001B[0m  [1]View            [2]Find            [3]Replace         [4]SpellCheck      \u001B[35m║");
                                    System.out.println("║                                                                              ║");
                                    System.out.println("║\u001B[0m  [5]ConvertCase     [6]ChangeFont      [7]SortLines       [8]DuplicateWords  \u001B[35m║");
                                    System.out.println("║                                                                              ║");
                                    System.out.println("║\u001B[0m  [9]Undo            [10]Redo           [11]WordSummary    [12]Exit           \u001B[35m║");
                                    System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝\u001B[0m\n");
                                    System.out.print("Enter Choice : ");
                                    ch = sc.nextInt();
                                    break;        
                                } catch (Exception e) {
                                    System.out.println("\nEnter valid choice!\n");
                                    sc.nextLine();
                                }
                            }
                            switch (ch) {
                                case 1:
                                    //To View Content Of File
                                    t.view(line);
                                break;
                                case 2:
                                    //To Find the Specific Word
                                    t.find(line);
                                break;
                                case 3:
                                    //To replace the Specific Word 
                                    while(!redo.empty()){
                                        undo.push(redo.pop());
                                    }
                                    undo.push(new ArrayList<>(line));
                                    t.replace(line);
                                break;
                                case 4:
                                    //For Spell Checking
                                    HashSet<String> dict = t.spellCheck();
                                    System.out.println(dict.size());
                                    for (String string : line) {
                                        String s1 = "";
                                        String[] str1 = string.split(" ");
                                        for (int i=0; i<str1.length; i++) {
                                            str1[i] = str1[i].toLowerCase().replaceAll("[^a-zA-Z]", "");
                                            if(!dict.contains(str1[i])){
                                                str1[i] = "\u001B[31m" + str1[i] + "\u001B[0m";
                                            }
                                            s1 = s1+" "+str1[i];
                                        }
                                        System.out.println(s1);
                                    }

                                break;
                                case 5:
                                    //For Coverting Word to Uppercase or LowerCase
                                    int ch1;
                                    while(true) {
                                        try {
                                            System.out.println("[1] UpperCase\t[2]LowerCase");
                                            ch1=sc.nextInt();
                                            break;        
                                        } catch (Exception e) {
                                            System.out.println("\nEnter valid choice!\n");
                                            sc.nextLine();
                                        }
                                    }
                                    if(ch1==1){
                                        while(!redo.empty()){
                                            undo.push(redo.pop());
                                        }
                                        undo.push(new ArrayList<>(line));
                                        t.convertCase(line,1);
                                    }
                                    else if(ch1==2){
                                        while(!redo.empty()){
                                            undo.push(redo.pop());
                                        }
                                        undo.push(new ArrayList<>(line));
                                        t.convertCase(line,2);
                                    }
                                    else{
                                        System.out.println("Enter valid Option!");
                                    }
                                break;
                                case 6:
                                    //For Viewing Content in Underline, Bold, or Italic style 
                                    int c1;
                                    while(true) {
                                        try {
                                            System.out.println("\n"+"[1]"+"\033[4m"+"Underlined text"+"\033[0m"+"\t[2]"+"\033[3m"+"Italicized text"+"\033[0m"+"\t[3]"+"\033[1m"+"Bold text"+"\033[0m");
                                            c1=sc.nextInt();
                                            break;        
                                        } catch (Exception e) {
                                            System.out.println("\nEnter valid choice!\n");
                                            sc.nextLine();
                                        }
                                    }
                                    switch(c1){
                                        case 1:
                                            for (String st : line) {
                                                System.out.println("\033[4m"+st+"\033[0m");
                                            } 
                                        break;
                                        case 2:
                                            for (String st : line) {
                                                System.out.println("\033[3m"+st+"\033[0m");
                                            }
                                        break;
                                        case 3:
                                            for (String st : line) {
                                                System.out.println("\033[1m"+st+"\033[0m");
                                            }
                                        break;
                                        default:
                                            System.out.println("Enter valid Option!");
                                        break;
                                    }
                                break;
                                case 7:
                                    //For Sorting line Lexicographical Order
                                    while(!redo.empty()){
                                        undo.push(redo.pop());
                                    }
                                    undo.push(new ArrayList<>(line));
                                    line=t.sortLines(line);
                                    System.out.println("**Lines Sorted Successfully**\n");
                                break;
                                case 8:
                                    //For highlighting or removing Duplicate Words
                                    t.duplicateWords(line);
                                break;
                                case 9:
                                    //To Perform Undo Operation
                                    undo();
                                    System.out.println("**Undo Operation Successfully**\n");
                                break;
                                case 10:
                                    //To Perform Redo Operation
                                    redo();
                                    System.out.println("**Redo Operation Successfully**\n");
                                break;
                                case 11:
                                    //To get Words Frequency
                                    t.wordsFrequency(line);
                                break;
                                case 12:
                                    c = false;
                                break;
                                default:
                                    System.out.println("Enter Valid Number...");
                                break;
                            }
                        }
                    break;
                    case 4:
                        //For Saving all the changes applied in File
                        BufferedWriter bfw=new BufferedWriter(new FileWriter(f2));
                        for (String string : line) {
                            bfw.write(string+"\n");
                        }
                        System.out.println("**Saved Successfully**\n");
                        bfw.close();
                        undo.clear();
                        redo.clear();
                    break;
                    case 5:
                        System.out.println("\n\t\t\t\u001B[33m THANKS FOR USING TEXT EDITOR \u001B[0m\n");
                        b = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Enter valid Choice...");
                        break;
                }
            }
        } else if(!f2.exists()) {
            String nameTemp;
            while(true) {
                try {
                    System.out.print("Enter File Name : ");
                    nameTemp = sc.next();
                    File f=new File(nameTemp);
                    f.createNewFile();
                    break;        
                } catch (Exception e) {
                    System.out.println("Enter Valid name...");
                    sc.nextLine();
                }
            }
            openFile(nameTemp);
        }
        sc.close();
    }

    //For performing Undo Function
    public static void undo(){
        if(!undo.isEmpty()){
            redo.push(new ArrayList<>(line));
            line=undo.pop();
        }
    }

    //For performing Redo Function
    public static void redo(){
        if(!redo.isEmpty()){
            undo.push(new ArrayList<>(line));
            line=redo.pop();
        }
    }
}