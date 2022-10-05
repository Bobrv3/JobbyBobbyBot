const url = window.location.origin;

document.querySelectorAll('button.removeBtn')
    .forEach(el => {
        el.addEventListener("click", removeFilter)
    });

function removeFilter(event) {
    sendRequest('DELETE', `${url}/filter/${event.target.id}`, null)
        .then(() => location.reload())
}