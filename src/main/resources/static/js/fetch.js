function sendRequest(method, url, body) {
    const headers = {
        'Content-Type': 'application/json',
    }

    return fetch(url, {
        method: method,
        body: JSON.stringify(body),
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response.text()
        }
    }).then((data) => {
        return data ? JSON.parse(data) : {}
    }).catch((error) => {
        console.log(error)
    })
}