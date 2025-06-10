package org.kelompok20;

import org.kelompok20.view.LoginView;
import org.kelompok20.view.WargaDashboardView;
import org.kelompok20.view.AdminDashboardView;
import org.kelompok20.view.FormPengaduanView;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
