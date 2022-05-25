import Classes.Commands;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        Commands commands = new Commands();
        commands.menu();
    }
}