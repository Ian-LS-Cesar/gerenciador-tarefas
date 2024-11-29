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
