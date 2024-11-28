document.getElementById('login-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('login-email').value
    const senha = document.getElementById('login-senha').value

    try{
        const response = await fetch('/auth/login',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email, senha})
        });

        if (response.ok){
            const data = await response.json();
            const token = data.token;
            localStorage.setItem('token', token);

        } else {
            const errorData = await response.json();
            alert('Login fracassado: ' + errorData.message);
        }
        } catch (error) {
            console.error('Erro durante login:', error);
            alert('Um erro ocorreu. Por favor tente novamente.');

    }
});

