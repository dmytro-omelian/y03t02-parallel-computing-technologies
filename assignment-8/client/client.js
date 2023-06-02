const baseUrl = 'http://localhost:8080';

function createMatrix() {
    const filename = document.getElementById('matrixAFilename').value;
    const rows = document.getElementById('rows').value;
    const columns = document.getElementById('columns').value;

    // Викликати серверний API для створення матриці
    fetch(`${baseUrl}/api/matrix/create?filename=${filename}&rows=${rows}&columns=${columns}`,{
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Сталася помилка');
            }
        })
        .then(result => {
            // Вивести результат на сторінці
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML += `<p>${result}</p>`;
        })
        .catch(error => console.error('Помилка:', error));
}

function multiplyOnServer() {
    const matrixAFilename = document.getElementById('matrixAFilename').value;
    const matrixBFilename = document.getElementById('matrixBFilename').value;

    // Викликати серверний API для множення матриць на сервері
    fetch(`${baseUrl}/api/matrix/multiply-server?matrixAFilename=${matrixAFilename}&matrixBFilename=${matrixBFilename}`)
        .then(response => response.json())
        .then(result => {
            // Вивести результат на сторінці
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML += `<p>Результат множення: ${JSON.stringify(result)}</p>`;
        })
        .catch(error => console.error('Помилка:', error));
}

function multiplyFromClient() {
    // Create FormData object and append the files to the body
    const matrixAFile = document.getElementById('matrixA').files[0];
    const matrixBFile = document.getElementById('matrixB').files[0];

    // Create FormData object and append the files to the body
    const formData = new FormData();
    formData.append('matrixA', matrixAFile);
    formData.append('matrixB', matrixBFile);
    console.log('going to send...')
    // Call the server-side API for matrix multiplication on the client
    fetch(`${baseUrl}/api/matrix/multiply-client`, {
        method: 'POST',
        headers: {
                'Access-Control-Allow-Origin': '*'
            },
        body: formData
    })
        .then(response => response.json())
        .then(result => {
            // Display the result on the page
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML += `<p>Результат множення: ${JSON.stringify(result)}</p>`;
        })
        .catch(error => console.error('Помилка:', error));
}

