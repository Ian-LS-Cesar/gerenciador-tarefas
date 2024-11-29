const token = localStorage.getItem('token');

async function fetchGerenciador() {
  try {
      const response = await fetch('/gerenciador', {
          method: 'GET',
          headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}` // Include the token in the Authorization header
          }
      });

      if (response.ok) {
          const data = await response.json();
          // Handle the data received from the server, e.g., update the UI or store data
          console.log(data);
          // Optionally redirect or load the content as needed
      } else {
          const errorData = await response.json();
          alert('Error loading page: ' + errorData.message);
      }
  } catch (error) {
      console.error('Error during fetch:', error);
      alert('An error occurred. Please try again.');
  }
}

fetchGerenciador();