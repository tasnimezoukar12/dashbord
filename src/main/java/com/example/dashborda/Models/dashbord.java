package com.example.dashborda.Models;

public class dashbord {
    private int totalClients;
    private int totalEntrepreneurs;

    public dashbord(int totalClients, int totalEntrepreneurs) {
        this.totalClients = totalClients;
        this.totalEntrepreneurs = totalEntrepreneurs;
    }

    // Getters and setters
    public int getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
    }

    public int getTotalEntrepreneurs() {
        return totalEntrepreneurs;
    }

    public void setTotalEntrepreneurs(int totalEntrepreneurs) {
        this.totalEntrepreneurs = totalEntrepreneurs;
    }
}
