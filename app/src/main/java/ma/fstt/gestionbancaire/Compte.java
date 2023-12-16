package ma.fstt.gestionbancaire;

public class Compte {
    private Long numCompte;
    private String nom;
    private Double solde;

    public Long getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(Long numCompte) {
        this.numCompte = numCompte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }
    public String toString() {
        return nom;

    }
}
