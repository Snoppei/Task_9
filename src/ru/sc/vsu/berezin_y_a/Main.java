package ru.sc.vsu.berezin_y_a;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Cmd cmd = new Cmd();

        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));

        cmd.runCmd();

    }

}
