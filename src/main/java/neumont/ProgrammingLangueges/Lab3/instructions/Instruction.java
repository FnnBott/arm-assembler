package neumont.ProgrammingLangueges.Lab3.instructions;

public class Instruction {
   protected int getConditionCode(String condition){
       return switch (condition) {
           case "EQ" -> 0b0000;
           case "NE" -> 0b0001;
           case "CS" -> 0b0010;
           case "CC" -> 0b0011;
           case "MI" -> 0b0100;
           case "PL" -> 0b0101;
           case "VS" -> 0b0110;
           case "VC" -> 0b0111;
           case "HI" -> 0b1000;
           case "LS" -> 0b1001;
           case "GE" -> 0b1010;
           case "LT" -> 0b1011;
           case "GT" -> 0b1100;
           case "LE" -> 0b1101;
           default -> 0b1110;
       };
    }

    
    
}