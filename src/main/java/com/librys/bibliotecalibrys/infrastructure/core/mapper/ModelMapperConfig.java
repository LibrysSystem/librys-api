package com.librys.bibliotecalibrys.infrastructure.core.mapper;

import com.librys.bibliotecalibrys.api.DTO.AlugarLivroDTO;
import com.librys.bibliotecalibrys.domain.model.Cliente;
import com.librys.bibliotecalibrys.domain.model.Livro;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<AlugarLivroDTO, LivroAlugado>() {
            @Override
            protected void configure() {
                skip(destination.getId());

                using(livroIdToLivro -> {
                    Long livroId = (Long) livroIdToLivro.getSource();
                    Livro livro = new Livro();
                    livro.setId(livroId);
                    return livro;
                }).map(source.getLivroId(), destination.getLivro());

                using(clienteIdToCliente -> {
                    Long clienteId = (Long) clienteIdToCliente.getSource();
                    Cliente cliente = new Cliente();
                    cliente.setId(clienteId);
                    return cliente;
                }).map(source.getClienteId(), destination.getCliente());
            }
        });

        return modelMapper;
    }
}
