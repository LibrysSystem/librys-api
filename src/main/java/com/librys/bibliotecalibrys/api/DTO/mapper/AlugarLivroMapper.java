package com.librys.bibliotecalibrys.api.DTO.mapper;

import com.librys.bibliotecalibrys.api.DTO.AlugarLivroDTO;
import com.librys.bibliotecalibrys.api.DTO.AlugarLivroResponseDTO;
import com.librys.bibliotecalibrys.domain.model.LivroAlugado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlugarLivroMapper {

    @Autowired
    private ModelMapper mapper;

    public LivroAlugado toEntity(AlugarLivroDTO alugarLivroDTO){
        LivroAlugado entity = mapper.map(alugarLivroDTO, LivroAlugado.class);
        return entity;
    }

    public AlugarLivroResponseDTO toDTO(LivroAlugado entity){
        AlugarLivroResponseDTO alugarLivroResponseDTO = mapper.map(entity, AlugarLivroResponseDTO.class);
        return alugarLivroResponseDTO;
    }

    public List<AlugarLivroResponseDTO> toDTO(List<LivroAlugado> livroAlugados){
        return livroAlugados.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
