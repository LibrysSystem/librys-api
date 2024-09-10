package com.librys.bibliotecalibrys.api.DTO.mapper;

import com.librys.bibliotecalibrys.api.DTO.FuncionarioDTO;
import com.librys.bibliotecalibrys.domain.model.Funcionario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    @Autowired
    private ModelMapper mapper;

    public Funcionario toEntity(FuncionarioDTO funcionarioDTO){
        return mapper.map(funcionarioDTO, Funcionario.class);
    }

    public FuncionarioDTO toDTO(Funcionario funcionario){
        return mapper.map(funcionario, FuncionarioDTO.class);
    }

}
