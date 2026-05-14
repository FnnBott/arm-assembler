package neumont.ProgrammingLangueges.Lab3;

import neumont.ProgrammingLangueges.Lab3.controller.Controller;

/**
 * @author fnn
 */
public class Lab3 {

    public static void main(String[] args) {
    Controller controller = new Controller();

    controller.readFile();

    controller.buildLabelTable();  

    controller.assemble();        
      
    controller.writeKernelImage();
}
}
