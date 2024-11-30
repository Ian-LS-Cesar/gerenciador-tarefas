document.getElementById('tarefa-form').addEventListener('submit', async function (event) {
    event.preventDefault();


    const titulo = document.getElementById('titulo-tarefa').value
    const descricao = document.getElementById('descricao-tarefa').value
    const prazo = document.getElementById('prazo-tarefa').value;
    const tarefaDTO = {
        titulo,
        descricao,
        prazo
    };


    try{
        const response = await fetch('/tarefa/criar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
                },
            body: JSON.stringify(tarefaDTO)
       });

       if (response.ok){
        alert('Tarefa criada com sucesso!')

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
    
    function carregarTarefas() {
        fetch('/tarefa/listar')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Não foi possível carregar as tarefas');
                }
                return response.json();
            })
            .then(tarefas => {
                console.log(tarefas); // Check the structure of the received data
                exibirTarefas(tarefas);
            })
            .catch(error => {
                console.error('Erro ao carregar as tarefas:', error);
            });
    }

    function exibirTarefas(tarefasPorStatus){
        const listaTarefasPendentes = document.getElementById('lista-tarefas-pendentes');
        const listaTarefasFeedback = document.getElementById('lista-tarefas-feedback');
        const listaTarefasFinalizadas = document.getElementById('lista-tarefas-finalizadas');

        listaTarefasPendentes.innerHTML = '';
        listaTarefasFeedback.innerHTML = '';
        listaTarefasFinalizadas.innerHTML = '';

        if (tarefasPorStatus['Pendente']) {
            tarefasPorStatus['Pendente'].forEach(tarefa => {
                const tarefaDiv = document.createElement('div');
                tarefaDiv.className = 'tarefa-pendente';
                tarefaDiv.innerHTML = `
                    <h3>${tarefa.titulo}</h3>
                    <p>${tarefa.descricao}</p>
                    <p><strong>Prazo: </strong>${tarefa.prazo}</p>
                    <button type="button" class="botao-feedback-tarefa btn btn-warning" data-id="${tarefa.id_tarefa}" status="${tarefa.status_tarefa}">Enviar para Feedback</button>
                `;
                listaTarefasPendentes.appendChild(tarefaDiv);
                const feedbackButton = tarefaDiv.querySelector('.botao-feedback-tarefa');
                feedbackButton.addEventListener('click', async () => {
                    
                    listaTarefasPendentes.removeChild(tarefaDiv);
                    
                    
                    const feedbackDTO = {
                        id_tarefa: tarefa.id_tarefa,
                        novo_status_tarefa: 2
                    };
    
                    try {
                        const response = await fetch('/tarefa/status', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(feedbackDTO)
                        });
    
                        if (!response.ok) {
                            const errorData = await response.json();
                            alert('Erro ao mudar status da tarefa: ' + errorData.message);
                        } else {
                            console.log('Status da tarefa atualizado com sucesso!');
                        }
                    } catch (error) {
                        console.error('Erro ao atualizar status:', error);
                        alert('Um erro ocorreu. Por favor tente novamente.');
                    }
                });
            });
        }

        if(tarefasPorStatus['Esperando aprovação']){
            tarefasPorStatus['Esperando aprovação'].forEach(tarefa => {
                const tarefaDiv = document.createElement('div');
                tarefaDiv.className = 'tarefa-feedback';
                tarefaDiv.innerHTML = `
                <h3>${tarefa.titulo}</h3>
                <p>${tarefa.descricao}</p>
                <p><strong>Prazo: </strong>${tarefa.prazo}</p>
                <button type="button" class="botao-reprovar-tarefa btn btn-danger" data-id="${tarefa.id_tarefa} status="${tarefa.status_tarefa}">Retornar tarefa</button>
                <button type="button" class="botao-aprovar-tarefa btn btn-success" data-id="${tarefa.id_tarefa} status="${tarefa.status_tarefa}">Enviar para aprovação</button>
                `;
                listaTarefasFeedback.appendChild(tarefaDiv);
                const reprovarButton = tarefaDiv.querySelector('.botao-reprovar-tarefa');
                reprovarButton.addEventListener('click', async () => {
                    listaTarefasFeedback.removeChild(tarefaDiv);
                    
                    const feedbackDTO = {
                        id_tarefa: tarefa.id_tarefa,
                        novo_status_tarefa: 1 // Change to status ID 1 for returning the task
                    };
    
                    try {
                        const response = await fetch('/tarefa/status', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(feedbackDTO)
                        });
    
                        if (!response.ok) {
                            const errorData = await response.json();
                            alert('Erro ao mudar status da tarefa: ' + errorData.message);
                        } else {
                            console.log('Status da tarefa atualizado com sucesso!');
                        }
                    } catch (error) {
                        console.error('Erro ao atualizar status:', error);
                        alert('Um erro ocorreu. Por favor tente novamente.');
                    }
                });
    
                // Add event listener for aprovar button
                const aprovarButton = tarefaDiv.querySelector('.botao-aprovar-tarefa');
                aprovarButton.addEventListener('click', async () => {
                    listaTarefasFeedback.removeChild(tarefaDiv);
                    
                    const feedbackDTO = {
                        id_tarefa: tarefa.id_tarefa,
                        novo_status_tarefa: 3
                    };
    
                    try {
                        const response = await fetch('/tarefa/status', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(feedbackDTO)
                        });
    
                        if (!response.ok) {
                            const errorData = await response.json();
                            alert('Erro ao mudar status da tarefa: ' + errorData.message);
                        } else {
                            console.log('Status da tarefa atualizado com sucesso!');
                        }
                    } catch (error) {
                        console.error('Erro ao atualizar status:', error);
                        alert('Um erro ocorreu. Por favor tente novamente.');
                    }
                });
            });
        }

        if(tarefasPorStatus['Finalizada']){
            tarefasPorStatus['Finalizada'].forEach(tarefa => {
                const tarefaDiv = document.createElement('div');
                tarefaDiv.className = 'tarefa-finalizada';
                tarefaDiv.innerHTML = `
                <h3>${tarefa.titulo}</h3>
                    <p>${tarefa.descricao}</p>
                    <p><strong>Prazo: </strong>Tarefa Finalizada</p>
                `;
                listaTarefasFinalizadas.appendChild(tarefaDiv);
            });
        }
    }
    carregarTarefas();
});
