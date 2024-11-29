document.getElementById('tarefa-form').addEventListener('submit', async function (event) {
    event.preventDefault();


    const titulo = document.getElementById('titulo-tarefa').value
    const descricao = document.getElementById('descricao-tarefa').value
    const prazo = document.getElementById('prazo-tarefa').value;

    try{
        const response = await fetch('/tarefa/criar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
                },
            body: JSON.stringify({titulo, descricao, prazo})
       });

       if (response.ok){
        console.log('Tarefa criada com sucesso!')

       } else {
        const errorData = await response.json();
        alert ('Criação de tarefa fracassada: ' + errorData.message);

       }
       
    } catch (error) {
        console.error('Erro ao criar tarefa:', error);
        alert('Um erro ocorreu. Por favor tente novamente.');
    }
    
});

document.addEventListener("DOMContentLoaded", function() {
    function carregarTarefas(){
        fetch('/tarefa/listar')
        .then(response => {
            if (!response.ok){
                throw new Error('Não foi possível carregar as tarefas');
            }
            return response.json();
        })
        .then(tarefas => {
            exibirTarefas(tarefas);
        })
        .catch(error => {
            console.error('Erro ao carregar as tarefas:', error);
        });
    }

    function exibirTarefas(tarefasPorStatus){
        const listaTarefasPendentes = document.getElementById('lista-tarefas-pendentes');
        const listaTarefasFinalizadas = document.getElementById('lista-tarefas-finalizadas');

        listaTarefasPendentes.innerHTML = '';
        listaTarefasFinalizadas.innerHTML = '';

        if(tarefasPorStatus['Pendente']){
            tarefasPorStatus['Pendente'].forEach(tarefa => {
                const tarefaDiv = document.createElement('div');
                tarefaDiv.className = 'tarefa-pendente';
                tarefaDiv.innerHTML = `
                <h3>${tarefa.titulo}</h3>
                <p>${tarefa.descricao}</p>
                <p><strong>Prazo: </strong>${tarefa.prazo}</p>
                <button type="button" class="botao-finalizar-tarefa btn btn-success">Finalizar Tarefa</button>
                `;
                listaTarefasPendentes.appendChild(tarefaDiv);
            });
        }

        if(tarefasPorStatus['Finalizada']){
            tarefasPorStatus['Finalizada'].forEach(tarefa => {
                const tarefaDiv = document.createElement('div');
                tarefaDiv.className = 'tarefa-finalizada';
                tarefaDiv.innerHTML = `
                <h3>${tarefa.titulo}</h3>
                    <p>${tarefa.descricao}</p>
                    <p><strong>Prazo: </strong>${tarefa.prazo}</p>
                `;
                listaTarefasFinalizadas.appendChild(tarefaDiv);
            });
        }

    }
    carregarTarefas();
});
