import java.io.*;
import java.util.*;

public class zhu_proj1 {

   // class for transition
   // q, a, r, b, x
      static class Transition {
        int nextState;
        char write;
        char direction;
        Transition(int nextState, char write, char direction){
            this.nextState = nextState;
            this.write = write;
            this.direction = direction;
        }
    }

     public static void main(String[] args){

        String path = args[0];
        String input = args[1];
        int maxTransitions = Integer.parseInt(args[2]);

        // holds the states
        int startState = -1; // only ONE start state

         // hash set for fast lookup and no unique states
        Set<Integer> acceptStates = new HashSet<>();
        Set<Integer> rejectStates = new HashSet<>(); 
        Map<Integer, Map<Character, Transition>> transFunc = new HashMap<>(); // holds entire transition function of machine

        try (BufferedReader br = new BufferedReader(new FileReader(path))){ // read the inputs from path file
            String line;
            while(((line = br.readLine()) != null)) {
                String[] inputs = line.split("\\s+"); 
                // can either be a transition / state
                if (inputs[0].equals("state")){
                    int num = Integer.parseInt(inputs[1]);
                    if (inputs.length >= 3){
                        switch (inputs[2]){
                            case "start": startState = num;
                            case "accept": acceptStates.add(num);
                            case "reject": rejectStates.add(num);
                        }
                    }
                }
                // transition q a r b x
                else if (inputs[0].equals("transition")){
                    // parse from line and put new transition object 
                    int q = Integer.parseInt(inputs[1]); // current state
                    char a = inputs[2].charAt(0); // symbol machine reads
                    int r = Integer.parseInt(inputs[3]); // state machine transitions to
                    char b = inputs[4].charAt(0); // symbol machine writes on top of a
                    char x = inputs[5].charAt(0); // L, S, or R

                    Transition t = new Transition(r, b, x);
                    transFunc.computeIfAbsent(q, k -> new HashMap<>()).put(a, t); // adds mapping from symbol a to transition t
                    // from state q, reading symbol a, follow transition t
                }

            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        };


       


        
    } 
}