package me.meegan.window.console;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class Console extends JTextArea {
    private CommandInterface commands;
    public Console() {
        super(6, 0);
        println("Welcome!");
        ((AbstractDocument)getDocument()).setDocumentFilter(new ConsoleDocumentFilter());
        addKeyListener(new listener());
    }

    public void println(String msg) {
        append(msg + "\n");
    }

    public void print(String msg) { append(msg); }



    public void addCommands(CommandInterface commands) {
        this.commands = commands;
    }

    public void executeCommand(String command, Object[] args) {
        if(commands == null) // if no command class has been added, return;.
            return;
        if(command.toLowerCase().equals("help")) {
            print(allCommands());
            return;
        }

        Method method = null;
        try {
            Class<?> c = commands.getClass(); // gets the class that's been set
            Method[] declaredMethods = c.getDeclaredMethods(); // gets all declared methods within the class

            for(Method m : declaredMethods) { // loops through all methods to check if it exists
                String methodName = m.getName(); // gets the method name
                if(methodName.toLowerCase().equals(command.toLowerCase())) // if method exists
                {
                    method = m;
                    Object returnMessage = m.invoke(commands, args); // invoke method with arguments provided and store return value in returnMessage
                    if(returnMessage != null) // check if has return value
                        println(returnMessage.toString()); // prints return value
                    return;
                }
            }
            // if no method exists
            println("ERROR: that command does not exist.");
        } catch(IllegalArgumentException e) {
            if (args.length > 0) // if more than one argument, show error message
                println("ERROR: " + e.getMessage() + "."); // prints the error message eg. ERROR: wrong number of arguments.

            println(getMethodUsage(method));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String allCommands() {
        String allCommandsString = "List of all commands: \n";
        if(commands == null) {
            println(allCommandsString);
            return null;
        }


        try {
            Class<?> c = commands.getClass(); // gets the class that's been set
            List<Method> declaredMethods = Arrays.asList(c.getDeclaredMethods());// gets all declared methods within the class
            Collections.sort(declaredMethods, Comparator.comparing(Method::getName)); // sorts the methods into alphabetical order

            for(Method m : declaredMethods) { // loops through all methods and adds usage to the string
                allCommandsString += "\t" + m.getName().toLowerCase() + " - " + getMethodUsage(m) + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allCommandsString;
    }

    public static String getMethodUsage(Method m) {
        String usage = "USAGE: " + m.getName().toLowerCase();
        for(Parameter p : m.getParameters()) {
            usage += " <" + p.getType().getSimpleName() + ">";
        }
        usage = usage + ".";
        return usage;
    }

    public class ConsoleDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (offset >= getLineStartOffset(getLineCount()-1)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset >= getLineStartOffset(getLineCount()-1)) {
                super.remove(fb, offset, length);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (offset >= getLineStartOffset(getLineCount()-1)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    class listener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume(); // prevents enter from occuring
                String input;
                try {
                    input = getText().split("\n")[getLineCount() - 1]; // gets last line of input
                    println(""); // simulates enter being pressed
                } catch (ArrayIndexOutOfBoundsException e1) {
                    return;
                }

                String[] inputSplit = input.trim().split(" "); // splits input into each argument + initial command

                String command = inputSplit[0]; // gets command name
                String[] commandargs = Arrays.copyOfRange(inputSplit, 1, inputSplit.length); // gets arguments

                ArrayList<Object> arguments = new ArrayList<>(); // creates temp list for looping
                for(String arg : commandargs) // checks if int and stores in list as object type.
                {
                    try {
                        arguments.add(Integer.parseInt(arg));
                    } catch (NumberFormatException e1) {
                        arguments.add(arg);
                    }
                }
                executeCommand(command, arguments.toArray()); // calls executeCommand(); with command name and args
            }
        }
    }
}
