import java.io.*;
import java.util.*;

public class zhu_proj1 {

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

        String path = args[0]; // definition of machine from file 
        String input = args[1]; // simulates input to automata
        int maxTransitions = Integer.parseInt(args[2]);


        // holds the states
        int startState = -1; // only ONE start state

         // hash set for fast lookup and no unique states
        Set<Integer> acceptStates = new HashSet<>();
        Set<Integer> rejectStates = new HashSet<>(); 
        Map<Integer, Map<Character, Transition>> transFunc = new HashMap<>(); // holds entire transition function of machine

         // sets up states and transitions from machine definition 
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while(((line = br.readLine()) != null)) {
                String[] inputs = line.split("\\s+"); 
                // can either be a transition / state
                if (inputs[0].equals("state")){
                    int num = Integer.parseInt(inputs[1]);
                    if (inputs.length >= 3){
                        switch (inputs[2]){
                            case "start": startState = num; break;
                            case "accept": acceptStates.add(num); break;
                            case "reject": rejectStates.add(num); break;
                            default:
                                break;
                        }
                    }
                }
                // transition q a r b x
                else if (inputs[0].equals("transition")){
                    // parse inputs from transition line
                    int q = Integer.parseInt(inputs[1]); // current state
                    char a = inputs[2].charAt(0); // symbol machine reads
                        if (a == '_'){
                            a = ' ';
                        }
                    int r = Integer.parseInt(inputs[3]); // state machine transitions to
                    char b = inputs[4].charAt(0); // symbol machine writes on top of a
                        if (b == '_'){
                         b = ' ';
                        }
                    char x = inputs[5].charAt(0); // L, S, or R

                    Transition t = new Transition(r, b, x); // creates new transition object to add to map

                    transFunc.computeIfAbsent(q, k -> new HashMap<>()).put(a, t); 
                    // the transition format for machine is “q,a->r,b,x”, from state q, reading symbol a, follow transition t (r,b,x)
                }

            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        };
        
        // Turing machine tape implementation with arraylist using input string
        List<Character> tape = new ArrayList<>();
        for (char c : input.toCharArray()) {
            tape.add(c);
        }
        int head = 0; // read write pointer for tape
        int state = startState;
        int steps = 0;

      
        while (true) {  // until accept/reject state reached
            
            if(acceptStates.contains(state)){
                printOutput(tape, head, "accept");
                return;
            }
            if(rejectStates.contains(state)){ 
               
                printOutput(tape, head, "reject");
                return; 
            }
            
            char a = ' ';
            if (head < tape.size()){
                
                a = tape.get(head);
               
            }
            Transition tr = transFunc.getOrDefault(state, Map.of()).get(a);
            if(tr == null){
                printOutput(tape, head, "reject");
                return; 
            }
            //write
           

            if(head == tape.size()){
                tape.add(tr.write); 
            }
                else{
                    tape.set(head, tr.write); 
                
            }
            // move 
            if(tr.direction == 'L'){
                head--; // move to left 
                
                if(head < 0){
                    printOutput(tape, head, "crash");
                    return; // crashed since -1: 
                }
            } else if (tr.direction == 'R'){
                head++;
            }
            // switch to transition next state

            state = tr.nextState; 

            steps++;
            // quit if >= max
            if(steps >= maxTransitions) {
                printOutput(tape, head, "quit");
                return;
            }
        
        }
           
     }

        static void printOutput(List<Character> tape, int head, String result){
            int start = head;
            
            StringBuilder output = new StringBuilder();
            // String from head until ' '
            for(int i  = start; ;i++){
                char c = (i < tape.size()) ? tape.get(i) : ' ';
                if(c == ' '){
                    break;
                }
                output.append(c);

            }
            System.out.println(output.toString() + " " + result);
        }


 
    } 
