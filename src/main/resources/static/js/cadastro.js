document.getElementById('cadastro-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    const nome = document.getElementById('cadastro-usuario').value
    const email = document.getElementById('cadastro-email').value
    const senha = document.getElementById('cadastro-senha').value
    const funcao = document.getElementById('cadastro-funcao').value;

    try{
        const response = await fetch('/auth/cadastro',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({nome, email, senha, funcao})
        });

        if (response.ok) {
            console.log('Cadastro feito com sucesso!')
            window.location.href = '/';
        } else {
            const errorData = await response.json();
            alert('Cadastro fracassado: ' + errorData.message);
            
        }
    } catch (error){
        console.error('Erro durante cadastro: ', error);
        alert('Um erro ocorreu. Por favor tente novamente.');
    }
});