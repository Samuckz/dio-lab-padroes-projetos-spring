package one.digitalinnovation.labpadroesprojetospring.service.impl;

import jakarta.persistence.EntityNotFoundException;
import one.digitalinnovation.labpadroesprojetospring.model.Cliente;
import one.digitalinnovation.labpadroesprojetospring.model.Endereco;
import one.digitalinnovation.labpadroesprojetospring.repository.ClienteRepository;
import one.digitalinnovation.labpadroesprojetospring.repository.EnderecoRepository;
import one.digitalinnovation.labpadroesprojetospring.service.ClienteServiceInterface;
import one.digitalinnovation.labpadroesprojetospring.service.ViaCepServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteServiceInterface {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepServiceInterface viaCepService;


    private void verificaEndereco(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.
                findById(cep)
                .orElseGet(() -> {
                    Endereco novoEndereco = viaCepService.consultarCep(cep);
                    enderecoRepository.save(novoEndereco);
                    return novoEndereco;
                });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public Iterable<Cliente> getAllClients() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getClientById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty())
            throw new EntityNotFoundException("Cliente não existe");

        return cliente.get();
    }

    @Override
    public void save(Cliente cliente) {
        verificaEndereco(cliente);
    }

    @Override
    public void update(Long id, Cliente cliente) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);

        if (clienteEncontrado.isEmpty())
            throw new EntityNotFoundException("Cliente não existe!");

        verificaEndereco(cliente);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
