package co.edu.uniquindio.poo.app;


import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Tienda;

import javax.swing.*;
import java.util.Optional;

public class Main {
    static void main() {
        Tienda tienda= new Tienda("Techstore", "34567-8", "3126574588");

        Cliente c1= new Cliente("1094843025", "Juan Esteban Quintero Gonzalez", "3145567983", "Circasia", "juane.quinterog@gmail.com", tienda);

        String msg= tienda.registrarCliente(c1);

        JOptionPane.showMessageDialog(null, msg);

    }
}
