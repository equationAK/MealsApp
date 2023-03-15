import events.Interaction;

public class Main {

    public static void main(String[] args) {

        // getting the only object for Interaction class
        Interaction interaction = Interaction.getInteractionInstance();
        // Interaction method to start the needed methods of the app
        interaction.startUp();
        // Interaction method to start the GUI interaction with the user
        interaction.guiStart();
    }
}