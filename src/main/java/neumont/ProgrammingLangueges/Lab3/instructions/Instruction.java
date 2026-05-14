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

    protected int getRegister(String reg) {
        reg = reg.trim().toUpperCase().replace(",", "");

        if (reg.startsWith("R")) {
            return Integer.parseInt(reg.substring(1));
        }

        switch (reg) {
            case "SP" -> {
                return 13;
           }
            case "LR" -> {
                return 14;
           }
            case "PC" -> {
                return 15;
           }
            default -> throw new IllegalArgumentException("Invalid register: " + reg);
        }
    }

    protected int getOpCode(String opcode) {
    return switch (opcode) {
        case "AND" -> 0b0000;
        case "EOR" -> 0b0001;
        case "SUB" -> 0b0010;
        case "RSB" -> 0b0011;
        case "ADD" -> 0b0100;
        case "ADC" -> 0b0101;
        case "SBC" -> 0b0110;
        case "RSC" -> 0b0111;
        case "TST" -> 0b1000;
        case "TEQ" -> 0b1001;
        case "CMP" -> 0b1010;
        case "CMN" -> 0b1011;
        case "ORR" -> 0b1100;
        case "MOV" -> 0b1101;
        case "BIC" -> 0b1110;
        case "MVN" -> 0b1111; 
        default    -> 0b0000;
    };
}

    protected int parseImmediate(String value) {
        value = value.trim().replace("#", "");

        if (value.startsWith("0x") || value.startsWith("0X")) {
            return Integer.parseInt(value.substring(2), 16);
        }

        return Integer.parseInt(value);
    }

}