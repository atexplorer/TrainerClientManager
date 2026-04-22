package org.atexplorer.TrainerClientManager.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("TRAINER")
public class TrainerProfile extends UserProfile{

    @OneToMany(mappedBy = "id")
    private List<ClientProfile> clients;
    private String[] certifications;

    public String[] getCertifications() {
        return certifications;
    }

    public void setCertifications(String[] certifications) {
        this.certifications = certifications;
    }

    //Should this be an unmodifiable list? This object should in, theory, manage adding clients.
    public List<ClientProfile> getClients() {
        return clients;
    }

    public void setClients(List<ClientProfile> clients) {
        this.clients = clients;
    }
}
