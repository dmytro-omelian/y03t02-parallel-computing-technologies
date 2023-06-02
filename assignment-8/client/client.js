const baseUrl = 'http://localhost:8080';

function createMatrix() {
    const filename = document.getElementById('matrixAFilename').value;
    const rows = document.getElementById('rows').value;
    const columns = document.getElementById('columns').value;

    console.time('createMatrix'); // Start measuring time

    fetch(`${baseUrl}/api/matrix/create?filename=${filename}&rows=${rows}&columns=${columns}`, {
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
            console.timeEnd('createMatrix'); // End measuring time
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML += `<p>${result}</p>`;
        })
        .catch(error => console.error('Помилка:', error));
}

function multiplyOnServer() {
    const matrixAFilename = document.getElementById('matrixAFilename').value;
    const matrixBFilename = document.getElementById('matrixBFilename').value;

    console.time('multiplyOnServer'); // Start measuring time

    fetch(`${baseUrl}/api/matrix/multiply-server?matrixAFilename=${matrixAFilename}&matrixBFilename=${matrixBFilename}`)
        .then(response => response.json())
        .then(result => {
            console.timeEnd('multiplyOnServer'); // End measuring time
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML += `<p>Результат множення: ${JSON.stringify(result)}</p>`;
        })
        .catch(error => console.error('Помилка:', error));
}

function multiplyFromClient() {
    const matrixAFile = document.getElementById('matrixA').files[0];
    const matrixBFile = document.getElementById('matrixB').files[0];

    console.time('multiplyFromClient'); // Start measuring time

    const formData = new FormData();
    formData.append('matrixA', matrixAFile);
    formData.append('matrixB', matrixBFile);

    fetch(`${baseUrl}/api/matrix/multiply-client`, {
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin': '*'
        },
        body: formData
    })
        .then(response => response.json())
        .then(result => {
            console.timeEnd('multiplyFromClient'); // End measuring time
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML += `<p>Результат множення: ${JSON.stringify(result)}</p>`;
        })
        .catch(error => console.error('Помилка:', error));
}
