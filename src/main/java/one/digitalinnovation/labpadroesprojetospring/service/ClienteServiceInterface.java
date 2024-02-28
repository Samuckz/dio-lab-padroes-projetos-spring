package one.digitalinnovation.labpadroesprojetospring.service;

import one.digitalinnovation.labpadroesprojetospring.model.Cliente;

public interface ClienteServiceInterface {
    Iterable<Cliente> getAllClients();

    Cliente getClientById(Long id);

    void save(Cliente cliente);

    void update(Long id, Cliente cliente);

    void delete(Long id);
}
