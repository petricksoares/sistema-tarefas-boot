package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Tarefa;

@Repository
public class TarefaRepository {

    private final List<Tarefa> tarefas = new ArrayList<>();
    private Long proximoId = 1L;

    public List<Tarefa> listarTodas() {
        return tarefas;
    }

    public Tarefa salvar(Tarefa tarefa) {
        tarefa.setId(proximoId++);
        tarefas.add(tarefa);
        return tarefa;
    }
}