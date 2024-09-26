package com.test.junit_test.service;

import static java.lang.String.format;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.junit_test.exception.BusinessException;
import com.test.junit_test.model.Pessoa;
import com.test.junit_test.repository.PessoaRepository;

@Service
public class PessoaService {

    @Value("${path.document}")
    private String path;

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> buscaPessoasPorCpf(String cpf) throws BusinessException {
        try {
            Objects.requireNonNull(cpf, "CPF é obrigatório!");
            return repository.findPessoa(cpf);
        } catch (Exception e) {
            throw null;
        }

    }

    public ResponseEntity<String> uploadDocument(MultipartFile file) {
        try {
            Objects.requireNonNull(file, "arquivo é obrigatório!");

            String rootFile = path + file.getOriginalFilename();
            File newDocument = new File(rootFile);
            FileOutputStream fileOutputStream = new FileOutputStream(newDocument, true);
            file.getInputStream().transferTo(fileOutputStream);

            return ResponseEntity.ok("Arquivo carregado " + file.getName());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar arquivo", e);
        }
    }

}
