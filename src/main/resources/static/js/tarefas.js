document.addEventListener('DOMContentLoaded', function () {
    var tituloTarefaInput = document.getElementById('titulo-tarefa');
    var descricaoTarefaInput = document.getElementById('descricao-tarefa');
    var prazoTarefaInput = document.getElementById('prazo-tarefa');
    var salvarButton = document.getElementById('salvar-tarefa-button');
    var tarefasPendentesDiv = document.getElementById('lista-tarefas-pendentes');
    var tarefasFinalizadasDiv = document.getElementById('lista-tarefas-finalizadas');
    salvarButton.addEventListener('click', function () {
        criarTarefa();
    });

    function formatarData(dateString){
        const date = new Date(dateString);
        const dia = date.getDate().toString().padStart(2, '0');
        const mes = (date.getMonth() + 1).toString().padStart(2, '0');
        const ano = date.getFullYear();
        return `${dia}/${mes}/${ano}`;
    }

    function criarTarefa() {
        var titulo = tituloTarefaInput.value;
        var descricao = descricaoTarefaInput.value;
        var prazo = formatarData(prazoTarefaInput.value);
        var hoje = new Date();

        hoje.setHours(0,0,0,0);

        if (prazoTarefaInput < hoje){
            alert("O prazo não pode ser menor que a data atual!");
            return;
        }
        var tarefa = document.createElement('div');

        tarefa.classList.add('tarefa-pendente');
        tarefa.innerHTML = `
        <h3>${titulo}</h3>
        <p>${descricao}</p>
        <p><strong>Prazo:</strong> ${prazo}</p>
        <button type="button" class="botao-finalizar-tarefa btn btn-success">Finalizar Tarefa</button>
        `;

        tarefa.querySelector('.botao-finalizar-tarefa').addEventListener('click', function() {
            finalizarTarefa(tarefa);
        });

        tarefasPendentesDiv.appendChild(tarefa);
        tituloTarefaInput.value = '';
        descricaoTarefaInput.value = '';
        prazoTarefaInput.value = '';
    }

    function finalizarTarefa(tarefa) {
        
        tarefasPendentesDiv.removeChild(tarefa);
        
        
        tarefa.classList.remove('tarefa-pendente'); 
        tarefa.classList.add('tarefa-finalizada'); 

        
        tarefasFinalizadasDiv.appendChild(tarefa);
        tarefa.querySelector('.botao-finalizar-tarefa').remove();
    }
});
