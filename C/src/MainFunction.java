import view.ConnectGUI;

import java.io.IOException;

public class MainFunction {
    public static void main(String[] args) throws IOException {
        // open the first interface
        ConnectGUI connectgui = new ConnectGUI();
        connectgui.initUI();
    }
}
