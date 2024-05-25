package com.librys.bibliotecalibrys.api.DTO.mapper;

import com.librys.bibliotecalibrys.api.DTO.AlugarLivroDTO;
import com.librys.bibliotecalibrys.api.DTO.AlugarLivroResponseDTO;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlugarLivroMapper {

    @Autowired
    private ModelMapper mapper;

    public LivroAlugado toEntity(AlugarLivroDTO alugarLivroDTO){
        return mapper.map(alugarLivroDTO, LivroAlugado.class);
    }

    public AlugarLivroResponseDTO toDTO(LivroAlugado entity){
        return mapper.map(entity, AlugarLivroResponseDTO.class);
    }

}
